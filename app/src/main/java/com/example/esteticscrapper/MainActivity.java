package com.example.esteticscrapper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ArrayList<LinkData> arrayList;
    ListView list;
    CustomAdapterList customAdapter;
    Thread th1, th2;
    WebView webView;
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (arrayList.isEmpty()) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "adding results...", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }

            LinkData returnedValue = (LinkData) msg.obj;
            customAdapter.add(returnedValue);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.list);
        arrayList = new ArrayList<LinkData>();

        customAdapter = new CustomAdapterList(this, arrayList);
        list.setAdapter(customAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Intent intent = new Intent(getApplicationContext(), ListDataActivity.class);
                // intent.putExtra("com.example.esteticscrapper.link", arrayList.get(position).Link);
                Toast.makeText(getBaseContext(), arrayList.get(position).Link, Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(getBaseContext(), NextPage.class);
                intent.putExtra("url", arrayList.get(position).Link.toString());
                startActivity(intent);
            }
        });

    }

    public void startSearching(View v) {
        EditText et = findViewById(R.id.editText);
        customAdapter.clear();
        closeKeyboard();

        // final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:65.0) Gecko/20100101 Firefox/65.0";
        final String urlPost = "http://estetik.pl";
        final String urlPost2 = "https://estetycznahurtownia.pl";

        Map<String, String> postData = new HashMap<>();
        postData.put("search", et.getText().toString());

        ScrapperThread scThread = new ScrapperThread(Site.Estetik, urlPost, postData, handler);
        th1 = new Thread(scThread);
        th1.start();

        ScrapperThread scThread2 = new ScrapperThread(Site.EstetycznaHurtownia, urlPost2, postData, handler);
        th2 = new Thread(scThread2);
        th2.start();


    }

    private void closeKeyboard() {
        // this will give us the view
        // which is currently focus
        // in this layout
        View view = this.getCurrentFocus();

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            InputMethodManager manager
                    = (InputMethodManager)
                    getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }
}

