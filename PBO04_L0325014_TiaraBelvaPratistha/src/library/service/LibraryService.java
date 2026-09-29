package library.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import library.exception.BookAlreadyBorrowedException;
import library.exception.BookNotFoundException;
import library.exception.BorrowLimitExceededException;
import library.model.Book;
import library.model.Member;

public class LibraryService {
    public static final int BATAS_PINJAM = 3;

    private final ArrayList<Book> books = new ArrayList<>();
    private final HashMap<String, Member> members = new HashMap<>();
    private final HashMap<String, Integer> pinjamPerKategori = new HashMap<>();
    private int totalPinjaman = 0;

    // ---------- Manajemen Buku ----------

    public void tambahBuku(String judul, String penulis, int tahun, String kategori) {
        books.add(new Book(formatKapital(judul), formatKapital(penulis), tahun, formatKapital(kategori)));
    }

    public ArrayList<Book> getBooks() { return books; }

    /** Manipulasi character & string: huruf pertama setiap kata dijadikan kapital. */
    public String formatKapital(String teks) {
        String[] kata = teks.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String k : kata) {
            if (k.isEmpty()) continue;
            sb.append(Character.toUpperCase(k.charAt(0)))
              .append(k.substring(1).toLowerCase())
              .append(' ');
        }
        return sb.toString().trim();
    }

    // ---------- Pencarian & Analisis ----------

    /** Cari berdasarkan judul atau kategori (case-insensitive). */
    public ArrayList<Book> cariBuku(String keyword) {
        String kunci = keyword.trim().toLowerCase();
        ArrayList<Book> hasil = new ArrayList<>();
        for (Book b : books) {
            if (b.getJudul().toLowerCase().contains(kunci)
                    || b.getKategori().toLowerCase().contains(kunci)) {
                hasil.add(b);
            }
        }
        return hasil;
    }

    /** Hitung jumlah buku per kategori dengan looping. */
    public HashMap<String, Integer> hitungBukuPerKategori() {
        HashMap<String, Integer> hitung = new HashMap<>();
        for (Book b : books) {
            hitung.put(b.getKategori(), hitung.getOrDefault(b.getKategori(), 0) + 1);
        }
        return hitung;
    }

    // ---------- Anggota ----------

    /** Validasi format ID: 1 huruf diikuti minimal 1 digit (contoh: M001). */
    public boolean validIdAnggota(String id) {
        if (id == null || id.length() < 2 || !Character.isLetter(id.charAt(0))) return false;
        for (int i = 1; i < id.length(); i++) {
            if (!Character.isDigit(id.charAt(i))) return false;
        }
        return true;
    }

    public void daftarAnggota(String id, String nama) {
        if (!validIdAnggota(id)) {
            throw new IllegalArgumentException("Format ID tidak valid (contoh: M001).");
        }
        String kunci = id.toUpperCase();
        if (members.containsKey(kunci)) {
            throw new IllegalArgumentException("ID anggota sudah terdaftar.");
        }
        members.put(kunci, new Member(kunci, formatKapital(nama)));
    }

    public Member getMember(String id) {
        Member m = members.get(id.trim().toUpperCase());
        if (m == null) throw new IllegalArgumentException("Anggota dengan ID '" + id + "' tidak terdaftar.");
        return m;
    }

    // ---------- Peminjaman & Pengembalian ----------

    private Book cariPersis(String judul) throws BookNotFoundException {
        for (Book b : books) {
            if (b.getJudul().equalsIgnoreCase(judul.trim())) return b;
        }
        throw new BookNotFoundException(judul);
    }

    public void pinjamBuku(String idMember, String judul)
            throws BookNotFoundException, BookAlreadyBorrowedException, BorrowLimitExceededException {
        Member member = getMember(idMember);
        // Assertion: pastikan data anggota valid sebelum transaksi (jalankan dengan -ea)
        assert member.getId() != null && !member.getId().isEmpty() : "ID anggota kosong";
        assert member.getNama() != null && !member.getNama().isBlank() : "Nama anggota kosong";
        assert member.getDaftarPinjaman().size() <= BATAS_PINJAM : "Data pinjaman anggota tidak konsisten";

        Book book = cariPersis(judul);
        if (!book.isTersedia()) throw new BookAlreadyBorrowedException(book.getJudul());
        if (member.getDaftarPinjaman().size() >= BATAS_PINJAM) {
            throw new BorrowLimitExceededException(member.getNama(), BATAS_PINJAM);
        }

        book.setTersedia(false);
        book.tambahJumlahDipinjam();
        member.pinjam(book);
        totalPinjaman++;
        pinjamPerKategori.put(book.getKategori(), pinjamPerKategori.getOrDefault(book.getKategori(), 0) + 1);
    }

    public void kembalikanBuku(String idMember, String judul) throws BookNotFoundException {
        Member member = getMember(idMember);
        assert member.getId() != null && !member.getId().isEmpty() : "ID anggota kosong";

        Book book = cariPersis(judul);
        if (!member.kembalikan(book)) {
            throw new BookNotFoundException(judul + " (tidak ada di daftar pinjaman " + member.getNama() + ")");
        }
        book.setTersedia(true);
    }

    // ---------- Laporan ----------

    public String laporan() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== LAPORAN PERPUSTAKAAN =====\n");
        sb.append("Jumlah total pinjaman : ").append(totalPinjaman).append('\n');

        // Buku paling sering dipinjam
        Book terpopuler = null;
        for (Book b : books) {
            if (terpopuler == null || b.getJumlahDipinjam() > terpopuler.getJumlahDipinjam()) {
                terpopuler = b;
            }
        }
        if (terpopuler != null && terpopuler.getJumlahDipinjam() > 0) {
            sb.append("Buku paling sering    : ").append(terpopuler.getJudul())
              .append(" (").append(terpopuler.getJumlahDipinjam()).append("x)\n");
        } else {
            sb.append("Buku paling sering    : -\n");
        }

        // Anggota paling aktif
        Member aktif = null;
        for (Member m : members.values()) {
            if (aktif == null || m.getTotalPinjam() > aktif.getTotalPinjam()) aktif = m;
        }
        if (aktif != null && aktif.getTotalPinjam() > 0) {
            sb.append("Anggota paling aktif  : ").append(aktif.getNama())
              .append(" (").append(aktif.getTotalPinjam()).append(" pinjaman)\n");
        } else {
            sb.append("Anggota paling aktif  : -\n");
        }

        // Kategori paling populer
        String kategoriTop = null;
        int max = 0;
        for (Map.Entry<String, Integer> e : pinjamPerKategori.entrySet()) {
            if (e.getValue() > max) { max = e.getValue(); kategoriTop = e.getKey(); }
        }
        sb.append("Kategori terpopuler   : ")
          .append(kategoriTop != null ? kategoriTop + " (" + max + " pinjaman)" : "-").append('\n');

        sb.append("\nJumlah buku per kategori:\n");
        for (Map.Entry<String, Integer> e : hitungBukuPerKategori().entrySet()) {
            sb.append("  - ").append(e.getKey()).append(": ").append(e.getValue()).append('\n');
        }
        return sb.toString();
    }
}

