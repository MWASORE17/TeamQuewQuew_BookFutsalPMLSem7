package tercyduk.appngasal.apihelper;

import retrofit2.http.Header;
import tercyduk.appngasal.coresmodel.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Tommy Aditya on 12/6/2017.
 */

public interface LoginService {
    @POST("/login")
    Call<Boolean> login(@Body User user);

    @POST("/user")
    Call<Boolean> create(@Body User user);
    @PUT("update")
    Call<Boolean> update(@Header("Authorization") User user);

    @GET("user/{email}")
    Call<User> find(@Path("email") String email);

}
