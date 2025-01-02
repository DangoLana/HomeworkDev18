package com.homework.springboot_first_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/list")
    public String getNotesList(Model model) {
        List<Note> notes = noteService.listAll();
        model.addAttribute("notes", notes);
        return "note-list";
    }

    @PostMapping("/delete")
    public String deleteNote(@RequestParam Long id) {
        try {
            noteService.deleteById(id);
        } catch (NoSuchElementException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return "redirect:/note/list";
    }

    @GetMapping("/edit")
    public String getEditPage(@RequestParam Long id, Model model) {
        Note note = noteService.getById(id);
        model.addAttribute("note", note);
        return "note-edit";
    }

    @PostMapping("/edit")
    public String editNote(@RequestParam Long id, @RequestParam String title, @RequestParam String content) {
        try {
            Note existingNote = noteService.getById(id);
            existingNote.setTitle(title);
            existingNote.setContent(content);
            noteService.update(existingNote);
        } catch (NoSuchElementException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return "redirect:/note/list";
    }

    @GetMapping("/create")
    public String getCreatePage() {
        return "note-create";
    }

    @PostMapping("/create")
    public String createNote(@RequestParam String title, @RequestParam String content) {
        Note newNote = new Note();
        newNote.setTitle(title);
        newNote.setContent(content);
        noteService.add(newNote);
        return "redirect:/note/list";
    }
}