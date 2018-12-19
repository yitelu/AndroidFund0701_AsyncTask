package com.ytl.simpleasynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TEXT_STATE = "currentText";

    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView1 = findViewById(R.id.textView1);

        //Restore TextView if there's a savedInstanceState
        if(savedInstanceState != null){
            mTextView1.setText(savedInstanceState.getString(TEXT_STATE));
        }


    }

    public void startTask(View view) {

        mTextView1.setText(getString(R.string.napping));

        new SimpleAsyncTask(mTextView1).execute();


    }

    private WeakReference<TextView> mTextView;

    public class SimpleAsyncTask extends AsyncTask<Void, Void, String>{

        SimpleAsyncTask(TextView tv){
            mTextView = new WeakReference<>(tv);
        }

        @Override
        protected String doInBackground(Void... voids) {
            //This is where you implement the code to execute the work that is to be performed on the separate thread.
            Random r = new Random();
            int n = r.nextInt(11);
            int s = n * 200;

            try{
                Thread.sleep(s);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            return "Awake at last after sleeping for " + s + " milliseconds!";
        }

        @Override
        protected void onPostExecute(String s) {
            //Again on the UI thread, this is used for updating the results to the UI once the AsyncTask has finished loading.
            mTextView.get().setText(s);
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            //This method runs on the UI thread, and is used for setting up your task (like showing a progress bar).
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //This is invoked on the UI thread and used for updating progress in the UI (such as filling up a progress bar)
            super.onProgressUpdate(values);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_STATE, mTextView1.getText().toString());

    }
}
