package dev.mczs.cdsdataproxy.items.cds;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.mczs.cdsdataproxy.items.TeamItem;
import lombok.Data;

// 2. 单个气球数据（核心实体，和 Python 返回字段完全一致）
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BalloonItem {
    @JsonProperty("balloonid") private Long balloonId;       // 气球ID（对应提交记录ID）
    private String time;          // UNIX时间戳
    private String problem;       // 题目标签（如 A、B）
    @JsonProperty("contestproblem") private ContestProblemItem contestProblem; // 题目详情子对象
    private String team;          // 队伍名称（Team + 队ID）
    @JsonProperty("teamid") private Long teamId;          // 队伍ID
    private String location = ""; // 默认为空
    private String affiliation = "";
    @JsonProperty("affiliationid") private Integer affiliationId = 0;
    private String category = "";
    @JsonProperty("categoryid") private Integer categoryId = 0;
    private Boolean done = false;

    public BalloonItem(Long balloonId,String problemLabel,String unixTime,Long teamID,ContestProblemItem contestProblemItem){
        this.balloonId=balloonId;
        this.problem=problemLabel;
        this.time=unixTime;
        this.teamId=teamID;
        this.team = "Team " +  teamID;
        this.contestProblem = contestProblemItem;
    }
    public void getSettingsFromTeams(TeamItem teamItem){
        if(teamItem==null){
            return;
        }
        this.team=teamItem.getTeam();
        this.setLocation(teamItem.getLocation());
        this.setAffiliation(teamItem.getOrganizationName());
        this.setAffiliationId(teamItem.getOrganizationId());
        this.setCategory(teamItem.getGroupName());
        this.setCategoryId(teamItem.getGroupId());
    }
}
