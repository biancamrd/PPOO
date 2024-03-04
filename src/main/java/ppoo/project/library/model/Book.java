package ppoo.project.library.model;

import ppoo.project.library.data.DataLoader;
import ppoo.project.library.enums.Collection;

import java.util.List;

import static ppoo.project.library.menu.Menu.BOOKS_JSON;

public class Book {
    private String title;
    private Author author;
    private Collection collection;
    private long publicationYear;
    private int availableNumber;

    private static List<Book> books = DataLoader.readBooksFromJSONFile(BOOKS_JSON);

    public Book(String title, Author author, Collection collection, long publicationYear, int availableNumber) {
        this.title = title;
        this.author = author;
        this.collection = collection;
        this.publicationYear = publicationYear;
        this.availableNumber = availableNumber;
    }

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public long getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(long publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getAvailableNumber() {
        return availableNumber;
    }

    public void setAvailableNumber(int availableNumber) {
        this.availableNumber = availableNumber;
    }

    public void decrementAvailableNo() {
        if (availableNumber > 0) {
            availableNumber--;
        } else {
            System.out.println("Cartea nu este disponibila pentru imprumut.");
        }
    }

    public void incrementAvailableNo() {
        availableNumber++;
    }



    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author=" + author +
                ", collection=" + collection +
                ", publicationYear=" + publicationYear +
                '}';
    }
}
