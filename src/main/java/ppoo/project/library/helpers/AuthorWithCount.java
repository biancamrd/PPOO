package ppoo.project.library.helpers;

import ppoo.project.library.data.DataLoader;
import ppoo.project.library.model.Author;
import ppoo.project.library.model.Book;
import ppoo.project.library.model.Loan;

import java.util.*;

import static ppoo.project.library.menu.Menu.BOOKS_JSON;

public class AuthorWithCount {
    private String authorName;
    private int appearanceCount;

    public AuthorWithCount(String authorName, int appearanceCount) {
        this.authorName = authorName;
        this.appearanceCount = appearanceCount;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getAppearanceCount() {
        return appearanceCount;
    }

    public void incrementAppearanceCount() {
        appearanceCount++;
    }

    public static List<AuthorWithCount> displayTopAuthors(List<Loan> loans) {
        List<AuthorWithCount> topAuthors = new ArrayList<>();
        List<Book> books = DataLoader.readBooksFromJSONFile(BOOKS_JSON);

        for (Loan loan : loans) {
            String bookTitle = loan.getBook().getTitle();
            for (Book book : books) {
                if (book.getTitle().equals(bookTitle)) {
                    Author author = book.getAuthor();
                    if (author != null) {
                        String authorName = author.getName();
                        boolean authorExists = false;
                        for (AuthorWithCount existingAuthor : topAuthors) {
                            if (existingAuthor.getAuthorName().equals(authorName)) {
                                existingAuthor.incrementAppearanceCount();
                                authorExists = true;
                                break;
                            }
                        }
                        if (!authorExists) {
                            AuthorWithCount newAuthor = new AuthorWithCount(authorName, 1);
                            topAuthors.add(newAuthor);
                        }
                    }
                    break;
                }
            }
        }


        topAuthors.sort((author1, author2) -> Integer.compare(author2.getAppearanceCount(), author1.getAppearanceCount()));

        int count = 1;
        for (AuthorWithCount author : topAuthors) {
            if (count > 3) {
                break;
            }
            System.out.println(author.getAuthorName() + " - Numar de aparitii: " + author.getAppearanceCount());
            count++;
        }

        return topAuthors;
    }


}

