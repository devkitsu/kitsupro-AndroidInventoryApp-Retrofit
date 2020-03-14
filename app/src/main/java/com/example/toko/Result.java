package com.example.toko;

public class Result {
    String nama_kategori;
    String nama_satuan;
    String nama_pelanggan;
    String telepon_pelanggan;
    String alamat_pelanggan;
    String nama_supplier;
    String telepon_supplier;
    String alamat_supplier;
    String nama_barang;
    String qty_barang;
    String id_barang;
    String id_satuan;
    String id_kategori;
    String id_pelanggan;
    String id_supplier;
    String id_penjualan;
    String tgl_penjualan;
    String tgl_pembelian;
    String harga_satuan_penjualan;
    String id_pembelian;
    String harga_satuan_pembelian;
    String qty_penjualan;
    String qty_pembelian;
    String grand_total_pembelian;
    String grand_total_penjualan;
    String total_harga_pembelian;
    String total_harga_penjualan;

    public Result() {
    }

    public Result(String nama_kategori, String nama_satuan, String nama_pelanggan, String telepon_pelanggan, String alamat_pelanggan,
                  String nama_supplier, String telepon_supplier, String alamat_supplier, String nama_barang, String qty_barang,
                  String id_barang, String id_satuan, String id_kategori, String id_pelanggan, String id_supplier, String id_penjualan,
                  String tgl_penjualan, String harga_satuan_penjualan, String id_pembelian, String harga_satuan_pembelian,
                  String qty_penjualan, String qty_pembelian, String tgl_pembelian, String grand_total_pembelian,
                  String grand_total_penjualan, String total_harga_pembelian, String total_harga_penjualan) {
        this.nama_kategori=nama_kategori;
        this.nama_satuan=nama_satuan;
        this.nama_pelanggan=nama_pelanggan;
        this.telepon_pelanggan=telepon_pelanggan;
        this.alamat_pelanggan=alamat_pelanggan;
        this.nama_supplier=nama_supplier;
        this.telepon_supplier=telepon_supplier;
        this.alamat_supplier=alamat_supplier;
        this.nama_barang=nama_barang;
        this.qty_barang=qty_barang;
        this.id_barang=id_barang;
        this.id_satuan=id_satuan;
        this.id_kategori=id_kategori;
        this.id_pelanggan=id_pelanggan;
        this.id_supplier=id_supplier;
        this.id_penjualan=id_penjualan;
        this.tgl_penjualan=tgl_penjualan;
        this.tgl_pembelian=tgl_pembelian;
        this.harga_satuan_penjualan=harga_satuan_penjualan;
        this.id_pembelian=id_pembelian;
        this.harga_satuan_pembelian=harga_satuan_pembelian;
        this.qty_penjualan=qty_penjualan;
        this.qty_pembelian=qty_pembelian;
        this.grand_total_pembelian=grand_total_pembelian;
        this.grand_total_penjualan=grand_total_penjualan;
        this.total_harga_pembelian=total_harga_pembelian;
        this.total_harga_penjualan=total_harga_penjualan;
    }

    public String getNama_kategori(){
        return nama_kategori;
    }
    public String getNama_satuan() { return nama_satuan; }
    public String getNama_pelanggan() { return nama_pelanggan;}
    public String getTelepon_pelanggan() { return telepon_pelanggan; }
    public String getAlamat_pelanggan() { return alamat_pelanggan; }
    public String getNama_supplier() { return nama_supplier;}
    public String getTelepon_supplier() { return telepon_supplier; }
    public String getAlamat_supplier() { return alamat_supplier; }
    public String getNama_barang() { return nama_barang; }
    public String getQty_barang() { return qty_barang; }
    public String getId_barang() { return id_barang;}
    public String getId_satuan() { return id_satuan;}
    public String getId_kategori() {   return id_kategori;}
    public String getId_pelanggan() {   return id_pelanggan;}
    public String getId_supplier() {   return id_supplier;}
    public String getId_penjualan() {   return id_penjualan;}
    public String getTgl_penjualan() {   return tgl_penjualan;}
    public String getTgl_pembelian() {   return tgl_pembelian;}
    public String getHarga_satuan_penjualan() {   return harga_satuan_penjualan;}
    public String getHarga_satuan_pembelian() {   return harga_satuan_pembelian;}
    public String getId_pembelian() {   return id_pembelian;}
    public String getQty_penjualan() { return qty_penjualan; }
    public String getQty_pembelian() { return qty_pembelian; }
    public String getGrand_total_pembelian(){ return grand_total_pembelian;}
    public String getGrand_total_penjualan(){ return grand_total_penjualan;}
    public String getTotal_harga_pembelian(){ return total_harga_pembelian;}
    public String getTotal_harga_penjualan(){ return total_harga_penjualan;}
}
