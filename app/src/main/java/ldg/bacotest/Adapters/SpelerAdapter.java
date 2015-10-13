package ldg.bacotest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ldg.bacotest.Activities.SpelerDetailActivity;
import ldg.bacotest.R;
import ldg.bacotest.entities.Speler;

/**
 * Created by Lars on 6/10/2015.
 */
public class SpelerAdapter extends RecyclerView.Adapter<SpelerAdapter.ViewHolder> {
    private static List<Speler> mDataSet;
    private static Context context;


    @Override
    public SpelerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_spelers_layout,parent,false);

        return new ViewHolder(view);
    }
    public SpelerAdapter(List<Speler>myDataSet,Context context){
        SpelerAdapter.context=context;
        mDataSet=myDataSet;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Speler speler=mDataSet.get(position);
        holder.textViewSpelersVoornaam.setText(speler.getSpelersVoornaam());
        holder.textViewSpelersNaam.setText(speler.getSpelersNaam());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewSpelersVoornaam;
        public TextView textViewSpelersNaam;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewSpelersVoornaam= (TextView) itemView.findViewById(R.id.textview_speler_voornaam);
            textViewSpelersNaam= (TextView) itemView.findViewById(R.id.textview_speler_naam);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context, SpelerDetailActivity.class);
            Speler speler=mDataSet.get(this.getLayoutPosition());
            intent.putExtra("objectId", speler.getObjectId());
            intent.putExtra("voornaam",speler.getSpelersVoornaam());
            intent.putExtra("achternaam",speler.getSpelersVoornaam());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}


