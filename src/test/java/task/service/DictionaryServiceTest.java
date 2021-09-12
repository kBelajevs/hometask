package task.service;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class DictionaryServiceTest {

    private static final String INPUT_CONTAINS_WORDS = "catababababacatalog";
    private static final String INPUT_CONTAINS_WORDS_CASE_SENSITIVE = "catababababacatalogDOG";
    private static final String INPUT_DOES_NOT_CONTAIN_WORDS = "aaaaaaaaaaaaaaaaa";
    private static final List<String> EXPECTED_WORDS_TO_BE_FOUND = Arrays.asList("cat", "catalog", "log");
    private final DictionaryService service = new DictionaryService();


    @Test
    public void shouldReturnFoundWords() {
        List<String> foundWords = service.containsWords(INPUT_CONTAINS_WORDS);

        assertNotNull(foundWords);
        assertEquals(EXPECTED_WORDS_TO_BE_FOUND, foundWords);

    }

    @Test
    public void shouldReturnEmptyListIfNoWordsFound() {
        List<String> foundWords = service.containsWords(INPUT_DOES_NOT_CONTAIN_WORDS);

        assertNotNull(foundWords);
        assertTrue(foundWords.isEmpty());
    }

    @Test
    public void shouldReturnFoundWordsByAhoCorasickAlgCaseSensitive() {
        Set<String> foundWords = service.containsWordsAhoCorasick(INPUT_CONTAINS_WORDS_CASE_SENSITIVE);

        assertNotNull(foundWords);
        assertEquals(EXPECTED_WORDS_TO_BE_FOUND.size(), foundWords.size());
        assertTrue(EXPECTED_WORDS_TO_BE_FOUND.containsAll(foundWords));
    }

    @Test
    public void shouldReturnFoundWordsByAhoCorasickAlgIgnoreCase() {
        Set<String> foundWords = service.containsWordsAhoCorasickIgnoreCase(INPUT_CONTAINS_WORDS_CASE_SENSITIVE);
        Set<String> expectedWordsToBeFound = new HashSet<>(EXPECTED_WORDS_TO_BE_FOUND);
        expectedWordsToBeFound.add("dog");

        assertNotNull(foundWords);
        assertEquals(expectedWordsToBeFound, foundWords);
    }


    @Test
    public void shouldReturnEmptyListIfNoWordsFoundAhoCorasick() {
        Set<String> foundWords = service.containsWordsAhoCorasick(INPUT_DOES_NOT_CONTAIN_WORDS);

        assertNotNull(foundWords);
        assertTrue(foundWords.isEmpty());
    }


}
