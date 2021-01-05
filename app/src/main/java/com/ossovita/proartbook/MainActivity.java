package com.ossovita.proartbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView li;
    @Override
    protected void onCreate(Bundle savedInstancecommState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        li = findViewById(R.id.listVie);
        ArrayList<String> artNameList = new ArrayList<>();
        ArrayList<Bitmap> artImageList = new ArrayList<Bitmap>();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,artNameList);
        li.setAdapter(adapter);

        String Url = "content://com.ossovita.proartbook.ArtContentProvier";
        Uri artUri = Uri.parse(Url);

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(artUri,null,null,null,"name");

        if(cursor!=null){
            while(cursor.moveToNext()){
                artNameList.add(cursor.getString(cursor.getColumnIndex(ArtContentProvider.NAME)));
                byte[] bytes = cursor.getBlob(cursor.getColumnIndex(ArtContentProvider.IMAGE));
                Bitmap image = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                artImageList.add(image);
                adapter.notifyDataSetChanged();

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_art,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add_art){
            startActivity(new Intent(MainActivity.this,MainActivity2.class));
        }

        return super.onOptionsItemSelected(item);
    }
}