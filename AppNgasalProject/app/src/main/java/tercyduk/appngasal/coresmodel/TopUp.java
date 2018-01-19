package tercyduk.appngasal.coresmodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tommy Aditya on 1/3/2018.
 */

public class TopUp {
    @SerializedName("id")
    private String id;

    @SerializedName("total_transfer")
    private double total_transfer;

    @SerializedName("bankuser_name")
    private String bankuser_name;

    @SerializedName("name_bankuser")
    private String name_bankuser;

    @SerializedName("transfer_date")
    private String transfer_date;

    @SerializedName("information")
    private String information;

    @SerializedName("status")
    private String status;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotal_transfer() {
        return total_transfer;
    }

    public void setTotal_transfer(double total_transfer) {
        this.total_transfer = total_transfer;
    }

    public String getBankuser_name() {
        return bankuser_name;
    }

    public void setBankuser_name(String bankuser_name) {
        this.bankuser_name = bankuser_name;
    }

    public String getName_bankuser() {
        return name_bankuser;
    }

    public void setName_bankuser(String name_bankuser) {
        this.name_bankuser = name_bankuser;
    }

    public String getTransfer_date() {
        return transfer_date;
    }

    public void setTransfer_date(String transfer_date) {
        this.transfer_date = transfer_date;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
