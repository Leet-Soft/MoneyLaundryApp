package uni.fmi.masters.moneylaundryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        UserEntity user = db.login(usernameET.getText().toString(),
                passwordET.getText().toString());

        counter++;

        if(user != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Wrong Credentials....",
                    Toast.LENGTH_SHORT).show();
            if(counter > 2){
                finish();
            }
        }
    }

    public void register(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}