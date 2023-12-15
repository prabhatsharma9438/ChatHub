package com.example.chathub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    RecyclerView chatRecyclerView;
    EditText messageBox;
    ImageView sendButton;
    MessageAdapter messageAdapter;
    ArrayList<Message> messageArrayList;
    DatabaseReference mDbRef;

    String receiverRoom;
    String senderRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String receiverUid = intent.getStringExtra("uid");

        String senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDbRef = FirebaseDatabase.getInstance().getReference();

        senderRoom = receiverUid + senderUid;
        receiverRoom = senderUid + receiverUid;

        ActionBar actionBar = getSupportActionBar(); // Get a reference to the action bar
        if (actionBar != null) {
            // Customize the action bar if needed
            actionBar.setTitle(name);
            actionBar.setDisplayHomeAsUpEnabled(true); // Enable the Up button
        }

        chatRecyclerView = findViewById(R.id.ChatRecyclerView);
        messageBox = findViewById(R.id.MessageBox);
        sendButton = findViewById(R.id.SendButton);
        messageArrayList = new ArrayList<>();
        messageAdapter = new MessageAdapter(ChatActivity.this,messageArrayList);

        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(messageAdapter);

        //logic for adding data to recyclerview
        mDbRef.child("chats").child(senderRoom).child("messages")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        messageArrayList.clear();
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Message message = dataSnapshot.getValue(Message.class);
                            messageArrayList.add(message);
                        }
                        messageAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        //adding the message to database
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = messageBox.getText().toString();
                Message messageObject = new Message(message,senderUid);

                mDbRef.child("chats").child(senderRoom).child("messages")
                        .push().setValue(messageObject).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                mDbRef.child("chats").child(receiverRoom).child("messages")
                                        .push().setValue(messageObject);
                            }
                        });
                messageBox.setText("");
            }
        });
    }
}