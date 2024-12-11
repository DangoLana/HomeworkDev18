package com.homework.springboot_first_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final List<Note> notes = new ArrayList<>();

    public NoteController() {
        notes.add(new Note(1L, "First Note", "Content of the first note"));
        notes.add(new Note(2L, "Second Note", "Content of the second note"));
    }

    @GetMapping("/list")
    public String getNotesList(Model model) {
        model.addAttribute("notes", notes);
        return "note-list";
    }

    @PostMapping("/delete")
    public String deleteNote(@RequestParam Long id) {
        notes.removeIf(note -> note.getId().equals(id));
        return "redirect:/note/list";
    }

    @GetMapping("/edit")
    public String getEditPage(@RequestParam Long id, Model model) {
        Optional<Note> note = notes.stream().filter(n -> n.getId().equals(id)).findFirst();
        model.addAttribute("note", note.orElse(null));
        return "note-edit";
    }

    @PostMapping("/edit")
    public String editNote(@RequestParam Long id, @RequestParam String title, @RequestParam String content) {
        for (Note note : notes) {
            if (note.getId().equals(id)) {
                note.setTitle(title);
                note.setContent(content);
            }
        }
        return "redirect:/note/list";
    }
}
