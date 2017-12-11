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
        // Initialise the file we're saving to, get entry boxes for use later
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

    public boolean save(View view){
        if (ratingEntry.getText().toString().equals("")){
            Toast.makeText(this,"Rating is missing!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (keywordEntry.getText().toString().equals("")){
            Toast.makeText(this,"Keyword is missing!", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Obtain the (British English) date using calendar
        currentDate = Calendar.getInstance();
        String stringDate = currentDate.get(Calendar.DATE) + "/" + (currentDate.get(Calendar.MONTH)+1) + "/" + currentDate.get(Calendar.YEAR);
        // Format a record for storage in diaryentries.text
        String data = stringDate + "|" + keywordEntry.getText().toString() + "|" + ratingEntry.getText().toString()
                + "|" + contentsEntry.getText().toString() + "|";
        try{
            // Append to the file and finish, notifiying the user of addition
            outputStream = new FileOutputStream(file, true);
            outputStream.write(data.getBytes());
            outputStream.close();
            Toast.makeText(this,"Diary entry successfully saved!", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
