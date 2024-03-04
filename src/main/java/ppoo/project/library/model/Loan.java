package ppoo.project.library.model;

import ppoo.project.library.enums.LoanStatus;

import java.time.LocalDate;

public class Loan {
    private Book book;
    private Customer customer;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private LoanStatus loanStatus;

    public Loan(Book book, Customer customer, LocalDate loanDate,
                LocalDate returnDate, LoanStatus loanStatus) {
        this.book = book;
        this.customer = customer;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.loanStatus = loanStatus;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }
}
