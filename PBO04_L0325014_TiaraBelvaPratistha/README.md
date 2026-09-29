# Perpustakaan Mini

Aplikasi konsol manajemen perpustakaan mini yang mengelola buku dan anggota, proses peminjaman/pengembalian, serta laporan analisis aktivitas.

Data awal otomatis terisi secara hardcode: 5 buku dan 2 anggota (`M001` Budi Santoso, `M002` Sari Dewi).

## 1. Struktur Proyek

```
src/library
├── model
│   ├── Book.java                          # data buku
│   └── Member.java                        # data anggota + daftar pinjaman
├── service
│   └── LibraryService.java                # seluruh logika bisnis
├── exception
│   ├── BookNotFoundException.java
│   ├── BookAlreadyBorrowedException.java
│   └── BorrowLimitExceededException.java
└── main
    └── MainApp.java                       # menu & input (Scanner)
```

| Class | Peran |
|---|---|
| `Book` | Atribut: `judul`, `penulis`, `tahunTerbit`, `kategori`, `tersedia`, `jumlahDipinjam`. Diisi lewat constructor. |
| `Member` | Atribut: `id`, `nama`, `daftarPinjaman` (`ArrayList<Book>`), `totalPinjam`. |
| `LibraryService` | Menyimpan `ArrayList<Book>`, `HashMap<String, Member>`, dan `HashMap<String,Integer>` pinjaman per kategori. Berisi semua proses (tambah, cari, pinjam, kembali, laporan). |
| `MainApp` | Menampilkan menu, membaca input, memanggil `LibraryService`, dan menangkap exception. |

## 2. Alur Program

### 2.1 Alur Utama

```
Mulai
  │
  ▼
isiDataAwal()  ── memuat 5 buku + 2 anggota
  │
  ▼
┌─► tampilMenu()
│     │
│     ▼
│   bacaInt("Pilih menu")  ── input bukan angka? ulangi sampai valid
│     │
│     ▼
│   switch(pilihan)
│     ├─ 1 → tambahBuku()
│     ├─ 2 → daftarBuku()
│     ├─ 3 → cariBuku()
│     ├─ 4 → pinjamBuku()
│     ├─ 5 → kembalikanBuku()
│     ├─ 6 → laporan()
│     ├─ 7 → daftarAnggota()
│     ├─ 0 → keluar dari loop ──► "Terima kasih!" ──► Selesai
│     └─ lainnya → "Menu tidak valid."
└─────┘  (kembali ke menu)
```

### 2.2 Alur Tiap Menu

**1. Tambah Buku**
1. Input judul, penulis, tahun (harus angka), kategori.
2. Jika ada field teks kosong, proses dibatalkan.
3. `formatKapital()` mengubah teks menjadi huruf kapital di awal tiap kata (`"clean code"` menjadi `"Clean Code"`) dengan `Character.toUpperCase()`.
4. Objek `Book` dibuat lewat constructor (status awal: tersedia) dan dimasukkan ke `ArrayList`.

**2. Daftar Buku**
Loop seluruh `ArrayList<Book>` dan cetak nomor, judul, penulis, tahun, kategori, dan status (Tersedia/Dipinjam).

**3. Cari Buku**
1. Input kata kunci.
2. Kata kunci dan judul/kategori buku diubah ke huruf kecil dengan `toLowerCase()`.
3. Buku dianggap cocok jika judul **atau** kategori memuat kata kunci (`contains()`).
4. Tampilkan hasil, atau "Tidak ada hasil.".

**4. Pinjam Buku** (`pinjamBuku(idMember, judul)`)
```
Input ID anggota & judul
   │
   ▼
getMember(id) ── tidak terdaftar ──► IllegalArgumentException
   │
   ▼
ASSERT: id tidak kosong, nama tidak kosong, jumlah pinjaman ≤ 3
   │
   ▼
cariPersis(judul) ── tidak ada ──► BookNotFoundException
   │
   ▼
buku sedang dipinjam? ── ya ──► BookAlreadyBorrowedException
   │
   ▼
anggota sudah punya 3 buku? ── ya ──► BorrowLimitExceededException
   │
   ▼
Berhasil: status buku = Dipinjam, jumlahDipinjam++, 
          daftarPinjaman anggota bertambah, totalPinjaman++, 
          hitungan kategori populer bertambah
```
Semua exception ditangkap di `MainApp` dan ditampilkan sebagai pesan `Gagal: ...`.

**5. Kembalikan Buku** (`kembalikanBuku(idMember, judul)`)
1. Validasi anggota dan assertion.
2. Cari buku; jika tidak ada, `BookNotFoundException`.
3. Jika buku tidak ada di daftar pinjaman anggota tersebut, `BookNotFoundException` juga.
4. Berhasil: buku dihapus dari daftar pinjaman anggota, status kembali "Tersedia".

**6. Laporan Perpustakaan** (`laporan()`)
Semua dihitung dengan looping:
- **Jumlah total pinjaman**: counter `totalPinjaman`.
- **Buku paling sering dipinjam**: loop `books`, ambil `jumlahDipinjam` terbesar.
- **Anggota paling aktif**: loop `members`, ambil `totalPinjam` terbesar.
- **Kategori terpopuler**: loop `HashMap` pinjaman per kategori, ambil nilai terbesar.
- **Jumlah buku per kategori**: loop `books` dan hitung ke `HashMap`.

**7. Daftar Anggota Baru**
ID divalidasi dengan `Character.isLetter()` (karakter pertama) dan `Character.isDigit()` (sisanya), contoh valid: `M003`. ID diubah ke huruf besar dan tidak boleh duplikat.

## 3. Contoh Sesi dan Output

Urutan input yang dicoba (data awal, anggota `M001`):

### a. Output Normal
Satu sesi lengkap yang menjalankan seluruh menu (1 sampai 7) hingga program dimatikan lewat menu `0`. Urutannya: tambah buku, daftar buku, cari buku, daftar anggota baru, tiga kali pinjam, satu kali kembalikan, laporan, keluar.
 
```
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 1
Judul     : the alchemist
Penulis   : paulo coelho
Tahun     : 1988
Kategori  : fiksi
Buku berhasil ditambahkan.
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 2
1. Laskar Pelangi         | Andrea Hirata          | 2005 | Novel      | Tersedia
2. Bumi Manusia           | Pramoedya Ananta Toer  | 1980 | Novel      | Tersedia
3. Clean Code             | Robert C Martin        | 2008 | Teknologi  | Tersedia
4. Head First Java        | Kathy Sierra           | 2005 | Teknologi  | Tersedia
5. Sapiens                | Yuval Noah Harari      | 2011 | Sejarah    | Tersedia
6. The Alchemist          | Paulo Coelho           | 1988 | Fiksi      | Tersedia
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 3
Kata kunci (judul/kategori): java
Ditemukan 1 buku:
 - Head First Java        | Kathy Sierra           | 2005 | Teknologi  | Tersedia
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 7
ID anggota (contoh M001): m003
Nama: rina wati
Anggota terdaftar.
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 4
ID anggota  : M001
Judul buku  : bumi manusia
Peminjaman berhasil.
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 4
ID anggota  : M001
Judul buku  : laskar pelangi
Peminjaman berhasil.
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 4
ID anggota  : M003
Judul buku  : clean code
Peminjaman berhasil.
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 5
ID anggota  : M001
Judul buku  : bumi manusia
Pengembalian berhasil.
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 6
 
===== LAPORAN PERPUSTAKAAN =====
Jumlah total pinjaman : 3
Buku paling sering    : Laskar Pelangi (1x)
Anggota paling aktif  : Budi Santoso (2 pinjaman)
Kategori terpopuler   : Novel (2 pinjaman)
 
Jumlah buku per kategori:
  - Sejarah: 1
  - Novel: 2
  - Fiksi: 1
  - Teknologi: 2
 
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 0
Terima kasih!
```
 
**Cara membaca output pada sesi ini:**
- Total pinjaman 3: hanya peminjaman yang berhasil yang dihitung, dan pengembalian tidak mengurangi total.
- Buku paling sering dipinjam: tiga buku sama-sama 1x, sehingga yang tampil adalah yang lebih dulu ada di koleksi (Laskar Pelangi).
- Anggota paling aktif: Budi Santoso (M001) meminjam 2 kali, Rina Wati (M003) 1 kali.
- Kategori terpopuler: Novel (Laskar Pelangi dan Bumi Manusia).
- Urutan daftar "Jumlah buku per kategori" mengikuti urutan `HashMap`, jadi tidak dijamin sama di setiap mesin.

### b. Output BookAlreadyBorrowedException
Buku sudah dipinjam anggota lain, sehingga peminjaman kedua ditolak.
 
```
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 2
1. Laskar Pelangi         | Andrea Hirata          | 2005 | Novel      | Tersedia
2. Bumi Manusia           | Pramoedya Ananta Toer  | 1980 | Novel      | Tersedia
3. Clean Code             | Robert C Martin        | 2008 | Teknologi  | Tersedia
4. Head First Java        | Kathy Sierra           | 2005 | Teknologi  | Tersedia
5. Sapiens                | Yuval Noah Harari      | 2011 | Sejarah    | Tersedia
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 4
ID anggota  : M001
Judul buku  : clean code
Peminjaman berhasil.
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 4
ID anggota  : M002
Judul buku  : clean code
Gagal: Buku 'Clean Code' sedang dipinjam.              <-- BookAlreadyBorrowedException
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 0
Terima kasih!
```
 
### c. Output BookNotFoundException (Peminjaman)
Judul yang dimasukkan tidak ada di koleksi.
 
```
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 4
ID anggota  : M001
Judul buku  : xyz
Gagal: Buku dengan judul 'xyz' tidak ditemukan.        <-- BookNotFoundException
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 0
Terima kasih!
```
 
### d. Output BookNotFoundException (Pengembalian)
Pengembalian gagal karena judul tidak ada di koleksi, atau buku ada tetapi tidak sedang dipinjam anggota tersebut.
 
```
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 5
ID anggota  : M001
Judul buku  : xyz
Gagal: Buku dengan judul 'xyz' tidak ditemukan.        <-- BookNotFoundException
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 5
ID anggota  : M001
Judul buku  : clean code
Gagal: Buku 'Clean Code' tidak ada di daftar pinjaman Budi Santoso.   <-- BookNotFoundException
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 0
Terima kasih!
```
 
### e. Output BorrowLimitExceededException
Anggota M001 meminjam 3 buku (batas maksimal), lalu mencoba meminjam buku ke-4.
 
```
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 4
ID anggota  : M001
Judul buku  : laskar pelangi
Peminjaman berhasil.
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 4
ID anggota  : M001
Judul buku  : bumi manusia
Peminjaman berhasil.
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 4
ID anggota  : M001
Judul buku  : clean code
Peminjaman berhasil.
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 4
ID anggota  : M001
Judul buku  : sapiens
Gagal: Anggota Budi Santoso sudah mencapai batas maksimal 3 buku.   <-- BorrowLimitExceededException
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 0
Terima kasih!
```
 
### f. Output Anggota Tidak Terdaftar
ID anggota yang tidak ada di sistem, baik saat meminjam maupun mengembalikan.
 
```
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 4
ID anggota  : M999
Judul buku  : clean code
Gagal: Anggota dengan ID 'M999' tidak terdaftar.        <-- IllegalArgumentException
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 5
ID anggota  : M999
Judul buku  : clean code
Gagal: Anggota dengan ID 'M999' tidak terdaftar.        <-- IllegalArgumentException
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 0
Terima kasih!
```
 
### g. Output Validasi Pendaftaran Anggota
Format ID salah, ID sudah terdaftar, dan nama kosong ditolak. Setelah itu pendaftaran dengan data benar berhasil.
 
```
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 7
ID anggota (contoh M001): 123
Nama: budi
Gagal: Format ID tidak valid (contoh: M001).        <-- IllegalArgumentException
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 7
ID anggota (contoh M001): M001
Nama: budi
Gagal: ID anggota sudah terdaftar.        <-- IllegalArgumentException
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 7
ID anggota (contoh M001): M010
Nama: 
Gagal: Nama anggota tidak boleh kosong.        <-- IllegalArgumentException
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 7
ID anggota (contoh M001): M010
Nama: dewi lestari
Anggota terdaftar.
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 0
Terima kasih!
```
 
### h. Output Validasi Input Menu dan Tambah Buku
Input menu bukan angka, nomor menu tidak ada, data buku kosong (penulis dikosongkan), dan tahun bukan angka. Buku baru yang valid kemudian masuk ke daftar.
 
```
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: abc
Masukkan angka yang valid.
Pilih menu: 9
Menu tidak valid.
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 1
Judul     : kamus
Penulis   : 
Tahun     : 2000
Kategori  : bahasa
Data tidak boleh kosong.
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 1
Judul     : kamus
Penulis   : ali
Tahun     : dua ribu
Masukkan angka yang valid.
Tahun     : 2000
Kategori  : bahasa
Buku berhasil ditambahkan.
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 2
1. Laskar Pelangi         | Andrea Hirata          | 2005 | Novel      | Tersedia
2. Bumi Manusia           | Pramoedya Ananta Toer  | 1980 | Novel      | Tersedia
3. Clean Code             | Robert C Martin        | 2008 | Teknologi  | Tersedia
4. Head First Java        | Kathy Sierra           | 2005 | Teknologi  | Tersedia
5. Sapiens                | Yuval Noah Harari      | 2011 | Sejarah    | Tersedia
6. Kamus                  | Ali                    | 2000 | Bahasa     | Tersedia
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 0
Terima kasih!
```
 
### i. Output Pencarian Tanpa Hasil dan Laporan Kosong
Pencarian yang tidak cocok dengan judul maupun kategori, dan laporan saat belum ada peminjaman sama sekali.
 
```
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 3
Kata kunci (judul/kategori): komik
Tidak ada hasil.
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 6
 
===== LAPORAN PERPUSTAKAAN =====
Jumlah total pinjaman : 0
Buku paling sering    : -
Anggota paling aktif  : -
Kategori terpopuler   : -
 
Jumlah buku per kategori:
  - Sejarah: 1
  - Novel: 2
  - Teknologi: 2
 
 
=== PERPUSTAKAAN MINI ===
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Daftar Anggota Baru
0. Keluar
Pilih menu: 0
Terima kasih!
```

## 4. Pemetaan ke Ketentuan Tugas

| Ketentuan | Implementasi |
|---|---|
| Class, Object, Constructor, Package | `Book`, `Member`, `LibraryService`, 4 package |
| Primitive & Reference | `int`, `boolean` / `String`, `ArrayList`, `HashMap` |
| Kondisional & Looping | `if`, `switch`, `for`, `while` di service dan main |
| Custom exception (min. 2) | 3 exception di package `exception` |
| Assertion | Validasi anggota di `pinjamBuku()` dan `kembalikanBuku()` |
| Character & String (min. 2) | `formatKapital()`, `validIdAnggota()`, `cariBuku()` |
| Scanner | `MainApp` (`bacaString`, `bacaInt`) |

## Identitas:
- Nama: Tiara Belva Pratistha
- NIM : L0325014
- Kelas: 3B