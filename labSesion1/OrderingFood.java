package Java.PBO.labSesion1;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderingFood {

    // Konstanta (final)
    static final double TAXES = 0.15;               // persentase pajak yang ditambahkan ke tiap item
    static final double DELIVERY_FEE = 5000;        // biaya kirim standar
    static final double MIN_FREE_DELIVERY = 40000;  // minimun belanja untuk mendapat gratis ongkir

    public static void main(String[] args) {
        // Scanner — dipakai untuk membaca input dari keyboard (pilihan menu & jawaban user)
        Scanner input = new Scanner(System.in);

        // ArrayList (Collection) — menyimpan kumpulan objek Menu yang jumlahnya dinamis
        ArrayList<Menu> menuList = new ArrayList<>();

        // Setiap baris ini memanggil constructor class Menu untuk membuat object baru
        // lalu langsung dimasukkan ke ArrayList
        menuList.add(new Menu("F1", "Nasi Goreng", 15000.00, 'f'));
        menuList.add(new Menu("F2", "Nasi Goreng Spesial", 20000.00, 'f'));
        menuList.add(new Menu("F3", "Nasi Goreng Mawut", 17000.00, 'f'));
        menuList.add(new Menu("F4", "Nasi Goreng Sosis", 17000.00, 'f'));
        menuList.add(new Menu("D1", "Es Teh", 4000.00, 'd'));
        menuList.add(new Menu("D2", "Es Jeruk", 5000.00, 'd'));
        menuList.add(new Menu("D3", "Es Teler", 9000.00, 'd'));

        // ArrayList kosong sebagai "keranjang belanja", diisi menu yang dipilih user nanti
        ArrayList<Menu> cart = new ArrayList<>();

        System.out.println("=== WELCOME TO WARUNG JAVA ===");

        //Menampilkan Menu
        System.out.println("\n----- MENU -----");
        
        // Looping (for-each) — dipakai untuk menelusuri setiap elemen ArrayList
        // tanpa perlu tahu index-nya, cocok untuk sekadar menampilkan semua isi
        for (Menu m : menuList) {
            m.getInfo();
        }
        
        boolean continueOrdering = true;
        
        // Looping (while) — dipakai karena jumlah pesanan tidak diketahui di awal;
        while (continueOrdering) {

            System.out.print("\nInput the menu code (example: F1): ");
            // String method: trim() membuang spasi di awal/akhir input,
            // toUpperCase() menyeragamkan huruf jadi kapital biar cocok dengan kode menu (misal "f1" -> "F1")
            String inputCode = input.nextLine().trim().toUpperCase();

            // Exception handling (try-catch) — menangani kondisi kode menu yang tidak terdaftar di daftar menu
            try {
                Menu pickMenu = searchMenu(menuList, inputCode);
                if (pickMenu == null) {
                    // throw — melempar exception secara manual saat data tidak ditemukan,
                    // akan langsung ditangkap oleh blok catch di bawah
                    throw new IllegalArgumentException("The food code '" + inputCode + "' is Not Found!");
                }
                cart.add(pickMenu);
                System.out.println("# " + pickMenu.getName() + " is added to cart.");

            } catch (IllegalArgumentException e) {
                // Pesan error diambil dari getMessage(), lalu ditampilkan ke user
                System.out.println(" Error: " + e.getMessage());
            }

            System.out.print("Continue ordering? (y/n): ");
            String answer = input.nextLine().trim();

            // Tipe data char — dipakai untuk menyimpan satu huruf jawaban user (y/n)
            // agar pengecekan lebih ringkas dibanding membandingkan seluruh String.
            // Kondisional (ternary if-else): jika input kosong dianggap 'n' (berhenti),
            // kalau tidak, ambil huruf pertama dan ubah ke huruf kecil dengan toLowerCase()
            char picked = answer.isEmpty() ? 'n' : Character.toLowerCase(answer.charAt(0));

            // Kondisional (if-else) — jika user memasukkan bukan 'y', maka program berhenti
            if (picked != 'y') {
                continueOrdering = false;
            }
        }

        double finalTotal = 0;

        // Looping (for biasa dengan index) — untuk menghitung total belanja (sebelum ongkir)
        for (int i = 0; i < cart.size(); i++) {
            finalTotal += cart.get(i).priceAfterTax();
        }

        // Kondisional (if-else) — menentukan apakah dapat gratis ongkir atau tidak
        double deliveryFeeFinal;
        if (finalTotal >= MIN_FREE_DELIVERY) {
            deliveryFeeFinal = 0;
        } else {
            deliveryFeeFinal = DELIVERY_FEE;
        }

        // menjumlahan total belanja dan ongkir
        double needToPay = finalTotal + deliveryFeeFinal;

        // cek apakah item di cart kosong. jika iya, average otomatis bernilai 0
        // jika tidak, average akan dihitung
        double averagePrice = cart.isEmpty() ? 0 : finalTotal / cart.size();

        // Menampilkan hasil akhir transaksi ke layar lewat System.out.println()
        System.out.println("\n===== PAYING RECEIPT =====");
        if (cart.isEmpty()) {
            System.out.println("None ordered.");
        } else {
            // Looping (for-each) — menampilkan tiap item pesanan beserta harganya satu per satu
            for (Menu m : cart) {
                System.out.println("- " + m.getName() + " : Rp" + m.priceAfterTax());
            }
            System.out.println("----------------------------");
            System.out.println("Subtotal (+tax) : Rp" + finalTotal);
            System.out.println("Delivery fee    : Rp" + deliveryFeeFinal +
                    (deliveryFeeFinal == 0 ? " (Gratis ongkir!)" : ""));
            System.out.println("Average/menu    : Rp" + averagePrice);
            System.out.println("\nTOTAL           : Rp" + needToPay);
            System.out.println("=============================");
            System.out.println("Thank You for Ordering in Warung Java!");
        }

        // Menutup Scanner setelah tidak dipakai lagi
        input.close();
    }

    // Method static terpisah dari main — bertugas mencari objek Menu berdasarkan kode,
    // memakai looping (for-each) dan String method equals() untuk mencocokkan kode secara persis
    static Menu searchMenu(ArrayList<Menu> menuList, String code) {
        for (Menu m : menuList) {
            if (m.getCode().equals(code)) {
                return m;
            }
        }
        return null;
    }
}
