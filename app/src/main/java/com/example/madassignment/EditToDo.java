package com.example.madassignment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditToDo extends AppCompatActivity {
EditText editTitle,editDec;
Button btnEdit;
Intent intentEdit;
private long updateDate;
Context context = this;
DbHnadler dbhandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_to_do);


        editTitle = findViewById(R.id.editToDoTextTitle);
        editDec = findViewById((R.id.editToDoTextDescription));
        btnEdit = findViewById(R.id.buttonEdit);
        intentEdit = getIntent();

        ToDo todo;
        DbHnadler db = new DbHnadler(context);
        final String id = intentEdit.getStringExtra("id");
        todo = db.getSingleRow(Integer.parseInt(id));
        editTitle.setText(todo.getTitle());
        editDec.setText(todo.getDescription());
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dec = editDec.getText().toString();
                String title = editTitle.getText().toString();
                int updateId  = Integer.parseInt(id);
                updateDate = System.currentTimeMillis();
                ToDo todoEdit = new ToDo(updateId,title,dec,updateDate,0);
                dbhandler = new DbHnadler(context);
                int state = dbhandler.updateToDos(todoEdit);
                if (state!=0){
                    // Inside your activity or fragment
                    Toast.makeText(context, "sucessfully update the record", Toast.LENGTH_SHORT).show();

                }
                startActivity(new Intent(context, MainActivity.class));
            }
        });

    }
}