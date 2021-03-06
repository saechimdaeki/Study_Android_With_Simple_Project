package junseong.k.myapplication;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

public class PlayService extends Service implements MediaPlayer.OnCompletionListener {
    public PlayService() {
    }
    MediaPlayer player;
    String filePath;
    BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String mode=intent.getStringExtra("mode");
            if(mode!=null){
                if(mode.equals("start")){
                    try{
                        if(player!=null && player.isPlaying()){
                            player.stop();
                            player.release();
                            player=null;
                        }
                        player=new MediaPlayer();
                        player.setDataSource(filePath);
                        player.prepare();
                        player.start();
                        Intent aintent=new Intent("junseong.k.PLAY_TO_ACTIVITY");
                        aintent.putExtra("mode","start");
                        aintent.putExtra("duratio",player.getDuration());
                        sendBroadcast(aintent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(mode.equals("stop")){
                    if(player!=null && player.isPlaying()){
                        player.stop();
                        player.release();
                        player=null;
                    }
                }
            }
        }
    };
    @Override
    public void onCompletion(MediaPlayer mp){
        Intent intent=new Intent("junseong.k.PLAY_TO_ACTIVITY");
        intent.putExtra("mode","stop");
        sendBroadcast(intent);
        stopSelf();
    }
    @Override
    public void onCreate(){
        super.onCreate();
        registerReceiver(receiver,new IntentFilter("junseong.k.PLAY_TO_SERVICE"));
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(receiver);
    }
    @Override
    public int onStartCommand(Intent intent,int flgs, int startId){
        filePath=intent.getStringExtra("filePath");
        if(player!=null){
            Intent intent1=new Intent("junseong.k.PLAY_TO_ACTIVITY");
            intent1.putExtra("mode","restart");
            intent1.putExtra("duration",player.getDuration());
            intent1.putExtra("current",player.getCurrentPosition());
            sendBroadcast(intent1);
        }
        return super.onStartCommand(intent,flgs,startId);
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
