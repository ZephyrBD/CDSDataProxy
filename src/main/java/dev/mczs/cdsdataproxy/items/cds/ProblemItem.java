package dev.mczs.cdsdataproxy.items.cds;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProblemItem {
    @JsonProperty("id") private Long id;         // 题目ID
    @JsonProperty("label") private String label;    // 题目标签（A/B/C）
    @JsonProperty("name") private String name = "Unknown name";     // 题目名称
    @JsonProperty("ordinal") private int ordinal;
    @JsonProperty("color") private String color;
    @JsonProperty("rgb") private String rgb;
    @JsonProperty("time_limit") private int timeLimit;
}
