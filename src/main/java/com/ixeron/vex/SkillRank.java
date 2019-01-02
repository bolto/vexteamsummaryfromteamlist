package com.ixeron.vex;

public class SkillRank {
    private int rank;
    Team team;
    Event event;
    Scores scores;
    private boolean eligible;


    // Getter Methods

    public float getRank() {
        return rank;
    }

    public Team getTeam() {
        return team;
    }

    public Event getEvent() {
        return event;
    }

    public Scores getScores() {
        return scores;
    }

    public boolean getEligible() {
        return eligible;
    }

    // Setter Methods

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setTeam(Team teamObject) {
        this.team = teamObject;
    }

    public void setEvent(Event eventObject) {
        this.event = eventObject;
    }

    public void setScores(Scores scoresObject) {
        this.scores = scoresObject;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }
}