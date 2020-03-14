package com.example.toko.API;

import com.example.toko.Value;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface insertPembelianAPI {

        @FormUrlEncoded
        @POST("insert_pembelian.php")
        Call<Value> pembelian(@Field("id_pembelian") String id_pembelian,
                              @Field("tgl_pembelian") String tgl_pembelian,
                              @Field("id_supplier") int id_supplier,
                              @Field("id_barang") int id_barang,
                              @Field("qty_pembelian") int qty_pembelian,
                              @Field("harga_satuan_pembelian") int harga_satuan_pembelian
        );

        @FormUrlEncoded
        @POST("simpan_pembelian.php")
        Call<Value> simpan_pembelian(@Field("id_pembelian") String id_pembelian);

    @FormUrlEncoded
        @POST("read_cart.php")
        Call<Value> getdetail(@Field("id_pembelian") String id_pembelian);

    @FormUrlEncoded
    @POST("get_laporan.php")
    Call<Value> getlaporan(@Field("tgl_pembelian") String tgl_pembelian);

    @FormUrlEncoded
    @POST("get_detaillaporan.php")
    Call<Value> getdetaillaporan(@Field("id_pembelian") String id_pembelian);

    @FormUrlEncoded
    @POST("search_supplier.php")
    Call<Value> searchsup(@Field("search") String search);

    @FormUrlEncoded
    @POST("delete_cart.php")
    Call<Value> hapus(@Field("id_barang") String id_barang,
                      @Field("id_pembelian") String id_pembelian,
                      @Field("qty_pembelian") int qty_pembelian);

}
