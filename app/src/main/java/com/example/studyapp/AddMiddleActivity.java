package com.example.studyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class AddMiddleActivity extends AppCompatActivity {
    private Button button;
    private EditText content;
    private EditText hint;
    private Switch s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_middle);
        button = findViewById(R.id.button2);
        content = findViewById(R.id.editTextContent);
        hint = findViewById(R.id.editTextHint);
        s = findViewById(R.id.switch1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!content.getText().toString().equals("")&&!hint.getText().toString().equals("")){
                    Intent intent = new Intent();
                    intent.putExtra("content",content.getText().toString());
                    intent.putExtra("hint",hint.getText().toString());
                    intent.putExtra("two",s.isChecked());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
}
