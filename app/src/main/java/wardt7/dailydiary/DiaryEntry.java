package wardt7.dailydiary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toby on 18/11/2017.
 */

public class DiaryEntry {
    String rating;
    String keyword;
    String date;

    DiaryEntry(String date, String keyword, String rating) {
        this.date = date;
        this.keyword = keyword;
        this.rating = rating;
    }
}

