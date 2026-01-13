package dev.mczs.cdsdataproxy.items.cds;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// 4. CDS 提交记录实体（用于接收 CDS 返回的 submissions 数据）
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubmissionItem {
    @JsonProperty("id") private Long id;         // 提交ID（作为气球ID）
    @JsonProperty("problem_id") private Long problemId; // 题目ID
    @JsonProperty("team_id") private Long teamId;    // 队伍ID
    @JsonProperty("time") private String time;     // ISO格式时间
}
