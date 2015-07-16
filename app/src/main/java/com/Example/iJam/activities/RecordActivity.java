package com.Example.iJam.activities;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Example.iJam.R;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class RecordActivity extends ActionBarActivity implements View.OnClickListener {
    Button next;
    private String outputFile = null;
    TextView timer;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    ImageView recordbut,stopbut;
    private boolean isRecording=false;

    public static final int FREQUENCY = 44100;
    public static final int CHANNEL_CONFIGURATION = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    public static final int AUDIO_ENCODING =  AudioFormat.ENCODING_PCM_16BIT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        timer=(TextView)findViewById(R.id.timer);
        recordbut=(ImageView)findViewById(R.id.record_image_record);
        stopbut=(ImageView)findViewById(R.id.record_image_stop);
        next=(Button)findViewById(R.id.record_btn_next);

        next.setOnClickListener(this);
        recordbut.setOnClickListener(this);
        stopbut.setOnClickListener(this);
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
                    outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording";
                    isRecording=true;
                    new AsyncTask<Void, Void, Void>(){

                        @Override
                        protected Void doInBackground(Void... params) {
                            recordSound();
                            return null;
                        }
                    }.execute();

                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);
                    Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();

                    recordbut.setEnabled(false);
                    stopbut.setEnabled(true);
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;

            case R.id.record_image_stop:
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);

                isRecording=false;
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

    private void recordSound(){
        File file = new File(outputFile);

        // Delete any previous recording.
        if (file.exists())
            file.delete();

        try {
            file.createNewFile();

            // Create a DataOuputStream to write the audio data into the saved file.
            OutputStream os = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);


            // Create a new AudioRecord object to record the audio.
            int bufferSize = AudioRecord.getMinBufferSize(FREQUENCY, CHANNEL_CONFIGURATION, AUDIO_ENCODING);
            AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, FREQUENCY, CHANNEL_CONFIGURATION, AUDIO_ENCODING, bufferSize);

            short[] buffer = new short[bufferSize];
            audioRecord.startRecording();

            int Fsize = 0;
            while (isRecording) {
                int bufferReadResult = audioRecord.read(buffer, 0, bufferSize);
                Fsize += bufferReadResult;
                for (int i = 0; i < bufferReadResult; i++)
                    dos.writeShort(buffer[i]);
            }

            audioRecord.stop();
            audioRecord.release();
            dos.close();

        }  catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            Log.e("shit", e.toString());
            //e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
