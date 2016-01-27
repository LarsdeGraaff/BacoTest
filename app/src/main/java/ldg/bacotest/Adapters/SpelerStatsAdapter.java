package ldg.bacotest.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ldg.bacotest.R;
import ldg.bacotest.entities.SpelerStats;

/**
 * Created by Lars on 21/01/2016.
 */
public class SpelerStatsAdapter extends RecyclerView.Adapter<SpelerStatsAdapter.ViewHolder> {
    private static List<SpelerStats> mDataSet;
    private static Context context;


    @Override
    public SpelerStatsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.speler_statistieken,parent,false);
        ViewHolder viewholder=new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(SpelerStatsAdapter.ViewHolder holder, int position) {
        SpelerStats spelerStats=mDataSet.get(position);
        holder.textViewSpelerStatsThuisPloeg.setText(spelerStats.getThuisploeg());
        holder.textViewSpelerStatsUitPloeg.setText(spelerStats.getUitploeg());
        holder.textViewSpelerStatsGoals.setText(spelerStats.getGoals());
        holder.textViewSpelerStatsAssists.setText(spelerStats.getAssists());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewSpelerStatsGoals;
        private TextView textViewSpelerStatsAssists;
        private TextView textViewSpelerStatsThuisPloeg;
        private TextView textViewSpelerStatsUitPloeg;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewSpelerStatsGoals= (TextView) itemView.findViewById(R.id.textview_speler_stats_goals);
            textViewSpelerStatsAssists= (TextView) itemView.findViewById(R.id.textview_speler_stats_assists);
            textViewSpelerStatsThuisPloeg= (TextView) itemView.findViewById(R.id.textview_speler_stats_thuisploeg);
            textViewSpelerStatsUitPloeg= (TextView) itemView.findViewById(R.id.textview_speler_stats_uitploeg);
        }
    }

    public SpelerStatsAdapter(List<SpelerStats> myDataSet, Context context){
        mDataSet=myDataSet;
        this.context=context;
    }
}
