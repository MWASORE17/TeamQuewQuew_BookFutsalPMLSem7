package tercyduk.appngasal.Activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import retrofit2.Callback;
import retrofit2.Response;
import tercyduk.appngasal.Main2Activity;
import tercyduk.appngasal.R;
import tercyduk.appngasal.apihelper.APIClient;
import tercyduk.appngasal.apihelper.TopUpService;
import tercyduk.appngasal.apihelper.UserClient;
import tercyduk.appngasal.coresmodel.*;
import tercyduk.appngasal.coresmodel.TopUp;

public class KonfirmasiTopup extends AppCompatActivity {
    Calendar mCurrentdate;
    EditText edtDetailtop,nameUserTop,ttltransfer,nameBankTop,edttopups,nomorRekening,catatan;
    Button btnCancel,btnConfirm;
    String date,name,jlhtrf,nmbankuser,tgl,norek,ctt,user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_topup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initComponents();
        initData();
        Callendar();
    }
    public int ConvertDate(String s)
    {
        String[] Tanggal = s.split("-");
        int tanggal = Integer.parseInt(Tanggal[0]);
        int bulan = Integer.parseInt(Tanggal[1]);
        int tahun = Integer.parseInt(Tanggal[2]);
        return tanggal+ bulan + tahun;
    }
    private void initData()
    {
        Intent intents = getIntent();
        String email = intents.getStringExtra("email");
        String token = intents.getStringExtra("token");
        //Toast.makeText(getApplicationContext(),token.toString(), Toast.LENGTH_SHORT).show();

        final UserClient userClient= APIClient.getClient().create(UserClient.class);
        retrofit2.Call<User> call = userClient.find("Bearer "+token, email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(retrofit2.Call<User> call, retrofit2.Response<User> response) {
                if(response.body() != null){
                    User users= response.body();
//
                    user_id =users.getId();
                }

            }
            @Override
            public void onFailure(retrofit2.Call call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initComponents() {

        final Intent inten = getIntent();
        final String idt = inten.getStringExtra("idt");
        final String token = inten.getStringExtra("token");
        final String email = inten.getStringExtra("email");
        final String topup = inten.getStringExtra("topup");
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KonfirmasiTopup.this, Main2Activity.class);
                intent.putExtra("token", token);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameUserTop = (EditText)findViewById(R.id.nameUsertop);
                name = nameUserTop.getText().toString();
                ttltransfer = (EditText)findViewById(R.id.ttltransfer);
                jlhtrf =ttltransfer.getText().toString();
                nameBankTop = (EditText)findViewById(R.id.nameBanktop);
                nmbankuser =nameBankTop.getText().toString();
                nomorRekening = (EditText)findViewById(R.id.nomorRekening);
                norek = nomorRekening.getText().toString();
                edttopups = (EditText)findViewById(R.id.edttopups);
                tgl = edttopups.getText().toString();
                catatan = (EditText)findViewById(R.id.catatan);
                ctt = catatan.getText().toString();
                TopUpService topUpService= APIClient.getClient().create(TopUpService.class);
                retrofit2.Call call = topUpService.update("Bearer "+token,idt,jlhtrf,nmbankuser,norek,name,tgl,ctt,"Menunggu Konfirmasi");
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(retrofit2.Call call, Response response) {
                        if (response.isSuccessful()) {
                            Intent intentToWallet = new Intent(KonfirmasiTopup.this, HistoryTopup.class);
                            intentToWallet.putExtra("token", token);
                            intentToWallet.putExtra("email", email);
                            intentToWallet.putExtra("user_id", user_id);
                            startActivity(intentToWallet);
//
                        }else {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });


                //coding masukkan data kedata database;
            }
        });
    }
    private void Callendar()
    {
        //Calender
        edtDetailtop = (EditText) findViewById(R.id.edttopups);
        edtDetailtop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentdate = Calendar.getInstance();
//                mCurrentdate.setTimeInMillis(System.currentTimeMillis() - 1000);
                int year = mCurrentdate.get(Calendar.YEAR);
                int month = mCurrentdate.get(Calendar.MONTH);
                int day = mCurrentdate.get(Calendar.DAY_OF_WEEK);
                final DatePickerDialog datePickerDialog = new DatePickerDialog(KonfirmasiTopup.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int Tahunbook, int Bulanbook, int Haribook) {
                        int Bulans = Bulanbook +1 ;
                        edtDetailtop.setText(Haribook + "-" + Bulans + "-" + Tahunbook);
                        mCurrentdate.set(Tahunbook, Bulanbook, Haribook);

                        date = edtDetailtop.getText().toString();// Ini data date yang nnti dikirim ke api

                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });

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
                Intent intentToMainmenu = new Intent(KonfirmasiTopup.this, Main2Activity.class);
                intentToMainmenu.putExtra("token", token);
                intentToMainmenu.putExtra("email", email);
                startActivity(intentToMainmenu);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
