package com.example;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping
    public Iterable<Todo> findAll() {
        return todoRepository.findAll();
    }

    @GetMapping("/sorted")
    public List<Todo> getAllSorted() {
        return todoRepository.findAllByOrderByOrder();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Todo> update(@RequestBody Todo todo, @PathVariable("id") Long id) {
        todoRepository.save(todo);
        return ResponseEntity.ok(todo);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Todo> createNew(@RequestBody Todo todo) {
        todoRepository.save(todo);
        return new ResponseEntity<>( todo, HttpStatus.CREATED );
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        todoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
