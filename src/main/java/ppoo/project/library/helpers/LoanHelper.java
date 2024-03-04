package ppoo.project.library.helpers;

import ppoo.project.library.data.DataLoader;
import ppoo.project.library.enums.LoanStatus;
import ppoo.project.library.exceptions.BookNotFoundException;
import ppoo.project.library.exceptions.CustomerNotFoundException;
import ppoo.project.library.exceptions.NoAvailableCopiesException;
import ppoo.project.library.model.Book;
import ppoo.project.library.model.Customer;
import ppoo.project.library.model.Loan;

import java.time.LocalDate;
import java.util.*;

import static ppoo.project.library.menu.Menu.*;

public class LoanHelper {


    private static List<Book> books = DataLoader.readBooksFromJSONFile(BOOKS_JSON);
    private static List<Loan> loans = DataLoader.loadLoansFromFile(LOANS_FILE);
    public static Loan findLoanByBookTitleAndCustomerName(String bookTitle, String customerName, List<Loan> loans) {
        for (Loan loan : loans) {
            Book book = loan.getBook();
            Customer customer = loan.getCustomer();

            if (book.getTitle().equals(bookTitle) && customer.getName().equals(customerName)) {
                return loan;
            }
        }
        return null;
    }




    public static List<Loan> displayLoansFromDate(Scanner input) {
        List<Loan> loansOnDate = new ArrayList<>();

        System.out.print("Introduceti data (yyyy-MM-dd): ");
        String dateString = input.next();
        LocalDate date = LocalDate.parse(dateString);

        for (Loan loan : loans) {
            if (loan.getLoanDate().isEqual(date)) {
                loansOnDate.add(loan);
            }
        }

        return loansOnDate;
    }

    public static void displayLoans(List<Loan> loansToDisplay) {
        for (Loan loan : loansToDisplay) {
            System.out.print("Client: " + loan.getCustomer().getName() + " | ");
            System.out.print("Carte: " + loan.getBook().getTitle() + " | ");
            System.out.print("Data imprumutului: " + loan.getLoanDate() + " | ");
            System.out.print("Data de returnare: " + loan.getReturnDate() + " | ");
            System.out.println("Stare imprumut: " + loan.getLoanStatus());
            System.out.println("=".repeat(50));
        }
    }


}
