package ppoo.project.library.data;

import com.google.gson.Gson;
import ppoo.project.library.helpers.AuthorWithCount;
import ppoo.project.library.helpers.BookWithLoanCount;
import ppoo.project.library.model.*;

import java.io.*;
import java.util.List;

public class DataSaver {
    public static void saveBooksToJSONFile(String filename, List<Book> books) {
        try (Writer writer = new FileWriter(filename)) {
            Gson gson = new Gson();
            gson.toJson(books, writer);
            System.out.println("Cartile au fost salvate cu succes in " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveLibrariansToFile(String filename, List<Librarian> librarians) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Librarian librarian : librarians) {
                writer.write(librarian.getName());
                writer.newLine();
                writer.write(librarian.getEmail());
                writer.newLine();
                writer.write(librarian.getPassword());
                writer.newLine();
            }
            System.out.println("Bibliotecarii au fost salvati cu succes in " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveCustomersToFile(String filename, List<Customer> customers) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Customer customer : customers) {
                writer.println(customer.getName());
                writer.println(customer.getEmail());
            }
            System.out.println("Clientii au fost salvati cu succes in " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveLoansToFile(String filename, List<Loan> loans) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Loan loan : loans) {
                writer.println(loan.getBook().getTitle());
                writer.println(loan.getCustomer().getName());
                writer.println(loan.getLoanDate());
                writer.println(loan.getReturnDate());
                writer.println(loan.getLoanStatus());
            }
            System.out.println("Imprumuturile au fost salvati cu succes in " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveTopBooksToFile(String fileName, List<BookWithLoanCount> topBooks) {
        if (topBooks != null && !topBooks.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                int count = 1;
                for (BookWithLoanCount bookEntry : topBooks) {
                    if (count > 3) {
                        break;
                    }
                    writer.write(count + ". " + bookEntry.getTitle() + " - Numar de imprumuturi: " + bookEntry.getLoanCount());
                    writer.newLine();
                    count++;
                }
                System.out.println("Cele mai imprumutate carti au fost salvate in " + fileName);
            } catch (IOException e) {
                System.err.println("Eroare la salvarea cartilor cele mai imprumutate in fisier.");
            }
        } else {
            System.out.println("Nu exista date pentru a salva topul cartilor.");
        }
    }

    public static void saveTopAuthorsToFile(String fileName, List<AuthorWithCount> topAuthors) {
        if (topAuthors != null && !topAuthors.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                int count = 1;
                for (AuthorWithCount author : topAuthors) {
                    if (count > 3) {
                        break;
                    }
                    writer.write(count + ". " + author.getAuthorName() + " - Numar de aparitii: " + author.getAppearanceCount());
                    writer.newLine();
                    count++;
                }
                System.out.println("Cei mai cititi autori au fost salvati în " + fileName);
            } catch (IOException e) {
                System.err.println("Eroare la salvarea autorilor cei mai cititi în fișier.");
            }
        } else {
            System.out.println("Nu exista date pentru a salva topul autorilor.");
        }
    }



}
