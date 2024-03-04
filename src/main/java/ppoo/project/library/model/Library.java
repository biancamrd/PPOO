package ppoo.project.library.model;

import ppoo.project.library.data.DataLoader;
import ppoo.project.library.data.DataSaver;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private String name;
    private List<Librarian> librarians;

    public Library() {
    }

    public Library(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Librarian> getLibrarians() {
        return librarians;
    }

    public void setLibrarians(List<Librarian> librarians) {
        this.librarians = librarians;
    }

    public void addLibrarian(Librarian librarian){
        if (this.librarians == null) {
            this.librarians = new ArrayList<>();
        }
        this.librarians.add(librarian);
    }

    public Librarian searchLibrarian(String email, String password){
        for(Librarian librarian : librarians){
            if(librarian.getEmail().equals(email) && librarian.getPassword().equals(password)){
                return librarian;
            }
        }
        return null;
    }

    public void loadLibrariansFromFile(String fileName) {
        librarians = DataLoader.loadLibrariansFromFile(fileName);
    }

    public void saveLibrariansToFile(String filename) {
        DataSaver.saveLibrariansToFile(filename, librarians);
    }
}
