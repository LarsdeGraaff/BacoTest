package ldg.bacotest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ldg.bacotest.Activities.KalenderDetailActivity;
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
        holder.textViewDatum.setText(kalender.getDatum());
        holder.textViewUur.setText(kalender.getUur());
        holder.textViewPlaats.setText(kalender.getPlaats());
        holder.textviewScoreThuis.setText(kalender.getScoreThuis());
        holder.textviewScoreUit.setText(kalender.getScoreUit());

    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewThuisPloeg;
        private TextView textViewUitploeg;
        private TextView textViewDatum;
        private TextView textViewUur;
        private TextView textViewPlaats;
        private TextView textviewScoreThuis;
        private TextView textviewScoreUit;
        private TextView textViewGoToStats;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewThuisPloeg= (TextView) itemView.findViewById(R.id.textview_kalender_thuisploeg);
            textViewUitploeg= (TextView) itemView.findViewById(R.id.textview_kalender_uitploeg);
            textViewDatum= (TextView) itemView.findViewById(R.id.textview_kalender_date);
            textViewUur= (TextView) itemView.findViewById(R.id.textview_kalender_uur);
            textViewPlaats= (TextView) itemView.findViewById(R.id.textview_kalender_plaats);
            textviewScoreThuis= (TextView) itemView.findViewById(R.id.textview_kalender_score_thuisploeg);
            textviewScoreUit= (TextView) itemView.findViewById(R.id.textview_kalender_score_uitploeg);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context, KalenderDetailActivity.class);
            Kalender kalender=mDataset.get(getLayoutPosition());
            intent.putExtra("objectId", kalender.getObjectId());
            intent.putExtra("thuisPloeg", kalender.getThuisPloeg());
            intent.putExtra("uitPloeg", kalender.getUitPloeg());
            intent.putExtra("datum", kalender.getDatum());
            intent.putExtra("uur", kalender.getUur());
            intent.putExtra("plaats", kalender.getPlaats());
            intent.putExtra("scoreThuisPloeg",kalender.getScoreThuis());
            intent.putExtra("scoreUitPloeg",kalender.getScoreUit());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
