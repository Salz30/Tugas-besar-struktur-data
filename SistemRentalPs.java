package sistemrentalps;

import java.util.Scanner;
import java.util.Stack;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SistemRentalPs {
    
    private static Scanner input = new Scanner(System.in);
    private static List<Transaksi> transaksiHistory = new ArrayList<>();
    
    
    

    private static class PlayStation {
        String nama;
        int harga;
        int stok;

        public PlayStation(String nama, int harga, int stok) {
            this.nama = nama;
            this.harga = harga;
            this.stok = stok;
        }
    }

    private static class Transaksi {
        String namaPeminjam;
        String nomorPeminjam;
        String jenisPs;
        int lamaPinjam;
        int hargaPinjam;
        LocalDateTime waktuPeminjaman;
        LocalDateTime waktuBatasPeminjaman;

        public Transaksi(String namaPeminjam, String nomorPeminjam, String jenisPs, int lamaPinjam, int hargaPinjam) {
            this.namaPeminjam = namaPeminjam;
            this.nomorPeminjam = nomorPeminjam;
            this.jenisPs = jenisPs;
            this.lamaPinjam = lamaPinjam;
            this.hargaPinjam = hargaPinjam;
            this.waktuPeminjaman = LocalDateTime.now();
            this.waktuBatasPeminjaman = this.waktuPeminjaman.plusDays(lamaPinjam);
        }

        public String getFormattedWaktuPeminjaman() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            return waktuPeminjaman.format(formatter);
        }

        public String getFormattedWaktuBatasPeminjaman() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            return waktuBatasPeminjaman.format(formatter);
        }
    }

    private static class Node {
        Transaksi transaksi;
        Node next;

        public Node(Transaksi transaksi) {
            this.transaksi = transaksi;
            this.next = null;
        }
    }

    private static class daftarPeminjaman {
        private Node head;

        public daftarPeminjaman() {
            this.head = null;
        }

        public void tambahTransaksi(Transaksi transaksi) {
            Node newNode = new Node(transaksi);
            if (head == null) {
                head = newNode;
            } else {
                Node current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
        }

        public void tampilkanRiwayat() {
            Node current = head;
            while (current != null) {
                System.out.println("Nama: " + current.transaksi.namaPeminjam);
                System.out.println("Nomer HP Peminjam: " + current.transaksi.nomorPeminjam);
                System.out.println("Playstation yang dipinjam: " + current.transaksi.jenisPs);
                System.out.println("Lama peminjaman: " + current.transaksi.lamaPinjam + " hari");
                System.out.println("Harga sewa: Rp" + current.transaksi.hargaPinjam);
                System.out.println("Waktu Peminjaman: " + current.transaksi.getFormattedWaktuPeminjaman());
                System.out.println("Batas Peminjaman: " + current.transaksi.getFormattedWaktuBatasPeminjaman());
                System.out.println();
                current = current.next;
            }
        }
    }

    private static daftarPeminjaman daftarPeminjaman = new daftarPeminjaman();
    
    private static PlayStation[] daftarPs = {
        new PlayStation("Playstation 1", 20000, 5),
        new PlayStation("Playstation 2", 40000, 5),
        new PlayStation("Playstation 3", 60000, 5),
        new PlayStation("Playstation 4", 80000, 5),
        new PlayStation("Playstation 5", 100000, 5)
    };

    public static void main(String[] args) {
        int pilihanMenu;
        do {
            System.out.println("=== Aplikasi Rental Playstation ===");
            System.out.println("1. Lihat daftar Playstation");
            System.out.println("2. Sewa Playstation");
            System.out.println("3. Promo Awal tahun 2024");
            System.out.println("4. Lihat daftar peminjam");
            System.out.println("5. Pengembalian Playstation");
            System.out.println("6. Lihat Riwayat Peminjaman");
            System.out.println("7. Keluar");
            System.out.print("Masukkan pilihan menu: ");
            pilihanMenu = input.nextInt();
            switch (pilihanMenu) {
                case 1:
                    lihatDaftarPs();
                    break;
                case 2:
                    sewaPs();
                    break;
                case 3:
                    promoMenu();
                    break;
                case 4:
                    lihatDaftarPeminjam();
                    break;
                case 5:
                    pengembalianPs();
                    break;
                case 6:
                    tampilkanRiwayatPeminjaman();
                    break;
                case 7:
                    System.out.println("Terima kasih telah menggunakan aplikasi rental Playstation!");
                    break;
                default:
                    System.out.println("Pilihan menu tidak valid!");
                    break;
            }
        } while (pilihanMenu != 7);
    }

    private static void lihatDaftarPs() {
        for (int i = 0; i < daftarPs.length; i++) {
            System.out.println((i + 1) + ". " + daftarPs[i].nama + " (Rp" + daftarPs[i].harga + "/hari) - tersedia " + daftarPs[i].stok + " unit");
        }
    }

    private static void sewaPs() {
        lihatDaftarPs();

        System.out.print("Masukkan nomor Playstation yang ingin disewa: ");
        int nomorPs = input.nextInt();

        if (nomorPs < 1 || nomorPs > daftarPs.length) {
            System.out.println("Nomor Playstation tidak valid!");
            return;
        }

        int nomorIndexPs = nomorPs - 1;
        if (daftarPs[nomorIndexPs].stok == 0) {
            System.out.println("Maaf, Playstation yang Anda pilih sedang tidak tersedia.");
            return;
        }

        input.nextLine();
        String namaPeminjam;
        do {
            System.out.print("Masukkan nama peminjam: ");
            namaPeminjam = input.nextLine();
            if (!namaPeminjam.matches("[a-zA-Z ]+")) {
                System.out.println("Nama peminjam tidak valid. Harap masukkan hanya huruf dan spasi.");
            }
        } while (!namaPeminjam.matches("[a-zA-Z ]+"));

        String nomorPeminjam;
        do {
            System.out.print("Masukkan nomor HP peminjam: ");
            nomorPeminjam = input.next();
            if (!nomorPeminjam.matches("\\d{10,13}")) {
                System.out.println("Nomor HP tidak valid. Harap masukkan antara 10 hingga 13 digit angka.");
            }
        } while (!nomorPeminjam.matches("\\d{10,13}"));

        System.out.print("Masukkan lama peminjaman (hari): ");
        int lamaPinjam = input.nextInt();
        int hargaPinjam = daftarPs[nomorIndexPs].harga * lamaPinjam;
        System.out.println("Total harga sewa: Rp" + hargaPinjam);

        daftarPs[nomorIndexPs].stok--;
        Transaksi transaksi = new Transaksi(namaPeminjam, nomorPeminjam, daftarPs[nomorIndexPs].nama, lamaPinjam, hargaPinjam);
        daftarPeminjaman.tambahTransaksi(transaksi);

        System.out.println("Playstation berhasil disewa!");
        System.out.println("Waktu Peminjaman: " + transaksi.getFormattedWaktuPeminjaman());
        System.out.println("Batas Peminjaman: " + transaksi.getFormattedWaktuBatasPeminjaman());
    }

    private static void promoMenu() {
        System.out.println("=== Promo Spesial ===");
        System.out.println("Dapatkan diskon 15% untuk penyewaan Ps selama 4 hari atau lebih!");
        System.out.println("Playstation yang sedang promo: Playstation 5");

        if (daftarPs[4].stok == 0) {
            System.out.println("Maaf, Playstation 5 sedang tidak tersedia untuk promo saat ini.");
            return;
        }

        String namaPeminjam;
        do {
            System.out.print("Masukkan nama peminjam: ");
            namaPeminjam = input.next();
            if (!namaPeminjam.matches("[a-zA-Z ]+")) {
                System.out.println("Nama peminjam tidak valid. Harap masukkan hanya huruf dan spasi.");
            }
        } while (!namaPeminjam.matches("[a-zA-Z ]+"));

        String nomorPeminjam;
        do {
            System.out.print("Masukkan nomor HP peminjam: ");
            nomorPeminjam = input.next();
            if (!nomorPeminjam.matches("\\d{10,12}")) {
                System.out.println("Nomor HP tidak valid. Harap masukkan antara 10 hingga 12 digit angka.");
            }
        } while (!nomorPeminjam.matches("\\d{10,12}"));

        System.out.print("Masukkan lama peminjaman (hari): ");
        int lamaPinjam = input.nextInt();
        int hargaPinjam = daftarPs[4].harga * lamaPinjam;

        if (lamaPinjam >= 4) {
            int diskon = hargaPinjam * 15 / 100;
            int total = hargaPinjam - diskon;

            System.out.println("Anda mendapatkan diskon sebesar Rp" + diskon + "!");
            System.out.println("Total harga sewa: Rp" + total);
            System.out.print("Apakah Anda yakin ingin menyewa PlayStation ini? (y/n) ");

            String konfirmasi = input.next();
            if (konfirmasi.equalsIgnoreCase("y")) {
                daftarPs[4].stok--;
                Transaksi transaksi = new Transaksi(namaPeminjam, nomorPeminjam, daftarPs[4].nama, lamaPinjam, total);
                daftarPeminjaman.tambahTransaksi(transaksi);

                System.out.println("Playstation berhasil disewa!");
            } else {
                System.out.println("Sewa Playstation dibatalkan.");
            }
        }
    }

    private static void lihatDaftarPeminjam() {
        System.out.println("=== Daftar Peminjam ===");
        System.out.println("1. Tampilkan Semua Peminjam");
        System.out.println("2. Cari Peminjam");
        System.out.print("Masukkan pilihan: ");
        int pilihan = input.nextInt();

        switch (pilihan) {
            case 1:
                tampilkanSemuaPeminjam();
                break;
            case 2:
                cariPeminjam();
                break;
            default:
                System.out.println("Pilihan tidak valid!");
        }
    }

    private static void tampilkanSemuaPeminjam() {
        System.out.println("=== DAFTAR SEMUA PEMINJAM ===");
        daftarPeminjaman.tampilkanRiwayat();
    }

    private static void cariPeminjam() {
        input.nextLine();
        System.out.print("Masukkan nama peminjam yang ingin dicari: ");
        String keyword = input.nextLine();

        Node minjem = daftarPeminjaman.head;
        boolean found = false;

        while (minjem != null) {
            if (minjem.transaksi.namaPeminjam.toLowerCase().contains(keyword.toLowerCase())) {
                found = true;
                System.out.println("=== HASIL PENCARIAN ===");
                System.out.println("Nama: " + minjem.transaksi.namaPeminjam);
                System.out.println("Nomer HP Peminjam: " + minjem.transaksi.nomorPeminjam);
                System.out.println("Playstation yang dipinjam: " + minjem.transaksi.jenisPs);
                System.out.println("Lama peminjaman: " + minjem.transaksi.lamaPinjam + " hari");
                System.out.println("Harga sewa: Rp" + minjem.transaksi.hargaPinjam);
                System.out.println("Waktu Peminjaman: " + minjem.transaksi.getFormattedWaktuPeminjaman());
                System.out.println("Batas Peminjaman: " + minjem.transaksi.getFormattedWaktuBatasPeminjaman());
                System.out.println();
            }
            minjem = minjem.next;
        }

        if (!found) {
            System.out.println("Data peminjam tidak ditemukan!");
        }
    }

    private static void pengembalianPs() {
        System.out.println("=== Pengembalian PlayStation ===");
        System.out.print("Masukkan nama peminjam: ");
        String namaPeminjam = input.next();

        Node current = daftarPeminjaman.head;
        Node prev = null;
        boolean found = false;

        while (current != null) {
            if (current.transaksi.namaPeminjam.equalsIgnoreCase(namaPeminjam)) {
                found = true;
                System.out.println("Anda mengembalikan PlayStation:");
                System.out.println("Nama: " + current.transaksi.namaPeminjam);
                System.out.println("Nomer HP Peminjam: " + current.transaksi.nomorPeminjam);
                System.out.println("Playstation yang dipinjam: " + current.transaksi.jenisPs);
                System.out.println("Lama peminjaman: " + current.transaksi.lamaPinjam + " hari");
                System.out.println("Harga sewa: Rp" + current.transaksi.hargaPinjam);

                System.out.print("Apakah Anda yakin ingin mengembalikan? (Y/N): ");
                String verifikasi = input.next();
                if (verifikasi.equalsIgnoreCase("Y")) {
                    
                    System.out.print("Apakah ada keterlambatan? (Y/N): ");
                    String keterlambatan = input.next();
                    if (keterlambatan.equalsIgnoreCase("Y")) {
                        System.out.print("Lama Keterlambatan (hari): ");
                        int terlambat = input.nextInt();
                        int denda = terlambat * 100000;
                        System.out.println("Denda yang harus dibayar: Rp. " + denda);
                        int total = denda + current.transaksi.hargaPinjam;
                        System.out.println("Total yang harus dibayar: Rp. " + total);
                    } else {
                        int total = current.transaksi.hargaPinjam;
                        System.out.println("Total yang harus dibayar: Rp. " + total);
                    }

                    int nomorIndexPs = getIndexPs(current.transaksi.jenisPs);
                    daftarPs[nomorIndexPs].stok++;
                    transaksiHistory.add(current.transaksi);
                    System.out.println("Terima kasih telah menggunakan layanan kami!");
                    // Hapus transaksi dari riwayat
                    if (prev == null) {
                        daftarPeminjaman.head = current.next;
                        
                    } else {
                        prev.next = current.next;
                    }
                } else {
                    System.out.println("Pengembalian dibatalkan.");
                }
            }
            prev = current;
            current = current.next;
        }

        if (!found) {
            System.out.println("Data peminjam tidak ditemukan!");
        }
    }

    private static void tampilkanRiwayatPeminjaman() {
    System.out.println("=== Riwayat Peminjaman ===");
    for (Transaksi transaksi : transaksiHistory) {
        System.out.println("Nama: " + transaksi.namaPeminjam);
        System.out.println("Nomer HP Peminjam: " + transaksi.nomorPeminjam);
        System.out.println("Playstation yang dipinjam: " + transaksi.jenisPs);
        System.out.println("Lama peminjaman: " + transaksi.lamaPinjam + " hari");
        System.out.println("Harga sewa: Rp" + transaksi.hargaPinjam);
        System.out.println("Waktu Peminjaman: " + transaksi.getFormattedWaktuPeminjaman());
        System.out.println("Batas Peminjaman: " + transaksi.getFormattedWaktuBatasPeminjaman());
        System.out.println();
    }
}

    private static int getIndexPs(String jenisPs) {
        for (int i = 0; i < daftarPs.length; i++) {
            if (daftarPs[i].nama.equalsIgnoreCase(jenisPs)) {
                return i;
            }
        }
        return -1;
    }
}