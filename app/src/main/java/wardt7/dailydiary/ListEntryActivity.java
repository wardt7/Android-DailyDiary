package wardt7.dailydiary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Toby on 18/11/2017.
 */

public class ListEntryActivity extends AppCompatActivity{

    RecyclerView rv;
    private List<DiaryEntry> entries;
    public static final String FILE_NAME = "diaryentries.txt";
    private File file;
    private FileInputStream inputStream;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_entry);
        rv = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        file = new File(this.getFilesDir(), FILE_NAME);
        initialize_data();
        initialize_adapter();

    }


    private void initialize_data(){
        Log.d("data", "started");
        entries = new ArrayList<>();
        int length = (int)file.length();
        byte[] bytes = new byte[length];
        Log.d("data","setup");
        try{
            inputStream = new FileInputStream(file);
            inputStream.read(bytes);
            inputStream.close();
            String data = new String(bytes);
            Log.d("data", "read");
            if (file.exists()) {
                String[] splitted = data.split("\\|");
                Log.d("data", "splitted");
                while (splitted.length >= 4) {
                    String entry_date = splitted[0];
                    String entry_keyword = splitted[1];
                    String entry_rating = splitted[2];
                    String entry_contents = splitted[3];
                    entries.add(new DiaryEntry(entry_date, entry_keyword, entry_rating, entry_contents));
                    Log.d("data","added");
                    splitted = Arrays.copyOfRange(splitted, 4, splitted.length);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize_adapter(){
        RVAdapter adapter = new RVAdapter(entries);
        rv.setAdapter(adapter);
    }

}
