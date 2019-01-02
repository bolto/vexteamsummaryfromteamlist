package com.ixeron.vex;

public class EventTeam {
    private final String id;
    private final String name;
    private final String organization;
    private final String location;
    private final String link;

    public EventTeam(String id, String name, String organization, String location, String link) {
        this.id = id;
        this.name = name;
        this.organization = organization;
        this.location = location;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOrganization() {
        return organization;
    }

    public String getLocation() {
        return location;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "EventTeam{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", organization='" + organization + '\'' +
                ", location='" + location + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
