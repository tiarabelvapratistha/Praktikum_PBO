// Motor tidak mengimplementasikan Asuransi
public class Motor extends Kendaraan {
    private int cc;

    public Motor(String plat, String merek, double tarifPerHari, int cc) {
        super(plat, merek, tarifPerHari);
        this.cc = cc;
    }

    public int getCc() { return cc; }

    @Override
    public double hitungBiayaSewa(int hari) {
        double total = getTarifPerHari() * hari;
        if (cc > 150) total += 10000 * hari; // biaya tambahan motor besar
        return total;
    }

    @Override
    public String getJenis() { return "Motor"; }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println("   Mesin: " + cc + " cc");
    }
}
