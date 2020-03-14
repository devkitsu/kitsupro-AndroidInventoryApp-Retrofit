package com.example.toko.API;

import com.example.toko.Value;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface insertPenjualanAPI {

        @FormUrlEncoded
        @POST("insert_penjualan.php")
        Call<Value> penjualan (@Field("id_penjualan") String id_penjualan,
                               @Field("tgl_penjualan") String tgl_penjualan,
                               @Field("id_pelanggan")  int id_pelanggan,
                               @Field("id_barang") int id_barang,
                               @Field("qty_penjualan") int qty_penjualan,
                               @Field("harga_satuan_penjualan") int harga_satuan_penjualan
                               );

        @FormUrlEncoded
        @POST("simpan_penjualan.php")
        Call<Value> simpan_penjualan(@Field("id_penjualan") String id_penjualan);

    @FormUrlEncoded
        @POST("read_cart.php")
        Call<Value> getdetail(@Field("id_penjualan") String id_penjualan);

    @FormUrlEncoded
    @POST("delete_cart.php")
    Call<Value> hapus(@Field("id_barang") String id_barang,
                      @Field("id_penjualan") String id_penjualan,
                      @Field("qty_penjualan") int qty_penjualan);

    @FormUrlEncoded
    @POST("get_laporan.php")
    Call<Value> getlaporan(@Field("tgl_penjualan") String tgl_penjualan);

    @FormUrlEncoded
    @POST("get_detaillaporan.php")
    Call<Value> getdetaillaporan(@Field("id_penjualan") String id_penjualan);

    @FormUrlEncoded
    @POST("search_pelanggan.php")
    Call<Value> searchpel(@Field("search") String search);
}
