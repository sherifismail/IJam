package com.Example.iJam.activities;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.Example.iJam.R;
import com.Example.iJam.models.Track;
import com.Example.iJam.network.NetworkManager;
import com.android.volley.toolbox.NetworkImageView;

import java.io.IOException;

public class JammingActivity extends AppCompatActivity implements View.OnClickListener {

    private String outputFile = null;
    TextView timer, countdown;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    NetworkImageView imagetrack;
    MediaController mc;
    VideoView trackplayer;

    ImageView recordbut, stopbut;
    Button btNext;
    private MediaRecorder myAudioRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jamming);

        imagetrack = (NetworkImageView) findViewById(R.id.jamming_img_videoimage);
        trackplayer = (VideoView) findViewById(R.id.jamming_vp_player);
        recordbut = (ImageView) findViewById(R.id.jamming_image_record);
        stopbut = (ImageView) findViewById(R.id.jamming_image_stop);
        countdown = (TextView) findViewById(R.id.countdown);
        timer = (TextView) findViewById(R.id.timer);
        btNext = (Button) findViewById(R.id.jamming_bt_next);

        Track myTrack = (Track) getIntent().getSerializableExtra("track");
        final String imgUrl = myTrack.getImgUrl();
        final String trackUrl = myTrack.getTrackUrl();

        recordbut.setOnClickListener(this);
        stopbut.setOnClickListener(this);
        btNext.setOnClickListener(this);

        imagetrack.setImageUrl(imgUrl, NetworkManager.getInstance(getApplicationContext()).getImageLoader());
        trackplayer.setVideoURI(Uri.parse(trackUrl));

        mc = new MediaController(this);
        mc.setMediaPlayer(trackplayer);

        trackplayer.setMediaController(mc);
        AudioManager m_amAudioManager;
        m_amAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        m_amAudioManager.setMode(AudioManager.MODE_IN_CALL);
        m_amAudioManager.setSpeakerphoneOn(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jamming, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jamming_bt_next:
                Intent intent = new Intent(this, UploadTrackActivity.class);
                intent.putExtra("filename",outputFile);
                startActivity(intent);
                finish();
                break;
            case R.id.jamming_image_record:
                new CountDownTimer(5000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        countdown.setText("" + millisUntilFinished / 1000);
                    }

                    @Override
                    public void onFinish() {
                        try {
                            countdown.setVisibility(View.INVISIBLE);
                            outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/testrecord.mp3";
                            myAudioRecorder = new MediaRecorder();
                            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                            myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                            myAudioRecorder.setOutputFile(outputFile);
                            trackplayer.start();
                            startTime = SystemClock.uptimeMillis();
                            customHandler.postDelayed(updateTimerThread, 0);
                            myAudioRecorder.prepare();
                            myAudioRecorder.start();

                            Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
                            recordbut.setEnabled(false);
                            stopbut.setEnabled(true);
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;

            case R.id.jamming_image_stop:
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);

                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;

                stopbut.setEnabled(false);

                Toast.makeText(getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timer.setText("" + String.format("%02d", mins) + ":" + String.format("%02d", secs));
            customHandler.postDelayed(this, 0);
        }
    };

}
