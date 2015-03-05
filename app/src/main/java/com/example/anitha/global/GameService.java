package com.example.anitha.global;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.parse.ParseUser;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.WritableMessage;

import java.util.List;

/**
 * Created by Nora on 23-Feb-15.
 */
public class GameService extends Service implements SinchClientListener {

    private static final String APP_KEY = "32a30f8e-097b-43c0-8c94-4cde012c9885";
    private static final String APP_SECRET = "xxv2Vi23YUu+r0xi37PBPw==";
    private static final String ENVIRONMENT = "sandbox.sinch.com";
    private final GameServiceInterface serviceInterface = new GameServiceInterface();
    private SinchClient sinchClient = null;
    private MessageClient gameClient = null;
    private String currentUserId;

    //load spinner
    private Intent broadcastIntent = new Intent("com.example.anitha.faceon.ListUsersActivity");
    private LocalBroadcastManager broadcaster;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //get the current user id from Parse
        currentUserId = ParseUser.getCurrentUser().getObjectId();
        if (currentUserId != null && !isSinchClientStarted()) {
            startSinchClient(currentUserId);
            broadcaster = LocalBroadcastManager.getInstance(this);
        }
        return super.onStartCommand(intent, flags, startId);
    }


    public void startSinchClient(String username) {
        sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                .userId(username)
                .applicationKey(APP_KEY)
                .applicationSecret(APP_SECRET)
                .environmentHost(ENVIRONMENT)
                .build();
        //this client listener requires that you define
        //a few methods below
        sinchClient.addSinchClientListener(this);
        //messaging is "turned-on", but calling is not
        sinchClient.setSupportMessaging(true);
        sinchClient.setSupportActiveConnectionInBackground(true);
        sinchClient.checkManifest();
        sinchClient.start();
    }
    private boolean isSinchClientStarted() {
        return sinchClient != null && sinchClient.isStarted();
    }
    //The next 5 methods are for the sinch client listener
    @Override
    public void onClientFailed(SinchClient client, SinchError error) {
        sinchClient = null;
    }
    @Override
    public void onClientStarted(SinchClient client) {
        broadcastIntent.putExtra("success", false);
        broadcaster.sendBroadcast(broadcastIntent);
        client.startListeningOnActiveConnection();
        broadcastIntent.putExtra("success", true);
        broadcaster.sendBroadcast(broadcastIntent);
        gameClient = client.getMessageClient();
    }
    @Override
    public void onClientStopped(SinchClient client) {
        sinchClient = null;
    }
    @Override
    public void onRegistrationCredentialsRequired(SinchClient client, ClientRegistration clientRegistration) {}
    @Override
    public void onLogMessage(int level, String area, String message) {}
    @Override
    public IBinder onBind(Intent intent) {
        return serviceInterface;
    }
    public void sendMessage(List<String> recipientUserId, String textBody) {
        if (gameClient != null) {
            // Create a WritableMessage and send to multiple recipients
            WritableMessage message = new WritableMessage(
                    recipientUserId,
                    textBody);
// Send it
            gameClient.send(message);
        }
    }
    public void addMessageClientListener(MessageClientListener listener) {
        if (gameClient != null) {
            gameClient.addMessageClientListener(listener);
        }
    }
    public void removeMessageClientListener(MessageClientListener listener) {
        if (gameClient != null) {
            gameClient.removeMessageClientListener(listener);
        }
    }
    @Override
    public void onDestroy() {
        sinchClient.stopListeningOnActiveConnection();
        sinchClient.terminate();
    }
    //public interface for ListUsersActivity & MessagingActivity
    public class GameServiceInterface extends Binder {
        public void sendMessage(List<String> recipientUserId, String textBody) {
            GameService.this.sendMessage(recipientUserId, textBody);
        }
        public void addMessageClientListener(MessageClientListener listener) {
            GameService.this.addMessageClientListener(listener);
        }
        public void removeMessageClientListener(MessageClientListener listener) {
            GameService.this.removeMessageClientListener(listener);
        }
        public boolean isSinchClientStarted() {
            return GameService.this.isSinchClientStarted();
        }
    }
}
