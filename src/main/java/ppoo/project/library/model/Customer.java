package ppoo.project.library.model;

import ppoo.project.library.data.DataLoader;

import java.util.List;

import static ppoo.project.library.menu.Menu.CLIENTS_FILE;

public class Customer {
    private String name;
    private String email;
    private static List<Customer> customers = DataLoader.loadCustomersFromFile(CLIENTS_FILE);

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static Customer findCustomerByName(String customerName) {
        for (Customer customer : customers) {
            if (customer.getName().equals(customerName)) {
                return customer;
            }
        }
        return null;
    }
}
