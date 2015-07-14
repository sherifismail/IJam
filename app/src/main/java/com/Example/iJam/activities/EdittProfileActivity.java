package com.Example.iJam.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Example.iJam.R;
import com.Example.iJam.network.NetworkManager;
import com.android.volley.toolbox.NetworkImageView;

public class EdittProfileActivity extends ActionBarActivity implements View.OnClickListener {

    final String lName = MainActivity.user.getLast_name();
    final String fName = MainActivity.user.getFirst_name();
    final String mail = MainActivity.user.getEmail();
    final String pass = MainActivity.user.getPassword();
    final String imgpro=MainActivity.user.getImgUrl();
    EditText firstname,lastname,email,oldpassword,newpassword,confpassword;
    NetworkImageView proimg;
    String editfname,editlname,editemail,editoldpass,editnewpass,editconfpass;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editt_profile);

        firstname=(EditText)findViewById(R.id.Editprofile_edittxt_fname);
        lastname=(EditText)findViewById(R.id.Editprofile_edittxt_lname);
        email=(EditText)findViewById(R.id.Editprofile_edittxt_email);
        oldpassword=(EditText)findViewById(R.id.Editprofile_edittxt_pass);
        newpassword=(EditText)findViewById(R.id.Editprofile_edittxt_newpass);
        confpassword=(EditText)findViewById(R.id.Editprofile_edittxt_confpass);

        submit=(Button)findViewById(R.id.Editprofile_btn_submit);
        proimg=(NetworkImageView)findViewById(R.id.editprofile_img_profile);
        firstname.setText(fName);
        lastname.setText(lName);
        email.setText(mail);
        proimg.setImageUrl(imgpro, NetworkManager.getInstance(this).getImageLoader());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.Editprofile_btn_submit):
                editfname=firstname.getText().toString().trim();
                editlname=lastname.getText().toString().trim();
                editemail=email.getText().toString().trim();
                editoldpass=oldpassword.getText().toString().trim();
                editnewpass=newpassword.getText().toString().trim();
                editconfpass=confpassword.getText().toString().trim();
                if((!editfname.isEmpty())||(!editlname.isEmpty())||(!editemail.isEmpty())) {
                    if (!editfname.equals(fName)) {
                        //update database
                    }
                    if (!editlname.equals(lName)) {
                        //update database
                    }
                    if (!editemail.equals(mail)) {
                        //update database with new email
                    }
                }

                if((!editnewpass.isEmpty())&& (editoldpass.isEmpty())){
                        Toast.makeText(getApplicationContext(), "Please enter old password", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(editoldpass.equals(pass)){
                            if(editnewpass.equals(editconfpass)){
                                //update database with new password
                            }
                            else
                                Toast.makeText(getApplicationContext(), "Confirm password doesn't match new password", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                    }

                break;

        }
    }
}
