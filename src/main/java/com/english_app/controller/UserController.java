package com.english_app.controller;

import com.english_app.service.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private VocabularyService vocabularyService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listVocabulary", this.vocabularyService.getVocabularies());
        return "user/index";
    }

}
