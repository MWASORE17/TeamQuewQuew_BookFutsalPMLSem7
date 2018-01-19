package tercyduk.appngasal.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tercyduk.appngasal.Main2Activity;
import tercyduk.appngasal.R;
import tercyduk.appngasal.apihelper.APIClient;
import tercyduk.appngasal.apihelper.Adapter.CustomeSpinnerAdapter;
import tercyduk.appngasal.apihelper.TopUpService;
import tercyduk.appngasal.apihelper.UserClient;
import tercyduk.appngasal.coresmodel.*;

public class DetailTopups extends AppCompatActivity {
    Spinner CustomeSpinner;
    TextView txtName, txtTopup;
    TextView txtbanks;
    Calendar mCurrentdate;
    EditText edtDetailtop;
    Button btnConfirm,btnCancel;
    String id,names,alamat,no_hp,emails,bankname,norek1,jenisbank1;
    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBuilder;


    private String norek[]={"4025053698","10700036982589","0315052858","6586287895"};
    String namaRek = "PT. APP NGASAL";
    String jenisBank[]={"Bank Central Asia","Bank Mandiri","Bank Negara Indonesia","Bank Permata"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_topups);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initComponents();
        SpinnerCustome();
//
    }
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    private void initComponents() {

        final Intent inten = getIntent();
        final String token = inten.getStringExtra("token");
        final String email = inten.getStringExtra("email");
        final String topup = inten.getStringExtra("topup");
        final double jlhtrf = Double.parseDouble(topup);

        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailTopups.this, Main2Activity.class);
                intent.putExtra("token",token);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        btnConfirm = (Button)findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopUpService topUpService= APIClient.getClient().create(TopUpService.class);
                Call<tercyduk.appngasal.coresmodel.TopUp> call = topUpService.create("Bearer "+token,id,jlhtrf,"","","",jenisbank1,norek1,bankname,"","","Pending");
                call.enqueue(new Callback<tercyduk.appngasal.coresmodel.TopUp>() {
                    @Override
                    public void onResponse(Call<tercyduk.appngasal.coresmodel.TopUp> call, Response<tercyduk.appngasal.coresmodel.TopUp> response) {


                        if (response.isSuccessful()) {
                            tercyduk.appngasal.coresmodel.TopUp topUp = response.body();
                            Intent intentoConfrim = new Intent(DetailTopups.this,KonfirmasiTopup.class);
                            intentoConfrim.putExtra("idt",topUp.getId());
                            intentoConfrim.putExtra("token",token);
                            intentoConfrim.putExtra("email",email);
                            startActivity(intentoConfrim);
                        } else {
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
                    }

                    @Override
                    public void onFailure(Call<tercyduk.appngasal.coresmodel.TopUp> call, Throwable t) {
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
        });

        UserClient userClient= APIClient.getClient().create(UserClient.class);
        Call<User> call = userClient.find("Bearer "+token, email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                if(response.body() != null){
                    User users= response.body();
                    id = users.getId();
                    emails = users.getEmail();
                    names = users.getName();
                    txtName = (TextView) findViewById(R.id.txtNametop);
                    txtName.setText(users.getName());
                    txtTopup = (TextView)findViewById(R.id.txtJumlahTop);
                    txtTopup.setText(topup);

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
    private void SpinnerCustome()
    {
        CustomeSpinner = (Spinner)findViewById(R.id.spinnerbank);

        final TextView txtBankName =(TextView)findViewById(R.id.txtBankName);
        final TextView txtNoRek =(TextView)findViewById(R.id.txtNoRek);
        final TextView txtNamaPenerima =(TextView)findViewById(R.id.txtNamaPenerima);
        final List<SpinnerData> customeList =new ArrayList<>();
        customeList.add(new SpinnerData(R.mipmap.bca,"Bank Central Asia"));// tambahkan di sini bank nya
        customeList.add(new SpinnerData(R.mipmap.mandiri,"Bank Mandiri"));
        customeList.add(new SpinnerData(R.mipmap.bni,"Bank Negara Indonesia"));
        customeList.add(new SpinnerData(R.mipmap.permata,"Bank Permata"));
        CustomeSpinnerAdapter customeSpinnerAdapter =new CustomeSpinnerAdapter(DetailTopups.this,R.layout.spinner_layour,customeList);
        CustomeSpinner.setAdapter(customeSpinnerAdapter);
        CustomeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        bankname = namaRek;
                        norek1 = norek[0];
                        jenisbank1 = jenisBank[0];
                        txtBankName.setText(jenisBank[0]);
                        txtNoRek.setText(norek[0]);
                        txtNamaPenerima.setText(namaRek);
                        break;
                    case 1:
                        bankname = namaRek;
                        norek1 = norek[1];
                        jenisbank1 = jenisBank[1];
                        txtBankName.setText(jenisBank[1]);
                        txtNoRek.setText(norek[1]);
                        txtNamaPenerima.setText(namaRek);
                        break;
                    case 2:
                        bankname = namaRek;
                        norek1 = norek[2];
                        jenisbank1 = jenisBank[2];
                        txtBankName.setText(jenisBank[2]);
                        txtNoRek.setText(norek[2]);
                        txtNamaPenerima.setText(namaRek);
                        break;
                    case 3:
                        bankname = namaRek;
                        norek1 = norek[3];
                        jenisbank1 = jenisBank[3];
                        txtBankName.setText(jenisBank[3]);
                        txtNoRek.setText(norek[3]);
                        txtNamaPenerima.setText(namaRek);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                Intent intentToMainmenu = new Intent(DetailTopups.this, Main2Activity.class);
                intentToMainmenu.putExtra("token", token);
                intentToMainmenu.putExtra("email", email);
                startActivity(intentToMainmenu);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
