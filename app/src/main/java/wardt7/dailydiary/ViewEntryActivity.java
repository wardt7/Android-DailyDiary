package wardt7.dailydiary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Toby on 05/12/2017.
 */

public class ViewEntryActivity extends AppCompatActivity{
    private EditText ratingDisplay;
    private EditText keywordDisplay;
    private EditText contentsDisplay;
    private EditText dateDisplay;
    private LinearLayout subLayout;
    private ScrollView scroll;
    private LinearLayout superLayout;
    private Toolbar toolbar;
    private int position;
    public static final String FILE_NAME = "diaryentries.txt";
    private File file;
    private FileOutputStream outputStream;
    private FileInputStream inputStream;
    private DiaryEntry updated;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry);
        file = new File(this.getFilesDir(), FILE_NAME);
        toolbar = findViewById(R.id.toolbar);
        superLayout = (LinearLayout)findViewById(R.id.activity_view_entry_toolbar);
        subLayout = (LinearLayout)findViewById(R.id.activity_view_entry_nested);
        scroll = (ScrollView)findViewById(R.id.scroll);
        setSupportActionBar(toolbar);
        Intent data = getIntent();
        updated = new DiaryEntry(data.getStringExtra("date"),data.getStringExtra("keyword"),data.getStringExtra("rating"),data.getStringExtra("contents"));
        position = data.getIntExtra("position",0);
        dateDisplay = findViewById(R.id.date_display);
        dateDisplay.setText(data.getStringExtra("date"));
        dateDisplay.setEnabled(false);
        dateDisplay.setFocusable(false);
        ratingDisplay = findViewById(R.id.rating_display);
        ratingDisplay.setText(data.getStringExtra("rating"));
        ratingDisplay.setEnabled(false);
        ratingDisplay.setFocusable(false);
        keywordDisplay = findViewById(R.id.keyword_display);
        keywordDisplay.setText(data.getStringExtra("keyword"));
        keywordDisplay.setEnabled(false);
        keywordDisplay.setFocusable(false);
        contentsDisplay = findViewById(R.id.contents_display);
        contentsDisplay.setText(data.getStringExtra("contents"));
        contentsDisplay.setEnabled(false);
        contentsDisplay.setFocusable(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.view_entry_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.edit_button:
                ratingDisplay.setEnabled(true);
                ratingDisplay.setInputType(InputType.TYPE_CLASS_NUMBER);
                ratingDisplay.setFocusableInTouchMode(true);
                keywordDisplay.setEnabled(true);
                keywordDisplay.setInputType(InputType.TYPE_CLASS_TEXT);
                keywordDisplay.setFocusableInTouchMode(true);
                contentsDisplay.setEnabled(true);
                contentsDisplay.setInputType(InputType.TYPE_CLASS_TEXT);
                contentsDisplay.setFocusableInTouchMode(true);
                Button saveButton = new Button(this);
                saveButton.setText("Save Changes");
                saveButton.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        save();
                    }
                });
                superLayout.removeAllViews();
                subLayout.addView(saveButton);
                superLayout.addView(toolbar);
                superLayout.addView(scroll);
                setContentView(superLayout);
                Toast.makeText(this,"Editing Enabled!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    public boolean save(){
        List<DiaryEntry> entries = new ArrayList<>();
        int length = (int)file.length();
        byte[] bytes = new byte[length];
        try{
            inputStream = new FileInputStream(file);
            inputStream.read(bytes);
            inputStream.close();
            String data = new String(bytes);
            if (file.exists()) {
                String[] splitted = data.split("\\|");
                while (splitted.length >= 4) {
                    String entry_date = splitted[0];
                    String entry_keyword = splitted[1];
                    String entry_rating = splitted[2];
                    String entry_contents = splitted[3];
                    entries.add(new DiaryEntry(entry_date, entry_keyword, entry_rating, entry_contents));
                    splitted = Arrays.copyOfRange(splitted, 4, splitted.length);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        updated.rating = ratingDisplay.getText().toString();
        updated.keyword = keywordDisplay.getText().toString();
        updated.contents = contentsDisplay.getText().toString();
        updated.date = entries.get(position).date;
        entries.set(position,updated);
        String data = "";
        StringBuilder builder = new StringBuilder();
        for(Iterator<DiaryEntry> i = entries.iterator(); i.hasNext();){
            DiaryEntry entry = i.next();
            if (entry.rating.equals("No Entries!")){
                return true;
            }
            builder.append(entry.date + "|" + entry.keyword + "|" + entry.rating + "|" + entry.contents + "|");
        }
        data = builder.toString();
        try{
            outputStream = new FileOutputStream(file);
            outputStream.write(data.getBytes());
            outputStream.close();
            Toast.makeText(this,"Entry Updated!", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
