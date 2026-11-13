package Java.PBO.prak7;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final String NAMA_FILE = "laporan_nilai.txt";

    public static void main(String[] args) {
        // Collection: ArrayList dinamis, bukan array statis
        ArrayList<Mahasiswa> daftar = new ArrayList<>();

        // Console I/O dengan Scanner (ditutup otomatis lewat try-with-resources)
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== PENGELOLAAN NILAI MAHASISWA ===");
            int jumlah = bacaJumlah(scanner);

            for (int i = 1; i <= jumlah; i++) {
                System.out.println("\nData mahasiswa ke-" + i);
                daftar.add(bacaMahasiswa(scanner, daftar));
            }
        }

        String laporan = buatLaporan(daftar);
        System.out.println("\n" + laporan);
        simpanKeFile(laporan);
    }

    private static int bacaJumlah(Scanner scanner) {
        while (true) {
            System.out.print("Jumlah mahasiswa yang akan diinput: ");
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

    private static Mahasiswa bacaMahasiswa(Scanner scanner, ArrayList<Mahasiswa> daftar) {
        String nama;
        while (true) {
            System.out.print("Nama  : ");
            nama = scanner.nextLine().trim();
            if (!nama.isEmpty()) {
                break;
            }
            System.out.println("[!] Nama tidak boleh kosong.");
        }

        String nim;
        while (true) {
            System.out.print("NIM   : ");
            nim = scanner.nextLine().trim();
            if (!Mahasiswa.isNimValid(nim)) {
                System.out.println("[!] NIM harus berisi angka saja dan tidak boleh kosong.");
            } else if (nimSudahAda(daftar, nim)) {
                System.out.println("[!] NIM sudah terdaftar.");
            } else {
                break;
            }
        }

        while (true) {
            System.out.print("Nilai : ");
            String input = scanner.nextLine().trim().replace(',', '.');
            try {
                double nilai = Double.parseDouble(input);
                return new Mahasiswa(nama, nim, nilai); // bisa melempar NilaiInvalidException
            } catch (NumberFormatException e) {
                System.out.println("[!] Nilai harus berupa angka.");
            } catch (NilaiInvalidException e) {
                System.out.println("[!] " + e.getMessage());
            }
        }
    }

    private static boolean nimSudahAda(ArrayList<Mahasiswa> daftar, String nim) {
        for (Mahasiswa m : daftar) {
            if (m.getNim().equals(nim)) {
                return true;
            }
        }
        return false;
    }

    private static String buatLaporan(ArrayList<Mahasiswa> daftar) {
        String sep = System.lineSeparator();
        String garis = "=".repeat(74);
        String garisTipis = "-".repeat(74);
        StringBuilder sb = new StringBuilder();

        sb.append(garis).append(sep);
        sb.append("                      LAPORAN NILAI MAHASISWA").append(sep);
        sb.append(garis).append(sep);
        sb.append(String.format("%-4s %-12s %-28s %7s %-6s %s",
                "No", "NIM", "Nama", "Nilai", "Grade", "Status")).append(sep);
        sb.append(garisTipis).append(sep);

        double total = 0;
        int lulus = 0;
        int tidakLulus = 0;
        int no = 1;
        Mahasiswa tertinggi = null;
        Mahasiswa terendah = null;

        // Penelusuran dengan for-each
        for (Mahasiswa m : daftar) {
            sb.append(String.format(Locale.US, "%-4d %-12s %-28s %7.2f %-6s %s",
                    no++, m.getNim(), m.getNama(), m.getNilai(), m.getGrade(),
                    m.isLulus() ? "LULUS" : "TIDAK LULUS")).append(sep);

            total += m.getNilai();
            if (m.isLulus()) {
                lulus++;
            } else {
                tidakLulus++;
            }
            if (tertinggi == null || m.getNilai() > tertinggi.getNilai()) {
                tertinggi = m;
            }
            if (terendah == null || m.getNilai() < terendah.getNilai()) {
                terendah = m;
            }
        }

        double rataRata = daftar.isEmpty() ? 0 : total / daftar.size();

        sb.append(garisTipis).append(sep);
        sb.append("STATISTIK KELAS").append(sep);
        sb.append("Jumlah mahasiswa : ").append(daftar.size()).append(sep);
        sb.append(String.format(Locale.US, "Rata-rata nilai   : %.2f", rataRata)).append(sep);
        sb.append("Jumlah lulus     : ").append(lulus).append(sep);
        sb.append("Jumlah tidak lulus: ").append(tidakLulus).append(sep);
        if (tertinggi != null) {
            sb.append(String.format(Locale.US, "Nilai tertinggi   : %.2f (%s)",
                    tertinggi.getNilai(), tertinggi.getNama())).append(sep);
            sb.append(String.format(Locale.US, "Nilai terendah    : %.2f (%s)",
                    terendah.getNilai(), terendah.getNama())).append(sep);
        }
        sb.append(String.format(Locale.US, "Standar kelulusan : %.1f", Mahasiswa.FINAL_PASSING_GRADE)).append(sep);
        sb.append(garis).append(sep);

        return sb.toString();
    }

    // File I/O dengan BufferedWriter + FileWriter
    private static void simpanKeFile(String isi) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NAMA_FILE))) {
            writer.write(isi);
            System.out.println("Laporan berhasil disimpan ke " + NAMA_FILE);
        } catch (IOException e) {
            System.out.println("[!] Gagal menyimpan laporan: " + e.getMessage());
        } finally {
            System.out.println("Proses penyimpanan selesai.");
        }
    }
}