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
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import tercyduk.appngasal.R;
import tercyduk.appngasal.coresmodel.SpinnerData;

/**
 * Created by User on 12/25/2017.
 */

public class CustomeSpinnerAdapter extends ArrayAdapter<SpinnerData> {
    private Context context;
    private List<SpinnerData> spinnerData;
    public CustomeSpinnerAdapter(@NonNull Context context, @LayoutRes  int resource, List<SpinnerData> spinnerData) {
        super(context, resource,spinnerData);
        this.context = context;
        this.spinnerData = spinnerData;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return myCustomeSpinnerView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return myCustomeSpinnerView(position, convertView, parent);
    }

    private View myCustomeSpinnerView(int position, @Nullable View myView , @NonNull ViewGroup parent)
    {
        LayoutInflater layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View CustomeView = layoutInflater.inflate(R.layout.spinner_layour,parent,false);
        TextView txtView =(TextView)CustomeView.findViewById(R.id.txtbankname);
        ImageView imgView =(ImageView)CustomeView.findViewById(R.id.imgViewBank);
        txtView.setText(spinnerData.get(position).getIconName());
        imgView.setImageResource(spinnerData.get(position).getIcon());
        return CustomeView;
    }



    private View mycustomeSpinnerhariView(int postion, @Nullable View myView , @NonNull ViewGroup parent)
    {
        LayoutInflater Linflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View CustomehariView = Linflater.inflate(R.layout.spinner_layout_hari,parent,false);
        TextView txtView =(TextView)CustomehariView.findViewById(R.id.txthari);
        ImageView imgView =(ImageView)CustomehariView.findViewById(R.id.imgViewhari);
        txtView.setText(spinnerData.get(postion).getIconName());
        imgView.setImageResource(spinnerData.get(postion).getIcon());
        return CustomehariView;
    }
}
