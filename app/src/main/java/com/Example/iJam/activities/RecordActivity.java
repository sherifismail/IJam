package com.Example.iJam.activities;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Example.iJam.R;

import java.io.IOException;

public class RecordActivity extends ActionBarActivity implements View.OnClickListener {
    Button next,uploadstorage;
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    TextView timer;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    ImageView recordbut,stopbut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        timer=(TextView)findViewById(R.id.timer);
        recordbut=(ImageView)findViewById(R.id.record_image_record);
        stopbut=(ImageView)findViewById(R.id.record_image_stop);
        next=(Button)findViewById(R.id.record_btn_next);
        //uploadstorage=(Button)findViewById(R.id.record_btn_upload);
        next.setOnClickListener(this);
        recordbut.setOnClickListener(this);
        stopbut.setOnClickListener(this);
        //uploadstorage.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_record, menu);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 3:

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_btn_next:
                Intent intent=new Intent(RecordActivity.this,UploadTrackActivity.class);
                intent.putExtra("filename",outputFile);
                startActivity(intent);
                finish();
                break;
            case R.id.record_image_record:
                try {
                    outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
                    myAudioRecorder = new MediaRecorder();
                    myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                    myAudioRecorder.setOutputFile(outputFile);

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
                break;
            case R.id.record_image_stop:
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);

                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;

                stopbut.setEnabled(false);

                Toast.makeText(getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_LONG).show();
                break;
            /*case R.id.record_btn_upload:
                Intent musicPicker2 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(musicPicker2, 3);
                break;*/
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
