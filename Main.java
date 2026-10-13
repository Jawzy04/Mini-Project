import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    static final String NAMA_FILE = "data_mahasiswa.txt";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int pilihan;

        // Menu berulang sampai pengguna memilih keluar
        do {
            System.out.println();
            System.out.println("===== MENU DATA MAHASISWA =====");
            System.out.println("1. Tambah data mahasiswa");
            System.out.println("2. Tampilkan semua data tersimpan");
            System.out.println("0. Keluar");
            System.out.print("Pilihan: ");
            pilihan = bacaPilihan(input);

            switch (pilihan) {
                case 1:
                    tambahData(input);
                    break;
                case 2:
                    tampilkanSemuaData();
                    break;
                case 0:
                    System.out.println("Program selesai.");
                    break;
                default:
                    System.out.println("Pilihan salah.");
            }
        } while (pilihan != 0);

        input.close();
    }

    // Membaca pilihan menu, return -1 jika bukan angka
    static int bacaPilihan(Scanner input) {
        try {
            return Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Input data mahasiswa, tampilkan, lalu simpan ke file
    static void tambahData(Scanner input) {
        // Jumlah mahasiswa ditentukan saat program berjalan
        int jumlah = bacaJumlah(input);
        Mahasiswa[] daftar = new Mahasiswa[jumlah];

        // Loop untuk input data setiap mahasiswa
        for (int i = 0; i < jumlah; i++) {
            System.out.println();
            System.out.println("Data mahasiswa ke-" + (i + 1));

            Mahasiswa mhs = new Mahasiswa();
            System.out.print("Nama  : ");
            mhs.nama = input.nextLine();
            System.out.print("NIM   : ");
            mhs.nim = input.nextLine();
            mhs.nilai = bacaNilai(input);

            daftar[i] = mhs;
        }

        // Tampilkan data yang baru dimasukkan
        System.out.println();
        System.out.println("===== HASIL INPUT =====");
        for (int i = 0; i < jumlah; i++) {
            System.out.println("Mahasiswa ke-" + (i + 1));
            daftar[i].tampilkanData();
            System.out.println();
        }

        simpanKeFile(daftar);
    }

    // Membaca file txt lalu menampilkan semua data yang tersimpan
    static void tampilkanSemuaData() {
        File file = new File(NAMA_FILE);
        if (!file.exists()) {
            System.out.println("Belum ada data tersimpan.");
            return;
        }

        int nomor = 0;
        try (Scanner baca = new Scanner(file)) {
            System.out.println();
            System.out.println("===== DATA TERSIMPAN =====");
            while (baca.hasNextLine()) {
                String baris = baca.nextLine().trim();
                if (baris.isEmpty()) {
                    continue;
                }

                // Format baris: nama | nim | nilai | status
                String[] bagian = baris.split("\\|");
                if (bagian.length != 4) {
                    continue;
                }

                try {
                    Mahasiswa m = new Mahasiswa();
                    m.nama = bagian[0].trim();
                    m.nim = bagian[1].trim();
                    m.nilai = Double.parseDouble(bagian[2].trim());

                    nomor++;
                    System.out.println("Mahasiswa ke-" + nomor);
                    m.tampilkanData();
                    System.out.println();
                } catch (NumberFormatException e) {
                    // baris rusak, lewati
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File tidak dapat dibuka.");
            return;
        }

        if (nomor == 0) {
            System.out.println("Belum ada data tersimpan.");
        }
    }

    // Membaca jumlah mahasiswa, minimal 3
    static int bacaJumlah(Scanner input) {
        int jumlah = 0;
        while (jumlah < 3) {
            System.out.print("Jumlah mahasiswa (minimal 3): ");
            try {
                jumlah = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                jumlah = 0;
            }
            if (jumlah < 3) {
                System.out.println("Jumlah harus berupa angka minimal 3.");
            }
        }
        return jumlah;
    }

    // Membaca nilai (0 sampai 100), ulangi jika input salah
    static double bacaNilai(Scanner input) {
        while (true) {
            System.out.print("Nilai (0-100): ");
            try {
                String teks = input.nextLine().trim().replace(",", ".");
                double nilai = Double.parseDouble(teks);
                if (nilai >= 0 && nilai <= 100) {
                    return nilai;
                }
            } catch (NumberFormatException e) {
                // input bukan angka, minta ulang di bawah
            }
            System.out.println("Nilai harus berupa angka 0 sampai 100.");
        }
    }

    // Menyimpan semua data ke file txt (ditambahkan di akhir file)
    static void simpanKeFile(Mahasiswa[] daftar) {
        try (PrintWriter out = new PrintWriter(new FileWriter(NAMA_FILE, true))) {
            for (int i = 0; i < daftar.length; i++) {
                Mahasiswa m = daftar[i];
                out.println(m.nama + " | " + m.nim + " | " + m.nilai + " | " + m.cekKelulusan());
            }
            System.out.println("Data tersimpan di " + NAMA_FILE);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data: " + e.getMessage());
        }
    }
}
