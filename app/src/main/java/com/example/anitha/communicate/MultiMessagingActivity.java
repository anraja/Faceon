package com.example.anitha.communicate;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anitha.faceon.R;
import com.example.anitha.global.GameService;
import com.parse.ParseUser;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Nora on 24-Feb-15.
 */
public class MultiMessagingActivity extends Activity {
    private List<String> recipientIds;
    private EditText messageBodyField;
    private String messageBody;
    private GameService.GameServiceInterface messageService;
    private String currentUserId;
    private ServiceConnection serviceConnection = new MyServiceConnection();
    MyMessageClientListener messageClientListener=new MyMessageClientListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaging);
    bindService(new Intent(this, GameService.class), serviceConnection, BIND_AUTO_CREATE);
    //get recipientId from the intent
    Intent intent = getIntent();
    recipientIds= intent.getStringArrayListExtra("users");
        if (recipientIds.size()>0) {
            Log.w("LIST", "RECEIVED SIZE: " + recipientIds.size());
        }
        else {
            Log.w("LIST","CAUTION%^&^%$$#@$%^&^");
        }
        
    currentUserId = ParseUser.getCurrentUser().getObjectId();
    messageBodyField = (EditText) findViewById(R.id.messageBodyField);
    //listen for a click on the send button
    findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //send the message!
            messageBody = messageBodyField.getText().toString();
            if (messageBody.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter a message", Toast.LENGTH_LONG).show();
                return;
            }
            messageService.sendMessage(recipientIds, messageBody);
            messageBodyField.setText("");

        }
    });
}
    //unbind the service when the activity is destroyed
    @Override
    public void onDestroy() {
        unbindService(serviceConnection);
        messageService.removeMessageClientListener(messageClientListener);
        super.onDestroy();
    }
private class MyServiceConnection implements ServiceConnection {
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        messageService = (GameService.GameServiceInterface) iBinder;
        messageService.addMessageClientListener(messageClientListener);

    }
    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        messageService = null;
    }


}

    private class MyMessageClientListener implements MessageClientListener {
        //Notify the user if their message failed to send
        @Override
        public void onMessageFailed(MessageClient client, Message message,
                                    MessageFailureInfo failureInfo) {
            Toast.makeText(MultiMessagingActivity.this, "Message failed to send.", Toast.LENGTH_LONG).show();
        }
        @Override
        public void onIncomingMessage(MessageClient client, Message message) {
            //Display an incoming message
        }
        @Override
        public void onMessageSent(MessageClient client, Message message, String recipientId) {
            //Display the message that was just sent
            //Later, I'll show you how to store the
            //message in Parse, so you can retrieve and
            //display them every time the conversation is opened
        }
        //Do you want to notify your user when the message is delivered?
        @Override
        public void onMessageDelivered(MessageClient client, MessageDeliveryInfo deliveryInfo) {}
        //Don't worry about this right now
        @Override
        public void onShouldSendPushData(MessageClient client, Message message, List<PushPair> pushPairs) {}
    }
}
