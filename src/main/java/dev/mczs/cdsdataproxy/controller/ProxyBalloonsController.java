package dev.mczs.cdsdataproxy.controller;

import dev.mczs.cdsdataproxy.items.TeamItem;
import dev.mczs.cdsdataproxy.items.cds.*;
import dev.mczs.cdsdataproxy.util.CdsGetterUtil;
import dev.mczs.cdsdataproxy.util.TimeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;

import static dev.mczs.cdsdataproxy.CdsDataProxyApplication.CONFIG;
import static dev.mczs.cdsdataproxy.util.FilesUtil.getTeamsMapFromExcel;

@RestController
@RequestMapping("/api/v4/contests/{cid}")
public class ProxyBalloonsController {
    private static final Long CONTEST_ID = CONFIG.getContestId();
    private static final Map<Long, TeamItem> TEAMS = getTeamsMapFromExcel();
    public static final List<ProblemItem> PROBLEMS = CdsGetterUtil.getProblems(CONTEST_ID);

    private static final Logger LOGGER = Logger.getLogger(ProxyBalloonsController.class.getName());
    private final Set<Long> forwardedBalloons = new CopyOnWriteArraySet<>();
    private final Map<String,Boolean> teamAcMap = new ConcurrentHashMap<>(); // 队-题AC记录（key: teamId_problemId）


    @GetMapping("/balloons")
    public List<BalloonItem> getBalloons(@PathVariable Long cid) {
        List<BalloonItem> balloonList = new ArrayList<>();

        List<SubmissionItem> submissions = CdsGetterUtil.getSubmissions(cid);
        Map<Long,ProblemItem> problemMap = new HashMap<>();
        if(PROBLEMS.isEmpty() || TEAMS.isEmpty()){
            LOGGER.warning("No problems or team found!");
            System.exit(0);
        }
        for(var problem : PROBLEMS){
            problemMap.put(problem.getId(), problem);
        }
        for(var submission : submissions){
            Long submissionId = submission.getId();
            Long teamId = submission.getTeamId();
            Long problemId = submission.getProblemId();

            if(!CdsGetterUtil.getSubmissionStatus(CONTEST_ID,submissionId)) {
                continue;
            }

            String acKey = teamId + "_" +problemId;
            if(teamAcMap.containsKey(acKey)){
                continue;
            }
            teamAcMap.put(acKey,true);

            if(forwardedBalloons.contains(submissionId)){
                continue;
            }
            forwardedBalloons.add(submissionId);

            ProblemItem targetItem = problemMap.getOrDefault(problemId,new ProblemItem());
            ContestProblemItem contestProblemItem = new ContestProblemItem(targetItem);
            BalloonItem balloon = new BalloonItem(submissionId,targetItem.getLabel(),TimeUtil.isoToUnix(submission.getTime()),teamId,contestProblemItem);
            balloon.getSettingsFromTeams(TEAMS.get(teamId));
            balloonList.add(balloon);
        }
        return balloonList;
    }
}
