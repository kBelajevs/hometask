package task.service;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DictionaryService {

    private static final List<String> DICTIONARY = fillDictionary();
    private static final Trie TRIE = Trie.builder().addKeywords(DICTIONARY).build();
    private static final Trie TRIE_IGNORE_CASE = Trie.builder().ignoreCase().addKeywords(DICTIONARY).build();

    private static List<String> fillDictionary() {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:bigDictionary.txt")))) {

            return reader.lines().map(String::trim).map(String::toLowerCase).collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Dictionary file not found", e);
        }
    }

    public List<String> containsWords(String inputString) {
        return DICTIONARY.stream().filter(it -> it.length() <= inputString.length()).filter(inputString::contains).collect(Collectors.toList());
    }

    public Set<String> containsWordsAhoCorasick(String inputString) {
        Collection<Emit> emits = TRIE.parseText(inputString);
        return mapEmitsToStringList(emits);
    }

    public Set<String> containsWordsAhoCorasickIgnoreCase(String inputString) {
        Collection<Emit> emits = TRIE_IGNORE_CASE.parseText(inputString);
        return mapEmitsToStringList(emits);
    }

    private Set<String> mapEmitsToStringList(Collection<Emit> emits) {
        return emits.stream().map(Emit::getKeyword).collect(Collectors.toSet());
    }
}
