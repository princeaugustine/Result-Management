package com.udom.school.model;

import java.io.Serializable;
import java.util.Objects;

public class ResultId implements Serializable {
    private String candidate;
    private String date;

    public ResultId() {
    }

    public ResultId(String candidate, String date) {
        this.candidate = candidate;
        this.date = date;
    }

    // Getters and setters
    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Override equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultId resultId = (ResultId) o;
        return Objects.equals(candidate, resultId.candidate) && Objects.equals(date, resultId.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidate, date);
    }
}
