package tercyduk.appngasal.modules.auth.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.*;
import tercyduk.appngasal.Activity.EditProfile;
import tercyduk.appngasal.Main2Activity;
import tercyduk.appngasal.R;
import tercyduk.appngasal.apihelper.APIClient;
import tercyduk.appngasal.apihelper.UserClient;
import tercyduk.appngasal.coresmodel.User;

public class Login extends AppCompatActivity {
    EditText etEmail, etPassword;
    TextInputLayout tilEmail, tilPassword;
    Button btnLogin;
    TextView txtRegis;
    ProgressDialog loading;
    Context mContext;
    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        initComponents();
        initobject();
    }
    private String token;
    private String name;
    private String hp;
    private String email;
    private void initComponents() {
        etPassword = (EditText) findViewById(R.id.etxtPass);
        etEmail = (EditText) findViewById(R.id.etxtEmail);
        txtRegis = (TextView) findViewById(R.id.txtRegis);
        btnLogin = (Button) findViewById(R.id.btnCreateAccount);
        tilEmail = (TextInputLayout) findViewById(R.id.login_email);
        tilPassword = (TextInputLayout) findViewById(R.id.login_pass);
        txtRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, Register.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean _isvalid = true;
                tilEmail.setErrorEnabled(false);
                tilPassword.setErrorEnabled(false);
                if (TextUtils.isEmpty(etEmail.getText())) {
                    _isvalid = false;
                    tilEmail.setErrorEnabled(true);
                    tilEmail.setError("Email is required");
                }
//
                else if (TextUtils.isEmpty(etPassword.getText())) {

                    tilPassword.setErrorEnabled(true);
                    tilPassword.setError("Password is required");
                    _isvalid = false;
                }

                if (_isvalid) {
                    try {
                        final User user = new User();
                        user.setEmail(etEmail.getText().toString());
                        user.setPassword(etPassword.getText().toString());
                        final UserClient userClient = APIClient.getClient().create(UserClient.class);
                        retrofit2.Call call = userClient.login(user);

                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(retrofit2.Call<User> call, retrofit2.Response<User> response) {



                                if (response.isSuccessful()) {
//                        Toast.makeText(getApplicationContext(),response.body().getToken(), Toast.LENGTH_SHORT).show();
//                                    final ProgressDialog progress = new ProgressDialog(Login.this);
//                                    progress.setMessage("Harap Tunggu ...");
//                                    progress.show();
//
//                                    Thread _thread = new Thread() {
//                                        @Override
//                                        public void run() {
//                                            try {
//                                                sleep(2000);
//                                                progress.dismiss();
//                                            } catch (InterruptedException e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    };
//                                    _thread.start();
                                    Intent intent = new Intent(Login.this, Main2Activity.class);
                                    token = response.body().getToken().toString();
                                    intent.putExtra("token", token);
                                    intent.putExtra("email",etEmail.getText().toString());
                                    startActivity(intent);
                                    finish();


                                }
                                else
                                {

                                    etEmail.setText("");
                                    etPassword.setText("");
                                    alertDialogBuilder.setMessage("Email Atau Password Salah").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
                            public void onFailure(retrofit2.Call call, Throwable t) {
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

                    } catch (Exception e) {
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
                if (TextUtils.isEmpty(etEmail.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())) {
                    alertDialogBuilder.setMessage("Harap isi semua field").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }


            }
        });
    }
    private void initobject() {
        alertDialogBuilder = new AlertDialog.Builder(this);
    }
}

