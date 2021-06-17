package com.GDMS.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GDMS.R;
import com.GDMS.activities.MainActivity;
import com.GDMS.activities.ReturnActivity;
import com.GDMS.Utilities.DCListDataBase;
import com.GDMS.Utilities.DialogBoxes;
import com.GDMS.Utilities.Helper;
import com.GDMS.Utilities.Utility;
import com.GDMS.models.GetDc.Dch;

import java.util.ArrayList;

public class DcListAdapter extends RecyclerView.Adapter<DcListAdapter.ViewHolder> {

    Context context;
    ArrayList<Dch> dches;
    DCListDataBase dcListDataBase;
    private OnItemClickListener onItemClickListener;

    public DcListAdapter(Context context, ArrayList<Dch> dches, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.dches = dches;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Dch dch = dches.get(position);
        holder.number.setText(""+dch.getDocnumber());

     if (dch.getStatus()  ==2){
            holder.checkDilverOrNot.setVisibility(View.VISIBLE);
            holder.tv1.setText("DELIVERD");
            holder.dilvery.setVisibility(View.GONE);
            holder.returnBtn.setVisibility(View.GONE);
        }
        if (dch.getStatus()  ==3){
            holder.checkDilverOrNot.setVisibility(View.VISIBLE);
            holder.tv1.setText("Retrun");
            holder.checkDilverOrNot.setBackgroundResource(R.drawable.bg_button);
            holder.dilvery.setVisibility(View.GONE);
            holder.returnBtn.setVisibility(View.GONE);
        }
        holder.dilvery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                DialogBoxes.showSingleButtonDialog(context, "Confirmation", "Are your you want to Deliver", "ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                       UpdateDataa(dch.getDocnumber(),dch.getDocserial());

                    }
                }, true, null);*/
                onItemClickListener.onButtonClick(v, holder.getAdapterPosition());
            }
        });

        holder.returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.getDch().setDocnumber(dch.getDocnumber());
                Helper.getDch().setDocserial(dch.getDocserial());
                Utility.launchActivity((Activity) context, ReturnActivity.class ,true);



            }
        });


    }
    public void updateList(ArrayList<Dch> arrayList) {
        this.dches = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dches.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout dilvery,returnBtn,checkDilverOrNot;
        TextView number,tv1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number  = itemView.findViewById(R.id.number);
            tv1  = itemView.findViewById(R.id.tv1);
            dilvery  = itemView.findViewById(R.id.dilvery);
            returnBtn  = itemView.findViewById(R.id.returnBtn);
            checkDilverOrNot  = itemView.findViewById(R.id.checkDilverOrNot);
        }
    }
    public interface OnItemClickListener {
        void onButtonClick(View v, int position);
    }
}
