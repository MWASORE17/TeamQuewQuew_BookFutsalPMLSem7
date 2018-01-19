package tercyduk.appngasal.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tercyduk.appngasal.R;

public class HistoryBooking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_booking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
