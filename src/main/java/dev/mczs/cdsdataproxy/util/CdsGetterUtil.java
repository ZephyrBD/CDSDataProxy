package dev.mczs.cdsdataproxy.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mczs.cdsdataproxy.items.cds.ProblemItem;
import dev.mczs.cdsdataproxy.items.cds.SubmissionItem;
import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static dev.mczs.cdsdataproxy.CdsDataProxyApplication.CONFIG;
import static dev.mczs.cdsdataproxy.CdsDataProxyApplication.REST_TEMPLATE;

public class CdsGetterUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static final Logger LOGGER = Logger.getLogger(CdsGetterUtil.class.getName());
    // 获取题目
    public static List<ProblemItem> getProblems(long cid) {
        List<ProblemItem> problemItemList = new ArrayList<>();
        try {
            String url = CONFIG.getCdsApiUrl() + ":" + CONFIG.getServerPort() + "/api/contests/" + cid + "/problems";

            ResponseEntity<String> response = REST_TEMPLATE.getForEntity(url, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                problemItemList = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
                LOGGER.info("gets " + problemItemList.size() + " PROBLEMS successfully! (Contest ID: " + cid + ")");
            } else {
                LOGGER.warning("gets PROBLEMS failed! (Contest ID: " + cid + ", Status: " + response.getStatusCode() + ")");
            }

        } catch (Exception e) {
            LOGGER.warning("gets PROBLEMS failed! Contest=" + cid + ", Error=" + e.getMessage());
        }
        return problemItemList;
    }

    // 获取提交请求
    public static List<SubmissionItem> getSubmissions(long cid) {
        List<SubmissionItem> submissionItemList = new ArrayList<>();
        try {
            String url = CONFIG.getCdsApiUrl() + ":" + CONFIG.getServerPort() + "/api/contests/" + cid + "/submissions";

            ResponseEntity<String> response = REST_TEMPLATE.getForEntity(url, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                submissionItemList = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
                LOGGER.info("gets " + submissionItemList.size() + " submissions successfully! (Contest ID: " + cid + ")");
            } else {
                LOGGER.warning("gets submissions failed! Contest=" + cid + ", Status=" + response.getStatusCode());
            }

        } catch (Exception e) {
            LOGGER.warning("gets submissions failed! Contest=" + cid + ", Error=" + e.getMessage());
        }
        return submissionItemList;
    }

    // 获取判题状态
    public static boolean getSubmissionStatus(long cid,long subId) {
        try {
            String url = CONFIG.getCdsApiUrl() + ":" + CONFIG.getServerPort() + "/api/contests/" + cid + "/judgements/" + subId;

            ResponseEntity<String> response = REST_TEMPLATE.getForEntity(url, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JudgementItem judgement = objectMapper.readValue(response.getBody(), JudgementItem.class);

                return "AC".equals(judgement.getJudgement_type_id());
            } else {
                LOGGER.warning("gets judgement failed! Submission=" + subId + ", Status=" + response.getStatusCode());
            }
        } catch (Exception e) {
            LOGGER.warning("gets submission " + subId + " status failed! Error=" + e.getMessage());
        }
        return false;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class JudgementItem {
        private String judgement_type_id;
    }

}
