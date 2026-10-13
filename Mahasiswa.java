public class Mahasiswa {

    // Aturan tetap: nilai minimal untuk lulus
    static final double BATAS_LULUS = 60;

    String nama;
    String nim;
    double nilai;

    String cekKelulusan() {
        if (nilai >= BATAS_LULUS) {
            return "Lulus";
        } else {
            return "Tidak Lulus";
        }
    }

    void tampilkanData() {
        System.out.println("Nama   : " + nama);
        System.out.println("NIM    : " + nim);
        System.out.println("Nilai  : " + nilai);
        System.out.println("Status : " + cekKelulusan());
    }
}
