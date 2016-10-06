package droid.com.ben.datastorage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    public static final String mypreference = "mypref";

    Button add;
    Button save;
    TextView tv;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = (Button)findViewById(R.id.button2);
        save = (Button)findViewById(R.id.button);
        tv = (TextView)findViewById(R.id.textView);

        SharedPreferences settings = getSharedPreferences(mypreference, 0);
        count = settings.getInt("COUNT", 0); //0 is the default value
        tv.setText(Integer.toString(count));
    }

    public void addOne(View view) {
        count++;
        tv.setText(Integer.toString(count));
    }

    public void saveCount(View view) {
        // Save current count
        SharedPreferences settings = getSharedPreferences(mypreference, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("COUNT",count);
        editor.commit();
    }
}
