package com.example.toko.API;

import com.example.toko.Value;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface insertPelangganAPI {
    @FormUrlEncoded
    @POST("insert_pelanggan.php")
    Call<Value> insertpelanggan(@Field("nama_pelanggan")    String nama_pelanggan,
                                @Field("telepon_pelanggan") String telepon_pelanggan,
                                @Field("alamat_pelanggan")  String alamat_pelanggan);

    @GET("read_pelanggan.php")
    Call<Value> getPelanggan();

    @FormUrlEncoded
    @POST("search_pelanggan.php")
    Call<Value> search (@Field("search")String search);

    @FormUrlEncoded
    @POST("ubah_pelanggan.php")
    Call<Value> ubah (@Field("id_pelanggan")String id_pelanggan,
                      @Field("nama_pelanggan")String nama_pelanggan,
                      @Field("telepon_pelanggan") String telepon_pelanggan,
                      @Field("alamat_pelanggan")  String alamat_pelanggan);

    @FormUrlEncoded
    @POST("delete_pelanggan.php")
    Call<Value> hapus(@Field("id_pelanggan") String id_pelanggan);
}
