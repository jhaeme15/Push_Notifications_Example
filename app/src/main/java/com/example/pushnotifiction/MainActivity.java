package com.example.pushnotifiction;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

/**
 * Authors: Kyle Workman and Jared Haeme
 * Example for sending and receiving push notifications through firebase
 *
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main Activity";
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //from https://firebase.google.com/docs/database/android/start
        // Write a message to the database
        database = FirebaseDatabase.getInstance().getReference();
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        storeTokenFirebase(token);
                    }
                });
    }

    /**
     * Sends device token to firebase
     * @param token device token
     */
    public void storeTokenFirebase(String token){
        database.push().child("token").setValue(token);
    }

}
