package uni.fmi.masters.moneylaundryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class RegisterActivity extends AppCompatActivity {

    EditText usernameET;
    EditText passwordET;
    EditText repeatPasswordET;
    EditText nameET;
    EditText genderET;

    SQLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameET = findViewById(R.id.registerUsernameET);
        passwordET = findViewById(R.id.registerPasswordET);
        repeatPasswordET = findViewById(R.id.registerRepeatPassword);
        nameET = findViewById(R.id.registerNameET);
        genderET = findViewById(R.id.registerEmailET);

        db = new SQLiteHelper(this);
    }
    public void returnToLogin(View view){
        if(view.getId() == R.id.registerOkB){
            if(passwordET.getText().length() > 0
                && genderET.getText().length() > 0
                && usernameET.getText().length() > 0
                && passwordET.getText().toString()
                    .equals(repeatPasswordET.getText().toString())) {

                UserEntity user = new UserEntity();

                user.setGender(genderET.getText().toString());
                user.setFullName(nameET.getText().toString());
                user.setPassword(passwordET.getText().toString());
                user.setUsername(usernameET.getText().toString());

                new RegisterAsyncTask(user).execute();

//                if(!db.register(user)){
//                    Toast.makeText(this, "Username exists", Toast.LENGTH_SHORT).show();
//                    return;
//                }
            }else{
                Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                return;
            }
        }

//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);

    }

    private class RegisterAsyncTask extends AsyncTask<Void, Void, Void>{

        UserEntity user;
        boolean successfull;
        ProgressDialog dialog;


        RegisterAsyncTask(UserEntity user){
            this.user = user;
            dialog = new ProgressDialog(RegisterActivity.this);
        }

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Keep calm and wait for the registraion...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String urlString = String.format("http://10.0.0.22:8989/RegisterUser?username=%s&password=%s&name=%s&gender=%s",
                    user.getUsername(), user.getPassword(), user.getFullName(), user.getGender());

            HttpURLConnection urlConnection = null;

            try{
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection)url.openConnection();

                InputStream stream =
                        new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(stream));

                String line = reader.readLine();
                if(line != null){
                    if(line.equals("true")){
                        successfull = true;
                    }
                }

            }catch (IOException e) {
                throw new RuntimeException(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            dialog.hide();

            if(successfull){
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);

            }else{
                Toast.makeText(RegisterActivity.this, "Something went wrong"
                        , Toast.LENGTH_SHORT).show();
            }
        }
    }
}