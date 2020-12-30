package com.example.translator;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FamilyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FamilyFragment extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FamilyFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FamilyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FamilyFragment newInstance(String param1, String param2)
    {
        FamilyFragment fragment = new FamilyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    MediaPlayer player;
    AudioManager manager;

    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener()
            {
                public void onAudioFocusChange(int focusChange)
                {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS)
                    {
                        // Permanent loss of audio focus
                        // Pause playback immediately
                        // manager.abandonAudioFocusRequest(afChangeListener);
                        releaseMediaPlayer();
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
                    {
                        // Pause playback
                        player.pause();
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
                    {
                        // Lower the volume, keep playing
                        releaseMediaPlayer();
                        //player.pause();
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_GAIN || focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                    {
                        // Your app has been granted audio focus again
                        // Raise volume to normal, restart playback if necessary
                        player.start();
                    }
                }
            };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.word_list, container, false);
        final ArrayList<Words> words = new ArrayList<Words>();
        words.add(new Words("father", "әpә", R.drawable.family_father, R.raw.family_father));
        words.add(new Words("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Words("son", "angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new Words("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Words("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Words("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Words("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Words("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Words("grandmother ", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Words("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        //CustomAdaptor Just as ArrayAdaptor
        ListAdapter itemsAdapter = new ListAdapter(getActivity(), words, R.color.categoryFamilyMembers);

        ListView listView = (ListView) view.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        manager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId)
            {
                releaseMediaPlayer();

                int result = manager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    int temp = words.get(itemPosition).getAudioResourceId();

                    player = MediaPlayer.create(getActivity(), temp);

                    player.start();

                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                    {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer)
                        {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });
        return view;
    }

    @Override
    public void onStop()
    {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer()
    {
        // If the media player is not null, then it may be currently playing a sound.
        if (player != null)
        {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            player.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            player = null;
            manager.abandonAudioFocus(afChangeListener);
        }
    }
}