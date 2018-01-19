package tercyduk.appngasal.coresmodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created User on 12/5/2017.
 */

public class LapanganFutsal implements Serializable {


    @SerializedName("futsal_name")
    private String futsal_name;

    @SerializedName("address")
    private String address;

    @SerializedName("type_field")
    private String type_field;

    @SerializedName("price")
    private Double price;

    @SerializedName("description")
    private String description;

    @SerializedName("districts")
    private String districts;

    @SerializedName("photo_url")
    private String photo_url;

    @SerializedName("id")
    private String id;

    private String token;

    //public static ArrayList<LapanganFutsal> lapangans = new ArrayList<>();
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFutsal_name() {
        return futsal_name;
    }

    public void setFutsal_name(String futsal_name) {
        this.futsal_name = futsal_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType_field() {
        return type_field;
    }

    public void setType_field(String type_field) {
        this.type_field = type_field;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistricts() {
        return districts;
    }

    public void setDistricts(String districts) {
        this.districts = districts;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
