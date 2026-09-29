package library.exception;

public class BookNotFoundException extends Exception {
    public BookNotFoundException(String judul) {
        super("Buku dengan judul '" + judul + "' tidak ditemukan.");
    }
}
