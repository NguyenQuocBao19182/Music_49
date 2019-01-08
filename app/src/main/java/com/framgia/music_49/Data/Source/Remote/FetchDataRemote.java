package com.framgia.music_49.Data.Source.Remote;

import android.os.AsyncTask;
import com.framgia.music_49.Data.Model.Song;
import com.framgia.music_49.Data.Repository.SongRepository;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.framgia.music_49.Utils.LinkConnect.CLIENT_ID;

public class FetchDataRemote extends AsyncTask<String, Void, List<Song>>
        implements SongRepository.SongRemote {
    private DataRemote mDataRemote;
    private List<Song> mSongList = new ArrayList<>();
    private static final String COLLECTION = "collection";
    private static final String GET = "GET";

    @Override
    protected List<Song> doInBackground(String... strings) {
        try {
            String data = getDataFromUrl(strings[0]);
            mSongList = pareJsonToObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mSongList;
    }

    private String getDataFromUrl(String url) throws Exception {
        URL link = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) link.openConnection();
        httpURLConnection.setRequestMethod(GET);
        httpURLConnection.connect();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        line = bufferedReader.readLine();
        httpURLConnection.disconnect();
        return line;
    }

    private List<Song> pareJsonToObject(String data) throws Exception {
        List<Song> songList = new ArrayList<>();
        JSONObject jsonObj = new JSONObject(data);
        JSONArray jsonArray = jsonObj.getJSONArray(COLLECTION);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Song song =
                    new Song.Builder().setNameSong(jsonObject.getString(Song.JsonEntity.NAME_SONG))
                            .setNameArtist(jsonObject.getString(Song.JsonEntity.NAME_ARTIST))
                            .setImageSong(
                                    jsonObject.getString(Song.JsonEntity.URL_STREAM) + CLIENT_ID)
                            .setLink(jsonObject.getString(Song.JsonEntity.URL_STREAM))
                            .setDuration(jsonObject.getString(Song.JsonEntity.DURATION))
                            .setDownloadLink(jsonObject.getString(Song.JsonEntity.URL_DOWNLOAD))
                            .build();
            songList.add(song);
        }
        return songList;
    }

    @Override
    protected void onPostExecute(List<Song> songList) {
        super.onPostExecute(songList);
    }

    @Override
    public void getSongRemote(DataRemote dataRemote) {
    }
}
