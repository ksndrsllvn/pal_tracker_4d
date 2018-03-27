package io.pivotal.pal.tracker;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class InMemoryTimeEntryRepository {
    public TimeEntry find(long id) {
        return null;
    }

    public TimeEntry create(TimeEntry timeEntry) {
        return null;
    }

    public List<TimeEntry> list() {
        return null;
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        return null;
    }

    public void delete(long id) {
        throw new NotImplementedException();
    }
}
