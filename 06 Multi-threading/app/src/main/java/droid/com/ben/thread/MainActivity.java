package droid.com.ben.thread;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    private Button button1, button2, button3;
    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    private File file = new File("numbers.txt");
    private int fileSize = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            button1 = (Button) findViewById(R.id.button);
            button2 = (Button) findViewById(R.id.button2);
            button3 = (Button) findViewById(R.id.button3);

            button1.setOnClickListener(listener);
            button2.setOnClickListener(listener);
            button3.setOnClickListener(listener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createIt() {
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            for (int i = 1; i < 11; i++) {
                fileWriter.write(i);
                fileWriter.write("\n");
                Thread.sleep(250);
            }
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void load() throws Exception {


    }

    private void clear() {

    }

    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            // create and display a new ProgressBarDialog
            progressBar = new ProgressDialog(view.getContext());
            progressBar.setCancelable(true);
            progressBar.setMessage("Updating files");
            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();

            progressBarStatus = 0;

            fileSize = 0;

            new Thread(new Runnable() {

                public void run() {
                    while (progressBarStatus < 100) {

                        // process some tasks
                        progressBarStatus = downloadFile();


                        // sleep 1 second (simulating a time consuming task...)
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // Update the progress bar
                        progressBarbHandler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(progressBarStatus);
                            }
                        });
                    }

                    // if the file is downloaded,
                    if (progressBarStatus >= 100) {

                        // sleep 2 seconds, so that you can see the 100%
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        createIt();

                        // and then close the progressbar dialog
                        progressBar.dismiss();
                    }


                }
            }).start();
        }
    };


    // file download simulator...
    public int downloadFile() {

        while (fileSize <= 1000000) {

            fileSize++;
            switch(fileSize) {
                case 100000:
                    return 10;
                case 200000:
                    return 20;
                case 300000:
                    return 30;
                case 400000:
                    return 40;
                case 500000:
                    return 50;
                case 600000:
                    return 60;
                case 700000:
                    return 70;
                case 800000:
                    return 80;
                case 900000:
                    return 90;
            }
        }
        return 100;
    }

}

