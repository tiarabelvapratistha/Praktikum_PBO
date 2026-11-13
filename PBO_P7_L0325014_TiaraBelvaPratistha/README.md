# Program Pengelolaan Nilai Mahasiswa (Java)

Program console untuk menginput data mahasiswa, memvalidasinya, menghitung statistik kelas, lalu menyimpan rekapitulasinya ke file `laporan_nilai.txt`.

---

## 1. Struktur Package

```
NilaiMahasiswa/
|
│── Mahasiswa.java               (kelas data)
│── NilaiInvalidException.java   (custom exception)
│── Main.java                    (kelas utama: input, proses, output)
└── README.md
```


---

## 2. Rincian per File

### 2.1 `NilaiInvalidException.java`

**Materi yang dipakai:** Custom Exception, Inheritance (`extends`), Constructor dan `super()`, `final`.

| Jenis | Nama | Keterangan |
|---|---|---|
| Class | `NilaiInvalidException extends Exception` | *Checked exception*: pemanggil wajib menangani dengan `try-catch` atau `throws` |
| Variabel | `private final double nilai` | Menyimpan nilai yang ditolak (primitive `double`) |
| Constructor | `NilaiInvalidException(double nilai)` | Memanggil `super("Nilai harus antara 0 dan 100, tetapi diberikan: " + nilai)` untuk mengisi pesan error |
| Method | `getNilai()` | Mengembalikan nilai yang menyebabkan exception |

**Exception ini memegang:** pesan error yang menjelaskan nilai tidak valid, ditambah nilai yang salah itu sendiri (lewat `getNilai()`).

### 2.2 `Mahasiswa.java`

**Materi yang dipakai:**
- Class, Constructor, Variabel, dan `final` (Pertemuan 2)
- If-Else, Switch-Case, tipe primitif dan reference (Pertemuan 3)
- Exception, Assertion, String, dan Character (Pertemuan 4)

**Variabel**

| Variabel | Tipe | Modifier | Keterangan |
|---|---|---|---|
| `FINAL_PASSING_GRADE` | `double` (primitive) | `public static final` | Konstanta standar kelulusan, nilainya `70.0` |
| `nama` | `String` (reference) | `private` | Nama mahasiswa (sudah dirapikan) |
| `nim` | `String` (reference) | `private final` | NIM, tidak bisa diubah setelah objek dibuat |
| `nilai` | `double` (primitive) | `private` | Nilai mahasiswa (0 sampai 100) |
| `nimBersih` | `String` | variabel lokal di constructor | NIM setelah `trim()` |
| `c` | `char` | variabel lokal di `isNimValid()` | Karakter yang sedang diperiksa pada perulangan |

**Constructor**

| Constructor | Keterangan |
|---|---|
| `Mahasiswa(String nama, String nim, double nilai) throws NilaiInvalidException` | Merapikan nama, memeriksa NIM dengan `assert`, menyimpan NIM, lalu memanggil `setNilai()` untuk memvalidasi nilai |

**Method**

| Method | Tipe return | Fungsi | Materi |
|---|---|---|---|
| `rapikanNama(String)` | `String` (private static) | `trim()`, `replaceAll("\\s+", " ")`, `toUpperCase()` | String |
| `isNimValid(String)` | `boolean` (public static) | `false` jika `null`, kosong, atau ada karakter bukan angka (`Character.isDigit()`) | Character, Looping |
| `getNama()`, `getNim()`, `getNilai()` | `String` / `double` | Getter | Encapsulation |
| `setNilai(double)` | `void`, `throws NilaiInvalidException` | Melempar exception jika nilai di luar 0 sampai 100 (termasuk `NaN`) | Exception |
| `getGrade()` | `char` | If-Else: A ≥ 85, B ≥ 75, C ≥ 70, selain itu D | Kondisional |
| `getKeterangan()` | `String` | Switch-Case: A = Sangat Baik, B = Baik, C = Cukup, D = Kurang | Kondisional |
| `isLulus()` | `boolean` | `true` jika `nilai >= FINAL_PASSING_GRADE` | Primitive boolean |

**Peran Assertion:** `assert !nimBersih.isEmpty() : "NIM tidak boleh kosong!";` berfungsi sebagai pengaman internal di constructor. Jika program dijalankan dengan `-ea` dan NIM kosong, Java melempar `AssertionError` dengan pesan tersebut.

### 2.3 `Main.java`

**Materi yang dipakai:** Collection (`ArrayList`), Looping (`for`, `for-each`, `while`), Console I/O (`Scanner`), File I/O (`BufferedWriter`, `FileWriter`), Try-with-Resources, Try-Catch-Finally, String formatting.

**Variabel**

| Variabel | Tipe | Lokasi | Keterangan |
|---|---|---|---|
| `NAMA_FILE` | `String` (`private static final`) | class | Nama file laporan: `"laporan_nilai.txt"` |
| `daftar` | `ArrayList<Mahasiswa>` | `main()` | Menyimpan seluruh objek mahasiswa |
| `scanner` | `Scanner` | `main()` | Membaca input; ditutup otomatis oleh try-with-resources |
| `jumlah` | `int` | `main()`, `bacaJumlah()` | Jumlah mahasiswa yang akan diinput |
| `laporan` | `String` | `main()` | Teks laporan lengkap |
| `nama`, `nim`, `input`, `nilai` | `String` / `double` | `bacaMahasiswa()` | Penampung input sementara |
| `sep`, `garis`, `garisTipis`, `sb` | `String` / `StringBuilder` | `buatLaporan()` | Pemisah baris, garis pembatas, dan penyusun teks |
| `total`, `rataRata` | `double` | `buatLaporan()` | Jumlah dan rata-rata nilai |
| `lulus`, `tidakLulus`, `no` | `int` | `buatLaporan()` | Penghitung lulus, tidak lulus, dan nomor urut |
| `tertinggi`, `terendah` | `Mahasiswa` (reference) | `buatLaporan()` | Penyimpan mahasiswa dengan nilai tertinggi dan terendah |

**Method**

| Method | Fungsi | Materi |
|---|---|---|
| `main(String[] args)` | Alur utama: input, buat laporan, tampilkan, simpan | Collection, Try-with-Resources |
| `bacaJumlah(Scanner)` | Meminta jumlah mahasiswa sampai valid (bilangan bulat > 0) | Looping `while`, `Integer.parseInt()` |
| `bacaMahasiswa(Scanner, ArrayList)` | Meminta nama, NIM, dan nilai; mengulang jika tidak valid; membuat objek `Mahasiswa` | Looping, Exception, Character |
| `nimSudahAda(ArrayList, String)` | Mengecek NIM duplikat | For-each |
| `buatLaporan(ArrayList)` | Menyusun tabel dan statistik kelas dalam satu `String` | For-each, `StringBuilder`, `String.format()` |
| `simpanKeFile(String)` | Menulis laporan ke `laporan_nilai.txt` | File I/O, Try-Catch-Finally |

---

## 3. Peran Exception dan Assertion

| Jenis | Dilempar oleh | Ditangani di | Pemicu | Akibat |
|---|---|---|---|---|
| `NilaiInvalidException` | `Mahasiswa.setNilai()` (lewat constructor) | `Main.bacaMahasiswa()` | Nilai < 0 atau > 100 | Pesan error ditampilkan, nilai diminta ulang |
| `NumberFormatException` | `Integer.parseInt()` di `bacaJumlah()`, `Double.parseDouble()` di `bacaMahasiswa()` | Method yang sama | Input bukan angka | Pesan error ditampilkan, input diminta ulang |
| `IOException` | `FileWriter`, `BufferedWriter` | `Main.simpanKeFile()` | File gagal dibuat, ditulis, atau ditutup | Pesan `Gagal menyimpan laporan` ditampilkan |
| `AssertionError` | `assert` di constructor `Mahasiswa` | Tidak ditangani (disengaja) | NIM kosong saat dijalankan dengan `-ea` | Program berhenti dengan pesan `NIM tidak boleh kosong!` |

Catatan:
- NIM kosong atau mengandung huruf **lebih dulu ditolak** oleh `Mahasiswa.isNimValid()` di `Main`, sehingga `assert` hanya berperan sebagai lapisan pengaman terakhir.
- `finally` di `simpanKeFile()` selalu mencetak `Proses penyimpanan selesai.`, baik penyimpanan berhasil maupun gagal.
- Jika input pengguna ditutup mendadak (misalnya `Ctrl + D`), `Scanner` melempar `NoSuchElementException` yang tidak ditangani di program ini.

---

## 4. Alur Program

1. `main()` membuat `ArrayList<Mahasiswa> daftar` dan membuka `Scanner` dengan try-with-resources.
2. `bacaJumlah()` meminta jumlah mahasiswa sampai valid.
3. Untuk tiap mahasiswa, `bacaMahasiswa()` berjalan:
   - **Nama:** diulang sampai tidak kosong.
   - **NIM:** diulang sampai hanya berisi angka dan belum dipakai mahasiswa lain.
   - **Nilai:** diulang sampai berupa angka dalam rentang 0 sampai 100. Constructor `Mahasiswa` merapikan nama, memeriksa NIM dengan `assert`, lalu memvalidasi nilai.
4. Objek yang valid dimasukkan ke `daftar` dengan `add()`.
5. `Scanner` ditutup otomatis, lalu `buatLaporan()` menelusuri `daftar` dengan for-each untuk menyusun tabel dan menghitung statistik.
6. Laporan ditampilkan di layar.
7. `simpanKeFile()` menulis laporan ke `laporan_nilai.txt`, lalu `finally` mencetak pesan selesai.

---

## 5. Contoh Penggunaan

### Contoh input (termasuk input yang salah)

```
=== PENGELOLAAN NILAI MAHASISWA ===
Jumlah mahasiswa yang akan diinput: 4

Data mahasiswa ke-1
Nama  :   budi   santoso
NIM   : 21A01
[!] NIM harus berisi angka saja dan tidak boleh kosong.
NIM   : 2101001
Nilai : 105
[!] Nilai harus antara 0 dan 100, tetapi diberikan: 105.0
Nilai : 90

Data mahasiswa ke-2
Nama  : Siti Aminah
NIM   : 2101002
Nilai : 78

Data mahasiswa ke-3
Nama  : Andi Pratama
NIM   : 2101003
Nilai : 72

Data mahasiswa ke-4
Nama  : Dewi Lestari
NIM   : 2101004
Nilai : 60
```

Pada mahasiswa ke-1: NIM `21A01` ditolak oleh `isNimValid()` (ada huruf), nilai `105` ditolak lewat `NilaiInvalidException`, dan nama `  budi   santoso` otomatis menjadi `BUDI SANTOSO`.

### Contoh isi `laporan_nilai.txt`

```
==========================================================================
                      LAPORAN NILAI MAHASISWA
==========================================================================
No   NIM          Nama                           Nilai Grade  Status
--------------------------------------------------------------------------
1    2101001      BUDI SANTOSO                   90.00 A      LULUS
2    2101002      SITI AMINAH                    78.00 B      LULUS
3    2101003      ANDI PRATAMA                   72.00 C      LULUS
4    2101004      DEWI LESTARI                   60.00 D      TIDAK LULUS
--------------------------------------------------------------------------
STATISTIK KELAS
Jumlah mahasiswa : 4
Rata-rata nilai   : 75.00
Jumlah lulus     : 3
Jumlah tidak lulus: 1
Nilai tertinggi   : 90.00 (BUDI SANTOSO)
Nilai terendah    : 60.00 (DEWI LESTARI)
Standar kelulusan : 70.0
==========================================================================
```

Setelah laporan tampil di layar, program mencetak:

```
Laporan berhasil disimpan ke laporan_nilai.txt
Proses penyimpanan selesai.
```

---

## 6. Ringkasan Pemetaan Materi

| Materi | Letak |
|---|---|
| Package, Class, Constructor, Variabel, `final` | `Mahasiswa` (constructor, `FINAL_PASSING_GRADE`), struktur `model` dan `app` |
| Kondisional | `Mahasiswa.getGrade()` (if-else), `getKeterangan()` (switch-case), `isLulus()` |
| Looping | `Main.buatLaporan()`, `nimSudahAda()` (for-each), `bacaJumlah()`, `bacaMahasiswa()` (while), `Mahasiswa.isNimValid()` (for-each pada karakter) |
| Primitive vs Reference | Primitive: `double`, `int`, `boolean`, `char`. Reference: `Mahasiswa`, `String`, `ArrayList`, `Scanner` |
| Custom Exception | `NilaiInvalidException`, dilempar di `Mahasiswa.setNilai()` |
| Assertion | Constructor `Mahasiswa` |
| String dan Character | `rapikanNama()` (`trim`, `replaceAll`, `toUpperCase`), `isNimValid()` (`Character.isDigit`) |
| Collection | `ArrayList<Mahasiswa>` di `Main` (`add`, `size`, for-each) |
| Console I/O | `Scanner` di `Main` |
| File I/O | `Main.simpanKeFile()` (`BufferedWriter`, `FileWriter`, try-with-resources, finally) |