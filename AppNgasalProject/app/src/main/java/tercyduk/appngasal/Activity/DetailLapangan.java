package tercyduk.appngasal.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tercyduk.appngasal.R;
import tercyduk.appngasal.apihelper.APIClient;
import tercyduk.appngasal.apihelper.LapanganFutsalService;
import tercyduk.appngasal.coresmodel.LapanganFutsal;
import tercyduk.appngasal.modules.auth.user.Login;

public class DetailLapangan extends AppCompatActivity {
    private LapanganFutsal _lapang;
    ImageLoader imageLoader;
    ProgressDialog loading;
    Context mcontext;
    Button btnBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_lapangan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Detail Lapangan");
        Intent _intent = getIntent();

        String id = _intent.getStringExtra("id");
        String token = _intent.getStringExtra("token");


        LapanganFutsalService lapanganFutsalService = APIClient.getClient().create(LapanganFutsalService.class);

        Call<LapanganFutsal> call=   lapanganFutsalService.find("Bearer "+token,id);
        call.enqueue(new Callback<LapanganFutsal>() {
            @Override
            public void onResponse(Call<LapanganFutsal> call, Response<LapanganFutsal> response) {
                if(response.body() != null){
                    final ProgressDialog progress = new ProgressDialog(DetailLapangan.this);
                    progress.setMessage("Harap Tunggu ...");
                    progress.show();

                    Thread _thread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                sleep(2000);
                                progress.dismiss();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    _thread.start();
                    LapanganFutsal lapanganFutsal= response.body();
                    ImageView photo_lapang = (ImageView) findViewById(R.id.detail_lapang_photo);
                    
                    Picasso.with(getApplicationContext()).load(lapanganFutsal.getPhoto_url()).into(photo_lapang);
                    TextView namalapang = (TextView) findViewById(R.id.detail_lapang_name);
                    namalapang.setText(lapanganFutsal.getFutsal_name());
                    TextView alamat = (TextView) findViewById(R.id.detail_lapang_alamat);
                    alamat.setText(lapanganFutsal.getAddress());
                    TextView kecamatan = (TextView) findViewById(R.id.detail_lapang_kecamatan);
                    kecamatan.setText(lapanganFutsal.getDistricts());
                    TextView deskripsi = (TextView) findViewById(R.id.detail_lapang_deskripsi);
                    deskripsi.setText(lapanganFutsal.getDescription());
                    TextView price = (TextView) findViewById(R.id.detail_lapang_price);
                    price.setText("RP " + lapanganFutsal.getPrice().intValue());


                }
            }


            @Override
            public void onFailure(Call<LapanganFutsal> call, Throwable t) {

            }
        }) ;
        initData();

    }
    public void initData()
    {
        btnBook = (Button)findViewById(R.id.btn_book);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = getIntent();
                String id = intents.getStringExtra("id");
                String token = intents.getStringExtra("token");
                Intent intenttobook = new Intent(DetailLapangan.this,Booking.class);
                intenttobook.putExtra("token",token);
                intenttobook.putExtra("id",id);
                startActivity(intenttobook);

            }
        });

    }
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
