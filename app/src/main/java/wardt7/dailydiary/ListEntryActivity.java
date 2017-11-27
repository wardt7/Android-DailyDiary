package wardt7.dailydiary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toby on 18/11/2017.
 */

public class ListEntryActivity extends AppCompatActivity{

    RecyclerView rv;
    private List<DiaryEntry> entries;
    public static final String FILE_NAME = "diaryentries.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_entry);
        rv = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        initialize_data();
        initialize_adapter();

    }


    private void initialize_data() {
        entries = new ArrayList<>();
        entries.add(new DiaryEntry("10/03/2012", "Happy", "10"));
        entries.add(new DiaryEntry("04/11/2013", "Mixed", "7"));
        entries.add(new DiaryEntry("29/04/2010", "Sad",  "2"));
    }

    private void initialize_adapter(){
        RVAdapter adapter = new RVAdapter(entries);
        rv.setAdapter(adapter);
    }

}
