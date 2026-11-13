package Java.PBO.prak6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Locale;
import java.util.Scanner;

public class NilaiSiswa {
    private static final String NAMA_FILE = "laporan_nilai.txt";
    private static final String NAMA_FOLDER = "C:/Tera/Coding/Global_language/Java/PBO/prak6";
    private static final File FILE_LAPORAN = new File(NAMA_FOLDER, NAMA_FILE);
    private static final double BATAS_LULUS = 70.0;

    public static void main(String[] args) {
        // Scanner ditutup otomatis oleh try-with-resources
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== PENGELOLAAN NILAI SISWA ===");

            // Langkah 1: input console + tulis file
            int jumlah = bacaJumlah(scanner);
            boolean berhasil = tulisLaporan(scanner, jumlah);

            // Langkah 2: baca kembali file dan tampilkan di konsol
            if (berhasil) {
                System.out.println("\n=== ISI FILE " + FILE_LAPORAN + " ===");
                bacaLaporan();
            }
        }
    }

    // ---------- Input console ----------

    private static int bacaJumlah(Scanner scanner) {
        while (true) {
            System.out.print("Jumlah siswa: ");
            String input = scanner.nextLine().trim();
            try {
                int jumlah = Integer.parseInt(input);
                if (jumlah > 0) {
                    return jumlah;
                }
                System.out.println("[!] Jumlah harus lebih dari 0.");
            } catch (NumberFormatException e) {
                System.out.println("[!] Masukkan bilangan bulat yang valid.");
            }
        }
    }

    private static String bacaNama(Scanner scanner) {
        while (true) {
            System.out.print("Nama  : ");
            String nama = scanner.nextLine().trim();
            if (!nama.isEmpty()) {
                return nama;
            }
            System.out.println("[!] Nama tidak boleh kosong.");
        }
    }

    private static double bacaNilai(Scanner scanner) {
        while (true) {
            System.out.print("Nilai : ");
            String input = scanner.nextLine().trim().replace(',', '.');
            try {
                double nilai = Double.parseDouble(input);
                if (nilai >= 0 && nilai <= 100) {
                    return nilai;
                }
                System.out.println("[!] Nilai harus antara 0 dan 100.");
            } catch (NumberFormatException e) {
                System.out.println("[!] Nilai harus berupa angka.");
            }
        }
    }

    // ---------- Tulis file (FileWriter + BufferedWriter) ----------

    private static boolean tulisLaporan(Scanner scanner, int jumlah) {
        double total = 0;
        int lulus = 0;
        int tidakLulus = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_LAPORAN))) {
            writer.write("LAPORAN NILAI SISWA");
            writer.newLine();
            writer.write("=".repeat(50));
            writer.newLine();
            writer.write(String.format("%-4s %-25s %7s  %s", "No", "Nama", "Nilai", "Status"));
            writer.newLine();
            writer.write("-".repeat(50));
            writer.newLine();

            for (int i = 1; i <= jumlah; i++) {
                System.out.println("\nData siswa ke-" + i);
                String nama = bacaNama(scanner);
                double nilai = bacaNilai(scanner);

                // Tentukan status langsung di dalam loop
                String status;
                if (nilai >= BATAS_LULUS) {
                    status = "Lulus";
                    lulus++;
                } else {
                    status = "Tidak Lulus";
                    tidakLulus++;
                }
                total += nilai;

                writer.write(String.format(Locale.US, "%-4d %-25s %7.2f  %s", i, nama, nilai, status));
                writer.newLine();
            }

            // Ringkasan di akhir file
            double rataRata = total / jumlah;
            writer.write("-".repeat(50));
            writer.newLine();
            writer.write(String.format(Locale.US, "Rata-rata nilai kelas : %.2f", rataRata));
            writer.newLine();
            writer.write("Jumlah lulus          : " + lulus);
            writer.newLine();
            writer.write("Jumlah tidak lulus    : " + tidakLulus);
            writer.newLine();
            writer.write("=".repeat(50));
            writer.newLine();

            System.out.println("\nData berhasil ditulis ke " + FILE_LAPORAN);
            return true;
        } catch (IOException e) {
            System.out.println("[!] Gagal menulis file: " + e.getMessage());
            return false;
        }
    }

    // ---------- Baca file (FileReader + BufferedReader) ----------

    private static void bacaLaporan() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_LAPORAN))) {
            String baris;
            while ((baris = reader.readLine()) != null) {
                System.out.println(baris);
            }
        } catch (IOException e) {
            System.out.println("[!] Gagal membaca file: " + e.getMessage());
        }
    }
}