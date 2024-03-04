package ppoo.project.library.helpers;

import ppoo.project.library.model.Customer;
import ppoo.project.library.validator.Validator;

import java.util.List;
import java.util.Scanner;

import static ppoo.project.library.menu.Menu.customers;

public class CustomerHelper {
    public static void addNewCustomer(Scanner input) {
        System.out.println("Introduceti detaliile noului client:");
        System.out.print("Numele: ");
        input.nextLine();
        String name = input.nextLine();

        System.out.print("Email: ");
        String email = input.next();

        if (Validator.isValidEmail(email)) {
            Customer newCustomer = new Customer(name, email);

            customers.add(newCustomer);

            System.out.println("Clientul a fost adaugat cu succes!");
        } else {
            System.out.println("Adresa de email introdusa nu este valida. Clientul nu a fost adăugat.");
        }
    }

    public static void displayCustomers(List<Customer> customersToDisplay) {
        for (Customer customer : customersToDisplay) {
            System.out.print("Nume: " + customer.getName()+ " | ");
            System.out.println("Email: " + customer.getEmail()+ " | ");
            System.out.println("=".repeat(50));
        }
    }

}
