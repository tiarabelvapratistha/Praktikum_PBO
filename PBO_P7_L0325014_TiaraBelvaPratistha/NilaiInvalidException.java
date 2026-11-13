package PBO_P7_L0325014_TiaraBelvaPratisha;

// CUSTOM EXCEPTION: dilempar jika nilai di luar rentang 0 - 100
public class NilaiInvalidException extends Exception {
    private final double nilai;
 
    public NilaiInvalidException(double nilai) {
        super("Nilai harus antara 0 dan 100, tetapi diberikan: " + nilai);
        this.nilai = nilai;
    }
 
    public double getNilai() {
        return nilai;
    }
}
