package leonding.create.resttest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView tx1 = (TextView) findViewById(R.id.textView);
        TextView tx2 = (TextView) findViewById(R.id.textView2);
        Button button = (Button) findViewById(R.id.button);
        final String url="https://httpbin.org/get";




        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new FetchRest().execute(url);


            }

        });

    }

    private class FetchRest extends AsyncTask<String,Void,String>{




        @Override
        protected String doInBackground(String... strings) {
            String adress = strings[0];
            String  output ="";
            try{
                InputStream input = new java.net.URL(adress).openStream();
                 output = input.toString();
            }
           catch (Exception e){
                e.printStackTrace();
           }
            return output;
        }
    }

}
