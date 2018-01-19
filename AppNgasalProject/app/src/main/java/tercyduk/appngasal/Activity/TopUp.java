package tercyduk.appngasal.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import tercyduk.appngasal.R;
import tercyduk.appngasal.coresmodel.User;

public class TopUp extends AppCompatActivity {
    Button btnToDetail;
    EditText edtTop;
    String topups;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Topup");

        edtTop = (EditText)findViewById(R.id.edttopup);
        btnToDetail = (Button)findViewById(R.id.btntoDetail);
        btnToDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean _isvalid = true;
                int minimal=5;

                if(TextUtils.isEmpty(edtTop.getText().toString()))
                {
                    _isvalid = false;
                    edtTop.setError("Topup is required");
                }
                else if(edtTop.getText().toString().length()<6)
                {
                    _isvalid =false;
                    edtTop.setError("Minimal 100000");
                }
                if(_isvalid) {
                    final Intent inten = getIntent();
                    final String token = inten.getStringExtra("token");
                    final String email = inten.getStringExtra("email");
                    topups = edtTop.getText().toString();
                    Intent intenttodetail = new Intent(TopUp.this, DetailTopups.class);
                    intenttodetail.putExtra("email", email);
                    intenttodetail.putExtra("token", token);
                    intenttodetail.putExtra("topup", topups);
                    startActivity(intenttodetail);
                }
            }
        });

    }
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
