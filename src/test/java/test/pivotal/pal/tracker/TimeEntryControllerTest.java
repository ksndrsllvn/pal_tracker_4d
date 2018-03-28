package test.pivotal.pal.tracker;

import io.pivotal.pal.tracker.TimeEntry;
import io.pivotal.pal.tracker.TimeEntryBuilder;
import io.pivotal.pal.tracker.TimeEntryController;
import io.pivotal.pal.tracker.TimeEntryRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class TimeEntryControllerTest {
    private TimeEntryRepository timeEntryRepository;
    private TimeEntryController controller;

    @Before
    public void setUp() throws Exception {
        timeEntryRepository = mock(TimeEntryRepository.class);
        controller = new TimeEntryController(timeEntryRepository);
    }

    @Test
    public void testCreate() throws Exception {
        TimeEntry timeEntryToCreate = new TimeEntryBuilder().projectId(123L).userId(456L).date(LocalDate.parse("2017-01-08")).hours(8).createTimeEntry();
        TimeEntry expectedResult = new TimeEntryBuilder().id(1L).projectId(123L).userId(456L).date(LocalDate.parse("2017-01-08")).hours(8).createTimeEntry();
        doReturn(expectedResult)
            .when(timeEntryRepository)
            .create(any(TimeEntry.class));


        ResponseEntity response = controller.create(timeEntryToCreate);


        verify(timeEntryRepository).create(timeEntryToCreate);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(expectedResult);
    }

    @Test
    public void testRead() throws Exception {
        TimeEntry expected = new TimeEntryBuilder().id(1L).projectId(123L).userId(456L).date(LocalDate.parse("2017-01-08")).hours(8).createTimeEntry();
        doReturn(expected)
            .when(timeEntryRepository)
            .find(1L);

        ResponseEntity<TimeEntry> response = controller.read(1L);

        verify(timeEntryRepository).find(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    public void testRead_NotFound() throws Exception {
        doReturn(null)
            .when(timeEntryRepository)
            .find(1L);

        ResponseEntity<TimeEntry> response = controller.read(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testList() throws Exception {
        List<TimeEntry> expected = asList(
                new TimeEntryBuilder().id(1L).projectId(123L).userId(456L).date(LocalDate.parse("2017-01-08")).hours(8).createTimeEntry(),
                new TimeEntryBuilder().id(2L).projectId(789L).userId(321L).date(LocalDate.parse("2017-01-07")).hours(4).createTimeEntry()
        );
        doReturn(expected).when(timeEntryRepository).list();

        ResponseEntity<List<TimeEntry>> response = controller.list();

        verify(timeEntryRepository).list();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    public void testUpdate() throws Exception {
        TimeEntry expected = new TimeEntryBuilder().id(1L).projectId(987L).userId(654L).date(LocalDate.parse("2017-01-07")).hours(4).createTimeEntry();
        doReturn(expected)
            .when(timeEntryRepository)
            .update(eq(1L), any(TimeEntry.class));

        ResponseEntity response = controller.update(1L, expected);

        verify(timeEntryRepository).update(1L, expected);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    public void testUpdate_NotFound() throws Exception {
        doReturn(null)
            .when(timeEntryRepository)
            .update(eq(1L), any(TimeEntry.class));

        ResponseEntity response = controller.update(1L, new TimeEntryBuilder().createTimeEntry());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testDelete() throws Exception {
        ResponseEntity<TimeEntry> response = controller.delete(1L);
        verify(timeEntryRepository).delete(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
