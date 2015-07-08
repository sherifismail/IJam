package com.Example.iJam;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class activitySingIn extends AppCompatActivity {

    EditText et_user_name;
    EditText et_password;
    Button signup;
    Button signin;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        et_user_name = (EditText) findViewById(R.id.editText7);
        et_password = (EditText) findViewById(R.id.editText8);
        signin = (Button)findViewById(R.id.button);
        signup = (Button)findViewById(R.id.button2);

        TextView mTextView=(TextView)findViewById(R.id.txtview_forget);
        mTextView.setPaintFlags(mTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mTextView.setText("Forget Password");
        mTextView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
Intent intent=new Intent(activitySingIn.this,activitySignUp.class);
                startActivity(intent);
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = et_user_name.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                JSONObject login_info = new JSONObject();
                try {
                    login_info.put("user_name", user_name);
                    login_info.put("password", password);

                    LogInTask logInTask = new LogInTask(getApplicationContext());
                    logInTask.execute(login_info);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activitySingIn.this, activitySignUp.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
