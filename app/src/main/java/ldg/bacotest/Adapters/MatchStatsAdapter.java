package ldg.bacotest.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ldg.bacotest.R;
import ldg.bacotest.entities.MatchStats;
import ldg.bacotest.entities.Reacties;


/**
 * Created by Lars on 19/01/2016.
 */
public class MatchStatsAdapter extends RecyclerView.Adapter<MatchStatsAdapter.ViewHolder> {
    private static List<MatchStats> mDataSet;
    private static Context context;

    @Override
    public MatchStatsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_statistieken, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MatchStatsAdapter.ViewHolder holder, int position) {

        MatchStats matchStats=mDataSet.get(position);
        holder.textViewStatsNaamSpeler.setText(matchStats.getNaamSpeler());
        holder.textViewStatsGoals.setText(matchStats.getGoals());
        holder.textViewStatsAssists.setText(matchStats.getAssists());

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewStatsNaamSpeler;
        private TextView textViewStatsGoals;
        private TextView textViewStatsAssists;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewStatsNaamSpeler= (TextView) itemView.findViewById(R.id.textview_stats_naam);
            textViewStatsGoals= (TextView) itemView.findViewById(R.id.textview_stats_goals);
            textViewStatsAssists= (TextView) itemView.findViewById(R.id.textview_stats_assists);
        }
    }

    public MatchStatsAdapter(List<MatchStats> myDataSet, Context context){
        mDataSet=myDataSet;
        this.context=context;
    }
}
