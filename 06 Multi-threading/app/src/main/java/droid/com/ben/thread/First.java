package droid.com.ben.thread;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileOutputStream;

public class First extends AppCompatActivity {
    private TextView myText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        LinearLayout lView = new LinearLayout(this);

        myText = new TextView(this);
        myText.setText("My Text");

        lView.addView(myText);

        setContentView(lView);
    }

    public void write() {
        try {
            String filename = "myFile";
            FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);

            for (int i = 1; i < 11; i++) {
                outputStream.write(i);
                outputStream.write("\n".getBytes());
                Thread.sleep(250);
            }
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
