package tercyduk.appngasal.Activity;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tercyduk.appngasal.Main2Activity;
import tercyduk.appngasal.R;
import tercyduk.appngasal.apihelper.APIClient;
import tercyduk.appngasal.apihelper.Adapter.CustomeSpinnerAdapter;
import tercyduk.appngasal.apihelper.Adapter.CustomeSpinnerAdapterHari;
import tercyduk.appngasal.apihelper.Adapter.CustomeSpinnerAdapterJam;
import tercyduk.appngasal.apihelper.LapanganFutsalService;
import tercyduk.appngasal.coresmodel.LapanganFutsal;
import tercyduk.appngasal.coresmodel.SpinnerData;
import tercyduk.appngasal.coresmodel.User;

public class Booking extends AppCompatActivity  {
    RadioGroup rg;
    RadioButton rbtn;
    EditText EtextDate;
    Calendar mCurrentdate;
    Spinner CustomeSpinner;
    private User _user;
    String date,waktu,lamajam;
    Button btnbook,btnccl;
    String LamaJam[]={"1 JAM","2 JAM","3 JAM"};
    String Waktu[]={"09.00","10.00","11.00","12.00","13.00","14.00","15.00"
    ,"16.00","17.00","18.00","19.00","20.00","21.00"};
    int angka[]={1,2,3,4,5,6,7,8,9,10,11,12};
    int angkas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Pemesanan");
        rg =(RadioGroup)findViewById(R.id.RdioGroup);
        //SpinnerJam




        initDatalapangan();
        SpinnerJam();
        SpinnerLama();
        Callendar();
        btnbook = (Button)findViewById(R.id.book_btn);
        btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radiobuttonid = rg.getCheckedRadioButtonId();
                rbtn = (RadioButton)findViewById(radiobuttonid);
                if(rbtn.getText() == "Sakuku")
                {
                    Toast.makeText(getApplicationContext(),rbtn.getText(),Toast.LENGTH_SHORT);
                }
                else{

                    Intent _intent = getIntent();
                    String id = _intent.getStringExtra("id");
                    String token = _intent.getStringExtra("token");
                    Intent intentinvoice = new Intent(Booking.this,Invoice.class);
                    intentinvoice.putExtra("id",id);
                    intentinvoice.putExtra("token",token);
                    startActivity(intentinvoice);
                }
//                if(EtextDate.getText().toString() != lapanganyangada ksh statment nya lapangan nya ambil dari api)
            }
        });
        btnccl = (Button)findViewById(R.id.cancelbook_btn);
        btnccl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent _intent = getIntent();
                String id = _intent.getStringExtra("id");
                String token = _intent.getStringExtra("token");
              Intent intentback = new Intent(Booking.this,DetailLapangan.class);
              intentback.putExtra("id",id);
              intentback.putExtra("token",token);
              startActivity(intentback);
            }
        });
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
                    ImageView photo_lapang = (ImageView) findViewById(R.id.bookimg);

                    Picasso.with(getApplicationContext()).load(lapanganFutsal.getPhoto_url()).into(photo_lapang);
                    TextView namalapang = (TextView) findViewById(R.id.bookname);
                    namalapang.setText(lapanganFutsal.getFutsal_name());
                    TextView alamat = (TextView) findViewById(R.id.bookadress);
                    alamat.setText(lapanganFutsal.getAddress());
                    TextView kecamatan = (TextView) findViewById(R.id.bookkecamatan);
                    kecamatan.setText(lapanganFutsal.getDistricts());
                    TextView price = (TextView) findViewById(R.id.bookprice);
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
                Intent intentToMainmenu = new Intent(Booking.this, Main2Activity.class);
                intentToMainmenu.putExtra("token", token);
                intentToMainmenu.putExtra("email", email);
                startActivity(intentToMainmenu);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void Callendar()
    {
        //Calender
        EtextDate = (EditText) findViewById(R.id.edttxtbook);
        EtextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentdate = Calendar.getInstance();
//                mCurrentdate.setTimeInMillis(System.currentTimeMillis() - 1000);
                int year = mCurrentdate.get(Calendar.YEAR);
                int month = mCurrentdate.get(Calendar.MONTH);
                int day = mCurrentdate.get(Calendar.DAY_OF_WEEK);
                final DatePickerDialog datePickerDialog = new DatePickerDialog(Booking.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int Tahunbook, int Bulanbook, int Haribook) {
                        int Bulans = Bulanbook +1 ;
                        EtextDate.setText(Haribook + "-" + Bulans + "-" + Tahunbook);
                        mCurrentdate.set(Tahunbook, Bulanbook, Haribook);
                        date = EtextDate.getText().toString();//send date to api

                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });
    }


    private void SpinnerJam()
    {
        CustomeSpinner =(Spinner) findViewById(R.id.SpinnerJam);
        final  TextView txtwaktu = (TextView)findViewById(R.id.txtJammulai);
        final List<SpinnerData> customeList =new ArrayList<>();
        customeList.add(new SpinnerData(R.mipmap.sunrise,"09.00"));
        customeList.add(new SpinnerData(R.mipmap.sunrise,"10.00"));
        customeList.add(new SpinnerData(R.mipmap.sunrise,"11.00"));
        customeList.add(new SpinnerData(R.mipmap.sunrise,"12.00"));
        customeList.add(new SpinnerData(R.mipmap.cloudy,"13.00"));
        customeList.add(new SpinnerData(R.mipmap.cloudy,"14.00"));
        customeList.add(new SpinnerData(R.mipmap.cloudy,"15.00"));
        customeList.add(new SpinnerData(R.mipmap.cloudy,"16.00"));
        customeList.add(new SpinnerData(R.mipmap.cloudy,"17.00"));
        customeList.add(new SpinnerData(R.mipmap.cloudy,"18.00"));
        customeList.add(new SpinnerData(R.mipmap.night,"19.00"));
        customeList.add(new SpinnerData(R.mipmap.night,"20.00"));
        customeList.add(new SpinnerData(R.mipmap.night,"21.00"));
        CustomeSpinnerAdapterHari customeSpinnerAdapter =new CustomeSpinnerAdapterHari(Booking.this,R.layout.spinner_layout_hari,customeList);
        CustomeSpinner.setAdapter(customeSpinnerAdapter);
        CustomeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        waktu = Waktu[0];
                        txtwaktu.setText(waktu);
                        angkas = 1;
                        break;
                    case 1:
                        waktu = Waktu[1];
                        txtwaktu.setText(waktu);
                        angkas = 2;
                        break;
                    case 2:
                        waktu = Waktu[2];
                        txtwaktu.setText(waktu);
                        angkas = 3;
                        break;
                    case 3:
                        waktu = Waktu[3];
                        txtwaktu.setText(waktu);
                        angkas = 4;
                        break;
                    case 4:
                        waktu = Waktu[4];
                        txtwaktu.setText(waktu);
                        angkas = 5;
                        break;
                    case 5:
                        waktu = Waktu[5];
                        txtwaktu.setText(waktu);
                        angkas = 6;
                        break;
                    case 6:
                        waktu = Waktu[6];
                        txtwaktu.setText(waktu);
                        angkas = 7;
                        break;
                    case 7:
                        waktu = Waktu[7];
                        txtwaktu.setText(waktu);
                        angkas = 8;
                        break;
                    case 8:
                        waktu = Waktu[8];
                        txtwaktu.setText(waktu);
                        angkas = 9;
                        break;
                    case 9:
                        waktu = Waktu[9];
                        txtwaktu.setText(waktu);
                        angkas = 10;
                        break;
                    case 10:
                        waktu = Waktu[10];
                        txtwaktu.setText(waktu);
                        angkas =11;
                        break;
                    case 11:
                        waktu = Waktu[11];
                        txtwaktu.setText(waktu);
                        angkas =12;
                        break;
                    case 12:
                        waktu = Waktu[12];
                        txtwaktu.setText(waktu);
                        angkas = 13;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void SpinnerLama()
    {

        CustomeSpinner =(Spinner) findViewById(R.id.SpinnerlamaJam);
        final TextView txtLamaJam =(TextView)findViewById(R.id.txtLamaJam);
        final TextView txtJamhabis =(TextView)findViewById(R.id.txtJamSelesai);
        final List<SpinnerData> customeList =new ArrayList<>();
        customeList.add(new SpinnerData(R.mipmap.onejam,"1 JAM"));
        customeList.add(new SpinnerData(R.mipmap.twojam,"2 JAM"));
        customeList.add(new SpinnerData(R.mipmap.threejam,"3 JAM"));
        CustomeSpinnerAdapterJam customeSpinnerAdapter =new CustomeSpinnerAdapterJam(Booking.this,R.layout.spinner_layout_hari,customeList);
        CustomeSpinner.setAdapter(customeSpinnerAdapter);
        CustomeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        lamajam = LamaJam[0];
                        txtLamaJam.setText(lamajam);
                        if(angkas == 1)
                        {
                            txtJamhabis.setText("10.00");
                        }
                        else if(angkas == 2 )
                        {
                            txtJamhabis.setText("11.00");
                        }
                        else if(angkas == 3 )
                        {
                            txtJamhabis.setText("12.00");
                        }
                        else if(angkas == 4 )
                        {
                            txtJamhabis.setText("13.00");
                        }
                        else if(angkas == 5 )
                        {
                            txtJamhabis.setText("14.00");
                        }
                        else if(angkas == 6 )
                        {
                            txtJamhabis.setText("15.00");
                        }
                        else if(angkas == 7 )
                        {
                            txtJamhabis.setText("16.00");
                        }
                        else if(angkas == 8 )
                        {
                            txtJamhabis.setText("17.00");
                        }
                        else if(angkas == 9 )
                        {
                            txtJamhabis.setText("18.00");
                        }
                        else if(angkas == 10 )
                        {
                            txtJamhabis.setText("19.00");
                        }
                        else if(angkas == 11 )
                        {
                            txtJamhabis.setText("20.00");
                        }
                        else if(angkas == 12 )
                        {
                            txtJamhabis.setText("21.00");
                        }
                        break;
                    case 1:
                        lamajam = LamaJam[1];
                        txtLamaJam.setText(lamajam);
                        if(angkas == 1)
                        {
                            txtJamhabis.setText("11.00");
                        }
                        else if(angkas == 2 )
                        {
                            txtJamhabis.setText("12.00");
                        }
                        else if(angkas == 3 )
                        {
                            txtJamhabis.setText("13.00");
                        }
                        else if(angkas == 4 )
                        {
                            txtJamhabis.setText("14.00");
                        }
                        else if(angkas == 5 )
                        {
                            txtJamhabis.setText("15.00");
                        }
                        else if(angkas == 6 )
                        {
                            txtJamhabis.setText("16.00");
                        }
                        else if(angkas == 7 )
                        {
                            txtJamhabis.setText("17.00");
                        }
                        else if(angkas == 8 )
                        {
                            txtJamhabis.setText("18.00");
                        }
                        else if(angkas == 9 )
                        {
                            txtJamhabis.setText("19.00");
                        }
                        else if(angkas == 10 )
                        {
                            txtJamhabis.setText("20.00");
                        }
                        else if(angkas == 11 )
                        {
                            txtJamhabis.setText("21.00");
                        }
                        break;
                    case 2:
                        lamajam = LamaJam[2];
                        txtLamaJam.setText(lamajam);
                        if(angkas == 1)
                        {
                            txtJamhabis.setText("12.00");
                        }
                        else if(angkas == 2 )
                        {
                            txtJamhabis.setText("13.00");
                        }
                        else if(angkas == 3 )
                        {
                            txtJamhabis.setText("14.00");
                        }
                        else if(angkas == 4 )
                        {
                            txtJamhabis.setText("15.00");
                        }
                        else if(angkas == 5 )
                        {
                            txtJamhabis.setText("16.00");
                        }
                        else if(angkas == 6 )
                        {
                            txtJamhabis.setText("17.00");
                        }
                        else if(angkas == 7 )
                        {
                            txtJamhabis.setText("18.00");
                        }
                        else if(angkas == 8 )
                        {
                            txtJamhabis.setText("19.00");
                        }
                        else if(angkas == 9 )
                        {
                            txtJamhabis.setText("20.00");
                        }
                        else if(angkas == 10 )
                        {
                            txtJamhabis.setText("21.00");
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
