package com.vnoders.spotify_el8alaba;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vnoders.spotify_el8alaba.models.PlayableTrack;
import com.vnoders.spotify_el8alaba.models.RealTrack;

/**
 * @author Ali Adel TrackViewModel with singleton pattern to have only 1 instance of current song
 * being played globally
 */
public class TrackViewModel extends ViewModel {

    // instance for singleton
    private static TrackViewModel mInstance;
    // mutable live data object to get hold current track
    private MutableLiveData<RealTrack> mCurrentTrack;
    // mutable live data object to hold progress of bar
    private MutableLiveData<Integer> mTrackProgress;

    /**
     * private constructor for singleton
     */
    private TrackViewModel() {
    }

    /**
     * static function to get instance of singleton
     *
     * @return global instance of class
     */
    public static TrackViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new TrackViewModel();
        }
        return mInstance;
    }

    /**
     * @return global instance of current track being played
     */
    public MutableLiveData<RealTrack> getCurrentTrack() {
        if (mCurrentTrack == null) {
            mCurrentTrack = new MutableLiveData<>();
        }
        return mCurrentTrack;
    }

    /**
     * called by service to update data and hence update UI
     *
     * @param track track object that is changed
     */
    public void updateCurrentTrack(RealTrack track) {
        mCurrentTrack.postValue(track);
    }


    /**
     * @return global instance of progress of current track being played
     */
    public MutableLiveData<Integer> getTrackProgress() {
        if (mTrackProgress == null) {
            mTrackProgress = new MutableLiveData<>();
        }
        return mTrackProgress;
    }

    /**
     * called by service to update data and hence update UI
     *
     * @param progress progress object that is changed
     */
    public void updateTrackProgress(Integer progress) {
        mTrackProgress.postValue(progress);
    }

}
