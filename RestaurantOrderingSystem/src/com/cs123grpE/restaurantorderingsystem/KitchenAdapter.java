package com.cs123grpE.restaurantorderingsystem;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class KitchenAdapter extends BaseAdapter implements OnClickListener{
	private Activity activity;
	private ArrayList data;
	 private static LayoutInflater inflater=null;
     public Resources res;
     OrderModel tempValues=null;
     int i=0;
     
     public KitchenAdapter(Activity a, ArrayList d,Resources resLocal) {
         
         /********** Take passed values **********/
          activity = a;
          data=d;
          res = resLocal;
       
          /***********  Layout inflator to call external xml layout () ***********/
           inflater = ( LayoutInflater )activity.
                                       getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       
  }
	@Override
	public int getCount() {
		if(data.size()<=0)
            return 1;
        return data.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
        ViewHolder holder;
         
        if(convertView==null){
             
            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.menu_item, null);
             
            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.text = (TextView) vi.findViewById(R.id.foodName);
            holder.text1=(TextView)vi.findViewById(R.id.tableName);
             
           /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else 
            holder=(ViewHolder)vi.getTag();
         
        if(data.size()<=0)
        {
            holder.text.setText("No Data");
             
        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = ( OrderModel ) data.get( position );
             
            /************  Set Model values in Holder elements ***********/

             holder.text.setText( tempValues.getFoodName() );
             holder.text1.setText( tempValues.getTableNumber() );
              
             /******** Set Item Click Listner for LayoutInflater for each row *******/

             vi.setOnClickListener((android.view.View.OnClickListener) new OnItemClickListener( position ));
        }
        return vi;
	}
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		
	}
	
	public void onClick(View v) {
		
	}
	public static class ViewHolder{
        
        public TextView text;
        public TextView text1;
 
    }
	 private class OnItemClickListener  implements OnClickListener{           
         private int mPosition;
          
         OnItemClickListener(int position){
              mPosition = position;
         }
          
         
         public void onClick(View v) {

    
           Kitchen sct = (Kitchen)activity;

          /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/

             sct.onItemClick(mPosition);
         }

		
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}               
     }   
     
     
}
