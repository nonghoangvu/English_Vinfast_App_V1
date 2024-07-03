package com.english_app.controller;

import com.english_app.dto.CustomUser;
import com.english_app.entity.Vocabulary;
import com.english_app.service.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private VocabularyService vocabularyService;

    @ModelAttribute("vocabulary")
    public Vocabulary getVocabulary() {
        return new Vocabulary();
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("message", "Role Admin");
        return "admin/index";
    }

    @PostMapping("/save")
    String store(@Validated @ModelAttribute("vocabulary") Vocabulary vocabulary, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("vocabulary", vocabulary);
            return "admin/index";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUser customUser = (CustomUser) auth.getPrincipal();
        vocabulary.setUser(customUser.getUser());
        this.vocabularyService.save(vocabulary);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        this.vocabularyService.deleteById(id);
        return "redirect:/user";
    }
}
