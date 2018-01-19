package tercyduk.appngasal.apihelper;


import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tercyduk.appngasal.coresmodel.LapanganFutsal;
import tercyduk.appngasal.coresmodel.TopUp;

/**
 * Created by Tommy Aditya on 1/3/2018.
 */

public interface TopUpService{
    @POST("transfer_topup/")
    @FormUrlEncoded
    Call<TopUp> create(@Header("Authorization") String authToken,
                       @Field("user_id") String user_id,
                       @Field("total_transfer") double total_transfer,
                       @Field("bankuser_name") String bankuser_name,
                       @Field("bankuser_account_number") String bankuser_account_number,
                       @Field("name_bankuser") String name_bankuser,
                       @Field("bankadmin_name") String bankadmin_name,
                       @Field("bankadmin_account_number") String bankadmin_account_number,
                       @Field("name_adminbank") String name_adminbank,
                       @Field("transfer_date") String transfer_date,
                       @Field("information") String information,
                       @Field("status") String status
    );

    @POST("transfer_topup/update")
    @FormUrlEncoded
    Call<Void> update(@Header("Authorization") String authToken,
                       @Field("id") String id,
                       @Field("jumlah_transfer") String jumlah_transfer,
                       @Field("bankuser_name") String bankuser_name,
                       @Field("bankuser_account_number") String bankuser_account_number,
                       @Field("name_bankuser") String name_bankuser,
                       @Field("transfer_date") String transfer_date,
                       @Field("information") String information,
                       @Field("status") String status
    );

    @POST("transfer_topup/edit/")
    @FormUrlEncoded
        Call<List<TopUp>> find(@Header("Authorization") String authToken ,@Field("user_id") String user_id);


}
