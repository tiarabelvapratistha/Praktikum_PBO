package library.exception;

public class BookAlreadyBorrowedException extends Exception {
    public BookAlreadyBorrowedException(String judul) {
        super("Buku '" + judul + "' sedang dipinjam.");
    }
}
