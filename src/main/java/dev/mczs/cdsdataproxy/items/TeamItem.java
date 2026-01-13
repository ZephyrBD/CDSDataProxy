package dev.mczs.cdsdataproxy.items;

import lombok.Data;

@Data
public class TeamItem {
    private Long id;
    private String team;
    private String location;
    private int organizationId;
    private String organizationName;
    private int groupId;
    private String groupName;

    public TeamItem(Long id, String team, String location, int organizationId, String organizationName, int groupId, String groupName) {
        this.id = id;
        this.team = team;
        this.location = location;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.groupId = groupId;
        this.groupName = groupName;
    }
}
