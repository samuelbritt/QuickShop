package com.example.quickshop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;



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
  
    //ArrayList<ExpandListChild> list2 = new ArrayList<ExpandListChild>();
    
    private ArrayList<ExpandListGroup> ExpListChild;
    //Hashtable itemCatHash = new Hashtable();
    
    HashMap itemCatMap = new HashMap();
    ArrayList itemCatList = new ArrayList();
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_expand);
        String noChild = "";
        String noDrop = "";
      // DatabaseHandler db = new DatabaseHandler(this);
       
       //inserting stores in the store table 
      // Log.d("Insert: ","Inserting ..");
       //db.addItemCatNew(new ItemCatNew("Bread2", "Breads"));
        
     //  List<Store> stores = (List<Store>) db.getStore(1);
       
       /*
       db.deleteItems();
        Log.d("Reading: ", "Reading..");
        //ItemCatNew itc =  (ItemCatNew) db.getItemsNew("Breads");
        List<ItemCatNew> itcList = (List<ItemCatNew>) db.getItemsNewList("Wine Coolers");
        
        for(ItemCatNew itc : itcList){

            String log = "Cat name: " + itc.getCatName() +  " itemName: " + itc.getItemName();
             	Log.d("Name: ", log);
             
        }
       
       */
        /*
        DatabaseHandler db = new DatabaseHandler(this);
    	db.onCreate(null);
    	Log.d("Insert:", "Inserting..");
    	db.addItemCatNew(new ItemCatNew("Kunal", "Malhotra"));
    	
    	Log.d("Reading", "Reading cats");
    	List<ItemCatNew> items = (List<ItemCatNew>) db.getItemsNew("Malhotra");
    	
    	for(ItemCatNew ic : items){
    		String log = "ItemName " + ic.getItemName() + ", ItemCat " + ic.getCatName();
    		Log.d("Name: ", log);
    	}
    	*/
        
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
    	
    	// Add function for getItemCatNew
    	
    	for(Category c: categories){
    		ExpandListGroup gru1 = new ExpandListGroup();
    		String var = c.getCatName();
    		gru1.setName(var);
    		
    		if(flag.equals("true")){
    			//if(db.getItemsNew(var).getItemName().equals(null)){
    				
    		//	} 
    			//else {
    				List<ItemCatNew> items = (List<ItemCatNew>) db.getItemsNewList(var);
    	    		//if(var.equals(drop)){
    	            ArrayList<ExpandListChild> list2 = new ArrayList<ExpandListChild>();
    	        	for(ItemCatNew itc : items){
    	        		ExpandListChild child = new ExpandListChild();
    	        		child.setName(itc.getItemName());
    	        		list2.add(child);
    	           	}
    	            	
    	        	gru1.setItems(list2);
    	    		//}
    			//} // else end
    	
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
    	
    // Add Insert statements
    	DatabaseHandler db = new DatabaseHandler(this);
    	db.addItemCatNew(new ItemCatNew(itemChild, dropDownCat));
    	// ExpListItems = SetStandardGroups(itemChild , dropDownCat) ------------ add these 2 parameters in the SetStandardGroups 
    	
    	//itemCatList.add("good");
    	//itemCatList.add("bad");
    	//itemCatList.add(itemChild);
    	
    	//itemCatMap.put(dropDownCat, itemCatList);
    	//ArrayList l1 = (ArrayList) itemCatMap.get(dropDownCat);
    	//Iterator iter = l1.iterator();
    	//ArrayList L = (ArrayList) itemCatMap.get(dropDownCat);
    	//Iterator it = L.iterator();
    	
    
    	//while(it.hasNext()){
    		//Toast.makeText(getApplicationContext(),"The value is" + it.next(), Toast.LENGTH_LONG).show();
    	//}
    	
    	
    	
    	ExpListItems = SetStandardGroups(itemChild , dropDownCat, flag);
    	ExpAdapter = new ExpandListAdapter(TestExpand.this, ExpListItems);
        ExpandList.setAdapter(ExpAdapter);
    	
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
