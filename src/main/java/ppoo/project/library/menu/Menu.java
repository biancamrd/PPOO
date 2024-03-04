package ppoo.project.library.menu;

import ppoo.project.library.data.DataLoader;
import ppoo.project.library.data.DataSaver;
import ppoo.project.library.enums.Collection;
import ppoo.project.library.enums.LoanStatus;
import ppoo.project.library.exceptions.BookNotFoundException;
import ppoo.project.library.exceptions.CustomerNotFoundException;
import ppoo.project.library.exceptions.NoAvailableCopiesException;
import ppoo.project.library.helpers.*;
import ppoo.project.library.model.*;
import ppoo.project.library.validator.Validator;

import java.time.LocalDate;
import java.util.*;


public class Menu {
    public static final String LIBRARIANS_FILE = "src/main/java/ppoo/project/library/files/bibliotecari.txt";
    public static final String CLIENTS_FILE = "src/main/java/ppoo/project/library/files/customers.txt";
    public static final String LOANS_FILE = "src/main/java/ppoo/project/library/files/loans.txt";
    public static final String TOP_BOOKS = "src/main/java/ppoo/project/library/files/top_books.txt";
    public static final String TOP_AUTHORS = "src/main/java/ppoo/project/library/files/top_authors.txt";
    public static final String BOOKS_JSON = "src/main/java/ppoo/project/library/files/books.json";
    private static List<Librarian> librariansList = new ArrayList<>();
    public static List<Customer> customers = DataLoader.loadCustomersFromFile(CLIENTS_FILE);

    public static Library library = new Library();
    public static List<Book> books = DataLoader.readBooksFromJSONFile(BOOKS_JSON);
    public static List<Loan> loans = DataLoader.loadLoansFromFile(LOANS_FILE);
    public static BookHelper collectionManager = new BookHelper();

    static List<BookWithLoanCount> topBooks;
    static List<AuthorWithCount> topAuthors;

    private static Book[] booksByAuthor;
    private static Book[]   booksInCollection;


    public static void initMenu() {

        library.loadLibrariansFromFile(LIBRARIANS_FILE);
        librariansList.addAll(library.getLibrarians());

        for (Book book : books) {
            collectionManager.addBook(book);
        }

        System.out.println("=".repeat(50));
        System.out.println("Bine ati venit la Biblioteca Bianca");
        System.out.println("=".repeat(50));
        Scanner input = new Scanner(System.in);
        int option = 1;

        while (option != 0) {
            String textBlock = """
               Pentru a continua trebuie sa va autentificati!
                Apasati 1 pentru a va autentifica!
                Apasati 2 pentru a va crea cont!
                Apasati 0 pentru a inchide aplicatia!
                """;

            System.out.println(textBlock + " ");
            option = input.nextInt();

            switch (option) {
                case 1:
                    loginMenu(library, input);
                    break;
                case 2:
                    createAccountMenu(input);
                    break;
                case 0:
                    exitApplication();
                    break;
                default:
                    System.out.println("Optiune invalida.");
                    break;
            }
        }
    }

    private static void loginMenu(Library library, Scanner input) {
        String email;
        String password;
        boolean authenticated = false;

        System.out.println("Email: ");
        email = input.next();
        System.out.println("Parola: ");
        password = input.next();

        authenticated = Validator.validateAuth(email, password);
        if (authenticated) {
            Librarian authLibrarian = library.searchLibrarian(email, password);
            if (authLibrarian != null) {
                System.out.println("=".repeat(50));
                System.out.println("Bine ati venit in contul dvs. " + authLibrarian.getName());

                submenu(input);
            } else {
                System.out.println("Nu s-a gasit contul dvs. Incercati din nou.");
            }
        } else {
            System.out.println("Autentificare esuata. Incercati din nou.");
        }
    }

    private static void createAccountMenu(Scanner input) {
        System.out.println("-- Creare cont --");
        System.out.println("Nume: ");
        input.nextLine();
        String name = input.nextLine();

        System.out.println("Email: ");
        String newLibrarianEmail = input.nextLine();
        System.out.println("Parola: ");
        String newLibrarianPassword = input.next();

        boolean newValidData = Validator.validateRegister(name, newLibrarianEmail, newLibrarianPassword);

        if (newValidData) {
            Librarian newLibrarian = new Librarian(name, newLibrarianEmail, newLibrarianPassword);
            librariansList.add(newLibrarian);
            library.addLibrarian(newLibrarian);
            System.out.println("Contul a fost creat cu succes!");
        } else {
            System.out.println("Date invalide pentru crearea contului. Incercati din nou.");
        }
    }

    private static void submenu(Scanner input) {
        String textBlock2 = """
               Apasati tasta necesara pentru a efectua operatiile dorite!
                Apasati 1 pentru carti
                Apasati 2 pentru imprumuturi
                Apasati 3 pentru clienti
                Apasati 4 pentru statistici
                Apasati 0 pentru a merge inapoi!
                """;

        System.out.println(textBlock2 + " ");
        System.out.println("=".repeat(50));

        int subOption = input.nextInt();

        switch (subOption) {
            case 1:
                booksMenu(input);
                break;
            case 2:
                loansMenu(input);
                break;
            case 3:
                clientsMenu(input);
                break;
            case 4 :
                statisticsMenu(input);
                break;
            case 0:
                break;
            default:
                System.out.println("Optiune invalida.");
                break;
        }
    }

    private static void exitApplication() {
        library.saveLibrariansToFile(LIBRARIANS_FILE);
        DataSaver.saveBooksToJSONFile(BOOKS_JSON, books);
        DataSaver.saveCustomersToFile(CLIENTS_FILE, customers);
        DataSaver.saveLoansToFile(LOANS_FILE, loans);
        DataSaver.saveTopBooksToFile(TOP_BOOKS, topBooks );
        DataSaver.saveTopAuthorsToFile(TOP_AUTHORS, topAuthors);
        System.out.println("Aplicatia se inchide.");
    }

    private static void booksMenu(Scanner input) {
        boolean returnToSubmenu = false;

        do {
            String textBlock = """
        Alegeti optiunea dorita:
        Apasati 1 pentru a afisa intreaga colectie de carti!
        Apasati 2 pentru a afisa cartile unui anumit autor!
        Apasati 3 pentru a afisa cartile ce fac parte dintr-o anumita colectie!
        Apasati 4 pentru a adauga o noua carte!
        Apasati 0 pentru a merge inapoi!
        """;

            System.out.println(textBlock + " ");
            System.out.println("=".repeat(50));

            int reportOption = input.nextInt();

            switch (reportOption) {
                case 1:

                    BookHelper.displayBooks(books);
                    break;
                case 2:
                    System.out.print("Introduceti numele autorului: ");
                    input.nextLine();
                    String authorName = input.nextLine();

                    booksByAuthor = collectionManager.getBooksByAuthor(authorName);

                    if (booksByAuthor == null || booksByAuthor.length == 0) {
                        System.out.println("Nu s-au gasit carți scrise de " + authorName + ".");
                    } else {
                        System.out.println("Carți scrise de " + authorName + ":");
                        for (Book book : booksByAuthor) {
                            System.out.println(book.getTitle());
                        }
                    }
                    break;

                case 3:
                    System.out.print("Introduceti numele colectiei: ");
                    input.nextLine();
                    String collectionName = input.nextLine();
                    Collection collectionToDisplay = Collection.getCollectionByName(collectionName);

                    if (collectionToDisplay != null) {
                        booksInCollection = collectionManager.getBooksByCollection(collectionToDisplay);

                        System.out.println("Carti in colectia " + collectionToDisplay.getDisplayName() + ":");
                        for (Book collectionBook : booksInCollection) {
                            System.out.println(collectionBook.getTitle());
                        }
                    } else {
                        System.out.println("Colectia specificata nu exista.");
                    }
                    break;
                case 4:
                    input.nextLine();
                    BookHelper.addNewBook(input);
                    break;
                case 0:
                    returnToSubmenu = true;
                    break;
                default:
                    System.out.println("Optiune invalidă.");
                    break;
            }
        } while (!returnToSubmenu);


        submenu(input);
    }

    private static void clientsMenu(Scanner input) {

        boolean returnToSubmenu = false;
        do {
            String textBlock = """
        Alegeti optiunea dorita:
        Apasati 1 pentru a vizualiza clientii!
        Apasati 2 pentru a adauga un nou client!
        Apasati 0 pentru a merge inapoi!
        """;

            System.out.println(textBlock + " ");
            System.out.println("=".repeat(50));

            int reportOption = input.nextInt();

            switch (reportOption) {
                case 1:
                    CustomerHelper.displayCustomers(customers);
                    break;
                case 2:
                    CustomerHelper.addNewCustomer(input);
                    break;
                case 0:
                    returnToSubmenu = true;
                    break;
                default:
                    System.out.println("Optiune invalidă.");
                    break;
            }
        } while (!returnToSubmenu);

        submenu(input);
    }

    private static void loansMenu(Scanner input) {

        boolean returnToSubmenu = false;
        do {
            String textBlock = """
        Alegeti optiunea dorita:
        Apasati 1 pentru a vizualiza toate imprumuturile!
        Apasati 2 pentru a vizualiza imprumuturile de la o anumita data!
        Apasati 3 pentru a face un nou imprumut!
        Apasati 4 pentru a returna!
        Apasati 0 pentru a merge inapoi!
        """;

            System.out.println(textBlock + " ");
            System.out.println("=".repeat(50));

            int reportOption = input.nextInt();

            switch (reportOption) {
                case 1:
                    LoanHelper.displayLoans(loans);
                    break;
                case 2:
                    List<Loan> loansOnDate = LoanHelper.displayLoansFromDate(input);
                    LoanHelper.displayLoans(loansOnDate);
                    break;
                case 3:
                    makeNewLoan(input);
                    break;
                case 4:
                    returnLoan(input);
                    break;
                case 0:
                    returnToSubmenu = true;
                    break;
                default:
                    System.out.println("Optiune invalidă.");
                    break;
            }
        } while (!returnToSubmenu);

        submenu(input);
    }

    private static void statisticsMenu(Scanner input) {

        boolean returnToSubmenu = false;
        do {
            String textBlock = """
        Alegeti optiunea dorita:
        Apasati 1 pentru a vizualiza si descarca cele mai citite carti!
        Apasati 2 pentru a vizualiza si descarca cei mai cititi autori!
        Apasati 0 pentru a merge inapoi!
        """;

            System.out.println(textBlock + " ");
            System.out.println("=".repeat(50));

            int reportOption = input.nextInt();

            switch (reportOption) {
                case 1:
                    topBooks = BookWithLoanCount.displayTopBooks(loans);
                    break;
                case 2:
                    topAuthors = AuthorWithCount.displayTopAuthors(loans);
                    break;
                case 0:
                    returnToSubmenu = true;
                    break;
                default:
                    System.out.println("Optiune invalidă.");
                    break;
            }
        } while (!returnToSubmenu);

        submenu(input);
    }

    public static void makeNewLoan(Scanner input) {
        input.nextLine();
        System.out.print("Numele clientului: ");
        String customerName = input.nextLine();
        Customer customer = Customer.findCustomerByName(customerName);

        try {
            if (customer == null) {
                throw new CustomerNotFoundException("Clientul nu exista in sistem.");
            }

            System.out.print("Numele cartii: ");
            String bookTitle = input.nextLine();
            Book book = findBookByTitle(bookTitle);

            if (book == null) {
                throw new BookNotFoundException("Cartea nu exista in sistem.");
            }

            if (book.getAvailableNumber() <= 0) {
                throw new NoAvailableCopiesException("Nu exista copii disponibile pentru imprumut.");
            }

            LocalDate loanDate = LocalDate.now();
            LocalDate returnDate = null;

            Loan newLoan = new Loan(book, customer, loanDate, returnDate, LoanStatus.IN_DESFASURARE);
            loans.add(newLoan);
            book.decrementAvailableNo();
            int index = books.indexOf(book);
            if (index != -1) {
                books.set(index, book);
            }

            System.out.println("Imprumutul a fost creat cu succes.");
        } catch (CustomerNotFoundException | BookNotFoundException | NoAvailableCopiesException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    public static void returnLoan(Scanner input) {
        input.nextLine();

        try {
            System.out.print("Numele cartii: ");
            String bookTitle = input.nextLine();
            System.out.print("Numele clientului: ");
            String customerName = input.nextLine();

            Loan loanToReturn = LoanHelper.findLoanByBookTitleAndCustomerName(bookTitle, customerName, loans);

            if (loanToReturn == null) {
                throw new BookNotFoundException("Nu s-a găsit un imprumut cu aceste detalii.");
            }

            loanToReturn.setLoanStatus(LoanStatus.FINALIZAT);
            loanToReturn.setReturnDate(LocalDate.now());

            Book bookToReturn = findBookByTitle(bookTitle);
            System.out.println(bookToReturn);
            if (bookToReturn != null) {
                bookToReturn.incrementAvailableNo();
                int index = books.indexOf(bookToReturn);
                if (index != -1) {
                    books.set(index, bookToReturn);
                }
            }

            System.out.println("Imprumutul a fost returnat cu succes.");
        } catch (BookNotFoundException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }
    public static Book findBookByTitle(String bookTitle) {
        for (Book book : books) {
            if (book.getTitle().equals(bookTitle)) {
                return book;
            }
        }
        return null;
    }

}
