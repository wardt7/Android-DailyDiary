package wardt7.dailydiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Toby on 18/11/2017.
 * Reference for RecyclerView:
 * Hathibelagal, A (2015) Getting Started with RecyclerView and CardView on Android
 * [online] available from
 * <https://code.tutsplus.com/tutorials/getting-started-with-recyclerview-and-cardview-on-android--cms-23465>
 *     **/

public class ListEntryActivity extends AppCompatActivity{

    RecyclerView rv;
    private List<DiaryEntry> entries;
    public static final String FILE_NAME = "diaryentries.txt";
    private File file;
    private FileInputStream inputStream;
    private RVAdapter adapter;
    private final int EDIT_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialise the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_entry);
        // Give the recyclerview a layout manager and an adapter
        rv = findViewById(R.id.recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        file = new File(this.getFilesDir(), FILE_NAME);
        initializeData();
        initializeAdapter();

    }


    private void initializeData(){
        entries = new ArrayList<>();
        int length = (int)file.length();
        byte[] bytes = new byte[length];
        try{
            // Read out the file and convert to a string
            inputStream = new FileInputStream(file);
            inputStream.read(bytes);
            inputStream.close();
            String data = new String(bytes);
            if (file.exists()) {
                // Split the data into chunks for reading into the entries list
                String[] splitted = data.split("\\|");
                while (splitted.length >= 4) {
                    // Get the date, keyword, rating and contents and put into entries
                    // as DiaryEntry
                    String entryDate = splitted[0];
                    String entryKeyword = splitted[1];
                    String entryRating = splitted[2];
                    String entryContents = splitted[3];
                    entries.add(new DiaryEntry(entryDate, entryKeyword, entryRating, entryContents));
                    splitted = Arrays.copyOfRange(splitted, 4, splitted.length);
                }
            }
        } catch (Exception e) {
            // Notify the user that there was a problem reading their entries
            entries.add(new DiaryEntry("", "", "No Entries!", ""));
            Toast.makeText(this,"Failed to read diaryentries.txt", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        if (entries.size() == 0){
            // Create an entry to display "No entries" when there are no entries
            entries.add(new DiaryEntry("", "", "No Entries!", ""));
        }
    }

    private void initializeAdapter(){
        adapter = new RVAdapter(entries);
        rv.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        if (requestCode == EDIT_RESULT){
            if (resultCode == Activity.RESULT_OK){
                boolean check = data.getBooleanExtra("result", false);
                if (check){
                    // Recreate the activity as we have new information
                    recreate();
                } else {
                    Toast.makeText(this,"Editing failed, try again!", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

}
