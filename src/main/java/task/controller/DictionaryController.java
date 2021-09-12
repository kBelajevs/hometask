package task.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import task.service.DictionaryService;

import java.util.List;
import java.util.Set;

@RestController()
@RequestMapping(path = "/dictionary", produces = MediaType.APPLICATION_JSON_VALUE)
public class DictionaryController {

    private final DictionaryService service;

    @Autowired
    public DictionaryController(DictionaryService service) {
        this.service = service;
    }

    @GetMapping("/containsV1")
    public List<String> contains(@RequestParam String line, @RequestParam @Nullable boolean ignoreCase) {
        List<String> foundWords;
        if (ignoreCase) {
            foundWords = service.containsWords(line.toLowerCase());
        } else {
            foundWords = service.containsWords(line);
        }
        return foundWords;
    }

    @GetMapping("/containsV2")
    public Set<String> containsByAhoCorasick(@RequestParam String line, @RequestParam @Nullable boolean ignoreCase) {
        Set<String> foundWords;
        if (ignoreCase) {
            foundWords = service.containsWordsAhoCorasickIgnoreCase(line);
        } else {
            foundWords = service.containsWordsAhoCorasick(line);
        }
        return foundWords;
    }
}
