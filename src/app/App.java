package app;

import db.Database;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import model.Book;
import model.Loan;
import model.Student;
import repository.BookRepository;
import repository.LoanRepository;
import repository.StudentRepository;

public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static final BookRepository bookRepo = new BookRepository();
    private static final StudentRepository studentRepo = new StudentRepository();
    private static final LoanRepository loanRepo = new LoanRepository();

    public static void main(String[] args) {
        Database.init();
        runMenu();
    }

    private static void runMenu() {
        while (true) {
            System.out.println("===== SmartLibrary =====");
            System.out.println("1. Kitap Ekle");
            System.out.println("2. Kitaplari Listele");
            System.out.println("3. Ogrenci Ekle");
            System.out.println("4. Ogrencileri Listele");
            System.out.println("5. Kitap Odunc Ver");
            System.out.println("6. Odunc Listesini Goruntule");
            System.out.println("7. Kitap Geri Teslim Al");
            System.out.println("0. Cikis");
            System.out.print("Secim: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addBookMenu();
                    break;
                case "2":
                    listBooksMenu();
                    break;
                case "3":
                    addStudentMenu();
                    break;
                case "4":
                    listStudentsMenu();
                    break;
                case "5":
                    loanBookMenu();
                    break;
                case "6":
                    listLoansMenu();
                    break;
                case "7":
                    returnBookMenu();
                    break;
                case "0":
                    System.out.println("Cikis yapiliyor...");
                    return;
                default:
                    System.out.println("Gecersiz secim");
            }
            System.out.println();
        }
    }

    private static void addBookMenu() {
        System.out.print("Kitap basligi: ");
        String title = scanner.nextLine();
        System.out.print("Yazar: ");
        String author = scanner.nextLine();
        System.out.print("Yil: ");
        int year = Integer.parseInt(scanner.nextLine());

        Book book = new Book(title, author, year);
        bookRepo.add(book);
        System.out.println("Kitap eklendi.");
    }

    private static void listBooksMenu() {
        List<Book> books = bookRepo.getAll();
        if (books.isEmpty()) {
            System.out.println("Hic kitap yok.");
            return;
        }
        for (Book b : books) {
            System.out.println(b);
        }
    }

    private static void addStudentMenu() {
        System.out.print("Ogrenci adi: ");
        String name = scanner.nextLine();
        System.out.print("Bolum: ");
        String department = scanner.nextLine();

        Student s = new Student(name, department);
        studentRepo.add(s);
        System.out.println("Ogrenci eklendi.");
    }

    private static void listStudentsMenu() {
        List<Student> students = studentRepo.getAll();
        if (students.isEmpty()) {
            System.out.println("Hic ogrenci yok.");
            return;
        }
        for (Student s : students) {
            System.out.println(s);
        }
    }

    private static void loanBookMenu() {
        System.out.print("Ogrenci ID: ");
        int studentId = Integer.parseInt(scanner.nextLine());
        System.out.print("Kitap ID: ");
        int bookId = Integer.parseInt(scanner.nextLine());

        if (loanRepo.isBookBorrowed(bookId)) {
            System.out.println("Bu kitap zaten oduncte.");
            return;
        }

        String today = LocalDate.now().toString();
        Loan loan = new Loan(bookId, studentId, today, null);
        loanRepo.add(loan);
        System.out.println("Kitap odunc verildi.");
    }

    private static void listLoansMenu() {
        List<Loan> loans = loanRepo.getAll();
        if (loans.isEmpty()) {
            System.out.println("Hic odunc kaydi yok.");
            return;
        }
        for (Loan l : loans) {
            System.out.println(l);
        }
    }

    private static void returnBookMenu() {
        System.out.print("Teslim edilecek Loan ID: ");
        int loanId = Integer.parseInt(scanner.nextLine());
        String today = LocalDate.now().toString();
        loanRepo.returnBook(loanId, today);
        System.out.println("Kitap geri alindi.");
    }
}
