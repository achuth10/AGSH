package com.example.agsh.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agsh.Models.InvitedUser;
import com.example.agsh.R;
import com.example.agsh.SendMoney;
import com.example.agsh.Temp;

import java.util.ArrayList;

public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.InviteHolder> {
private Context context;
private ArrayList <InvitedUser> invitedUsers;

public InviteAdapter(Context context, ArrayList<InvitedUser> invitedUsers)
{
    this.context=context;
    this.invitedUsers=invitedUsers;
}

    public InviteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.invitelistitem,parent,false);
        return new InviteHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InviteHolder holder, int position) {
        InvitedUser invitedUser=invitedUsers.get(position);
        holder.setDetails(invitedUser);
    }

    @Override
    public int getItemCount() {
        return invitedUsers.size();
    }

    class InviteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView amt,name;
            private Button pay;
            private InvitedUser user;
        InviteHolder(@NonNull View itemView) {
            super(itemView);
            amt=itemView.findViewById(R.id.listamt);
            name=itemView.findViewById(R.id.listname);
            pay=itemView.findViewById(R.id.paybtn);
            pay.setOnClickListener(this);
        }

        void setDetails(InvitedUser invitedUser) {
            user=invitedUser;
        amt.setText(invitedUser.getAmt());
        name.setText(invitedUser.getInvitee() + " - ");
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==pay.getId())
            {
                Intent i = new Intent(v.getContext(), SendMoney.class);
                i.putExtra("Uuid",user.getBy());
                i.putExtra("Amt",user.getAmt());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(i);
            }
        }
    }
}
