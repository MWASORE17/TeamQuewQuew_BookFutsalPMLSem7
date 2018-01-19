package tercyduk.appngasal.apihelper.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tercyduk.appngasal.R;
import tercyduk.appngasal.coresmodel.SpinnerData;

/**
 * Created by User on 12/25/2017.
 */

public class CustomeSpinnerAdapterJam extends ArrayAdapter<SpinnerData> {
    private Context context;
    private List<SpinnerData> spinnerData;
    public CustomeSpinnerAdapterJam(@NonNull Context context, @LayoutRes  int resource, List<SpinnerData> spinnerData) {
        super(context, resource,spinnerData);
        this.context = context;
        this.spinnerData = spinnerData;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return mycustomeSpinnerhariView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return mycustomeSpinnerhariView(position, convertView, parent);
    }


    private View mycustomeSpinnerhariView(int postion, @Nullable View myView , @NonNull ViewGroup parent)
    {
        LayoutInflater Linflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View CustomehariView = Linflater.inflate(R.layout.spinner_layout_jam,parent,false);
        TextView txtView =(TextView)CustomehariView.findViewById(R.id.txtjam);
        ImageView imgView =(ImageView)CustomehariView.findViewById(R.id.imgViewjams);
        txtView.setText(spinnerData.get(postion).getIconName());
        imgView.setImageResource(spinnerData.get(postion).getIcon());
        return CustomehariView;
    }
}
