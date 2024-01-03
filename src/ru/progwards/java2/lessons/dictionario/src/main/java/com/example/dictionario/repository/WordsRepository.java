package com.example.dictionario.repository;

import com.example.dictionario.entity.Words;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordsRepository extends JpaRepository<Words, Integer> {
    Words findByEsIs(String word);

    Words findByRuIs(String ru);
}
