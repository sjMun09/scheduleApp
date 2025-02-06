package com.example.scheduleApp.controller;

import com.example.scheduleApp.entity.Author;
import com.example.scheduleApp.exception.DuplicateEmailException;
import com.example.scheduleApp.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorRepository authorRepository;

    @PostMapping
       public Author createAuthor(@RequestBody Author author) {
           try {
               return authorRepository.save(author);
           } catch (DataIntegrityViolationException e) {
               throw new DuplicateEmailException("이미 사용 중인 이메일입니다. " + author.getEmail());
           }
       }

       @GetMapping
       public List<Author> getAllAuthors() {
           return authorRepository.findAll();
       }
}
