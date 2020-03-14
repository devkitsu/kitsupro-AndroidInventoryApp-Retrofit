package com.example.toko.API;

import com.example.toko.Value;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface insertSupplierAPI {
    @FormUrlEncoded
    @POST("insert_supplier.php")
    Call<Value> insert_supplier (@Field("nama_supplier")    String nama_supplier,
                                 @Field("telepon_supplier") String telepon_supplier,
                                 @Field("alamat_supplier")  String alamat_supplier);

    @GET("read_supplier.php")
    Call<Value> getSupplier();

    @FormUrlEncoded
    @POST("search_supplier.php")
    Call<Value> search (@Field("search")String search);

    @FormUrlEncoded
    @POST("ubah_supplier.php")
    Call<Value> ubah (@Field("id_supplier") String id_supplier,
                      @Field("nama_supplier") String nama_supplier,
                      @Field("telepon_supplier") String telepon_supplier,
                      @Field("alamat_supplier")  String alamat_supplier);

    @FormUrlEncoded
    @POST("delete_supplier.php")
    Call<Value> hapus(@Field("id_supplier") String id_supplier);
}
