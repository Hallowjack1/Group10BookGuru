package com.demo.group10bookguru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddBook extends AppCompatActivity {
    EditText Title, Author, Publisher, Date;
    Button Save;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        Title = findViewById(R.id.etTitle);
        Author = findViewById(R.id.etAuthor);
        Publisher = findViewById(R.id.etPublisher);
        Date = findViewById(R.id.etDate);
        Save = findViewById(R.id.btnSave);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title, author, publisher, dateofpublication;
                title = String.valueOf(Title.getText());
                author = String.valueOf(Author.getText());
                publisher = String.valueOf(Publisher.getText());
                dateofpublication = String.valueOf(Date.getText());

                if (!title.equals("") && !author.equals("") && !publisher.equals("") && !dateofpublication.equals("")) {
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "title";
                            field[1] = "author";
                            field[2] = "publisher";
                            field[3] = "dateofpublication";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = title;
                            data[1] = author;
                            data[2] = publisher;
                            data[3] = dateofpublication;
                            PutData putData = new PutData("http://192.168.254.103/BookGuru/insert.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("Book Saved")){
                                        Toast.makeText(AddBook.this, result, Toast.LENGTH_SHORT).show();
                                        intent = new Intent(AddBook.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(AddBook.this, result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}