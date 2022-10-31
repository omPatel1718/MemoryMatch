package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class TitleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }

/*
spinnerSelectedText = spinner.getSelectedItem().toString();
        // This will take the option they clicked on and ensure it is a number.
        // My options went from 3 to 0, so that is why I have it adjusted with 4-i
        // I also had an instruction statement as my first line in my string array
        // ADJUST THIS LOOP TO MATCH YOUR CODE!

        // Note the syntax here for how to access an index of a string array within
        // the java
        for (int i = 1; i < 4; i++) {
            if (spinnerSelectedText.equals(getResources().
                    getStringArray(R.array.memoryRating)[i])) {
                memoryRatingNum = 4-i;
                break;
            }
        }
 */

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

}