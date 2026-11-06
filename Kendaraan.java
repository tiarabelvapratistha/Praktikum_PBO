// ABSTRACT CLASS: kerangka umum semua kendaraan
public abstract class Kendaraan {
    // ENCAPSULATION: atribut private
    private String plat;
    private String merek;
    private double tarifPerHari;
    private boolean tersedia;

    public Kendaraan(String plat, String merek, double tarifPerHari) {
        this.plat = plat;
        this.merek = merek;
        if (tarifPerHari <= 0) {
            throw new IllegalArgumentException("[!]Tarif awal harus lebih dari 0");
        }
        this.tarifPerHari = tarifPerHari;
        this.tersedia = true;
    }

    // Getter
    public String getPlat() { return plat; }
    public String getMerek() { return merek; }
    public double getTarifPerHari() { return tarifPerHari; }
    public boolean isTersedia() { return tersedia; }

    // Setter dengan validasi
    // Mengembalikan true jika berhasil, false jika ditolak (nilai lama tetap)
    public boolean setTarifPerHari(double tarifPerHari) {
        if (tarifPerHari <= 0) {
            System.out.println("[!]Ditolak: tarif harus lebih dari 0");
            return false;
        }
        this.tarifPerHari = tarifPerHari;
        return true;
    }

    // Perubahan status hanya lewat method ini
    public void sewa() {
        if (!tersedia) {
            throw new IllegalStateException(plat + " sedang disewa");
        }
        tersedia = false;
    }

    public void kembalikan() {
        tersedia = true;
    }

    // ABSTRACT METHOD: wajib diimplementasikan subclass
    public abstract double hitungBiayaSewa(int hari);

    public abstract String getJenis();

    public void tampilkanInfo() {
        System.out.printf("[%s] %s | %s | Rp%,.0f/hari | %s%n",
                getJenis(), merek, plat, tarifPerHari,
                tersedia ? "Tersedia" : "Disewa");
    }
}