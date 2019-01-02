package com.ixeron.vex;

public class Scores {
    private float score;
    private float programming;
    private float driver;
    private float maxProgramming;
    private float maxDriver;


    // Getter Methods

    public float getScore() {
        return score;
    }

    public float getProgramming() {
        return programming;
    }

    public float getDriver() {
        return driver;
    }

    public float getMaxProgramming() {
        return maxProgramming;
    }

    public float getMaxDriver() {
        return maxDriver;
    }

    // Setter Methods

    public void setScore(float score) {
        this.score = score;
    }

    public void setProgramming(float programming) {
        this.programming = programming;
    }

    public void setDriver(float driver) {
        this.driver = driver;
    }

    public void setMaxProgramming(float maxProgramming) {
        this.maxProgramming = maxProgramming;
    }

    public void setMaxDriver(float maxDriver) {
        this.maxDriver = maxDriver;
    }

    @Override
    public String toString() {
        return "Scores{" +
                "score=" + score +
                ", programming=" + programming +
                ", driver=" + driver +
                ", maxProgramming=" + maxProgramming +
                ", maxDriver=" + maxDriver +
                '}';
    }
}
