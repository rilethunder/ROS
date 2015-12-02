package com.cs123grpE.restaurantorderingsystem;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;
import com.parse.*;

public class Customer extends Activity {

	
    String [] menu = {"Fish", "Meat", "Soup", "Fruit", "Vegetables"};
    private ExpandableAdapter epa;
    private ExpandableListView exp;
    List<String> listDataHeader;
    List<String> listDataHeaderId;
    HashMap<String, List<String>> listDataChild;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer);
		
		generateMenu("", true);
		
		ListView lv = (ListView)findViewById(R.id.listview1);
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));

		
		exp.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		// Listview Group expanded listener
		exp.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						listDataHeader.get(groupPosition) + " Expanded",
						Toast.LENGTH_SHORT).show();
			}
		});

		// Listview Group collasped listener
		exp.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						listDataHeader.get(groupPosition) + " Collapsed",
						Toast.LENGTH_SHORT).show();

			}
		});

		// Listview on child click listener
		exp.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				
				String name = listDataChild.get(listDataHeader.get(groupPosition))
				.get(childPosition);
				ParseObject x = null;
				ParseObject order = null;
				double price = 0;
				
				try {
					x = (new ParseQuery("Menu_Item")).whereMatches("item_name", name).getFirst();
					price  = x.getDouble("item_price");
					
					order = new ParseObject("Order");
					order.put("item_id", x.getObjectId());
					order.put("quantity", 1);
					order.saveInBackground();
					
					Intent i = new Intent(getApplicationContext(), ViewProfile.class);
					i.putExtra("menu_name", name);
					startActivity(i);
					
				}catch(Exception e) {}
				
				
				Toast.makeText(
					getApplicationContext(),
					listDataHeader.get(groupPosition) + " : " + 
					listDataChild.get(listDataHeader.get(groupPosition))
					.get(childPosition) + " price: " + price, Toast.LENGTH_SHORT
				).show();
				return false;
			}
		});
		
		//expList.setOnChildClickListener(this); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.customer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onBackPressed() {
		// do nothing
	}
	
	public void filterOff (View v) {
		// toggle to filter items without specified item from item list
		
		EditText searchString = (EditText) findViewById (R.id.txtSearch);
		String criteria = searchString.getText().toString().trim();
		
		generateMenu(criteria, false);
	}
	
	public void filterOn (View v) {
		// toggle to filter items with specified item from item list
		EditText searchString = (EditText) findViewById (R.id.txtSearch);
		String criteria = searchString.getText().toString().trim();
		generateMenu(criteria, true);
	}
	
	public void search (View v) {
		// search for query in menu name or description
		
		EditText searchString = (EditText) findViewById (R.id.txtSearch);
		String criteria = searchString.getText().toString().trim();
		
	}
	
	public void viewCart (View v) {
		// go to cart screen
		Intent i = new Intent (this, Cart.class);
		startActivity (i);
	}
	
	public void generateMenu(String with, boolean in) {
		
		listDataHeader = new ArrayList<String>();
		listDataHeaderId = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		getHeaders(with, in);
		getChildren();
		
		exp = (ExpandableListView)findViewById(R.id.list);
		
		//prepareListData();
		
		epa = new ExpandableAdapter(this, listDataHeader, listDataChild);
		
		exp.setAdapter(epa);
	}
	
	public void getHeaders(String with, boolean in) {
		ParseQuery<ParseObject> pq = new ParseQuery("Ingredient");
		List<ParseObject> l = null; 
		try{
			l = pq.find();
		} catch(Exception e) {}
		
		for(ParseObject po: l) {
			String name = (String) po.get("ingredient_name");
			if(!with.equals("")) {
				if(in && name.equals("with")) {
					listDataHeader.add(name);
					listDataHeaderId.add(po.getObjectId());
					listDataChild.put(name, new ArrayList<String>());
				}
				else {
					if(name.equals("with")) continue;
					listDataHeader.add(name);
					listDataHeaderId.add(po.getObjectId());
					listDataChild.put(name, new ArrayList<String>());
				}
			}
			else {
				listDataHeader.add(name);
				listDataHeaderId.add(po.getObjectId());
				listDataChild.put(name, new ArrayList<String>());
			}
		}
	}
	
	public void getChildren() {
		ParseQuery<ParseObject> pq = new ParseQuery<ParseObject>("Menu_Item");
		List<String> tempFood = new ArrayList<String>();
		List<ParseObject> list = null;
		try {
			list = pq.find();
		} catch(Exception e) {}
		
		for(ParseObject po: list) {
			String name = (String)po.get("item_name");
			String ingr = (String)po.get("ingredient_name");
			if(listDataHeader.contains(ingr))
				listDataChild.get(ingr).add(name);
		}
	}
	
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		
		// Adding child data
		listDataHeader.add("MEAT");
		listDataHeader.add("SOUP");
		listDataHeader.add("FISH");

		// Adding child data
		List<String> top250 = new ArrayList<String>();
		top250.add("CRISPY PATA");
		top250.add("ADUBA");
		top250.add("PATAATIM");
		top250.add("SINIGANG");

		List<String> nowShowing = new ArrayList<String>();
		nowShowing.add("CHICKEN NOODLE SOUP");
		nowShowing.add("SINIGANG SA MISO");
		nowShowing.add("SPINACH");
		nowShowing.add("BIRD'S NEST");

		List<String> comingSoon = new ArrayList<String>();
		comingSoon.add("TALAKITOK");
		comingSoon.add("PAKSIW");
		comingSoon.add("ESCABECHE");

		listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
		listDataChild.put(listDataHeader.get(1), nowShowing);
		listDataChild.put(listDataHeader.get(2), comingSoon);
	}
}
