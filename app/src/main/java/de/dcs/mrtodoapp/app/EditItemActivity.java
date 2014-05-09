package de.dcs.mrtodoapp.app;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.activeandroid.query.Select;

import java.util.Date;

import de.dcs.mrtodoapp.app.models.Item;

public class EditItemActivity extends NewItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Item item = getItem();
        EditText content = (EditText) findViewById(R.id.editItemContent);
        Spinner categorySpinner = (Spinner) findViewById(R.id.editItemCategory);
        DatePicker deadline = (DatePicker) findViewById(R.id.editItemDeadline);
        EditText lat = (EditText) findViewById(R.id.editItemLat);
        EditText lng = (EditText) findViewById(R.id.editItemLng);

        content.setText(item.getContent());
        lat.setText(item.getLat().toString());
        lng.setText(item.getLng().toString());

        Date dl = item.getDeadline();
        deadline.updateDate(dl.getYear() + 1900, dl.getMonth(), dl.getDate());
    }

    @Override
    protected Item getItem() {
        long itemId = getIntent().getLongExtra("itemId", 0);
        return new Select().from(Item.class).where("_id = ?", itemId).executeSingle();
    }
}
