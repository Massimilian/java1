package com.example.dictionario.service;

import com.example.dictionario.entity.Words;
import com.example.dictionario.repository.WordsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class WordsService {
    private final WordsRepository repo;

    public WordsService(WordsRepository repo) {
        this.repo = repo;
    }

    public Words findById(int id) {
        return repo.findById(id).get();
    }

    public Words findByEsWord(String word) {
        return repo.findByEsIs(word);
    }

    public Words findByRuWord(Words word) {
        return repo.findByRuIs(word.getRu());
    }
}
