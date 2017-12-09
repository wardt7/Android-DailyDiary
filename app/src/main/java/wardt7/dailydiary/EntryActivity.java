package wardt7.dailydiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Toby on 27/11/2017.
 */

public class EntryActivity extends AppCompatActivity {
    private EditText ratingEntry;
    private EditText keywordEntry;
    private EditText contentsEntry;
    public static final String FILE_NAME = "diaryentries.txt";
    private File file;
    private FileOutputStream outputStream;
    private Calendar currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        file = new File(this.getFilesDir(), FILE_NAME);
        ratingEntry = findViewById(R.id.rating_entry);
        keywordEntry = findViewById(R.id.keyword_input);
        contentsEntry = findViewById(R.id.contents_input);
    }

    public void onButtonClick(View view){
        switch (view.getId()){
            case R.id.buttonSave:
                save(view);
                break;
            default:
                break;
        }
    }

    public void save(View view){
        currentDate = Calendar.getInstance();
        String stringDate = currentDate.get(Calendar.DATE) + "/" + currentDate.get(Calendar.MONTH) + "/" + currentDate.get(Calendar.YEAR);
        String data = stringDate + "|" + keywordEntry.getText().toString() + "|" + ratingEntry.getText().toString()
                + "|" + contentsEntry.getText().toString() + "|";
        try{
            outputStream = new FileOutputStream(file, true);
            outputStream.write(data.getBytes());
            outputStream.close();
            Toast.makeText(this,"Diary entry successfully saved!", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
