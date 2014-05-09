package de.dcs.mrtodoapp.app.models;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.dcs.mrtodoapp.app.R;

public class ItemsAdapter extends ArrayAdapter<Item> {

    public ItemsAdapter(Context context, List<Item> items) {
        super(context, R.layout.item, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView content    = (TextView) convertView.findViewById(R.id.content);
        TextView category   = (TextView) convertView.findViewById(R.id.category);
        TextView deadline   = (TextView) convertView.findViewById(R.id.deadline);
        ImageView completed = (ImageView) convertView.findViewById(R.id.completed);

        content.setText(item.getContent());
        category.setText(item.getCategoryName());
        deadline.setText(item.getDeadlineText());

        if (item.getCompleted()) {
            Log.v("item is completed", item.getContent());
            completed.setImageResource(R.drawable.ic_action_good);
        } else {
            completed.setImageResource(R.drawable.ic_action_bad);
        }

        return convertView;
    }

}
