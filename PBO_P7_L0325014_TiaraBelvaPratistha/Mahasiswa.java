package PBO_P7_L0325014_TiaraBelvaPratisha;

public class Mahasiswa {
    // Konstanta standar kelulusan (final + static)
    public static final double FINAL_PASSING_GRADE = 70.0;

    // Reference type: String | Primitive type: double
    private String nama;
    private final String nim;
    private double nilai;

    public Mahasiswa(String nama, String nim, double nilai) throws NilaiInvalidException {
        this.nama = rapikanNama(nama);

        String nimBersih = nim.trim();
        // ASSERTION: aktif jika program dijalankan dengan opsi -ea
        assert !nimBersih.isEmpty() : "NIM tidak boleh kosong!";
        this.nim = nimBersih;

        setNilai(nilai); // validasi nilai dilakukan di setter
    }

    // Merapikan nama: hapus spasi berlebih lalu ubah ke huruf kapital
    private static String rapikanNama(String nama) {
        return nama.trim().replaceAll("\\s+", " ").toUpperCase();
    }

    // Memastikan NIM tidak kosong dan hanya berisi angka
    public static boolean isNimValid(String nim) {
        if (nim == null || nim.isEmpty()) {
            return false;
        }
        for (char c : nim.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public String getNama() { return nama; }
    public String getNim() { return nim; }
    public double getNilai() { return nilai; }

    public void setNilai(double nilai) throws NilaiInvalidException {
        // Bentuk negasi dipakai agar NaN juga ditolak
        if (!(nilai >= 0 && nilai <= 100)) {
            throw new NilaiInvalidException(nilai);
        }
        this.nilai = nilai;
    }

    // Pemetaan nilai ke grade huruf (If-Else)
    public char getGrade() {
        if (nilai >= 85) {
            return 'A';
        } else if (nilai >= 75) {
            return 'B';
        } else if (nilai >= FINAL_PASSING_GRADE) {
            return 'C';
        } else {
            return 'D';
        }
    }

    // Keterangan grade (Switch-Case)
    public String getKeterangan() {
        switch (getGrade()) {
            case 'A': return "Sangat Baik";
            case 'B': return "Baik";
            case 'C': return "Cukup";
            default:  return "Kurang";
        }
    }

    // Status kelulusan (primitive boolean)
    public boolean isLulus() {
        return nilai >= FINAL_PASSING_GRADE;
    }
}
