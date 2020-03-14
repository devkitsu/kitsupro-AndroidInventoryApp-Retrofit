package com.example.toko.API;

import com.example.toko.Value;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface insertKatAPI {
    @FormUrlEncoded
    @POST("insert_kategori.php")
    Call<Value> kategori (@Field("nama_kategori")String nama_kategori);

    @GET("read_kategori.php")
    Call<Value> getkategori();

    @FormUrlEncoded
    @POST("search_kategori.php")
    Call<Value> search (@Field("search")String search);

    @FormUrlEncoded
    @POST("ubah_kategori.php")
    Call<Value> ubah (@Field("id_kategori")String id_kategori,
                      @Field("nama_kategori")String nama_kategori);

    @FormUrlEncoded
    @POST("delete_kategori.php")
    Call<Value> hapus(@Field("id_kategori") String id_kategori);

}
