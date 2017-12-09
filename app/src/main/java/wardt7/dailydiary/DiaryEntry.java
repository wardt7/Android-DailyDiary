package wardt7.dailydiary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toby on 18/11/2017.
 */

public class DiaryEntry implements Serializable {
    String rating;
    String keyword;
    String date;
    String contents;

    DiaryEntry(String date, String keyword, String rating, String contents) {
        this.date = date;
        this.keyword = keyword;
        this.rating = rating;
        this.contents = contents;
    }
}


