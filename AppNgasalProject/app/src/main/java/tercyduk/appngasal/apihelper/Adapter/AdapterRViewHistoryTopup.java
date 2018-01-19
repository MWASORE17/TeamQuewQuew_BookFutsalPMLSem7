package tercyduk.appngasal.apihelper.Adapter;


import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import tercyduk.appngasal.Activity.DetailLapangan;
import tercyduk.appngasal.R;
import tercyduk.appngasal.coresmodel.LapanganFutsal;
import tercyduk.appngasal.coresmodel.TopUp;

/**
 * Created by User on 12/9/2017.
 */

public class AdapterRViewHistoryTopup extends RecyclerView.Adapter<AdapterRViewHistoryTopup.ViewHolder> {
    List<TopUp> hstoryTopup;

    public AdapterRViewHistoryTopup(List<TopUp> hstoryTopup)
    {
                this.hstoryTopup = hstoryTopup;
         }

    @Override
    public AdapterRViewHistoryTopup.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history_topup, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRViewHistoryTopup.ViewHolder holder, int position) {
        final TopUp _topups = this.hstoryTopup.get(position);
        holder.txtStts.setText(_topups.getStatus());
        holder.txtTgltop.setText(_topups.getTransfer_date());
        holder.txtPriceTop.setText("RP." +_topups.getTotal_transfer());

    }

    @Override
    public int getItemCount() {
        return hstoryTopup.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTgltop,txtPriceTop,txtStts;

        public ViewHolder(View itemView) {
            super(itemView);
            txtPriceTop = (TextView) itemView.findViewById(R.id.txtTopupPrice);
            txtTgltop = (TextView) itemView.findViewById(R.id.txtTopupTgl);
            txtStts = (TextView)itemView.findViewById(R.id.sttsTopup);
        }
    }


}
