# Program Pengelolaan Nilai Siswa (File I/O)

Program Java prosedural sederhana yang:

1. membaca data siswa (nama dan nilai) dari konsol dengan `Scanner`,
2. menulis data tersebut beserta ringkasannya ke file `laporan_nilai.txt` memakai `FileWriter` dan `BufferedWriter`,
3. membaca kembali isi file memakai `FileReader` dan `BufferedReader`, lalu menampilkannya di konsol.

---

## 1. Struktur dan Cara Menjalankan

```
Global_language/
└── Java/
    └── PBO/
        └── prak6/
            ├── NilaiSiswa.java      (program utama, package Java.PBO.prak6)
            ├── laporan_nilai.txt    (dibuat otomatis oleh program)
            └── README.md
```

Karena file memakai `package Java.PBO.prak6;`, perintah kompilasi dan eksekusi dijalankan dari folder **di atas** `Java`, yaitu `Global_language`:

```bash
cd C:/Tera/Coding/Global_language
javac Java/PBO/prak6/NilaiSiswa.java
java Java.PBO.prak6.NilaiSiswa
```

Kalau memakai tombol Run di VS Code, langkah di atas ditangani otomatis. Program membutuhkan **Java 11 atau lebih baru** karena memakai `String.repeat()`.

---

## 2. Variabel Konstanta

| Konstanta | Tipe | Nilai | Fungsi |
|---|---|---|---|
| `NAMA_FILE` | `String` | `"laporan_nilai.txt"` | Nama file laporan |
| `NAMA_FOLDER` | `String` | `"C:/Tera/Coding/Global_language/Java/PBO/prak6"` | Folder tujuan file laporan (path absolut) |
| `FILE_LAPORAN` | `File` | `new File(NAMA_FOLDER, NAMA_FILE)` | Objek yang mewakili lokasi lengkap file; dipakai oleh writer dan reader |
| `BATAS_LULUS` | `double` | `70.0` | Batas nilai kelulusan |

`new File(NAMA_FOLDER, NAMA_FILE)` menggabungkan folder dan nama file menjadi satu path dengan pemisah yang sesuai sistem operasi. Itu sebabnya di output muncul `\` (Windows) walaupun di kode ditulis `/`.

---

## 3. Penjelasan Function

| Function | Fungsi |
|---|---|
| `main(String[] args)` | Mengatur alur: baca jumlah siswa, tulis laporan, lalu baca laporan jika penulisan berhasil |
| `bacaJumlah(Scanner)` | Meminta jumlah siswa sampai berupa bilangan bulat lebih dari 0 |
| `bacaNama(Scanner)` | Meminta nama sampai tidak kosong |
| `bacaNilai(Scanner)` | Meminta nilai sampai berupa angka dalam rentang 0 sampai 100 (koma dan titik sama-sama diterima) |
| `tulisLaporan(Scanner, int)` | Membaca data tiap siswa, menentukan status, dan **menulis ke file** (dijelaskan di bagian 4) |
| `bacaLaporan()` | **Membaca file** baris demi baris dan menampilkannya ke konsol (dijelaskan di bagian 5) |

Ketiga function `baca...(Scanner)` memakai `while (true)` yang baru berhenti dengan `return` saat input valid. `NumberFormatException` yang muncul dari `Integer.parseInt()` atau `Double.parseDouble()` ditangkap supaya program tidak berhenti saat pengguna salah ketik.

---

## 4. Function `tulisLaporan()`: File Writer

```java
try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_LAPORAN))) {
    writer.write("LAPORAN NILAI SISWA");
    writer.newLine();
    ...
}
```

### Objek yang terlibat

| Objek | Peran |
|---|---|
| `File` (`FILE_LAPORAN`) | Menunjukkan lokasi file tujuan |
| `FileWriter` | Membuka file tujuan untuk ditulis. Jika file belum ada, dibuat baru. Jika sudah ada, **isinya ditimpa** (karena tidak memakai mode `append`) |
| `BufferedWriter` | Pembungkus `FileWriter` yang menampung teks di memori (buffer) dulu sebelum dikirim ke file, sehingga penulisan lebih efisien |

### Method yang dipakai

| Method | Fungsi |
|---|---|
| `writer.write(String)` | Menulis teks ke buffer (belum menambah baris baru) |
| `writer.newLine()` | Menulis pemisah baris sesuai sistem operasi (`\r\n` di Windows) |

### Urutan isi yang ditulis

1. **Header:** judul, garis `=`, judul kolom, garis `-`.
2. **Isi (di dalam loop `for`):** untuk setiap siswa program membaca nama dan nilai, menentukan status dengan if-else, menambah `total`, `lulus`, atau `tidakLulus`, lalu menulis satu baris data. Baris data diformat dengan `String.format`:

   | Format | Arti |
   |---|---|
   | `%-4d` | Nomor urut, rata kiri, lebar 4 |
   | `%-25s` | Nama, rata kiri, lebar 25 |
   | `%7.2f` | Nilai, rata kanan, lebar 7, dua angka desimal |
   | `Locale.US` | Memastikan pemisah desimal selalu titik |

3. **Ringkasan (setelah loop):** rata-rata nilai (`total / jumlah`), jumlah lulus, dan jumlah tidak lulus, ditutup dengan garis `=`.

### Penutupan resource

`BufferedWriter` dideklarasikan di dalam tanda kurung `try (...)` (**try-with-resources**). Saat blok `try` selesai, baik normal maupun karena error, Java otomatis memanggil `close()` yang sekaligus menulis sisa isi buffer ke file (`flush`). Tanpa penutupan ini, sebagian isi file bisa hilang.

### Penanganan error

- Semua operasi file bisa melempar `IOException`, sehingga ditangkap dengan `catch (IOException e)`. Pesannya ditampilkan sebagai `[!] Gagal menulis file: ...`.
- Function mengembalikan `true` jika berhasil dan `false` jika gagal. `main()` memakai nilai ini agar pembacaan file **hanya dijalankan jika penulisan berhasil**.
- `FileWriter` tidak membuat folder. Jika folder `prak6` belum ada, penulisan gagal dengan `FileNotFoundException` (turunan `IOException`).

---

## 5. Function `bacaLaporan()`: File Reader

```java
try (BufferedReader reader = new BufferedReader(new FileReader(FILE_LAPORAN))) {
    String baris;
    while ((baris = reader.readLine()) != null) {
        System.out.println(baris);
    }
}
```

### Objek yang terlibat

| Objek | Peran |
|---|---|
| `FileReader` | Membuka file untuk dibaca sebagai teks. Jika file tidak ada, melempar `FileNotFoundException` |
| `BufferedReader` | Pembungkus `FileReader` yang membaca dalam blok besar dan menyediakan `readLine()` |

### Cara kerja loop

`reader.readLine()` mengembalikan satu baris teks (tanpa karakter pemisah barisnya). Saat file sudah habis, method ini mengembalikan **`null`**. Satu ekspresi pada kondisi `while` melakukan tiga hal:

1. Memanggil `readLine()`.
2. Menyimpan hasilnya ke variabel `baris`.
3. Memeriksa apakah hasilnya `null`. Jika ya, loop berhenti.

Selama bukan `null`, baris dicetak dengan `System.out.println(baris)`. Hasilnya, isi file tampil di konsol persis seperti yang ditulis, sebagai bukti data benar-benar tersimpan dan terbaca kembali.

### Penutupan dan error

`BufferedReader` juga memakai try-with-resources sehingga ditutup otomatis. `IOException` ditangkap dan ditampilkan sebagai `[!] Gagal membaca file: ...`.

---

## 6. Alur Program

1. `main()` membuka `Scanner` dengan try-with-resources.
2. `bacaJumlah()` meminta jumlah siswa.
3. `tulisLaporan()` membuka file untuk ditulis, menulis header, lalu untuk tiap siswa: meminta nama dan nilai, menentukan status, menulis satu baris.
4. Setelah loop, ringkasan ditulis, file ditutup otomatis, dan pesan `Data berhasil ditulis ke ...` muncul.
5. Jika berhasil, `bacaLaporan()` membuka file yang sama untuk dibaca dan mencetak seluruh isinya.

---

## 7. Output Program

### Output di konsol

```
=== PENGELOLAAN NILAI SISWA ===
Jumlah siswa: 3

Data siswa ke-1
Nama  : Tera
Nilai : 80

Data siswa ke-2
Nama  : Pedan
Nilai : 60

Data siswa ke-3
Nama  : Jaki
Nilai : 70

Data berhasil ditulis ke C:\Tera\Coding\Global_language\Java\PBO\prak6\laporan_nilai.txt

=== ISI FILE C:\Tera\Coding\Global_language\Java\PBO\prak6\laporan_nilai.txt ===
LAPORAN NILAI SISWA
==================================================
No   Nama                        Nilai  Status
--------------------------------------------------
1    Tera                        80.00  Lulus
2    Pedan                       60.00  Tidak Lulus
3    Jaki                        70.00  Lulus
--------------------------------------------------
Rata-rata nilai kelas : 70.00
Jumlah lulus          : 2
Jumlah tidak lulus    : 1
==================================================
```

### Penjelasan output

| Bagian output | Dihasilkan oleh | Penjelasan |
|---|---|---|
| `Jumlah siswa`, `Nama`, `Nilai` | `bacaJumlah()`, `bacaNama()`, `bacaNilai()` | Input pengguna lewat `Scanner` |
| `Data berhasil ditulis ke ...` | akhir `tulisLaporan()` | Penanda bahwa file selesai ditulis dan ditutup |
| `=== ISI FILE ... ===` dan semua baris sesudahnya | `bacaLaporan()` | Isi file dibaca dengan `readLine()` dan dicetak baris demi baris. Ini sama persis dengan isi `laporan_nilai.txt` |
| Path berpemisah `\` | `FILE_LAPORAN` | Objek `File` mencetak path sesuai Windows |
| Tera 80 → Lulus | if-else di `tulisLaporan()` | 80 ≥ 70 |
| Pedan 60 → Tidak Lulus | if-else di `tulisLaporan()` | 60 < 70 |
| Jaki 70 → Lulus | if-else di `tulisLaporan()` | Nilai tepat 70 termasuk lulus karena kondisinya `nilai >= BATAS_LULUS` |
| Rata-rata 70.00 | ringkasan di `tulisLaporan()` | (80 + 60 + 70) / 3 = 70 |
| Lulus 2, Tidak Lulus 1 | ringkasan di `tulisLaporan()` | Dihitung dengan `lulus++` dan `tidakLulus++` di dalam loop |

---

## 8. Catatan

- File `laporan_nilai.txt` **ditimpa** setiap program dijalankan. Jika ingin menambah di akhir file, gunakan `new FileWriter(FILE_LAPORAN, true)`.
- Karena `NAMA_FOLDER` berupa path absolut, program hanya cocok dijalankan di komputer dengan folder tersebut. Jika folder belum ada, tambahkan `new File(NAMA_FOLDER).mkdirs();` sebelum blok `try` di `tulisLaporan()`.
- Batas lulus memakai `>=`, jadi nilai 70 dianggap **Lulus**.