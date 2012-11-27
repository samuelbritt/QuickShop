package com.example.quickshop;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SimpleCursorAdapter;

@SuppressLint({ "NewApi", "NewApi" })
public class HomeScreen extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.quickshop.MESSAGE";
	private String[] catList;
	List<String> Cat = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
       getActionBar().setDisplayHomeAsUpEnabled(true);
        
       Intent intent = getIntent();
       String message = intent.getStringExtra(HomeScreen.EXTRA_MESSAGE);
       
        ListView listCategories = (ListView) findViewById(R.id.categoryList);
        
       DatabaseHandler db = new DatabaseHandler(this);
        //db.onCreate(null);
      
        // inserting stores in the store table 
     //  Log.d("Insert: ","Inserting ..");
      // db.addStore(new Store(4,"walmart","kgdjd"));
       
       // inserting categories
       
       /*
       Log.d("Reading: ", "Reading All cats");
       
       
       for(Category c : categories){
    	   	String log = "name: " + c.getCatName() + " Location: " +c.getCatLoc()+ " Anch: " +c.getAnchPt() + " Store: "+ c.getStoreID();
    	   	Log.d("Name: ", log);
       }
       */
       List<Category> categories = db.getAllCategories();
     
     for(Category c : categories){
    	 Cat.add(c.getCatName());
     }
       
    catList = Cat.toArray(new String[Cat.size()]);
    listCategories.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, catList));
    listCategories.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    
        for(int i = 0; i <catList.length ; i++){
    	if(catList[i].equalsIgnoreCase("false")){
    		listCategories.setItemChecked(i, false);
    	}else {
    		listCategories.setItemChecked(i, true);
    	}
    }
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home_screen, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void btnAdd (View view){
    	Intent intent = new Intent(this , HomeScreen.class);
    	EditText editText = (EditText) findViewById(R.id.editText1);
    	String message = editText.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);
    	
    	
    }

}
