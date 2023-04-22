package uni.fmi.masters.moneylaundryapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uni.fmi.masters.moneylaundryapp.R;
import uni.fmi.masters.moneylaundryapp.entity.GameResultEntity;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>{

    public ArrayList<GameResultEntity> results;

    public LeaderboardAdapter( ArrayList<GameResultEntity> results ){
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_row_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameResultEntity result = results.get(position);
        holder.usernameTV.setText(result.getUsername());
        holder.scoreTV.setText(result.getScore());
        holder.dateTV.setText(result.getCreationDate().toString());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView usernameTV;
        TextView dateTV;
        TextView scoreTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            usernameTV = itemView.findViewById(R.id.rowUsernameTV);
            dateTV = itemView.findViewById(R.id.rowScoreTV);
            scoreTV = itemView.findViewById(R.id.rowDateTV);

        }
    }
}
