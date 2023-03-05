package uni.fmi.masters.moneylaundryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import uni.fmi.masters.moneylaundryapp.entity.UserEntity;
import uni.fmi.masters.moneylaundryapp.helper.SQLiteHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameET;
    EditText passwordET;
    EditText repeatPasswordET;
    EditText nameET;
    EditText emailET;

    SQLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameET = findViewById(R.id.registerUsernameET);
        passwordET = findViewById(R.id.registerPasswordET);
        repeatPasswordET = findViewById(R.id.registerRepeatPassword);
        nameET = findViewById(R.id.registerNameET);
        emailET = findViewById(R.id.registerEmailET);

        db = new SQLiteHelper(this);
    }
    public void returnToLogin(View view){
        if(view.getId() == R.id.registerOkB){
            if(passwordET.getText().length() > 0
                && emailET.getText().length() > 0
                && usernameET.getText().length() > 0
                && passwordET.getText().toString()
                    .equals(repeatPasswordET.getText().toString())) {

                UserEntity user = new UserEntity();

                user.setEmail(emailET.getText().toString());
                user.setFullName(nameET.getText().toString());
                user.setPassword(passwordET.getText().toString());
                user.setUsername(usernameET.getText().toString());

                if(!db.register(user)){
                    Toast.makeText(this, "Username exists", Toast.LENGTH_SHORT).show();
                    return;
                }
            }else{
                Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }
}