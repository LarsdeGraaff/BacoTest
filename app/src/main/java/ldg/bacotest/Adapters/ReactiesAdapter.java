package ldg.bacotest.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ldg.bacotest.R;
import ldg.bacotest.entities.Berichten;
import ldg.bacotest.entities.Reacties;

/**
 * Created by Lars on 9/10/2015.
 */
public class ReactiesAdapter extends RecyclerView.Adapter<ReactiesAdapter.ViewHolder> {
    private static List<Reacties> mDataSet;
    private static Context context;

    public ReactiesAdapter(List<Reacties> myDataSet, Context context){
      mDataSet=myDataSet;
        this.context=context;
    }

    @Override
    public ReactiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reactie_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Reacties reacties=mDataSet.get(position);
        holder.textViewReactie.setText(reacties.getReactie());
        holder.textViewReactieUser.setText(reacties.getUserId());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewReactie;
        TextView textViewReactieUser;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewReactie= (TextView) itemView.findViewById(R.id.textview_reactie);
            textViewReactieUser= (TextView) itemView.findViewById(R.id.textview_reactie_userid);
        }
    }
}
