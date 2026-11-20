package Java.PBO.labSesion3;

import java.awt.Font;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.*;

// Tahap 3 dan Tahap 4 
public class KasirFrame extends JFrame {
    private JTextField txtMenu, txtHarga, txtJumlah;
    private JTextArea areaStruk;
    private JLabel lblTotal;
    private JButton btnTambah, btnHitung, btnCetak;
    private JLabel lblStatus;
    private Pesanan pesanan = new Pesanan();
    private NumberFormat format = NumberFormat.getNumberInstance(Locale.forLanguageTag("id-ID"));

    public KasirFrame() {
        setTitle("Kasir Kantin UNS");
        setSize(400, 450);
        setLayout(null);
        format.setMaximumFractionDigits(0);

        buatKomponen();
        pasangEvent();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Tahap 3 langkah 1: buat komponen, atur posisi dengan setBounds(), tambahkan ke jendela
    private void buatKomponen() {
        JLabel lblJudul = new JLabel("Kasir Kantin UNS");
        lblJudul.setFont(lblJudul.getFont().deriveFont(Font.BOLD, 16f));
        lblJudul.setBounds(20, 10, 350, 25);

        JLabel lblMenu = new JLabel("Nama Menu :");
        lblMenu.setBounds(20, 45, 85, 25);
        txtMenu = new JTextField();
        txtMenu.setBounds(110, 45, 250, 25);

        JLabel lblHarga = new JLabel("Harga     :");
        lblHarga.setBounds(20, 75, 85, 25);
        txtHarga = new JTextField();
        txtHarga.setBounds(110, 75, 250, 25);
        txtHarga.setToolTipText("Isi angka tanpa titik pemisah ribuan, contoh: 15000");

        JLabel lblJumlah = new JLabel("Jumlah    :");
        lblJumlah.setBounds(20, 105, 85, 25);
        txtJumlah = new JTextField();
        txtJumlah.setBounds(110, 105, 250, 25);

        btnTambah = new JButton("Tambah");
        btnTambah.setBounds(20, 140, 110, 30);
        btnHitung = new JButton("Hitung Total");
        btnHitung.setBounds(140, 140, 110, 30);
        btnCetak = new JButton("Cetak Struk");
        btnCetak.setBounds(260, 140, 110, 30);

        areaStruk = new JTextArea();
        areaStruk.setEditable(false);
        areaStruk.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollStruk = new JScrollPane(areaStruk);
        scrollStruk.setBounds(20, 180, 350, 150);

        lblTotal = new JLabel("Total Bayar : Rp 0");
        lblTotal.setFont(lblTotal.getFont().deriveFont(Font.BOLD, 14f));
        lblTotal.setBounds(20, 340, 350, 25);

        lblStatus = new JLabel("Status : -");
        lblStatus.setBounds(20, 370, 350, 25);

        add(lblJudul);
        add(lblMenu);
        add(txtMenu);
        add(lblHarga);
        add(txtHarga);
        add(lblJumlah);
        add(txtJumlah);
        add(btnTambah);
        add(btnHitung);
        add(btnCetak);
        add(scrollStruk);
        add(lblTotal);
        add(lblStatus);
    }

    private void pasangEvent() {
        btnTambah.addActionListener(e -> prosesTambah());
        btnHitung.addActionListener(e -> prosesHitung());

        btnCetak.addActionListener(e -> {
            new Thread(() -> {
                SwingUtilities.invokeLater(() -> {
                    btnCetak.setEnabled(false);
                    lblStatus.setText("Status : Mencetak struk...");
                });
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                SwingUtilities.invokeLater(() -> {
                    lblStatus.setText("Status : Struk selesai");
                    btnCetak.setEnabled(true);
                });
            }).start();
        });
    }

    // Tahap 3 langkah 2 dan 4: tombol Tambah & validasi
    private void prosesTambah() {
        String menu = txtMenu.getText().trim();
        String teksHarga = txtHarga.getText().trim();
        String teksJumlah = txtJumlah.getText().trim();

        if (menu.isEmpty() || teksHarga.isEmpty() || teksJumlah.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi.",
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double harga;
        int jumlah;
        try {
            harga = Double.parseDouble(teksHarga);
            jumlah = Integer.parseInt(teksJumlah);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Harga dan jumlah harus berupa angka.\nContoh harga: 15000, contoh jumlah: 2",
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!(harga > 0) || jumlah <= 0) {
            JOptionPane.showMessageDialog(this, "Harga dan jumlah harus lebih dari 0.",
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
            return;
        }

        pesanan.tambahItem(harga, jumlah);
        areaStruk.append(String.format("%-15s %d x %s = %s%n",
                menu, jumlah, format.format(harga), format.format(harga * jumlah)));

        txtMenu.setText("");
        txtHarga.setText("");
        txtJumlah.setText("");
        txtMenu.requestFocusInWindow();
    }

    // Tahap 3 langkah 3: tombol Hitung Total
    private void prosesHitung() {
        lblTotal.setText("Total Bayar : Rp " + format.format(pesanan.hitungTotal()));
    }
}
