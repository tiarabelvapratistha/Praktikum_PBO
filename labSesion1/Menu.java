package Java.PBO.labSesion1;

public class Menu {
    // atribut dari menu
    private String code;
    private String name;
    private double price;
    private char category;

    // construct - untuk memasukkan data menu
    public Menu(String code, String name, double price, char category) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    // method - untuk menampilkan informasi tentang semua menu di daftar menu
    public void getInfo() {
        String nameUpper = name.toUpperCase();
        System.out.println(code + "-" + nameUpper + " (" + (nameUpper.length()) + " karakter) - Rp" + price +
                " [" + (category == 'f' ? "Food" : "Drink") + "]");
    }

    // method - untuk menghitung harga setelah ditambah pajak
    public double priceAfterTax() {
        return price + (price * OrderingFood.TAXES);
    }

    // method - untuk mengambil data dari objek
    public String getCode() { return code; }    //mengambil kode satu menu
    public String getName() { return name; }    //mengambil nama satu menu
    public double getPrice() { return price; }  //mengambil harga satu menu
}
