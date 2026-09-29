package library.exception;

public class BorrowLimitExceededException extends Exception {
    public BorrowLimitExceededException(String nama, int batas) {
        super("Anggota " + nama + " sudah mencapai batas maksimal " + batas + " buku.");
    }
}
