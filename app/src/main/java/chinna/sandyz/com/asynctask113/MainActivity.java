package chinna.sandyz.com.asynctask113;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    EditText textmsg;
    Button add, delete;
    TextView data;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textmsg = (EditText) findViewById(R.id.edit_text);
        add = (Button) findViewById(R.id.button);
        delete = (Button) findViewById(R.id.delete_button);
        data = (TextView) findViewById(R.id.data);

        //creation of File
        file = new File(Environment.getExternalStorageDirectory(), "test.txt");

        try {
            if (file.createNewFile()) {
                makeText(getApplicationContext(), "File Created",
                        Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //update data to File
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value =  textmsg.getText().toString();
                textmsg.setText("");

                ReadFromFile rf = new ReadFromFile(file);
                rf.execute(value);


            }
        });

        //deletion of  file
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file.delete();

                makeText(getApplicationContext(),"File deleted",Toast.LENGTH_SHORT).show();

            }
        });

    }



    private class ReadFromFile extends AsyncTask<String, Integer, String> {

        // static String FILENAME = "test.txt";
        File f;

        ReadFromFile(File f) {
            super();
            this.f = f;
            // TODO Auto-generated constructor stub
        }

        //update data to file
        @Override
        protected String doInBackground(String... str) {
            String enter = "\n";
            FileWriter writer = null;
            try {
                writer = new FileWriter(f, true);
                writer.append(str[0]);
                writer.append(enter);
                writer.flush();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    assert writer != null;
                    writer.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return null;

        }


        //read data from file
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String name = "";
            StringBuilder sb = new StringBuilder();
            FileReader fr = null;

            try {
                fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                while ((name = br.readLine()) != null) {
                    sb.append(name);
                    sb.append("\n");

                }
                br.close();
                fr.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            data.setText(sb.toString());
        }

    }

}









