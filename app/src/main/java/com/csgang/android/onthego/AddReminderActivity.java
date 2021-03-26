package com.csgang.android.onthego;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.places.ui.PlacePicker;

public class AddReminderActivity extends AppCompatActivity {
    public static String reminderid = "0";

    private final static int PLACE_PICKER_REQUEST = 999;
    TextView txtLocation;
    EditText etxtRange, etxtMessage;
    double latitude = 0, longitude = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        txtLocation = (TextView)findViewById(R.id.txtLocation);
        etxtRange = (EditText) findViewById(R.id.etxtRange);
        etxtMessage = (EditText)findViewById(R.id.etxtMessage);

    }

    public void btnSearchLocationClick(View v)
    {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        }
        catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public void btnSaveReminderClick(View view)
    {
        String location = txtLocation.getText().toString();
        int range = Integer.parseInt("0" + etxtRange.getText().toString());
        String message = etxtMessage.getText().toString();
        if(location.equals("select location"))
        {
            Toast.makeText(this, "select location first", Toast.LENGTH_SHORT).show();
            return;
        }
        if(range == 0)
        {
            Toast.makeText(this, "enter range more than zero", Toast.LENGTH_SHORT).show();
            return;
        }
        if(message.equals(""))
        {
            Toast.makeText(this, "enter message", Toast.LENGTH_SHORT).show();
            return;
        }
        finish();
    }
}
