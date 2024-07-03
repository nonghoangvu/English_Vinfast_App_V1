package com.english_app.service;

import com.english_app.entity.Vocabulary;
import com.english_app.repository.VocabularyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VocabularyService {
    @Autowired
    private VocabularyRepository vocabularyRepository;

    public List<Vocabulary> getVocabularies() {
        return vocabularyRepository.findAll();
    }

    public void save(Vocabulary vocabulary) {
        vocabularyRepository.save(vocabulary);
    }

    public void deleteById(long id) {
        vocabularyRepository.deleteById(id);
    }
}
