package ppoo.project.library.helpers;

import ppoo.project.library.enums.Collection;
import ppoo.project.library.model.Author;
import ppoo.project.library.model.Book;

import java.util.*;

import static ppoo.project.library.menu.Menu.books;
import static ppoo.project.library.menu.Menu.collectionManager;

public class BookHelper {
    private Map<Collection, List<Book>> collectionMap;

    public BookHelper() {
        collectionMap = new HashMap<>();
    }

    public void addBook(Book book) {
        Collection collection = book.getCollection();
        if (!collectionMap.containsKey(collection)) {
            collectionMap.put(collection, new ArrayList<>());
        }
        collectionMap.get(collection).add(book);
    }

    public Book[] getBooksByCollection(Collection collection) {
        List<Book> books = collectionMap.get(collection);
        if (books == null) {
            return new Book[0];
        } else {
            return books.toArray(new Book[0]);
        }
    }


    public Book[] getBooksByAuthor(String authorName) {
        List<Book> booksByAuthor = new ArrayList<>();

        for (List<Book> booksInCollection : collectionMap.values()) {
            for (Book book : booksInCollection) {
                if (book.getAuthor().getName().equalsIgnoreCase(authorName)) {
                    booksByAuthor.add(book);
                }
            }
        }

        Book[] booksArray = booksByAuthor.toArray(new Book[0]);

        return booksArray;
    }


    public static void addNewBook(Scanner input) {
        System.out.println("Introduceti detaliile cartii noi:");

        System.out.print("Titlu: ");
        String title = input.nextLine();

        System.out.print("Numele autorului: ");
        String authorName = input.nextLine();

        System.out.print("Nationalitatea autorului: ");
        String authorNationality = input.nextLine();

        System.out.print("Numele colectiei: ");
        String collectionName = input.nextLine().trim();
        Collection collection = Collection.getCollectionByName(collectionName);

        System.out.print("Anul publicarii: ");
        long publicationYear = input.nextLong();

        System.out.print("Numarul disponibil: ");
        int availableNumber = input.nextInt();
        Book newBook = new Book(title, new Author(authorName, authorNationality), collection, publicationYear, availableNumber);

        books.add(newBook);
        collectionManager.addBook(newBook);
    }

    public static void displayBooks(List<Book> books) {
        for (Book book : books) {
            System.out.print("Titlu: " + book.getTitle() + " | ");
            System.out.print("Autor: " + book.getAuthor().getName() + " | ");
            System.out.print("Colectie: " + book.getCollection().getDisplayName() + " | ");
            System.out.print("An publicare: " + book.getPublicationYear() + " | ");
            System.out.println("Copii disponibile: " + book.getAvailableNumber());
            System.out.println("=".repeat(50));
        }
    }
}
