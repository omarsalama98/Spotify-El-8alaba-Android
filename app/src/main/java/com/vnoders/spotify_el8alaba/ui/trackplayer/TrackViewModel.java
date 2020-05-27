package com.vnoders.spotify_el8alaba.ui.trackplayer;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.Track;

/**
 * @author Ali Adel TrackViewModel with singleton pattern to have only 1 instance of current song
 * being played globally
 */
public class TrackViewModel extends ViewModel {

    // instance for singleton
    private static TrackViewModel mInstance;
    // mutable live data object to get hold current track
    private MutableLiveData<Track> mCurrentTrack;
    // mutable live data object to hold progress of bar
    private MutableLiveData<Integer> mTrackProgress;
    // mutable live data object to hold init status
    private MutableLiveData<Boolean> mInitRequired;

    /**
     * private constructor for singleton
     */
    private TrackViewModel() {
        mInitRequired = new MutableLiveData<>(true);
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
    public MutableLiveData<Track> getCurrentTrack() {
        if (mCurrentTrack == null) {
            mCurrentTrack = new MutableLiveData<>();
            mCurrentTrack.postValue(null);
        }
        return mCurrentTrack;
    }

    /**
     * called by service to update data and hence update UI
     *
     * @param track track object that is changed
     */
    public void updateCurrentTrack(Track track) {
        if (mCurrentTrack == null) {
            mCurrentTrack = new MutableLiveData<>();
        }
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
        if (mTrackProgress == null) {
            mTrackProgress = new MutableLiveData<>();
        }
        mTrackProgress.postValue(progress);
    }

    /**
     * Called to get the status of init by service to see if init now or not
     *
     * @return status of init
     */
    public MutableLiveData<Boolean> getInitRequired() {
        if (mInitRequired == null) {
            mInitRequired = new MutableLiveData<>();
            mInitRequired.postValue(Boolean.TRUE);
        }
        return mInitRequired;
    }

    /**
     * updates the init status if user logs out
     *
     * @param initRequired status of init
     */
    public void updateInitRequired(Boolean initRequired) {
        if (mInitRequired == null) {
            mInitRequired = new MutableLiveData<>();
            mInitRequired.postValue(Boolean.TRUE);
        }
        mInitRequired.postValue(initRequired);
    }
}
