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
import tercyduk.appngasal.R;
import tercyduk.appngasal.apihelper.APIClient;
import tercyduk.appngasal.apihelper.LoginService;
import tercyduk.appngasal.coresmodel.User;

public class Register extends AppCompatActivity {
    EditText etName, etEmail, etPassword, etRepassword, etNohp,etAlamat;
    TextInputLayout tilName, tilemail, tilhp, tilpassword, tilrepassword,tilAlamat;
    TextView txtLogin;
    Button btnRegister;
    Context mContext;
    ProgressDialog loading;
    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        initComponents();
        initobject();
    }

    private void initComponents() {

        etNohp = (EditText) findViewById(R.id.phone_number);
        etRepassword = (EditText) findViewById(R.id.repassword);
        etName = (EditText) findViewById(R.id.name);
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);
        etAlamat = (EditText)findViewById(R.id.alamat);
        tilName = (TextInputLayout) findViewById(R.id.register_name_container);
        tilAlamat = (TextInputLayout)findViewById(R.id.register_alamat_container);
        tilemail = (TextInputLayout) findViewById(R.id.register_name_email);
        tilhp = (TextInputLayout) findViewById(R.id.register_name_hp);
        tilpassword = (TextInputLayout) findViewById(R.id.register_name_pass);
        tilrepassword = (TextInputLayout) findViewById(R.id.register_name_pass2);
        btnRegister = (Button) findViewById(R.id.btnCreateAccount);
        txtLogin = (TextView) findViewById(R.id.txtSignin);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, Login.class));
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean _isvalid = true;
                tilName.setErrorEnabled(false);
                tilemail.setErrorEnabled(false);
                tilhp.setErrorEnabled(false);
                tilpassword.setErrorEnabled(false);
                tilrepassword.setErrorEnabled(false);
                if (TextUtils.isEmpty(etName.getText())) {
                    _isvalid = false;
                    tilName.setErrorEnabled(true);
                    tilName.setError("Name is required");
                } else if (etName.getText().length() < 7) {
                    _isvalid = false;
                    tilName.setErrorEnabled(true);
                    tilName.setError("Name minimal 7");

                } else if (!AuthUser.isemailvalid(etEmail.getText().toString())) {
                    _isvalid = false;
                    tilemail.setErrorEnabled(true);
                    tilemail.setError("Email is not valid");
                } else if (TextUtils.isEmpty(etEmail.getText())) {
                    _isvalid = false;
                    tilemail.setErrorEnabled(true);
                    tilemail.setError("Email is required");
                } else if (TextUtils.isEmpty((etNohp.getText()))) {
                    _isvalid = false;
                    tilhp.setErrorEnabled(true);
                    tilhp.setError("Handphone is required");
                } else if (etNohp.getText().length() < 12 ) {
                    _isvalid = false;
                    tilhp.setErrorEnabled(true);
                    tilhp.setError("Hanphone minimal 12");
                } else if (!AuthUser.ispasswordvalid(etPassword.getText().toString())) {
                    _isvalid = false;
                    tilpassword.setErrorEnabled(true);
                    tilpassword.setError("Password is not valid. Password must contains " +
                            "at least 1 lowercase, 1 uppercase, 1 number, 1 special character and minimum 8 characters");
                } else if (TextUtils.isEmpty(etPassword.getText())) {
                    _isvalid = false;
                    tilpassword.setErrorEnabled(true);
                    tilpassword.setError("Password is required");
                } else if (TextUtils.isEmpty(etRepassword.getText())) {
                    _isvalid = false;
                    tilrepassword.setErrorEnabled(true);
                    tilrepassword.setError("Re-Password is required");
                } else if (!etPassword.getText().toString().equals(etRepassword.getText().toString())) {
                    _isvalid = false;
                    tilrepassword.setErrorEnabled(true);
                    tilrepassword.setError("Password not match");

                }else if(TextUtils.isEmpty((etAlamat.getText())))
                {
                    _isvalid = false;
                    tilpassword.setErrorEnabled(true);
                    tilpassword.setError("Alamat is required");

                }
                if (TextUtils.isEmpty(etEmail.getText().toString()) ||
                        TextUtils.isEmpty(etPassword.getText().toString())||
                        TextUtils.isEmpty((etName.getText().toString()))||
                        TextUtils.isEmpty(etNohp.getText().toString())||
                        TextUtils.isEmpty(etRepassword.getText().toString())||
                        TextUtils.isEmpty(etAlamat.getText().toString()))
                     {
                    alertDialogBuilder.setMessage("Harap isi semua field").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                if (_isvalid) {
                    try {
                        User user = new User();
                        user.setEmail(etEmail.getText().toString());
                        user.setPassword(etPassword.getText().toString());
                        user.setPhone_number(etNohp.getText().toString());
                        user.setName(etName.getText().toString());
                        user.setAddress(etAlamat.getText().toString());
                        LoginService loginService = APIClient.getClient().create(LoginService.class);
                        Call call = loginService.create(user);
                        call.enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, retrofit2.Response response) {

                                Boolean result = (Boolean) response.body();
                                if (result) {
                                    Intent intent = new Intent(Register.this, Login.class);
                                    startActivity(intent);
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

                    } catch (Exception e) {

                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }

    private void initobject() {
        alertDialogBuilder = new AlertDialog.Builder(this);
    }
}

