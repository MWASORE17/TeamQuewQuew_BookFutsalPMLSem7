package tercyduk.appngasal.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import tercyduk.appngasal.R;
import tercyduk.appngasal.apihelper.APIClient;
import tercyduk.appngasal.apihelper.UserClient;
import tercyduk.appngasal.coresmodel.User;

public class Wallet extends AppCompatActivity {
    TextView wallets;
    String id,name,alamat,no_hp;
    AlertDialog alertDialog;
    Button btntopups;
    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Wallet");
        initComponents();
    }


    private void initComponents() {
        final Intent inten = getIntent();
        final String token = inten.getStringExtra("token");
        final String email = inten.getStringExtra("email");
        //Toast.makeText(getApplicationContext(),token.toString(), Toast.LENGTH_SHORT).show();
        btntopups = (Button) findViewById(R.id.btnTopup);
        btntopups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Wallet.this, TopUp.class);
                intent.putExtra("token",token);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });



        UserClient userClient= APIClient.getClient().create(UserClient.class);
        Call<User> call = userClient.find("Bearer "+token, email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                if(response.body() != null){
                    User users= response.body();
                    id = users.getId();
                    if(users.getBalance() != null)
                    {
                        wallets = (TextView) findViewById(R.id.txtwallets);
                        wallets.setText("Rp" + users.getBalance());
                    }
                    else
                    {

                        int ballaced = 0;
                        wallets = (TextView) findViewById(R.id.txtwallets);
                        wallets.setText("Rp." + ballaced);

                    }


                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                alertDialogBuilder.setMessage("Jaringan Sedang Bermasalah").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });





    }
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
