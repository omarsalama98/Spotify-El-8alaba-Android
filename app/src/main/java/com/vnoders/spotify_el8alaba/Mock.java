package com.vnoders.spotify_el8alaba;


public class Mock {

    public static class mock_track {
        private String mName;
        private String mAuthor;
        private int mImageId;
        private int mType;  // 0 for playlist, 1 for artist

        public mock_track(String name, String author, int imageId, int type) {
            mName = name;
            mAuthor = author;
            mImageId = imageId;
            mType = type;
        }

        public String getName() {
            return mName;
        }

        public String getAuthor() {
            return mAuthor;
        }

        public int getImageId() {
            return mImageId;
        }

        public int getType() {
            return mType;
        }
    }

    public static mock_track getMockTrack() {
        return new mock_track("This is the song that is being played right now", "Eminem",  R.mipmap.ic_launcher, 0);
    }
}
