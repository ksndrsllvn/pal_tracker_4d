package test.pivotal.pal.tracker;

import io.pivotal.pal.tracker.InMemoryTimeEntryRepository;
import io.pivotal.pal.tracker.TimeEntry;
import io.pivotal.pal.tracker.TimeEntryBuilder;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryTimeEntryRepositoryTest {
    @Test
    public void create() throws Exception {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();
        TimeEntry createdTimeEntry = repo.create(new TimeEntryBuilder().projectId(123L).userId(456L).date(LocalDate.parse("2017-01-08")).hours(8).createTimeEntry());

        TimeEntry expected = new TimeEntryBuilder().id(1L).projectId(123L).userId(456L).date(LocalDate.parse("2017-01-08")).hours(8).createTimeEntry();
        assertThat(createdTimeEntry).isEqualTo(expected);

        TimeEntry readEntry = repo.find(createdTimeEntry.getId());
        assertThat(readEntry).isEqualTo(expected);
    }

    @Test
    public void find() throws Exception {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();
        repo.create(new TimeEntryBuilder().projectId(123L).userId(456L).date(LocalDate.parse("2017-01-08")).hours(8).createTimeEntry());

        TimeEntry expected = new TimeEntryBuilder().id(1L).projectId(123L).userId(456L).date(LocalDate.parse("2017-01-08")).hours(8).createTimeEntry();
        TimeEntry readEntry = repo.find(1L);
        assertThat(readEntry).isEqualTo(expected);
    }

    @Test
    public void list() throws Exception {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();
        repo.create(new TimeEntryBuilder().projectId(123L).userId(456L).date(LocalDate.parse("2017-01-08")).hours(8).createTimeEntry());
        repo.create(new TimeEntryBuilder().projectId(789L).userId(654L).date(LocalDate.parse("2017-01-07")).hours(4).createTimeEntry());

        List<TimeEntry> expected = asList(
                new TimeEntryBuilder().id(1L).projectId(123L).userId(456L).date(LocalDate.parse("2017-01-08")).hours(8).createTimeEntry(),
                new TimeEntryBuilder().id(2L).projectId(789L).userId(654L).date(LocalDate.parse("2017-01-07")).hours(4).createTimeEntry()
        );
        assertThat(repo.list()).isEqualTo(expected);
    }

    @Test
    public void update() throws Exception {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();
        TimeEntry created = repo.create(new TimeEntryBuilder().projectId(123L).userId(456L).date(LocalDate.parse("2017-01-08")).hours(8).createTimeEntry());

        TimeEntry updatedEntry = repo.update(
                created.getId(),
                new TimeEntryBuilder().projectId(321L).userId(654L).date(LocalDate.parse("2017-01-09")).hours(5).createTimeEntry());

        TimeEntry expected = new TimeEntryBuilder().id(created.getId()).projectId(321L).userId(654L).date(LocalDate.parse("2017-01-09")).hours(5).createTimeEntry();
        assertThat(updatedEntry).isEqualTo(expected);
        assertThat(repo.find(created.getId())).isEqualTo(expected);
    }

    @Test
    public void delete() throws Exception {
        InMemoryTimeEntryRepository repo = new InMemoryTimeEntryRepository();
        TimeEntry created = repo.create(new TimeEntryBuilder().projectId(123L).userId(456L).date(LocalDate.parse("2017-01-08")).hours(8).createTimeEntry());

        repo.delete(created.getId());
        assertThat(repo.list()).isEmpty();
    }
}
