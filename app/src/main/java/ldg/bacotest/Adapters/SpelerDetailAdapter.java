package ldg.bacotest.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ldg.bacotest.R;
import ldg.bacotest.entities.Speler;

/**
 * Created by Lars on 6/10/2015.
 */
public class SpelerDetailAdapter extends RecyclerView.Adapter<SpelerDetailAdapter.ViewHolder> {
    private static List<Speler> mDataSet;
    private static Context context;



    @Override
    public SpelerDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_speler_detail,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SpelerDetailAdapter.ViewHolder holder, int position) {
        Speler speler=mDataSet.get(position);
        holder.textViewSpelerDetailVoornaam.setText(speler.getSpelersVoornaam());
        holder.textViewSpelerDetailNaam.setText(speler.getSpelersNaam());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewSpelerDetailVoornaam;
        public TextView textViewSpelerDetailNaam;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewSpelerDetailVoornaam= (TextView) itemView.findViewById(R.id.textview_detail_speler_voornaam);
            textViewSpelerDetailNaam= (TextView) itemView.findViewById(R.id.textview_detail_speler_naam);
        }
    }

    public SpelerDetailAdapter(List<Speler>myDataSet,Context context){
        SpelerDetailAdapter.context=context;
        mDataSet=myDataSet;
    }
}
