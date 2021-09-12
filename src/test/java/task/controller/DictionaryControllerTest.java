package task.controller;

import org.junit.Test;
import task.service.DictionaryService;

import java.util.Collections;

import static org.mockito.Mockito.*;

public class DictionaryControllerTest {

    private static final String INPUT = "abc";
    private static final String INPUT_CASE_SENSITIVE = "aBC";
    private static final boolean IGNORE_CASE = true;
    private static final boolean CASE_SENSITIVE = false;
    DictionaryService service = mock(DictionaryService.class);
    DictionaryController controller = new DictionaryController(service);

    @Test
    public void shouldTransformInputToLowerCase() {
        when(service.containsWords(INPUT)).thenReturn(Collections.emptyList());

        controller.contains(INPUT_CASE_SENSITIVE, IGNORE_CASE);

        verify(service).containsWords(INPUT);
    }

    @Test
    public void shouldCallAsItIs() {
        when(service.containsWords(INPUT_CASE_SENSITIVE)).thenReturn(Collections.emptyList());

        controller.contains(INPUT_CASE_SENSITIVE, CASE_SENSITIVE);

        verify(service).containsWords(INPUT_CASE_SENSITIVE);
    }

    @Test
    public void shouldCallAhoCorasickCaseSensitive() {
        when(service.containsWordsAhoCorasick(INPUT)).thenReturn(Collections.emptySet());

        controller.containsByAhoCorasick(INPUT, CASE_SENSITIVE);

        verify(service).containsWordsAhoCorasick(INPUT);
    }

    @Test
    public void shouldCallAhoCorasickIgnoreCase() {
        when(service.containsWordsAhoCorasickIgnoreCase(INPUT)).thenReturn(Collections.emptySet());

        controller.containsByAhoCorasick(INPUT, IGNORE_CASE);

        verify(service).containsWordsAhoCorasickIgnoreCase(INPUT);
    }


}
