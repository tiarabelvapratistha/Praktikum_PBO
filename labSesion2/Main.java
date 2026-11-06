public class Main {
    public static void main(String[] args) {
        RentalService rental = new RentalService();

        // Runtime polymorphism: objek subclass disimpan di referensi superclass
        Kendaraan k1 = new Mobil("AA 1234 BC", "Toyota Avanza", 350000, 7);
        Kendaraan k2 = new Motor("AA 5678 DE", "Honda Vario", 80000, 160);
        Kendaraan k3 = new Bus("AA 9012 FG", "Hino Medium Bus", 1500000, 35);

        rental.tambahKendaraan(k1);
        rental.tambahKendaraan(k2);
        rental.tambahKendaraan(k3);

        System.out.println("=== DAFTAR KENDARAAN ===");
        rental.tampilkanSemua();

        System.out.println("\n=== DEMO ENCAPSULATION ===");
        System.out.println("Tarif awal Avanza: " + k1.getTarifPerHari());
        k1.setTarifPerHari(375000);
        System.out.println("Tarif terkini Avanza: " + k1.getTarifPerHari());
        k1.setTarifPerHari(-5000);
        System.out.println("Tarif terkini Avanza: " + k1.getTarifPerHari());

        System.out.println("\n=== DEMO POLYMORPHISM (OVERLOADING & OVERRIDING) ===");
        rental.sewa("AA 5678 DE", 3);                   // overloading 1
        rental.sewa("AA 1234 BC", 7, true); // overloading 2
        rental.sewaDenganAsuransi("AA 9012 FG", 4);     // interface Asuransi

        System.out.println("\n=== COBA SEWA KENDARAAN YANG SEDANG DISEWA ===");
        rental.sewa("AA 5678 DE", 1);
        rental.sewa("AA 1234 BC", 2);

        System.out.println("\n=== PENGEMBALIAN ===");
        rental.kembalikan("AA 5678 DE");

        System.out.println("\n=== STATUS AKHIR ===");
        rental.tampilkanSemua();

        System.out.println("\n=== DEMO INTERFACE ===");
        Kendaraan[] semua = {k1, k2, k3};
        for (Kendaraan k : semua) {
            if (k instanceof Asuransi) {
                Asuransi a = (Asuransi) k;
                System.out.printf("%s -> %s, premi 5 hari: Rp%,.0f%n",
                        k.getMerek(), a.getNamaPaket(), a.hitungPremi(5));
            }
        }
    }
}
