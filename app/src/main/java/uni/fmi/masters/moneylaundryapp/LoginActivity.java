package uni.fmi.masters.moneylaundryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import uni.fmi.masters.moneylaundryapp.entity.UserEntity;
import uni.fmi.masters.moneylaundryapp.helper.SQLiteHelper;

public class LoginActivity extends AppCompatActivity {

    EditText usernameET;
    EditText passwordET;
    SQLiteHelper db;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameET = findViewById(R.id.loginUsernameET);
        passwordET = findViewById(R.id.loginPasswordET);
        db = new SQLiteHelper(this);
    }

    public void login(View view){
//        UserEntity user = db.login(usernameET.getText().toString(),
//                passwordET.getText().toString());

        new LoginAsyncTask(usernameET.getText().toString(),
                passwordET.getText().toString()).execute();

//        counter++;
//
//        if(user != null){
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        }else{
//            Toast.makeText(this, "Wrong Credentials....",
//                    Toast.LENGTH_SHORT).show();
//            if(counter > 2){
//                finish();
//            }
//        }
    }

    public void register(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private class LoginAsyncTask extends AsyncTask<Void, Void, Void>{

        String username;
        String password;

        UserEntity user = null;

        ProgressDialog dialog;

        public LoginAsyncTask(String username, String password) {
            this.username = username;
            this.password = password;
            dialog = new ProgressDialog(LoginActivity.this);
        }

        @Override
        protected void onPreExecute() {
            dialog.setTitle("I`m doing it... calm your horses...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String urlString = String.format("http://10.0.0.22:8989/LoginUser?username=%s&password=%s",
                    username, password);

            HttpURLConnection urlConnection = null;

            try{
                URL url = new URL(urlString);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream =
                        new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(stream));

                String line = reader.readLine();

                if(line != null){
                    JSONObject jsonOb = new JSONObject(line);

                    user = new UserEntity();
                    user.setUsername(username);
                    user.setValidator(jsonOb.getString("Validator"));
                    user.setGender(jsonOb.getString("Gender"));
                    user.setFullName(jsonOb.getString("Name"));
                    user.setId(jsonOb.getInt("Id"));
                }

            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            dialog.hide();

            if(user != null){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("user", user);

                startActivity(intent);

            }else{
                Toast.makeText(LoginActivity.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
            }
        }


    }
}