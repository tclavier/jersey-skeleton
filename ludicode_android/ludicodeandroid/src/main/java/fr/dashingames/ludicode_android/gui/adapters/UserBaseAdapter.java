package fr.dashingames.ludicode_android.gui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import fr.dashingames.ludicode_android.R;
import fr.dashingames.ludicode_android.beans.User;

/**
 * Created by Nico on 25/03/2016.
 */
//ajout perso
public class UserBaseAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {

    private static ArrayList<User> searchArrayList;


    private LayoutInflater mInflater;

    CheckBox[] checkBoxArray;
    private boolean[] checked;

    public UserBaseAdapter(Context context, ArrayList<User> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
        checked = new boolean[searchArrayList.size()];
        for(int i=0; i<checked.length ; i++){
            checked[i]=false;
        }
        checkBoxArray = new CheckBox[checked.length];
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_user_view, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.textViewCustom);

            checkBoxArray[position] = (CheckBox) convertView.findViewById(R.id.checkBox1Custom);
            checkBoxArray[position].setChecked(checked[position]);
            checkBoxArray[position].setOnCheckedChangeListener(this);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(searchArrayList.get(position).getName());

        return convertView;
    }

    public void checkAll(boolean areChecked){
        for(int i=0; i<checked.length; i++){
            checked[i]=areChecked;
            if(checkBoxArray[i] != null)
                checkBoxArray[i].setChecked(areChecked);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        for(int i=0; i<checked.length; i++){
            if(buttonView == checkBoxArray[i])
                checked[i]=isChecked;
        }
    }


    static class ViewHolder {
        TextView txtName;
        TextView CheckBox1;
        TextView CheckBox2;
        TextView CheckBox3;
        TextView CheckBox4;
        TextView CheckBox5;
        TextView intDirectionFleche;
    }
}
