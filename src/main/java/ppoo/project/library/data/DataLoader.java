package ppoo.project.library.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ppoo.project.library.enums.LoanStatus;
import ppoo.project.library.model.Book;
import ppoo.project.library.model.Customer;
import ppoo.project.library.model.Librarian;
import ppoo.project.library.model.Loan;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public static List<Librarian> loadLibrariansFromFile(String fileName) {
        List<Librarian> librarians = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String name = null;
            String email = null;
            String password = null;

            while ((line = reader.readLine()) != null) {
                if (name == null) {
                    name = line;
                } else if (email == null) {
                    email = line;
                } else if (password == null) {
                    password = line;
                    librarians.add(new Librarian(name, email, password));
                    name = null;
                    email = null;
                    password = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return librarians;
    }


    public static List<Book> readBooksFromJSONFile(String filePath) {
        List<Book> books = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new GsonBuilder().create();
            Type bookListType = new TypeToken<List<Book>>(){}.getType();
            books = gson.fromJson(reader, bookListType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }

    public static List<Customer> loadCustomersFromFile(String fileName) {
        List<Customer> customers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String name = null;
            String email = null;
            String line;

            while ((line = reader.readLine()) != null) {
                if (name == null) {
                    name = line;
                } else {
                    email = line;
                    if (name != null && email != null) {
                        customers.add(new Customer(name, email));
                    }
                    name = null;
                    email = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public static List<Loan> loadLoansFromFile(String fileName) {
        List<Loan> loans = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String title = null;
            String customerName = null;
            LocalDate loanDate = null;
            LocalDate returnDate = null;
            LoanStatus loanStatus = null;
            boolean readingReturnDate = false;

            String line;

            while ((line = reader.readLine()) != null) {
                if (title == null) {
                    title = line;
                } else if (customerName == null) {
                    customerName = line;
                } else if (loanDate == null) {
                    loanDate = LocalDate.parse(line);
                } else if (!readingReturnDate) {
                    if ("null".equals(line)) {
                        returnDate = null;
                        readingReturnDate = true;
                    } else {
                        returnDate = LocalDate.parse(line);
                        readingReturnDate = true;
                    }
                } else if (loanStatus == null) {
                    loanStatus = LoanStatus.valueOf(line);
                    Book book = new Book(title);
                    Customer customer = new Customer(customerName);
                    loans.add(new Loan(book, customer, loanDate, returnDate, loanStatus));
                    title = null;
                    customerName = null;
                    loanDate = null;
                    returnDate = null;
                    loanStatus = null;
                    readingReturnDate = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return loans;
    }

}
