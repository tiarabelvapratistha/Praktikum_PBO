# Lab Sesion 1

Program **OrderingFood** ini adalah simulasi sistem pemesanan makanan sederhana berbasis command-line yang merepresentasikan alur pemesanan di sebuah warung bernama "Warung Java". Program dibangun dengan pendekatan berorientasi objek, di mana setiap item menu (seperti Nasi Goreng dan Es Teh) direpresentasikan sebagai objek dari class `Menu` dengan atribut kode, nama, harga, dan kategori (makanan atau minuman). Seluruh koleksi menu dan keranjang belanja pengguna disimpan menggunakan `ArrayList`, sehingga jumlah item bisa bertambah secara dinamis sesuai kebutuhan tanpa dibatasi ukuran array tetap. Pengguna berinteraksi dengan program lewat `Scanner`, memilih kode menu satu per satu dalam sebuah perulangan (`while`) hingga mereka memutuskan berhenti memesan.

Setelah proses pemesanan selesai, program menghitung total harga pesanan (termasuk pajak), menentukan biaya pengiriman berdasarkan syarat minimal belanja untuk gratis ongkir, lalu menampilkan struk pembayaran lengkap ke layar. Program ini juga menerapkan penanganan kesalahan (`try-catch`) untuk mengantisipasi kode menu yang tidak valid saat dipesan, sehingga program tidak berhenti mendadak (crash) ketika pengguna salah input. Secara keseluruhan, program ini mendemonstrasikan penggunaan konsep dasar Java seperti class dan object, konstanta, struktur kondisional, perulangan, koleksi data, manipulasi tipe `char` dan `String`, serta exception handling dalam satu studi kasus yang ringkas dan mudah dipahami.

## Struktur File

```bash
Menu.java
OrderingFood.java
```

## Output 1: Pemesanan Normal

```text
=== WELCOME TO WARUNG JAVA ===

----- MENU -----
F1-NASI GORENG (11 karakter) - Rp15000.0 [Food]
F2-NASI GORENG SPESIAL (19 karakter) - Rp20000.0 [Food]
F3-NASI GORENG MAWUT (17 karakter) - Rp17000.0 [Food]
F4-NASI GORENG SOSIS (17 karakter) - Rp17000.0 [Food]
D1-ES TEH (6 karakter) - Rp4000.0 [Drink]
D2-ES JERUK (8 karakter) - Rp5000.0 [Drink]
D3-ES TELER (8 karakter) - Rp9000.0 [Drink]

Input the menu code (example: F1): F1
# Nasi Goreng is added to cart.
Continue ordering? (y/n): y

Input the menu code (example: F1): F4
# Nasi Goreng Sosis is added to cart.
Continue ordering? (y/n): y

Input the menu code (example: F1): D3
# Es Teler is added to cart.
Continue ordering? (y/n): n

===== PAYING RECEIPT =====
- Nasi Goreng : Rp17250.0
- Nasi Goreng Sosis : Rp19550.0
- Es Teler : Rp10350.0
----------------------------
Subtotal (+tax) : Rp47150.0
Delivery fee    : Rp0.0 (Gratis ongkir!)
Average/menu    : Rp15716.666666666666

TOTAL           : Rp47150.0
=============================
Thank You for Ordering in Warung Java!
```

## Output 2: Input kode dengan huruf kecil

```teks
=== WELCOME TO WARUNG JAVA ===

----- MENU -----
F1-NASI GORENG (11 karakter) - Rp15000.0 [Food]
F2-NASI GORENG SPESIAL (19 karakter) - Rp20000.0 [Food]
F3-NASI GORENG MAWUT (17 karakter) - Rp17000.0 [Food]
F4-NASI GORENG SOSIS (17 karakter) - Rp17000.0 [Food]
D1-ES TEH (6 karakter) - Rp4000.0 [Drink]
D2-ES JERUK (8 karakter) - Rp5000.0 [Drink]
D3-ES TELER (8 karakter) - Rp9000.0 [Drink]

Input the menu code (example: F1): f1
# Nasi Goreng is added to cart.
Continue ordering? (y/n): y

Input the menu code (example: F1): f4
# Nasi Goreng Sosis is added to cart.
Continue ordering? (y/n): n

===== PAYING RECEIPT =====
- Nasi Goreng : Rp17250.0
- Nasi Goreng Sosis : Rp19550.0
----------------------------
Subtotal (+tax) : Rp36800.0
Delivery fee    : Rp5000.0
Average/menu    : Rp18400.0

TOTAL           : Rp41800.0
=============================
Thank You for Ordering in Warung Java!
```

## Output 3: Memasukkan kode menu yang tidak ditemukan dan nota kosong

```teks
=== WELCOME TO WARUNG JAVA ===

----- MENU -----
F1-NASI GORENG (11 karakter) - Rp15000.0 [Food]
F2-NASI GORENG SPESIAL (19 karakter) - Rp20000.0 [Food]
F3-NASI GORENG MAWUT (17 karakter) - Rp17000.0 [Food]
F4-NASI GORENG SOSIS (17 karakter) - Rp17000.0 [Food]
D1-ES TEH (6 karakter) - Rp4000.0 [Drink]
D2-ES JERUK (8 karakter) - Rp5000.0 [Drink]
D3-ES TELER (8 karakter) - Rp9000.0 [Drink]

Input the menu code (example: F1): D8
 Error: The food code 'D8' is Not Found!
Continue ordering? (y/n): y

Input the menu code (example: F1): f5
 Error: The food code 'F5' is Not Found!
Continue ordering? (y/n): n

===== PAYING RECEIPT =====
None ordered.
```

---
## Identitas:
- Nama: Tiara Belva Pratistha
- NIM : L0325014
- Kelas: 3B