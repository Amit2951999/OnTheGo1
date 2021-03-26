package com.csgang.android.onthego;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MyProfileActivity extends AppCompatActivity {
    String name, mobileno, password, query;
    EditText etxtName, etxtMobileNo;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        etxtName = (EditText) findViewById(R.id.etxtName);
        etxtMobileNo = (EditText) findViewById(R.id.etxtMobileNo);
        query = "SELECT name FROM profile";
        etxtName.setText(DBClass.getSingleValue(query));
        query = "SELECT mobileno FROM profile";
        etxtMobileNo.setText(DBClass.getSingleValue(query));
    }

    public void btnSaveClick(View v)
    {
        name = etxtName.getText().toString();
        mobileno = etxtMobileNo.getText().toString();

        if (name.equals("")) {
            etxtName.setError("Please enter name");
            etxtName.requestFocus();
            return;
        }
        char []nameArray = name.toCharArray();
        boolean nameValid = true;
        for(int i = 0; i < nameArray.length; i++) {
            char c = nameArray[i];
            if((c <= 'a' && c >= 'z') || (c <= 'A' && c >= 'Z') || c == ' ')
                nameValid = false;
        }
        if (nameValid == false) {
            etxtName.setError("Please enter valid name");
            etxtName.requestFocus();
            return;
        }
        if (mobileno.length() != 10) {
            etxtMobileNo.setError("Please enter valid 10 digit mobile no");
            etxtMobileNo.requestFocus();
            return;
        }
        query = "DELETE FROM profile";
        DBClass.execNonQuery(query);
        query = "INSERT INTO profile(name, mobileno, password) VALUES('" + name + "', '" + mobileno + "', '" + password + "')";
        DBClass.execNonQuery(query);

        String query = "SELECT name FROM profile";
        String name = DBClass.getSingleValue(query);
        query = "SELECT mobileno FROM profile";
        String mobileno = DBClass.getSingleValue(query);
        HomeActivity.txtUser.setText("Welcome " + name.toUpperCase() + "(" + mobileno + ")");

        Toast.makeText(this, "profile saved successfully, now you can login", Toast.LENGTH_SHORT).show();
        finish();
    }
}
