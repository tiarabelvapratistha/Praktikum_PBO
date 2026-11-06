public class Mobil extends Kendaraan implements Asuransi {
    private int jumlahPenumpang;

    public Mobil(String plat, String merek, double tarifPerHari, int jumlahPenumpang) {
        super(plat, merek, tarifPerHari);
        this.jumlahPenumpang = jumlahPenumpang;
    }

    public int getJumlahPenumpang() { return jumlahPenumpang; }

    @Override
    public double hitungBiayaSewa(int hari) {
        double total = getTarifPerHari() * hari;
        if (hari >= 7) total *= 0.90; // diskon 10% sewa mingguan
        return total;
    }

    @Override
    public String getJenis() { return "Mobil"; }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println("   Kapasitas: " + jumlahPenumpang + " penumpang");
    }

    @Override
    public double hitungPremi(int hari) {
        return 25000 * hari;
    }

    @Override
    public String getNamaPaket() { return "Asuransi Mobil Standar"; }
}
