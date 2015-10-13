package ldg.bacotest.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ldg.bacotest.R;
import ldg.bacotest.entities.Kalender;

/**
 * Created by Lars on 13/10/2015.
 */
public class KalenderAdapter extends RecyclerView.Adapter<KalenderAdapter.ViewHolder> {
    private List<Kalender> mDataset;
    private static Context context;

    public KalenderAdapter( List<Kalender> myDataset,Context context){
        KalenderAdapter.context=context;
        mDataset=myDataset;
    }

    @Override
    public KalenderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_kalender_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Kalender kalender=mDataset.get(position);
        holder.textViewThuisPloeg.setText(kalender.getThuisPloeg());
        holder.textViewUitploeg.setText(kalender.getUitPloeg());

    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewThuisPloeg;
        private TextView textViewUitploeg;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewThuisPloeg= (TextView) itemView.findViewById(R.id.textview_kalender_thuisploeg);
            textViewUitploeg= (TextView) itemView.findViewById(R.id.textview_kalender_uitploeg);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
