package de.dcs.mrtodoapp.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.query.Select;

import org.javalid.core.AnnotationValidatorImpl;
import org.javalid.core.config.JvConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.dcs.mrtodoapp.app.models.Category;
import de.dcs.mrtodoapp.app.models.Item;


public class NewItemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        final EditText content = (EditText) findViewById(R.id.editItemContent);
        final Spinner categorySpinner = (Spinner) findViewById(R.id.editItemCategory);
        final Button submit = (Button) findViewById(R.id.editItemSubmit);
        final DatePicker deadline = (DatePicker) findViewById(R.id.editItemDeadline);
        final EditText lat = (EditText) findViewById(R.id.editItemLat);
        final EditText lng = (EditText) findViewById(R.id.editItemLng);
        final Activity current = this;

        List<Category> categories = new Select().from(Category.class).orderBy("name").execute();
        List<String> categoryNames = new ArrayList<String>();
        Category cat;
        final List<Long> categoryIds = new ArrayList<Long>();
        final Item item = getItem();
        int currentSpinnerSelection = -1;

        for (int i = 0; i < categories.size(); i++) {
            cat = categories.get(i);
            categoryNames.add(cat.getName());
            categoryIds.add(cat.getId());

            if (cat.getId() == item.getCategoryId()) {
                currentSpinnerSelection = i;
            }
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                categoryNames
        );

        categorySpinner.setAdapter(adapter);
        categorySpinner.setSelection(currentSpinnerSelection);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Category category = new Select().from(Category.class).where("_id = ?", categoryIds.get(position)).executeSingle();
                item.setCategory(category);
                Log.v("cat", "cat " + category.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date selectedDate = new Date(deadline.getYear() - 1900, deadline.getMonth(), deadline.getDayOfMonth());

                item.setContent(content.getText().toString());
                item.setDeadline(selectedDate);

                String latTxt = lat.getText().toString();
                String lngTxt = lng.getText().toString();

                if (latTxt.length() > 0) {
                    item.setLat(Double.valueOf(latTxt));
                }
                if (lngTxt.length() > 0) {
                    item.setLng(Double.valueOf(lngTxt));
                }

                if (item.isValid()) {
                    item.save();

                    Intent intent = new Intent(current, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "Item created!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), item.getErrorMessages().get(0), Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    protected Item getItem() {
        return new Item();
    }

}
