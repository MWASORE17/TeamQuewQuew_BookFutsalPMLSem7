package tercyduk.appngasal.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import tercyduk.appngasal.Main2Activity;
import tercyduk.appngasal.R;
import tercyduk.appngasal.apihelper.APIClient;
import tercyduk.appngasal.apihelper.UserClient;
import tercyduk.appngasal.coresmodel.User;
import tercyduk.appngasal.modules.auth.user.Login;
import tercyduk.appngasal.modules.auth.user.Register;

public class Profil extends AppCompatActivity implements OnChartValueSelectedListener {
    TextView txtName, txtAlamat, txtEmail, txtNohp;
    ImageView profil;
    Button btnEdit;
    String id,names,alamat,no_hp,emails;
    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBuilder;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Profil");
        initComponents();
        chart();
    }

    private void chart()
    {
        //chart
        PieChart pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);

        int oks = 5;//nanti masukkkan ke sini history nya
        int cancels = 5;
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        yvalues.add(new Entry(cancels, 0));
        yvalues.add(new Entry(oks, 1));

        PieDataSet dataSet = new PieDataSet(yvalues, "Lapangan Booking Detail");

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("Cancel");
        xVals.add("Deal");

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
        pieChart.setDescription("Report Pie Chart");

        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(25f);
        pieChart.setHoleRadius(25f);

        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);
        pieChart.setOnChartValueSelectedListener(this);

        pieChart.animateXY(1400, 1400);
    }
    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");

    }


    public boolean onSupportNavigateUp() {
        Intent intc = getIntent();
        String tokens  =intc.getStringExtra("token");
        Intent intens = new Intent(Profil.this, Main2Activity.class);
        intens.putExtra("name",names);
        intens.putExtra("email",emails);
        intens.putExtra("token",tokens);
        startActivity(intens);
        finish();
        return true;
    }


    private void initComponents() {

        final Intent inten = getIntent();
        final String token = inten.getStringExtra("token");
        final String email = inten.getStringExtra("email");
        //Toast.makeText(getApplicationContext(),token.toString(), Toast.LENGTH_SHORT).show();
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profil.this, EditProfile.class);
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
                    if(users.getPhoto() != null)
                    {
                        profil =(CircularImageView) findViewById(R.id.photo_profil);
                        Picasso.with(getApplicationContext()).load(users.getPhoto()).into(profil);

                    }
                    else {
                        profil =(CircularImageView) findViewById(R.id.photo_profil);
                        profil.setImageResource(R.mipmap.profile);
                    }
                    emails = users.getEmail();
                    names = users.getName();
                    txtNohp = (TextView) findViewById(R.id.edp_txt_hp);
                    txtNohp.setText(users.getPhone_number());
                    txtAlamat = (TextView) findViewById(R.id.edp_txt_alamat);
                    txtAlamat.setText(users.getAddress());
                    txtName = (TextView) findViewById(R.id.edp_txt_name);
                    txtName.setText(users.getName());
                    txtEmail = (TextView)findViewById(R.id.edp_txt_email);
                    txtEmail.setText(email);

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


}
