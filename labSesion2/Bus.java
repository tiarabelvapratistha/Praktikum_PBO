public class Bus extends Kendaraan implements Asuransi {
    private int kapasitas;

    public Bus(String plat, String merek, double tarifPerHari, int kapasitas) {
        super(plat, merek, tarifPerHari);
        this.kapasitas = kapasitas;
    }

    public int getKapasitas() { return kapasitas; }

    @Override
    public double hitungBiayaSewa(int hari) {
        double total = getTarifPerHari() * hari;
        if (hari >= 3) total *= 0.95; // diskon 5% sewa 3 hari ke atas
        return total + 200000;        // biaya BBM / operasional tetap
    }

    @Override
    public String getJenis() { return "Bus"; }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println("   Kapasitas: " + kapasitas + " penumpang");
    }

    @Override
    public double hitungPremi(int hari) {
        return 100000 * hari;
    }

    @Override
    public String getNamaPaket() { return "Asuransi Bus Premium"; }
}
