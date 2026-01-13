package dev.mczs.cdsdataproxy.items.cds;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContestProblemItem extends ProblemItem {
    @JsonProperty("short_name") private String shortName;
    private List<Object> statement = new ArrayList<>(); // 空列表
    private List<Object> attachments = new ArrayList<>(); // 空列表

    public ContestProblemItem(ProblemItem problem) {
        this.setId(problem.getId());
        this.setLabel(problem.getLabel());
        this.setName(problem.getName());
        this.setOrdinal(problem.getOrdinal());
        this.setColor(problem.getColor());
        this.setRgb(problem.getRgb());
        this.setTimeLimit(problem.getTimeLimit());
        this.shortName = problem.getLabel();
    }
}
