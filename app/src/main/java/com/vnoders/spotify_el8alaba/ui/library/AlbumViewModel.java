package com.vnoders.spotify_el8alaba.ui.library;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.Html;
import android.text.Spanned;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.vnoders.spotify_el8alaba.App;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.library.AlbumTrack;
import com.vnoders.spotify_el8alaba.repositories.LibraryRepository;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;


/**
 * This is the ViewModel of {@link AlbumFragment}. It is responsible for preparing and managing the
 * data for {@link AlbumFragment}. It handles the communication of {@link AlbumFragment} with the
 * rest of the application (e.g. calling the business logic classes).
 * <p>
 * The {@link AlbumFragment} Fragment should get the information from this view model and observe
 * its changing (Using Observer Design Pattern) by getting {@link LiveData} objects and applying
 * {@link LiveData#observe} on them.
 */
public class AlbumViewModel extends ViewModel {

    private String albumId;
    private String artistId;
    private MutableLiveData<String> albumName;
    private MutableLiveData<String> artistName;
    private MutableLiveData<String> artistImageUrl;
    private MutableLiveData<String> albumArtistInfo;
    private MutableLiveData<Spanned> tracksSummary;
    private MutableLiveData<String> imageUrl;
    private MutableLiveData<Boolean> isFollowed;
    private Date releaseDate;
    private MutableLiveData<String> formattedReleaseDate;
    private MutableLiveData<Boolean> finishedLoading;

    private Observer<String> infoObserver;

    public AlbumViewModel() {
        albumName = new MutableLiveData<>();
        artistName = new MutableLiveData<>();
        artistImageUrl = new MutableLiveData<>();
        albumArtistInfo = new MutableLiveData<>();
        tracksSummary = new MutableLiveData<>();
        imageUrl = new MutableLiveData<>();
        isFollowed = new MutableLiveData<>();
        formattedReleaseDate = new MutableLiveData<>();
        finishedLoading = new MutableLiveData<>(false);

        infoObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (artistName.getValue() != null && releaseDate != null) {
                    String info = "Album By " + artistName.getValue() + " • " + getReleaseYear();
                    albumArtistInfo.setValue(info);
                }
            }
        };

        artistName.observeForever(infoObserver);
        formattedReleaseDate.observeForever(infoObserver);
    }

    @Override
    protected void onCleared() {
        artistName.removeObserver(infoObserver);
        formattedReleaseDate.removeObserver(infoObserver);
    }

    /**
     * @param albumName The name of the album
     */
    public void setAlbumName(String albumName) {
        this.albumName.setValue(albumName);
    }

    /**
     * @param artistName The name of the owner of the album (who created this album)
     */
    public void setArtistName(String artistName) {
        this.artistName.setValue(artistName);
    }

    private Spanned buildTracksSummary(ArrayList<AlbumTrack> tracks) {

        final String WHITE_COLOR = "#FFFFFF";
        final String andMore = App.getInstance().getString(R.string.and_more);
        final int MAX_INFO_SIZE = 300;

        StringBuilder stringBuilder = new StringBuilder();

        for (AlbumTrack track : tracks) {

            if (track != null) {
                stringBuilder.append(track.getName()).append("  •  ");
            }

            if (stringBuilder.length() > MAX_INFO_SIZE) {
                break;
            }
        }

        // in case there are no tracks we do not display it
        if (stringBuilder.length() > 0) {
            String html = String.format("<font color=\'%s\'> %s </font>", WHITE_COLOR, andMore);
            stringBuilder.append(html);
        }

        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            return Html.fromHtml(stringBuilder.toString(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(stringBuilder.toString());
        }
    }


    public void setTracksSummary(ArrayList<AlbumTrack> tracks) {
        this.tracksSummary.setValue(buildTracksSummary(tracks));
    }

    /**
     * @param imageUrl The URL of the album's cover image
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl.setValue(imageUrl);
    }

    /**
     * @return The name of the album, wrapped in a {@link LiveData} object.
     */
    public LiveData<String> getAlbumName() {
        return albumName;
    }

    /**
     * @return The summary of the tracks in this album (e.g. Song_Name Artist_Name •  ... ), wrapped
     * in a {@link LiveData} object.
     */
    public LiveData<Spanned> getTracksSummary() {
        return tracksSummary;
    }

    /**
     * @return The URL of the album's cover image, wrapped in a {@link LiveData} object.
     */
    public LiveData<String> getImageUrl() {
        return imageUrl;
    }

    /**
     * @return The name of the owner of the album (who created this album), wrapped in a {@link
     * LiveData} object.
     */
    public LiveData<String> getArtistName() {
        return artistName;
    }

    /**
     * @return The id of the album
     */
    public String getAlbumId() {
        return albumId;
    }

    /**
     * @param albumId The id of the album
     */
    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public LiveData<Boolean> getFollowedState() {
        return isFollowed;
    }

    public void setFollowedState(boolean isFollowed) {
        this.isFollowed.setValue(isFollowed);
    }

    public LiveData<String> getArtistImageUrl() {
        return artistImageUrl;
    }

    public void setArtistImageUrl(String url) {
        this.artistImageUrl.setValue(url);
    }

    public LiveData<String> getReleaseDate() {
        return formattedReleaseDate;
    }

    public MutableLiveData<String> getAlbumArtistInfo() {
        return albumArtistInfo;
    }

    private int getReleaseYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(releaseDate);
        return calendar.get(Calendar.YEAR);
    }

    public void setReleaseDate(@NotNull Date releaseDate) {
        this.releaseDate = releaseDate;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(releaseDate);

        String formattedDate = String.format(Locale.US, "%s %d, %d",
                calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.YEAR));

        this.formattedReleaseDate.setValue(formattedDate);
    }

    public LiveData<Boolean> getFinishedLoadingState() {
        return finishedLoading;
    }

    public void setFinishedLoading() {
        this.finishedLoading.setValue(true);
    }

    public void followAlbum() {
        LibraryRepository.followAlbum(this);
    }

    public void unfollowAlbum() {
        LibraryRepository.unfollowAlbum(this);
    }

    /**
     * Make a call to {@link LibraryRepository#updateAlbum} to update the info of this album which
     * apply a level of abstraction between UI and business logic and data.
     */
    public void updateData() {
        LibraryRepository.updateAlbumFollowState(this);
        LibraryRepository.updateAlbum(this);
    }

}