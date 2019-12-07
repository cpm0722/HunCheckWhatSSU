package ssu.ssu.huncheckwhatssu;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Vector;

import ssu.ssu.huncheckwhatssu.utilClass.Customer;

public class NotificationService extends Service {
    public static final String TAG = "HUNYC";
    NotificationManager notificationManager;
    Notification notification;
    NotificationCompat.Builder builder;
    FirebaseHelper firebaseHelper;
    String myUid;
    Customer myInfo;
    Vector<String> tradeUidList;
    int count;

    @Override
    public void onCreate() {
        super.onCreate();
        firebaseHelper = new FirebaseHelper();
        createNotification();
        Log.d(TAG, "서비스 들어옴");
    }

        public void createNotification() {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Intent intent = new Intent(NotificationService.this, MainActivity.class);
            intent.putExtra("Started By Notification",true);
            PendingIntent pendingIntent = PendingIntent.getActivity(NotificationService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder = new NotificationCompat.Builder(getApplicationContext(),"HUNCHECKWHATSSU")
                    .setContentTitle("구매요청!")
                .setContentText("구매요청이 있습니다.")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setSmallIcon(R.drawable.notification_img);
            NotificationChannel channel = new NotificationChannel("HUNCHECKWHATSSU", "구매요청 알림", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("구매요청 알림입니다.");
            if (notificationManager != null)
                notificationManager.createNotificationChannel(channel);
        }
        else {
            builder.setSmallIcon(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand들어옴");
        firebaseHelper.addCallBackListener(new FirebaseHelper.CallBackListener() {
            @Override
            public void afterGetCustomer(Customer customer) {
                myInfo = customer;
                Log.d(TAG, "내정보 받음");
                setListener();
//                NotificationHandler handler = new NotificationHandler();
//                thread = new Thread(new NotificationThread(handler));
//                thread.start();
            }

            @Override
            public void afterGetPurchaseRequestCount(int count) {

            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        myUid = user.getDisplayName() + "_" + user.getUid();
        firebaseHelper.getCustomerById(myUid);

        return START_STICKY;
    }

    public void setListener() {
        firebaseHelper.addNotificationListener(new FirebaseHelper.NotificationListener() {
            @Override
            public void afterGetTradeUids(Vector<String> sellListUids) {
                tradeUidList = sellListUids;
                count = tradeUidList.size();

                firebaseHelper.setNotifyPurchaseRequest(tradeUidList);
            }

            @Override
            public void afterGetTradeBookName(String bookName) {
                String printName = bookName;
                if(bookName.length() > 20){
                    printName = bookName.substring(0, 20) + "...";
                }
                builder.setContentText(printName+"의 구매요청이 있습니다.");
                notification = builder.build();
                notificationManager.notify(19970703, notification);
            }
        });
        firebaseHelper.getSellListById(myUid);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public NotificationService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
