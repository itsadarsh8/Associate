package com.example.associate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;
import static com.example.associate.MainActivity.UPDATE_ID;
import static com.example.associate.MainActivity.UPDATE_INTENT;

public class NewWordActivity extends AppCompatActivity {

    private String defaultWord;
    public static final String EXTRA_REPLY =
            "com.example.android.NewWordActivity.ADD";
    public static final String EXTRA_REPLY_ID =
            "com.example.android.NewWordActivity.ADD_ID";
    private static final int NOTIFICATION_ID=0;
    private static final String PRIMARY_CHANNEL_ID="PRIMARY_CHANNEL_ID";
    private NotificationManager mNotificationManager;


    private  EditText mEditWordView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        mEditWordView = findViewById(R.id.edit_word);
        mNotificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        final Bundle bundle=getIntent().getExtras();
        if(bundle!=null && bundle.containsKey(UPDATE_ID)){
            defaultWord=bundle.getString(UPDATE_INTENT);
            if(defaultWord!=null){
                mEditWordView.setText(defaultWord);
            }
        }
       final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
              Intent addIntent=new Intent();
              if(TextUtils.isEmpty(mEditWordView.getText().toString())){
                  setResult(RESULT_CANCELED,addIntent);

              }
              else{
                  String word=mEditWordView.getText().toString();
                  addIntent.putExtra(EXTRA_REPLY,word);
                  if(bundle!=null && bundle.containsKey(UPDATE_ID)){
                      int id=bundle.getInt(UPDATE_ID,-1);
                      if(id!=-1){
                          addIntent.putExtra(EXTRA_REPLY_ID,id);
                      }
                  }
                  setResult(RESULT_OK,addIntent);

              }
              finish();
            }

        });


        ToggleButton toggleButton=findViewById(R.id.alarmToggle);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    deliverNotification(NewWordActivity.this,defaultWord);
                }
                else{
                    mNotificationManager.cancelAll();
                }
            }
        });

        createNotificationChannel();

    }

    private void createNotificationChannel(){
        mNotificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(PRIMARY_CHANNEL_ID,"stand up notification",NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setDescription("Notifies every 15min to stand up and walk");
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);

            mNotificationManager.createNotificationChannel(notificationChannel);

        }
    }

    private void deliverNotification(Context context,String description){
        Intent intent=new Intent(context,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,NOTIFICATION_ID,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Alert!!!")
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        mNotificationManager.notify(NOTIFICATION_ID,builder.build());
    }
}