package com.homework.springboot_first_app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.NoSuchElementException;

@SpringBootApplication
public class Main implements CommandLineRunner {

	private final NoteService noteService;

	public Main(NoteService noteService) {
		this.noteService = noteService;
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Note note1 = new Note();
		note1.setTitle("First Note");
		note1.setContent("This is the content of the first note.");
		Note addedNote1 = noteService.add(note1);
		System.out.println("Added Note 1: " + addedNote1);

		Note note2 = new Note();
		note2.setTitle("Second Note");
		note2.setContent("This is the content of the second note.");
		Note addedNote2 = noteService.add(note2);
		System.out.println("Added Note 2: " + addedNote2);

		List<Note> allNotes = noteService.listAll();
		System.out.println("\nAll Notes:");
		allNotes.forEach(note -> System.out.println(note));

		addedNote1.setTitle("Updated First Note");
		noteService.update(addedNote1);
		System.out.println("\nUpdated Note 1: " + addedNote1);

		noteService.deleteById(addedNote2.getId());
		System.out.println("\nDeleted Note 2.");

		try {
			noteService.getById(addedNote2.getId());
		} catch (NoSuchElementException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
