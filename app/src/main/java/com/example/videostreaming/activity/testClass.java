package com.example.videostreaming.activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Handler;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.videostreaming.R;

import java.io.IOException;

/**
 * Created by Mook on 18/08/2016.
 */

public class testClass extends AppCompatActivity implements OnPreparedListener, SurfaceHolder.Callback
{
    MediaPlayer mMediaPlayer ;
    SurfaceView mSurfaceView ;
    SurfaceHolder holder ;
    private Uri vidUri;
    Handler handler ;
    private SeekBar seekBar;
    LinearLayout mediacontroller;
    TextView timePosition,timeDuration;
    int mediaDuration, currentDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_play_video);

        mediacontroller = (LinearLayout) findViewById(R.id.mediacontroller);
        mediacontroller.setVisibility(View.INVISIBLE);
        timePosition = (TextView)findViewById(R.id.position);
        timeDuration = (TextView)findViewById(R.id.duration);

        String vidAddress = "http://www.html5videoplayer.net/videos/toystory.mp4";
        vidUri = Uri.parse(vidAddress);

        mSurfaceView = (SurfaceView) findViewById(R.id.firstSurface);
        handler = new Handler();
        holder = mSurfaceView.getHolder();
        holder.addCallback(this);
        playVideo();

    }

    private void playVideo() {
        // call method ValumnControls()
        ValumnControls();
        try {

            if(mMediaPlayer != null){
                mMediaPlayer.release();
            }
            mMediaPlayer = MediaPlayer.create(this, vidUri);
            mediaController();
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setScreenOnWhilePlaying(true);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void mediaController(){
        seekBar = (SeekBar)findViewById(R.id.seekBar1);
        seekBar.setMax(mMediaPlayer.getDuration());
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                UpdateseekChange(v);
                return false;
            }
        });

        final Button btnPlay = (Button) findViewById(R.id.play); // Start
        final Button btnPause = (Button) findViewById(R.id.puase); // Pause
        final Button btnStop = (Button) findViewById(R.id.stop); // Stop


        // Start
        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mMediaPlayer.start();
                startPlayProgressUpdater();
                btnPlay.setEnabled(false);
                btnPause.setEnabled(true);
                btnStop.setEnabled(true);
            }
        });

        // Pause
        btnPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mMediaPlayer.pause();
                btnPlay.setEnabled(true);
                btnPause.setEnabled(false);
                btnStop.setEnabled(false);
            }
        });

        // Stop
        btnStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mMediaPlayer.stop();
                btnPlay.setEnabled(true);
                btnPause.setEnabled(false);
                btnStop.setEnabled(false);
                try {
                    mMediaPlayer.prepare();
                    mMediaPlayer.seekTo(0);
                } catch (IllegalStateException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

    }

    private void UpdateseekChange(View v){
        if(mMediaPlayer.isPlaying()){
            SeekBar sb = (SeekBar)v;
            mMediaPlayer.seekTo(sb.getProgress());
        }
    }

    public void startPlayProgressUpdater() {
        seekBar.setProgress(mMediaPlayer.getCurrentPosition());

        if (mMediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    startPlayProgressUpdater();
                    mediaDuration = mMediaPlayer.getDuration();
                    currentDuration = mMediaPlayer.getCurrentPosition();

                    timePosition.setText(getTimeString(currentDuration));
                    timeDuration.setText(getTimeString(mediaDuration));
                }
            };
            handler.postDelayed(notification,1000);
        }
    }

    private void ValumnControls()
    {
        try
        {
            SeekBar seekBarVoice = (SeekBar)findViewById(R.id.seekBarVoice);
            final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            seekBarVoice.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            seekBarVoice.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            seekBarVoice.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
            {
                public void onStopTrackingTouch(SeekBar arg0)
                {
                }

                public void onStartTrackingTouch(SeekBar arg0)
                {
                }

                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mediacontroller.setVisibility(View.VISIBLE);
        startPlayProgressUpdater();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mMediaPlayer.setDisplay(holder);
        try {
            mMediaPlayer.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    private String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        int hours = (int) (millis / (1000 * 60 * 60));
        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf
                .append(String.format("%02d", hours))
                .append(":")
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }

}
