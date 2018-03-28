package io.pivotal.pal.tracker;

import java.time.LocalDate;

public class TimeEntryBuilder {
    private long projectId;
    private long userId;
    private LocalDate date;
    private int hours;
    private long id = 1;
    public static TimeEntryBuilder builder(){
        return new TimeEntryBuilder();
    }
    public static TimeEntryBuilder builder(TimeEntry copyFrom){
        return  builder()
                .id(copyFrom.getId())
                .projectId(copyFrom.getProjectId())
                .userId(copyFrom.getUserId())
                .date(copyFrom.getDate())
                .hours(copyFrom.getHours());

    }
   public static TimeEntryBuilder builder(TimeEntry copyFrom, long id){
        return builder(copyFrom)
                .id(id);
    }
    public TimeEntryBuilder projectId(long projectId) {
        this.projectId = projectId;
        return this;
    }

    public TimeEntryBuilder userId(long userId) {
        this.userId = userId;
        return this;
    }

    public TimeEntryBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public TimeEntryBuilder hours(int hours) {
        this.hours = hours;
        return this;
    }

    public TimeEntryBuilder id(long id) {
        this.id = id;
        return this;
    }

    public TimeEntry createTimeEntry() {
        return new TimeEntry(id, projectId, userId, date, hours);
    }
}