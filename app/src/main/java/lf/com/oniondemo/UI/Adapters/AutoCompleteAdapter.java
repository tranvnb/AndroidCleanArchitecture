package lf.com.oniondemo.UI.Adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Collection;

import lf.com.oniondemo.R;

/**
 * Created by tranvonb on 3/21/2017.
 */

public class AutoCompleteAdapter extends ArrayAdapter<String>
        implements Filterable {
    private ArrayList<String> originItems;
    private ArrayList<String> items;

    public AutoCompleteAdapter(Context context, int resourceId, int textViewResourceId) {
        super(context, resourceId, textViewResourceId);
        originItems = new ArrayList<String>();
        items = new ArrayList<String>();
    }

    @Override
    public int getCount() {
        if(items != null) {
            return items.size();
        }else{
            return 0;
        }
    }

    @Override
    public String getItem(int index) {
        return items.get(index);
    }

    @Override
    public void clear() {
        if(originItems != null){
            originItems.clear();
        }
        if(items != null){
            items.clear();
        }
    }

    @Override
    public void addAll(Collection<? extends String> collection) {
        if(originItems != null){
            originItems.addAll(collection);
        }
        if(items != null){
            items.addAll(collection);
            items.add(0, getContext().getString(R.string.all_brands));
        }
    }

    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                String temp;
                items.clear();
                int count = 0;
                if (constraint != null) {
                    if (originItems != null && originItems.size() > 0) {
                        for(int i=0; i<originItems.size(); i++)
                        {
                            temp = originItems.get(i).toUpperCase();

                            if(temp.startsWith(constraint.toString().toUpperCase()))
                            {
                                items.add(originItems.get(i));
                                count++;
                            }
                        }
                    }
                }
                items.add(0, getContext().getString(R.string.all_brands));
                filterResults.values = items;
                filterResults.count = count;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence contraint,
                                          FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return myFilter;
    }
}