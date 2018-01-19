package tercyduk.appngasal.apihelper;

import retrofit2.http.Path;
import tercyduk.appngasal.coresmodel.LapanganFutsal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import tercyduk.appngasal.coresmodel.LapanganResponse;

/**
 * Created by User on 12/6/2017.
 */

public interface LapanganFutsalService {

    @GET("futsal_field")
    Call<List<LapanganFutsal>> getSecret(@Header("Authorization") String authToken);

    @GET("futsal_field/{id}")
    Call<LapanganFutsal> find(@Header("Authorization") String authToken ,@Path("id") String id);

    @POST("create")
    Call<Void> create(@Body LapanganFutsal lapanganFutsal);

    @PUT("update")
    Call<Void> update(@Body LapanganFutsal lapanganFutsal);

    @DELETE("delete/{id}")
    Call<Void> delete(@Query("id") String id);


}
