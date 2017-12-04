package wardt7.dailydiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClick(View view){
        switch (view.getId()){
            case R.id.entry_go:
                Log.d("abcd", "hello");
                Intent intentEdit = new Intent(this, EntryActivity.class);
                startActivity(intentEdit);
                break;
            case R.id.list_go:
                Intent intentList = new Intent(this, ListEntryActivity.class);
                startActivity(intentList);
            default:
                break;
        }
    }
}
