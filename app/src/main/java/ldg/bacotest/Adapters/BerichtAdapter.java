package ldg.bacotest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import ldg.bacotest.Activities.BerichtDetailActivity;
import ldg.bacotest.R;
import ldg.bacotest.entities.Berichten;
import ldg.bacotest.entities.Speler;

/**
 * Created by Lars on 8/10/2015.
 */
public class BerichtAdapter extends RecyclerView.Adapter<BerichtAdapter.ViewHolder> {
    private static List<Berichten> mDataSet;
    private static Context context;

    @Override
    public BerichtAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_layout, parent, false);


        return new ViewHolder(view);
    }

    public BerichtAdapter(List<Berichten> myDataSet, Context context) {
        BerichtAdapter.context = context;
        mDataSet = myDataSet;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Berichten bericht = mDataSet.get(position);
        holder.textViewBerichtTitel.setText(bericht.getTitel());
        holder.textViewBerichtInleiding.setText(bericht.getInleiding());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewBerichtTitel;
        private TextView textViewBerichtInleiding;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewBerichtTitel = (TextView) itemView.findViewById(R.id.textview_home_bericht_titel);
            textViewBerichtInleiding = (TextView) itemView.findViewById(R.id.textview_home_bericht_inleiding);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent intent=new Intent(context, BerichtDetailActivity.class);
            Berichten berichten=mDataSet.get(this.getLayoutPosition());
            intent.putExtra("objectId", berichten.getObjectId());
            intent.putExtra("titel",berichten.getTitel());
            intent.putExtra("inleiding",berichten.getInleiding());
            intent.putExtra("bericht",berichten.getBericht());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
    }
}
