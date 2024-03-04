package ppoo.project.library.helpers;

import ppoo.project.library.model.Loan;

import java.util.*;

public class BookWithLoanCount {
    private String title;
    private int loanCount;

    public BookWithLoanCount(String title, int loanCount) {
        this.title = title;
        this.loanCount = loanCount;
    }

    public String getTitle() {
        return title;
    }

    public int getLoanCount() {
        return loanCount;
    }

    public static List<BookWithLoanCount> displayTopBooks(List<Loan> loans) {
        List<BookWithLoanCount> topBooks = new ArrayList<>();

        for (Loan loan : loans) {
            String bookTitle = loan.getBook().getTitle();
            boolean bookFound = false;

            for (BookWithLoanCount bookWithLoanCount : topBooks) {
                if (bookWithLoanCount.getTitle().equals(bookTitle)) {
                    bookWithLoanCount.incrementLoanCount();
                    bookFound = true;
                    break;
                }
            }

            if (!bookFound) {
                topBooks.add(new BookWithLoanCount(bookTitle, 1));
            }
        }

        topBooks.sort((b1, b2) -> Integer.compare(b2.getLoanCount(), b1.getLoanCount()));

        int count = 1;
        for (BookWithLoanCount bookWithLoanCount : topBooks) {
            if (count > 3) {
                break;
            }
            System.out.println(count + ". " + bookWithLoanCount.getTitle() + " - Numar de împrumuturi: " + bookWithLoanCount.getLoanCount());
            count++;
        }

        return topBooks;
    }

    public void incrementLoanCount() {
        loanCount++;
    }
}

