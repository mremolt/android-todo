package de.dcs.mrtodoapp.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.javalid.core.AnnotationValidatorImpl;
import org.javalid.core.config.JvConfiguration;

import de.dcs.mrtodoapp.app.models.Category;


public class NewCategoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        final Activity current = this;
        final EditText name = (EditText) findViewById(R.id.editCategoryName);
        Button submit = (Button) findViewById(R.id.editCategorySubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category category = new Category(name.getText().toString());

                if (category.isValid()) {
                    category.save();

                    Intent intent = new Intent(current, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "Category saved!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), category.getErrorMessages().get(0), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_category, menu);
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
}
