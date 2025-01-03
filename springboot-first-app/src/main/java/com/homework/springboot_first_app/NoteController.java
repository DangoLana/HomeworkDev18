package com.homework.springboot_first_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.listAll();
    }

    @PostMapping
    public Note createNote(@Valid @RequestBody Note note, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid note data");
        }
        return noteService.add(note);
    }

    @PutMapping("/{id}")
    public Note updateNote(@PathVariable Long id, @Valid @RequestBody Note note) {
        note.setId(id);
        noteService.update(note);
        return note;
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Long id) {
        noteService.deleteById(id);
    }
}
