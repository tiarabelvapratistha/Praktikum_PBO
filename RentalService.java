import java.util.ArrayList;

public class RentalService {
    private static final double BIAYA_SOPIR_PER_HARI = 150000;
    public static final double GAGAL = -1; // nilai kembalian jika penyewaan gagal
    private ArrayList<Kendaraan> daftar = new ArrayList<>();

    public void tambahKendaraan(Kendaraan k) {
        daftar.add(k);
    }

    public Kendaraan cariKendaraan(String plat) {
        for (Kendaraan k : daftar) {
            if (k.getPlat().equalsIgnoreCase(plat)) return k;
        }
        throw new IllegalArgumentException("[!]Kendaraan " + plat + " tidak ditemukan");
    }

    // OVERLOADING 1: sewa tanpa sopir
    public double sewa(String plat, int hari) {
        try {
            Kendaraan k = cariKendaraan(plat);
            k.sewa();
            double total = k.hitungBiayaSewa(hari); // runtime polymorphism
            System.out.printf("Sewa %s %s selama %d hari = Rp%,.0f%n",
                    k.getJenis(), k.getMerek(), hari, total);
            return total;
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("[!]Gagal menyewa: " + e.getMessage());
            return GAGAL;
        }
    }

    // OVERLOADING 2: sewa dengan opsi sopir
    public double sewa(String plat, int hari, boolean denganSopir) {
        try {
            Kendaraan k = cariKendaraan(plat);
            k.sewa();
            double total = k.hitungBiayaSewa(hari);
            if (denganSopir) total += BIAYA_SOPIR_PER_HARI * hari;
            System.out.printf("Sewa %s %s selama %d hari (%s) = Rp%,.0f%n",
                    k.getJenis(), k.getMerek(), hari,
                    denganSopir ? "dengan sopir" : "tanpa sopir", total);
            return total;
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("[!]Gagal menyewa: " + e.getMessage());
            return GAGAL;
        }
    }

    // Sewa + asuransi (jika kendaraan mendukung)
    public double sewaDenganAsuransi(String plat, int hari) {
        double total = sewa(plat, hari);
        if (total == GAGAL) return GAGAL; // sewa gagal, hentikan

        Kendaraan k = cariKendaraan(plat);
        if (k instanceof Asuransi) {
            Asuransi a = (Asuransi) k;
            double premi = a.hitungPremi(hari);
            System.out.printf("   + %s: Rp%,.0f%n", a.getNamaPaket(), premi);
            total += premi;
        } else {
            System.out.println("   (" + k.getJenis() + " tidak mendukung asuransi)");
        }
        System.out.printf("   Total akhir: Rp%,.0f%n", total);
        return total;
    }

    public void kembalikan(String plat) {
        try {
            cariKendaraan(plat).kembalikan();
            System.out.println("Kendaraan " + plat + " dikembalikan.");
        } catch (IllegalArgumentException e) {
            System.out.println("[!]Gagal mengembalikan: " + e.getMessage());
        }
    }

    public void tampilkanSemua() {
        for (Kendaraan k : daftar) {
            k.tampilkanInfo(); // runtime polymorphism
        }
    }
}