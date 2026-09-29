package library.main;

import java.util.ArrayList;
import java.util.Scanner;

import library.exception.BookAlreadyBorrowedException;
import library.exception.BookNotFoundException;
import library.exception.BorrowLimitExceededException;
import library.model.Book;
import library.service.LibraryService;

public class MainApp {
    private static final Scanner in = new Scanner(System.in);
    private static final LibraryService service = new LibraryService();

    public static void main(String[] args) {
        isiDataAwal();
        boolean jalan = true;
        while (jalan) {
            tampilMenu();
            int pilihan = bacaInt("Pilih menu: ");
            switch (pilihan) {
                case 1:
                    tambahBuku();
                    break;
                case 2:
                    daftarBuku();
                    break;
                case 3:
                    cariBuku();
                    break;
                case 4:
                    pinjamBuku();
                    break;
                case 5:
                    kembalikanBuku();
                    break;
                case 6:
                    System.out.println("\n" + service.laporan());
                    break;
                case 7:
                    daftarAnggota();
                    break;
                case 0:
                    jalan = false;
                    System.out.println("Terima kasih!");
                    break;
                default:
                    System.out.println("Menu tidak valid.");
                    break;
            }
        }
        in.close();
    }

    private static void tampilMenu() {
        System.out.println("\n=== PERPUSTAKAAN MINI ===");
        System.out.println("1. Tambah Buku");
        System.out.println("2. Daftar Buku");
        System.out.println("3. Cari Buku");
        System.out.println("4. Pinjam Buku");
        System.out.println("5. Kembalikan Buku");
        System.out.println("6. Laporan Perpustakaan");
        System.out.println("7. Daftar Anggota Baru");
        System.out.println("0. Keluar");
    }

    private static String bacaString(String prompt) {
        System.out.print(prompt);
        return in.nextLine().trim();
    }

    private static int bacaInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(bacaString(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid.");
            }
        }
    }

    private static void tambahBuku() {
        String judul = bacaString("Judul     : ");
        String penulis = bacaString("Penulis   : ");
        int tahun = bacaInt("Tahun     : ");
        String kategori = bacaString("Kategori  : ");
        if (judul.isEmpty() || penulis.isEmpty() || kategori.isEmpty()) {
            System.out.println("Data tidak boleh kosong.");
            return;
        }
        service.tambahBuku(judul, penulis, tahun, kategori);
        System.out.println("Buku berhasil ditambahkan.");
    }

    private static void daftarBuku() {
        ArrayList<Book> books = service.getBooks();
        if (books.isEmpty()) { System.out.println("Belum ada buku."); return; }
        int no = 1;
        for (Book b : books) System.out.println(no++ + ". " + b);
    }

    private static void cariBuku() {
        String kunci = bacaString("Kata kunci (judul/kategori): ");
        ArrayList<Book> hasil = service.cariBuku(kunci);
        if (hasil.isEmpty()) { System.out.println("Tidak ada hasil."); return; }
        System.out.println("Ditemukan " + hasil.size() + " buku:");
        for (Book b : hasil) System.out.println(" - " + b);
    }

    private static void daftarAnggota() {
        String id = bacaString("ID anggota (contoh M001): ");
        String nama = bacaString("Nama: ");
        try {
            service.daftarAnggota(id, nama);
            System.out.println("Anggota terdaftar.");
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private static void pinjamBuku() {
        String id = bacaString("ID anggota  : ");
        String judul = bacaString("Judul buku  : ");
        try {
            service.pinjamBuku(id, judul);
            System.out.println("Peminjaman berhasil.");
        } catch (BookNotFoundException | BookAlreadyBorrowedException | BorrowLimitExceededException e) {
            System.out.println("Gagal: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private static void kembalikanBuku() {
        String id = bacaString("ID anggota  : ");
        String judul = bacaString("Judul buku  : ");
        try {
            service.kembalikanBuku(id, judul);
            System.out.println("Pengembalian berhasil.");
        } catch (BookNotFoundException | IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private static void isiDataAwal() {
        service.tambahBuku("laskar pelangi", "andrea hirata", 2005, "novel");
        service.tambahBuku("bumi manusia", "pramoedya ananta toer", 1980, "novel");
        service.tambahBuku("clean code", "robert c martin", 2008, "teknologi");
        service.tambahBuku("head first java", "kathy sierra", 2005, "teknologi");
        service.tambahBuku("sapiens", "yuval noah harari", 2011, "sejarah");
        service.daftarAnggota("M001", "budi santoso");
        service.daftarAnggota("M002", "sari dewi");
    }
}

