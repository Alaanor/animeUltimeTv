package com.kingofgranges.max.animeultimetv.phone;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.MediaController;

import com.kingofgranges.max.animeultimetv.R;

public class animeStream extends AppCompatActivity {

    private String streamURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_stream);


        Intent intent = getIntent();
        this.streamURL = intent.getStringExtra("streamURL");
        if(!this.displayVideo(this.streamURL)){
            onBackPressed();
        }
    }

    public boolean displayVideo(String url) {
        try {
            VideoView videoView = (VideoView) findViewById(R.id.videoView);

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    onBackPressed();
                }
            });

            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            Uri video = Uri.parse(url);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(video);

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });

            return true;
        } catch (Exception e) {
            Toast.makeText(this, "Error connecting", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
