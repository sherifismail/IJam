package com.Example.iJam.models;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;
import android.os.Environment;

import com.Example.iJam.activities.RecordActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mostafa on 7/16/2015.
 */
public abstract class MyAudioManager {

    public static final int FREQUENCY = 44100;
    public static final int CHANNEL_CONFIGURATION = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    public static final int AUDIO_ENCODING =  AudioFormat.ENCODING_PCM_16BIT;
    final static int STREAM_TYPE = AudioManager.STREAM_MUSIC;
    public static final int PLAY_STATIC = AudioTrack.MODE_STATIC;
    public static final int PLAY_STREAM = AudioTrack.MODE_STREAM;

    private static boolean executing = false;
    private static AudioTrack track=null;

    public static AudioTrack InitAudio(String outputFile){

        AudioTrack track = null;
        try {
            File file = new File(outputFile);

            //GET THE LENGTH IN BYTES AND DIVIDE BY 2 TO GET THE LENGTH IN SHORT
            int audioLength = (int)(file.length());

            InputStream is = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            track = new AudioTrack(STREAM_TYPE, FREQUENCY, CHANNEL_CONFIGURATION, AUDIO_ENCODING,audioLength,PLAY_STATIC);

            short[] audioData = new short[audioLength];

            //INSERTING THE DATA IN THE FILE TO FILL THE UADIODATA ARRAY
            int i=0;
            while (dis.available()>0 && i<audioLength) {
                audioData[i] = dis.readShort();
                i++;
            }
            track.write(audioData, 0, audioLength);
            dis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return track;
    }

    public static AudioTrack InitAudioRemote(String url){

        executing = true;
        new AsyncTask<String, Void, Void>(){

            @Override
            protected Void doInBackground(String... params) {
                String outFileName = null;
                try {
                    outFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp";
                    File file = new File(outFileName);

                    URL source = new URL(params[0]);
                    BufferedInputStream in = new BufferedInputStream(source.openStream());
                    FileOutputStream fos = new FileOutputStream(outFileName);
                    BufferedOutputStream bout = new BufferedOutputStream(fos,1024);

                    byte[] data = new byte[1024];
                    int x=0;
                    while((x=in.read(data,0,1024))>=0){
                        bout.write(data,0,x);
                    }
                    fos.flush();
                    bout.flush();
                    fos.close();
                    bout.close();
                    in.close();

                    //GET THE LENGTH IN BYTES AND DIVIDE BY 2 TO GET THE LENGTH IN SHORT
                    int audioLength = (int)(file.length());

                    InputStream isf = new FileInputStream(file);
                    BufferedInputStream bisf = new BufferedInputStream(isf);
                    DataInputStream disf = new DataInputStream(bisf);

                    track = new AudioTrack(STREAM_TYPE, FREQUENCY, CHANNEL_CONFIGURATION, AUDIO_ENCODING,audioLength,PLAY_STATIC);

                    short[] audioData = new short[audioLength];

                    //INSERTING THE DATA IN THE FILE TO FILL THE UADIODATA ARRAY
                    int i=0;
                    while (disf.available()>0 && i<audioLength) {
                        audioData[i] = disf.readShort();
                        i++;
                    }
                    track.write(audioData, 0, audioLength);

                    disf.close();
                    executing = false;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(url);

        while(executing){}
        return track;
    }

    public static void InitStream(AudioTrack track, String url){
        try {
            //String outFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp";
            //File file = new File(outFileName);

            URL source = new URL(url);
            BufferedInputStream in = new BufferedInputStream(source.openStream());
            DataInputStream dis = new DataInputStream(in);

            int buffSize = AudioTrack.getMinBufferSize(FREQUENCY, CHANNEL_CONFIGURATION, AUDIO_ENCODING);
            track = new AudioTrack(STREAM_TYPE, FREQUENCY, CHANNEL_CONFIGURATION, AUDIO_ENCODING, buffSize, PLAY_STREAM);

            byte[] audioData = new byte[buffSize];
            //byte[] oneShort = new byte[2];


            //byte[] data = new byte[1024];
            //int x=0;
            //while((x=in.read(data,0,1024))>=0){
              //  bout.write(data,0,x);
            //}
            //INSERTING THE DATA IN THE FILE TO FILL THE UADIODATA ARRAY
            int x=0;
            //while (dis.available() > 0) {
              //  int i=0;
            while ((x = in.read(audioData, 0, buffSize)) >= 0) {
                track.write(audioData,0,audioData.length);
            }

            //}
            dis.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mixFiles(String fileInput1, String fileInput2, String fileOuput){

        List<Short> music1 = createMusicArray(fileInput1);
        List<Short> music2 = createMusicArray(fileInput2);

        completeStreams(music1, music2);
        short[] music1Array = buildShortArray(music1);
        short[] music2Array = buildShortArray(music2);

        short[] output = new short[music1Array.length];
        for(int i=0; i < output.length; i++){

            float samplef1 = music1Array[i] / 32768.0f;
            float samplef2 = music2Array[i] / 32768.0f;

            float mixed = samplef1 + samplef2;
            // reduce the volume a bit:
            mixed *= 0.8;
            // hard clipping
            if (mixed > 1.0f) mixed = 1.0f;
            if (mixed < -1.0f) mixed = -1.0f;
            short outputSample = (short)(mixed * 32768.0f);

            output[i] = outputSample;
        }
        saveToFile(output, fileOuput);

    }

    private static List<Short> createMusicArray(String fileName){
        List<Short> audioData = new ArrayList<>();
        try {
            File file = new File(fileName);
            InputStream is = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            int audioLength = (int) file.length();

            //INSERTING THE DATA IN THE FILE TO FILL THE UADIODATA ARRAY
            int i = 0;
            while (dis.available() > 0 && i < audioLength) {
                short data = dis.readShort();
                audioData.add(data);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return audioData;
    }

    private static void completeStreams(List<Short> track1, List<Short> track2){
        int size1 = track1.size();
        int size2 = track2.size();
        if(size1 > size2)
            completeList(track2, size1-size2);
        else if(size2 > size1)
            completeList(track1, size2-size1);
    }

    private static void completeList(List<Short> track, int difference){
        for(int i=0; i<difference; i++){
            Short data = 0;
            track.add(data);
        }
    }

    private static short[] buildShortArray(List<Short> track){
        short[] shorts = new short[track.size()];
        for(int i=0; i<track.size(); i++)
            shorts[i] = track.get(i);
        return shorts;
    }

    private static void saveToFile(short[] mix, String targetFile){
        try {
            File output = new File(targetFile);
            OutputStream os = new FileOutputStream(output);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            for(int i=0; i<mix.length; i++)
                dos.writeShort(mix[i]);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
