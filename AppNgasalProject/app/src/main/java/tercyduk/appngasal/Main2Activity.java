package tercyduk.appngasal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tercyduk.appngasal.Activity.Booking;
import tercyduk.appngasal.Activity.EditProfile;
import tercyduk.appngasal.Activity.HistoryTopup;
import tercyduk.appngasal.Activity.Profil;
import tercyduk.appngasal.Activity.TopUp;
import tercyduk.appngasal.Activity.Wallet;
import tercyduk.appngasal.apihelper.APIClient;
import tercyduk.appngasal.apihelper.Adapter.AdapterRViewLapangan;
import tercyduk.appngasal.apihelper.LapanganFutsalService;
import tercyduk.appngasal.apihelper.UserClient;
import tercyduk.appngasal.coresmodel.LapanganFutsal;
import tercyduk.appngasal.coresmodel.User;
import tercyduk.appngasal.modules.auth.user.Login;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    List<LapanganFutsal> lapang;
    RecyclerView rv;
    ImageView photoPronav,potoprofile;
    private Context context;
    private User user;
    private String user_id;
    TextView txtName;
    TextView txtEmail;
    AdapterRViewLapangan recyclerAdapter ;
    ProgressDialog loading;
    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Lapangan");

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        //lapangan





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setScrimColor(Color.TRANSPARENT);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        int width =getResources().getDisplayMetrics().widthPixels;
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) navigationView.getLayoutParams();
        params.width = width;
        navigationView.setLayoutParams(params);

        NavigationView nv = (NavigationView)findViewById(R.id.drawerlayout2);
        nv.setNavigationItemSelectedListener(this);
        initData();
        InitRviewlapangan();
        imageClik();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) search.getActionView();
        initSearch(searchView);
//        customizeSearchView(searchView);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intents = getIntent();
        String email = intents.getStringExtra("email");
        String tokent = intents.getStringExtra("token");
        if (id == R.id.profile) {

            Intent intent1 = new Intent(Main2Activity.this, Profil.class);
            intent1.putExtra("email",email);
            intent1.putExtra("token",tokent);
            startActivity(intent1);
        } else if (id == R.id.history) {
            Intent intenshistory = new Intent(Main2Activity.this, Booking.class);
            startActivity(intenshistory);
        } else if (id == R.id.wallet) {
            Intent inteswallet = new Intent(Main2Activity.this, Wallet.class);
            inteswallet.putExtra("email",email);
            inteswallet.putExtra("token",tokent);
            startActivity(inteswallet);
        } else if (id == R.id.topupshistory) {
            Intent intestopuphistory = new Intent(Main2Activity.this, HistoryTopup.class);
            intestopuphistory.putExtra("email",email);
            intestopuphistory.putExtra("token",tokent);
            intestopuphistory.putExtra("user_id",user_id);
            startActivity(intestopuphistory);
        }
        else if (id == R.id.topups) {
            Intent intetopup = new Intent(Main2Activity.this, TopUp.class);
            intetopup.putExtra("email",email);
            intetopup.putExtra("token",tokent);
            startActivity(intetopup);

        }else if(id == R.id.logout)
        {
            new AlertDialog.Builder(this).setMessage("Apakah Sudah Yakin Ingin Keluar ?").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doChangeActivity(Main2Activity.this,Login.class);
                    finish();
                }
            }).setNegativeButton("Tidak",null).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void InitRviewlapangan()
    {


        rv = (RecyclerView) findViewById(R.id.lapang_rv);
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        lapang = new ArrayList<>();
        Intent inten = getIntent();
        final String token = inten.getStringExtra("token");


        LapanganFutsalService lapanganFutsalService = APIClient.getClient().create(LapanganFutsalService.class);

        Call<List<LapanganFutsal>> call=   lapanganFutsalService.getSecret("Bearer "+token);
        call.enqueue(new Callback<List<LapanganFutsal>>() {
            @Override
            public void onResponse(Call<List<LapanganFutsal>> call, Response<List<LapanganFutsal>> response) {

                final List<LapanganFutsal> list = response.body();
                LapanganFutsal lapanganFutsal = new LapanganFutsal();
                for (int i =0 ;i<list.size();i++){
                    lapanganFutsal = new LapanganFutsal();
                    final String id = list.get(i).getId();
                    String name = list.get(i).getFutsal_name();
                    String districts = list.get(i).getDistricts();
                    String address = list.get(i).getAddress();
                    String deskripsi = list.get(i).getDescription();
                    String image = list.get(i).getPhoto_url();
                    double price = list.get(i).getPrice();
                    lapanganFutsal.setPrice(price);
                    lapanganFutsal.setDistricts(districts);
                    lapanganFutsal.setAddress(address);
                    lapanganFutsal.setDescription(deskripsi);
                    lapanganFutsal.setId(id);
                    lapanganFutsal.setFutsal_name(name);
                    lapanganFutsal.setPhoto_url(image);
                    lapanganFutsal.setToken(token);
                    lapang.add(lapanganFutsal);


                }


                recyclerAdapter = new AdapterRViewLapangan(lapang,ImageLoader.getInstance());
                rv.setHasFixedSize(true);
                //RecyclerView.LayoutManager recyce = new GridLayoutManager(LapanganRview.this,2);
                RecyclerView.LayoutManager recyce = new LinearLayoutManager(Main2Activity.this);
                //rv.addItemDecoration(new GridSpacingdecoration(2, dpToPx(10), true));
                rv.setLayoutManager(recyce);
                //rv.setItemAnimator( new DefaultItemAnimator());
                rv.setAdapter(recyclerAdapter);





            }

            @Override
            public void onFailure(Call<List<LapanganFutsal>> call, Throwable t) {

            }


        });


    }
    private void initSearch(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);

                return true;
            }
        });
    }
    private void initData()
    {
        Intent intents = getIntent();
        String email = intents.getStringExtra("email");
        String token = intents.getStringExtra("token");
        //Toast.makeText(getApplicationContext(),token.toString(), Toast.LENGTH_SHORT).show();

        final UserClient userClient= APIClient.getClient().create(UserClient.class);
        Call<User> call = userClient.find("Bearer "+token, email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                if(response.body() != null){
                    User users= response.body();
//                    String name = users.getName();
//                    String email = users.getEmail();
//                    String addres =users.getAddress();
//                    User _user = new User();
//                    _user.setName(name);
//                    _user.setEmail(email);
//                    _user.setAddress(addres);
                    if(users.getPhoto() != null)
                    {
                        photoPronav = (CircularImageView) findViewById(R.id.photoProNav);
                        Picasso.with(getApplicationContext()).load(users.getPhoto()).resize(100,100).into(photoPronav);


                    }
                    else {
                        photoPronav = (CircularImageView)findViewById(R.id.photoProNav);
                        photoPronav.setImageResource(R.mipmap.profile);

                    }
                    user_id =users.getId();
                    txtName = (TextView)findViewById(R.id.nameProfNav);
                    txtName.setText(users.getName());
                    txtEmail = (TextView)findViewById(R.id.emailProfNav);
                    txtEmail.setText(users.getEmail());
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
    public void imageClik()
    {
        potoprofile = (CircularImageView)findViewById(R.id.photoProNav);
        potoprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intents = getIntent();
                String email = intents.getStringExtra("email");
                String tokent = intents.getStringExtra("token");
                Intent intenttoprofile = new Intent(Main2Activity.this, Profil.class);
                intenttoprofile.putExtra("email",email);
                intenttoprofile.putExtra("token",tokent);
                startActivity(intenttoprofile);

            }
        });
    }
    public static void doChangeActivity (Context context, Class destination) {
        Intent _intent = new Intent(context, destination);
        _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(_intent);
    }
}

