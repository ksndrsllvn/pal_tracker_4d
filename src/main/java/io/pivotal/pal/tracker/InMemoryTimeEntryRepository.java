package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private HashMap<Long, TimeEntry> entryHashMap = new HashMap<Long, TimeEntry>();
    private int nextId = 1;

    public TimeEntry find(long id) {
        return entryHashMap.get(id);
    }

    public TimeEntry create(TimeEntry timeEntry) {
        TimeEntry newObj = TimeEntryBuilder.builder(timeEntry, nextId).createTimeEntry();
        nextId++;
        entryHashMap.put(newObj.getId(), newObj);
        return newObj;
    }

    public List<TimeEntry> list() {
        List<TimeEntry> timeEntryList = new ArrayList<TimeEntry>();
        timeEntryList.addAll(entryHashMap.values());
        return timeEntryList;
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        TimeEntry timeEntry2 = TimeEntryBuilder.builder(timeEntry).id(id).createTimeEntry();
        entryHashMap.replace(id, timeEntry2);
        return timeEntry2;
    }

    public void delete(long id) {
        entryHashMap.remove(id);
    }
}
