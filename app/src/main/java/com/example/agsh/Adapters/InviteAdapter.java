package com.example.agsh.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agsh.Models.InvitedUser;
import com.example.agsh.R;

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

    class InviteHolder extends RecyclerView.ViewHolder{
            private TextView amt,name;
        InviteHolder(@NonNull View itemView) {
            super(itemView);
            amt=itemView.findViewById(R.id.listamt);
            name=itemView.findViewById(R.id.listname);
        }

        void setDetails(InvitedUser invitedUser) {
        amt.setText(invitedUser.getAmt());
        name.setText(invitedUser.getInvitee() + " - ");
        }
    }
}
