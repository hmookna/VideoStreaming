package com.example.videostreaming;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by Mook on 17/08/2016.
 */

public class PlayVideoFragment extends Fragment {

    private SurfaceHolder mFirstSurface;
    private SurfaceHolder mSecondSurface;
    private SurfaceHolder mActiveSurface;
    private MediaPlayer mMediaPlayer;
    private Uri vidUri;


    public PlayVideoFragment() {  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_video, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

      //  VideoView vidView = (VideoView)view.findViewById(R.id.myVideo);

        String vidAddress = "http://techslides.com/demos/sample-videos/small.mp4";
        vidUri = Uri.parse(vidAddress);
       // vidView.setVideoURI(vidUri);
        //Playback Controls
     //   MediaController vidControl = new MediaController(getContext());
      //  vidControl.setAnchorView(vidView);
      //  vidView.setMediaController(vidControl);
        //   vidView.start();

        SurfaceView first = (SurfaceView) view.findViewById(R.id.firstSurface);
        first.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                Log.d(PlayVideoFragment.class.getName(), "First surface created!");
                mFirstSurface = surfaceHolder;
                if (vidUri != null) {
                    mMediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(),
                            vidUri, mFirstSurface);
                    mActiveSurface = mFirstSurface;
                    mMediaPlayer.start();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                Log.d(PlayVideoFragment.class.getName(), "First surface destroyed!");
            }
        });


    }
}

