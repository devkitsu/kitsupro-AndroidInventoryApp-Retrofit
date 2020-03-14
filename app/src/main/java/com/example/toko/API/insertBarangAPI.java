package com.example.toko.API;

import com.example.toko.Value;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface insertBarangAPI {

        @FormUrlEncoded
        @POST("insert_penjualan.php")
        Call<Value> jual (@Field("tgl_penjualan") String tgl_penjualan,
                          @Field("id_pelanggan")  int id_pelanggan);

        @FormUrlEncoded
        @POST("insert_barang.php")
        Call<Value> barang (@Field("nama_barang") String nama_barang,
                            @Field("id_kategori")  int id_kategori,
                            @Field("qty_barang")int qty_barang,
                            @Field("id_satuan")int id_satuan);

        @GET("read_barang.php")
        Call<Value> getbarang();

        @GET("read_stok.php")
        Call<Value> getstok();

        @FormUrlEncoded
        @POST("search_barang.php")
        Call<Value> searchbrg (@Field("search")String search);

        @FormUrlEncoded
        @POST("search_stok.php")
        Call<Value> searchstok (@Field("search")String search);

        @FormUrlEncoded
        @POST("ubah_barang.php")
        Call<Value> ubah (@Field("id_barang")String id_barang,
                          @Field("nama_barang")String nama_barang,
                          @Field("id_kategori")int id_kategori,
                          @Field("id_satuan")  int id_satuan);

        @FormUrlEncoded
        @POST("delete_barang.php")
        Call<Value> hapus(@Field("id_barang") String id_barang);
    }


