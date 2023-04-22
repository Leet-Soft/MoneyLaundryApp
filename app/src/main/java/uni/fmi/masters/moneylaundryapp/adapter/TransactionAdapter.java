package uni.fmi.masters.moneylaundryapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uni.fmi.masters.moneylaundryapp.R;
import uni.fmi.masters.moneylaundryapp.entity.GameResultEntity;
import uni.fmi.masters.moneylaundryapp.entity.TransactionEntity;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.VielHolder>{


    ArrayList<TransactionEntity> items;

    public TransactionAdapter(ArrayList<TransactionEntity> items){
        this.items = items;
    }

    @NonNull
    @Override
    public VielHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_row,parent,false);
        return new TransactionAdapter.VielHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull VielHolder holder, int position) {
        TransactionEntity trans = items.get(position);

        holder.nameTV.setText(trans.getName());
        holder.sumTV.setText(trans.getSum() + "");

        holder.deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.editIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class VielHolder extends RecyclerView.ViewHolder{

        TextView nameTV;
        TextView sumTV;
        ImageView groupIV;
        ImageView deleteIV;
        ImageView editIV;

        public VielHolder(@NonNull View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.rowNameTV);
            sumTV = itemView.findViewById(R.id.rowSumTV);
            groupIV = itemView.findViewById(R.id.rowGroupIV);
            deleteIV = itemView.findViewById(R.id.rowDeleteIV);
            editIV = itemView.findViewById(R.id.rowEditIV);
        }
    }
}
