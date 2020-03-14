package com.example.toko.API;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import com.example.toko.Value;

public interface insertSatuanBarangAPI {
    @FormUrlEncoded
    @POST("insert_satuan_barang.php")
    Call<Value> add (@Field("nama_satuan")String nama_satuan);

    @GET("read_satuan_barang.php")
    Call<Value> getsatuanbarang();

    @FormUrlEncoded
    @POST("search_satuan_barang.php")
    Call<Value> search (@Field("search")String search);

    @FormUrlEncoded
    @POST("ubah_satuan_barang.php")
    Call<Value> ubah (@Field("id_satuan")String id_satuan,
                      @Field("nama_satuan")String nama_satuan);

    @FormUrlEncoded
    @POST("delete_satuan_barang.php")
    Call<Value> hapus(@Field("id_satuan") String id_satuan);
}
