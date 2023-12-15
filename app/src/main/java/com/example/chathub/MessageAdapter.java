package com.example.chathub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context chatActivity;
    ArrayList<Message> MessageArrayList;
    int ITEM_RECEIVE = 1;
    int ITEM_SEND = 2;

    public MessageAdapter(ChatActivity chatActivity,ArrayList<Message> MessageArrayList){
        this.chatActivity = chatActivity;
        this.MessageArrayList = MessageArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == 1){
            //inflate receive
            View view = LayoutInflater.from(chatActivity).inflate(R.layout.receive,parent,false);
            return new ReceiveViewHolder(view);
        }
        else{
            //inflate sent
            View view = LayoutInflater.from(chatActivity).inflate(R.layout.sent,parent,false);
            return new SentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Message message = MessageArrayList.get(position);
        if(holder.getClass() == SentViewHolder.class){
            //do the stuff for sentViewHolder
            SentViewHolder viewHolder = (SentViewHolder) holder;
            viewHolder.sentMessage.setText(message.getMessage());
        }else{
            //do the stuff for ReceiveViewHolder
            ReceiveViewHolder viewHolder = (ReceiveViewHolder) holder;
            viewHolder.receiveMessage.setText(message.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return MessageArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = MessageArrayList.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(message.getSenderId())){
            return ITEM_SEND;
        }
        else{
            return ITEM_RECEIVE;
        }
    }

    class SentViewHolder extends RecyclerView.ViewHolder{
        TextView sentMessage;

        public SentViewHolder(@NonNull View itemView) {
            super(itemView);

            sentMessage = itemView.findViewById(R.id.txt_sent_message);
        }
    }

    class ReceiveViewHolder extends RecyclerView.ViewHolder{
        TextView receiveMessage;

        public ReceiveViewHolder(@NonNull View itemView) {
            super(itemView);
            receiveMessage = itemView.findViewById(R.id.txt_receive_message);
        }
    }


}
