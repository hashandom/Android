package com.example.madassignment;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Button add;
    private ListView listView;
    private TextView count;
    Context context;
    private DbHnadler dbHandler;
    private List<ToDo> toDos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        context = this;

            dbHandler = new DbHnadler(context);
            add = findViewById(R.id.add);
            listView = findViewById(R.id.todolist);
            count = findViewById(R.id.todocount);
            toDos = new ArrayList<>();

            toDos = dbHandler.getAllToDos();

            //display values in the screen
            ToDoAdapter adapter = new ToDoAdapter(context,R.layout.single_todo,toDos);
            listView.setAdapter(adapter);

            //get todo counts from the table
            int countTodo = dbHandler.countToDo();
            count.setText("You have "+countTodo+" todos");

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context,addTodo.class));
                }
            });

                    //show the alert dialog box when click
                  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                      @Override
                      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                          ToDo toDo = toDos.get(position);
                          AlertDialog.Builder builder = new AlertDialog.Builder(context);
                          builder.setTitle(toDo.getTitle());
                          builder.setMessage(toDo.getDescription());

                          builder.setPositiveButton("Finished", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  toDo.setFinished(System.currentTimeMillis());
                                  dbHandler.updateToDos(toDo);
                                        startActivity(new Intent(context,MainActivity.class));
                              }
                          });

                          builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  AlertDialog.Builder deleteConfirmBuilder = new AlertDialog.Builder(context);
                                  deleteConfirmBuilder.setTitle("Delete Todo");
                                  deleteConfirmBuilder.setMessage("Are you sure want to delete this todo?");
                                  deleteConfirmBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialog, int which) {
                                          // Delete the todo item
                                          dbHandler.deleteTodo(toDo.getId());
                                          // Assuming todoList is your list/array containing todo items
                                          Toast.makeText(MainActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                          startActivity(new Intent(context,MainActivity.class));
                                          // Optionally, you can refresh your UI or perform any other actions after deletion.
                                      }
                                  });
                                  deleteConfirmBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialog, int which) {
                                          // Dismiss the confirmation dialog
                                          dialog.dismiss();
                                      }
                                  });
                                  AlertDialog deleteConfirmDialog = deleteConfirmBuilder.create();
                                  deleteConfirmDialog.show();
                              }
                          });


                          builder.setNeutralButton("update", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  Intent intent = new Intent(context,EditToDo.class);
                                  intent.putExtra("id", String.valueOf(toDo.getId()));
                                  startActivity(intent);

                              }
                          });
                          builder.show();
                      }
                  });
        }
}