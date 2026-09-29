package library.model;

public class Book {
    private String judul;
    private String penulis;
    private int tahunTerbit;
    private String kategori;
    private boolean tersedia;      // statusKetersediaan
    private int jumlahDipinjam;    // untuk analisis aktivitas

    public Book(String judul, String penulis, int tahunTerbit, String kategori) {
        this.judul = judul;
        this.penulis = penulis;
        this.tahunTerbit = tahunTerbit;
        this.kategori = kategori;
        this.tersedia = true;
        this.jumlahDipinjam = 0;
    }

    public String getJudul() { return judul; }
    public String getPenulis() { return penulis; }
    public int getTahunTerbit() { return tahunTerbit; }
    public String getKategori() { return kategori; }
    public boolean isTersedia() { return tersedia; }
    public int getJumlahDipinjam() { return jumlahDipinjam; }

    public void setTersedia(boolean tersedia) { this.tersedia = tersedia; }
    public void tambahJumlahDipinjam() { jumlahDipinjam++; }

    @Override
    public String toString() {
        return String.format("%-22s | %-22s | %d | %-10s | %s",
                judul, penulis, tahunTerbit, kategori,
                tersedia ? "Tersedia" : "Dipinjam");
    }
}
