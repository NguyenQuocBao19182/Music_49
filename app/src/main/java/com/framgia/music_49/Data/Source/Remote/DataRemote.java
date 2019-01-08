package com.framgia.music_49.Data.Source.Remote;

import com.framgia.music_49.Data.Model.Song;
import java.util.List;

public interface DataRemote {
    void getDataRemoteSuccess(List<Song> list);

    void getDataRemoteFail(Exception e);
}
