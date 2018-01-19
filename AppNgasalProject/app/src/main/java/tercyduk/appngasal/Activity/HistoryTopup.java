package tercyduk.appngasal.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tercyduk.appngasal.Main2Activity;
import tercyduk.appngasal.R;
import tercyduk.appngasal.apihelper.APIClient;
import tercyduk.appngasal.apihelper.Adapter.AdapterRViewHistoryTopup;
import tercyduk.appngasal.apihelper.Adapter.AdapterRViewLapangan;
import tercyduk.appngasal.apihelper.LapanganFutsalService;
import tercyduk.appngasal.apihelper.TopUpService;
import tercyduk.appngasal.coresmodel.*;
import tercyduk.appngasal.coresmodel.TopUp;

public class HistoryTopup extends AppCompatActivity {
    List<tercyduk.appngasal.coresmodel.TopUp> topups;
    RecyclerView rv;
    AdapterRViewHistoryTopup adapterRViewHistoryTopup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_topup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Transaksi");
        InitRviewTopupHistory();
    }
    private void InitRviewTopupHistory()
    {
        rv = (RecyclerView) findViewById(R.id.historytopup);
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        topups = new ArrayList<>();
        Intent inten = getIntent();
        final String token = inten.getStringExtra("token");
        final String user_id = inten.getStringExtra("user_id");


        TopUpService topUpService = APIClient.getClient().create(TopUpService.class);
        Call<List<TopUp>> call = topUpService.find("Bearer "+token,user_id);
        call.enqueue(new Callback<List<TopUp>>() {
            @Override
            public void onResponse(Call<List<TopUp>> call, Response<List<TopUp>> response) {
                final List<TopUp> list = response.body();
                TopUp topUp= new TopUp();
                for (int i = 0; i < list.size(); i++) {
                    topUp = new TopUp();
                    final String id = list.get(i).getId();
                    String sttstopup = list.get(i).getStatus();
                    String topuptgl = list.get(i).getTransfer_date();
                    Double topupprice = list.get(i).getTotal_transfer();

                    topUp.setStatus(sttstopup);
                    topUp.setTransfer_date(topuptgl);
                    topUp.setTotal_transfer(topupprice);

                    topups.add(topUp);


                }


                adapterRViewHistoryTopup = new AdapterRViewHistoryTopup(topups);
                rv.setHasFixedSize(true);
                //RecyclerView.LayoutManager recyce = new GridLayoutManager(LapanganRview.this,2);
                RecyclerView.LayoutManager recyce = new LinearLayoutManager(HistoryTopup.this);
                //rv.addItemDecoration(new GridSpacingdecoration(2, dpToPx(10), true));
                rv.setLayoutManager(recyce);
                //rv.setItemAnimator( new DefaultItemAnimator());
                rv.setAdapter(adapterRViewHistoryTopup);
            }

            @Override
            public void onFailure(Call<List<TopUp>> call, Throwable t) {

            }
        });
        }
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    }
