package com.example.madassignment;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class addTodo extends AppCompatActivity {

    private EditText title, desc;
    private Button add;
    private DbHnadler dbHandler;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_todo);





            title = findViewById(R.id.editTextTitle);
            desc = findViewById(R.id.editTextDescription);
            add = findViewById(R.id.buttonAdd);
            context = this;

            dbHandler = new DbHnadler(context);


            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userTitle = title.getText().toString();
                    String userDesc = desc.getText().toString();
                    long started = System.currentTimeMillis();
                    if(userTitle.isEmpty() || userDesc.isEmpty()){
                        Toast.makeText(context,"Please add a Todo",Toast.LENGTH_SHORT).show();
                    }else{
                        ToDo toDo = new ToDo(userTitle,userDesc,started,0);
                        dbHandler.addToDo(toDo);
                        Toast.makeText(context, "Successfully added the ToDo", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context,MainActivity.class));
                    }


                }
            });
        }


    }
