package library.model;
import java.util.ArrayList;

public class Member {
    private String id;
    private String nama;
    private ArrayList<Book> daftarPinjaman;
    private int totalPinjam; // total pinjaman sepanjang waktu

    public Member(String id, String nama) {
        this.id = id;
        this.nama = nama;
        this.daftarPinjaman = new ArrayList<>();
        this.totalPinjam = 0;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }
    public ArrayList<Book> getDaftarPinjaman() { return daftarPinjaman; }
    public int getTotalPinjam() { return totalPinjam; }

    public void pinjam(Book book) {
        daftarPinjaman.add(book);
        totalPinjam++;
    }

    public boolean kembalikan(Book book) {
        return daftarPinjaman.remove(book);
    }
}

