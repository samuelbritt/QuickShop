package com.example.quickshop;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import com.example.quickshop.R;

public class TestExpand extends Activity implements OnItemSelectedListener {
	private String[] catList;
	List<String> Cat = new ArrayList<String>();
	public final static String EXTRA_MESSAGE = "com.example.quickshop.MESSAGE";
	String message;
	
	private ExpandListAdapter ExpAdapter;
	private ArrayList<ExpandListGroup> ExpListItems;
	private ArrayList<ExpandListGroup> ExpListItems_new;
	private ExpandableListView ExpandList;
	Spinner spinner;
	private String itemChild;
    private String dropDownCat;
    private String flag = "false";
  
    ArrayList<ExpandListChild> list2 = new ArrayList<ExpandListChild>();
    
    private ArrayList<ExpandListGroup> ExpListChild;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_expand);
        String noChild = "";
        String noDrop = "";
       
        ExpandList = (ExpandableListView) findViewById(R.id.ExpList);
        ExpListItems = SetStandardGroups(noChild, noDrop, flag);
        ExpAdapter = new ExpandListAdapter(TestExpand.this, ExpListItems);
        ExpandList.setAdapter(ExpAdapter);
        
        Intent intent = getIntent();
        message = intent.getStringExtra(TestExpand.EXTRA_MESSAGE);
      //  itemChild = message;
        // Loading spinner data
        spinner = (Spinner) findViewById(R.id.spinner);
        
        spinner.setOnItemSelectedListener(this);
        
        loadSpinnerData();
        
    }
 
	
	@SuppressWarnings("null")
	private void loadSpinnerData() {
	
		DatabaseHandler dbspin = new DatabaseHandler(this);
		List<Category> catData = dbspin.getAllCategories();
		
		ArrayList<String> catStringData = new ArrayList<String>();
		
		for(Category c : catData){
			String var = c.getCatName();
			catStringData.add(var);
			//Log.d("Name",var);
		}
		
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, catStringData);
	
	   // drop down loyout style - listview with radiobuttons
		dataAdapter
        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching dataAdapter to Spinner
		spinner.setAdapter(dataAdapter);
		
	
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_test_expand, menu);
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
   
    /*
    public ArrayList<ExpandListGroup> SetStandardGroups() {
    	
			
	    	 DatabaseHandler db = new DatabaseHandler(this);
	    	 List<Category> categories = db.getAllCategories();
	         
	         for(Category c : categories){
	        	 
	        	 ExpandListGroup gru1 = new ExpandListGroup();
	            	 String var = c.getCatName();
	            	 gru1.setName(var);
	            	 
	            	 if(var.equals(dropDownCat) && flag.equals("true")){
	            	//if(var.equals("Wine")){	
	            	 Toast.makeText(getApplicationContext(), flag, Toast.LENGTH_LONG).show();
	            		 ExpandListChild child = new ExpandListChild();
		            	 child.setName("itemChildxxx");
		            	 list2.add(child);
		            	 gru1.setItems(list2);
	            	 }
	            	 
	            	 list.add(gru1);
	            	 
	        	 }
	        	 
	
		return list;
    	 
    }
    
    */
    public ArrayList<ExpandListGroup> SetStandardGroups(String ch , String drop, String flag) {
    	
    	ArrayList<ExpandListGroup> list = new ArrayList<ExpandListGroup>();
    	  
    	DatabaseHandler db = new DatabaseHandler(this);
    	List<Category> categories = db.getAllCategories();
    	
    	for(Category c: categories){
    		ExpandListGroup gru1 = new ExpandListGroup();
    		String var = c.getCatName();
    		gru1.setName(var);
    		
    		if(var.equals(drop)){
                ArrayList<ExpandListChild> list2 = new ArrayList<ExpandListChild>();
        		ExpandListChild child = new ExpandListChild();
        		
        		child.setName(ch);
        		list2.add(child);
        		gru1.setItems(list2);
    		}
    		list.add(gru1);
    	}
    
    	return list;
    }
    // Make a generic method SetStandardGroups which takes into acc the dropdowncat and itemchild and ignores it when its called 
    // from the onCreate Method. 
   
    /*
    public ArrayList<ExpandListGroup> SetStandardGroups() {
    
    	ArrayList<ExpandListGroup> list = new ArrayList<ExpandListGroup>();
    	ArrayList<ExpandListChild> list2 = new ArrayList<ExpandListChild>();
        ExpandListGroup gru1 = new ExpandListGroup();
        gru1.setName("Comedy");
        ExpandListChild ch1_1 = new ExpandListChild();
        ch1_1.setName("A movie");
        ch1_1.setTag(null);
        list2.add(ch1_1);
        ExpandListChild ch1_2 = new ExpandListChild();
        ch1_2.setName("An other movie");
        ch1_2.setTag(null);
        list2.add(ch1_2);
        ExpandListChild ch1_3 = new ExpandListChild();
        ch1_3.setName("And an other movie");
        ch1_3.setTag(null);
        list2.add(ch1_3);
        gru1.setItems(list2);
        list2 = new ArrayList<ExpandListChild>();
        
        ExpandListGroup gru2 = new ExpandListGroup();
        gru2.setName("Action");
        ExpandListChild ch2_1 = new ExpandListChild();
        ch2_1.setName("A movie");
        ch2_1.setTag(null);
        list2.add(ch2_1);
        ExpandListChild ch2_2 = new ExpandListChild();
        ch2_2.setName("An other movie");
        ch2_2.setTag(null);
        list2.add(ch2_2);
        ExpandListChild ch2_3 = new ExpandListChild();
        ch2_3.setName("And an other movie");
        ch2_3.setTag(null);
        list2.add(ch2_3);
        gru2.setItems(list2);
        list.add(gru1);
        list.add(gru2);
        
        return list;
        }
*/
    public void button1 (View view){
    	
    	EditText editText = (EditText) findViewById(R.id.editText2);
    	Editable temp = editText.getText();
    	itemChild = temp.toString();
    	Toast.makeText(getApplicationContext(), itemChild, Toast.LENGTH_LONG).show();
    	flag = "true";
    	// ExpListItems = SetStandardGroups(itemChild , dropDownCat) ------------ add these 2 parameters in the SetStandardGroups 
    	
    	
    	ExpListItems = SetStandardGroups(itemChild , dropDownCat, flag);
    	ExpAdapter = new ExpandListAdapter(TestExpand.this, ExpListItems);
        ExpandList.setAdapter(ExpAdapter);
    	//addChild();
    	
    	//Intent intent = new Intent(this , TestExpand.class);
    	//EditText editText = (EditText) findViewById(R.id.editText2);
    	//String message = editText.getText().toString();
    	//intent.putExtra(EXTRA_MESSAGE, message);
    	//startActivity(intent);
    	
    	
    }


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		
		dropDownCat = parent.getItemAtPosition(position).toString();
		 Toast.makeText(parent.getContext(), "You selected: " + dropDownCat,
	                Toast.LENGTH_LONG).show();
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
    
    
}

/* else { // the case when child and dropdown has been sent
        		 ExpandListGroup grup1 = new ExpandListGroup();
        		 String var = c.getCatName();
        		 grup1.setName(var);
        		 if(dropDownCat.equals(var)){
        			 ArrayList<ExpandListChild> listChild = new ArrayList<ExpandListChild>();
        			 ExpandListChild ch = new ExpandListChild();
        			 ch.setName(itemChild);
        			 listChild.add(ch);
        			 grup1.setItems(listChild);
        		 }
 * 
 */
