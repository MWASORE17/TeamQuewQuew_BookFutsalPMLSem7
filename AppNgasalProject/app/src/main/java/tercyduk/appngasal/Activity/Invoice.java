package tercyduk.appngasal.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tercyduk.appngasal.Main2Activity;
import tercyduk.appngasal.R;
import tercyduk.appngasal.apihelper.APIClient;
import tercyduk.appngasal.apihelper.LapanganFutsalService;
import tercyduk.appngasal.coresmodel.LapanganFutsal;

public class Invoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initDatalapangan();
    }
    public void initDatalapangan()
    {
        Intent _intent = getIntent();
        String id = _intent.getStringExtra("id");
        String token = _intent.getStringExtra("token");


        LapanganFutsalService lapanganFutsalService = APIClient.getClient().create(LapanganFutsalService.class);

        Call<LapanganFutsal> call=   lapanganFutsalService.find("Bearer "+token,id);
        call.enqueue(new Callback<LapanganFutsal>() {
            @Override
            public void onResponse(Call<LapanganFutsal> call, Response<LapanganFutsal> response) {
                if(response.body() != null){
                    LapanganFutsal lapanganFutsal= response.body();
                    TextView namalapang = (TextView) findViewById(R.id.inforlapangname);
                    namalapang.setText(lapanganFutsal.getFutsal_name());
                    TextView price = (TextView) findViewById(R.id.inforharga);
                    price.setText("RP " + lapanganFutsal.getPrice().intValue());
                }
            }


            @Override
            public void onFailure(Call<LapanganFutsal> call, Throwable t) {

            }
        }) ;
    }
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homes:
                final Intent inten = getIntent();
                final String token = inten.getStringExtra("token");
                final String email = inten.getStringExtra("email");
                Intent intentToMainmenu = new Intent(Invoice.this, Main2Activity.class);
                intentToMainmenu.putExtra("token", token);
                intentToMainmenu.putExtra("email", email);
                startActivity(intentToMainmenu);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
