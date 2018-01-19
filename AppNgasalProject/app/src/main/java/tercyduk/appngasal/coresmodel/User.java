package tercyduk.appngasal.coresmodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 11/29/2017.
 */

public class User {
    @SerializedName("token")
    private String token;
    @SerializedName("id")
    private String id;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("name")
    private String name;

    @SerializedName("balance")
    private Double balance;

    @SerializedName("birth_date")
    private String birth_date;

    @SerializedName("phone_number")
    private String phone_number;

    @SerializedName("photo")
    private String photo;

    @SerializedName("chart")
    private int chart;

    @SerializedName("confirmed")
    private String comfirmed;

    @SerializedName("address")
    private String address;

    private String topupsementaraString;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getChart() {
        return chart;
    }

    public void setChart(int chart) {
        this.chart = chart;
    }

    public String getComfirmed() {
        return comfirmed;
    }

    public void setComfirmed(String comfirmed) {
        this.comfirmed = comfirmed;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setAddress(String address) {
        this.address= address;
    }
    public String getAddress() {
        return address;
    }

    public void setTopupsementaraString(String topupsementaraString) {
        this.topupsementaraString= topupsementaraString;
    }
    public String getTopupsementaraString() {
        return topupsementaraString;
    }
}
