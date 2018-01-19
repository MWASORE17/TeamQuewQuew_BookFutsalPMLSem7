 package tercyduk.appngasal.apihelper.Adapter;
 import android.app.ProgressDialog;
 import android.content.Context;
 import android.content.Intent;
 import android.support.v7.widget.RecyclerView;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.Filter;
 import android.widget.Filterable;
 import android.widget.ImageView;
 import android.widget.TextView;
 import android.widget.Toast;

 import java.util.ArrayList;
 import java.util.List;

 import tercyduk.appngasal.Activity.DetailLapangan;
 import tercyduk.appngasal.Main2Activity;
 import tercyduk.appngasal.R;
 import tercyduk.appngasal.Util.UtilLapangan;
 import tercyduk.appngasal.apihelper.APIClient;
 import tercyduk.appngasal.apihelper.LapanganFutsalService;
 import tercyduk.appngasal.coresmodel.LapanganFutsal;
 import tercyduk.appngasal.coresmodel.User;
 import tercyduk.appngasal.modules.auth.user.Login;

 import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * Created by User on 12/9/2017.
 */

public class AdapterRViewLapangan extends RecyclerView.Adapter<AdapterRViewLapangan.LapanganViewHolder> implements Filterable {

    private static final String TAG = AdapterRViewLapangan.class.getSimpleName();

    private Context context;
    private List<LapanganFutsal> lapanganFutsals;
    private List<LapanganFutsal> filteredList;
    ImageLoader imageLoader;

    public AdapterRViewLapangan( List<LapanganFutsal> lapanganFutsals,ImageLoader imageLoader) {

        this.lapanganFutsals = lapanganFutsals;
        this.filteredList = lapanganFutsals;
        this.imageLoader = imageLoader;
    }

    public static class LapanganViewHolder extends RecyclerView.ViewHolder {

        ImageView lapangPhoto;
        TextView tvLapangName;
        TextView tvLapangPrice;
        TextView tvLapangKecamatan;

        public LapanganViewHolder(final View view) {
            super(view);

            lapangPhoto = (ImageView) view.findViewById(R.id.lapang_photo);
            tvLapangName = (TextView) view.findViewById(R.id.lapang_name);
            tvLapangPrice = (TextView) view.findViewById(R.id.lapang_price);
            tvLapangKecamatan = (TextView) view.findViewById(R.id.lapang_kecamatan);
        }
    }

    @Override
    public LapanganViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_futsal_grid, parent, false);
        return new LapanganViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LapanganViewHolder holder, int position) {
        if (filteredList.size() > 0) {

            final LapanganFutsal lapanganFutsal= filteredList.get(position);

            if (lapanganFutsal != null) {
                final String idf= lapanganFutsal.getId();
                final String token = lapanganFutsal.getToken();

                holder.tvLapangName.setText(lapanganFutsal.getFutsal_name());
                holder.tvLapangPrice.setText("RP "+(lapanganFutsal.getPrice().intValue()));
                holder.tvLapangKecamatan.setText(lapanganFutsal.getDistricts());
                final String image1 = lapanganFutsal.getPhoto_url();
                imageLoader.displayImage(image1, holder.lapangPhoto);
                holder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent _intent = new Intent(v.getContext(), DetailLapangan.class);
                        _intent.putExtra("id",idf);
                        _intent.putExtra("LapanganFutsal",lapanganFutsal);
                        _intent.putExtra("token",token);

                        v.getContext().startActivity(_intent);

                    }
                });
            }
        }
    }

//    // Handle click by card github user
//    private void onCardClick(int position) {
//        Toast.makeText(context, "Show GitHub " + filteredList.get(position).getId(), Toast.LENGTH_SHORT).show();
//    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredList = lapanganFutsals;
                } else {
                    filteredList = UtilLapangan.searchLapanganFutsalFilter(lapanganFutsals, charString);
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<LapanganFutsal>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return filteredList == null ? 0 : filteredList.size();
    }

}