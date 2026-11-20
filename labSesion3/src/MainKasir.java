package Java.PBO.labSesion3;

import java.text.NumberFormat;
import java.util.Locale;

public class MainKasir {
    private static final NumberFormat FORMAT = NumberFormat.getNumberInstance(Locale.forLanguageTag("id-ID"));

    public static void main(String[] args) {
        FORMAT.setMaximumFractionDigits(0);

        ujiPesanan();
        System.out.println();
        simulasiKasir();
    }

    // Tahap 1
    private static void ujiPesanan() {
        System.out.println("=== TAHAP 1: UJI PESANAN ===");
        Pesanan pesanan = new Pesanan();

        pesanan.tambahItem(15000, 2); 
        pesanan.tambahItem(5000, 3);  
        System.out.println("Setelah 2 menu -> subtotal: " + FORMAT.format(pesanan.getSubtotal())
                + ", total: " + FORMAT.format(pesanan.hitungTotal()));

        pesanan.tambahItem(25000, 1); 
        System.out.println("Setelah 3 menu -> subtotal: " + FORMAT.format(pesanan.getSubtotal())
                + ", total: " + FORMAT.format(pesanan.hitungTotal()));

        pesanan.reset();
        System.out.println("Setelah reset  -> subtotal: " + FORMAT.format(pesanan.getSubtotal()));
    }

    // Tahap 2
    private static void simulasiKasir() {
        System.out.println("=== TAHAP 2: DUA KASIR ===");
        Kasir dina = new Kasir("Dina");
        Kasir eko = new Kasir("Eko");

        dina.start();
        eko.start();

        try {
            dina.join();
            eko.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Semua pelanggan selesai dilayani");
    }
}
