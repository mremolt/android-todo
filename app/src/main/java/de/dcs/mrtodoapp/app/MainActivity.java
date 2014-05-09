package de.dcs.mrtodoapp.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.activeandroid.query.Select;

import java.util.List;

import de.dcs.mrtodoapp.app.models.Item;
import de.dcs.mrtodoapp.app.models.ItemsAdapter;


public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Item> items = new Select().from(Item.class).orderBy("_id DESC").execute();
        ItemsAdapter adapter = new ItemsAdapter(this, items);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        Item item = (Item) getListAdapter().getItem(position);

        Intent intent = new Intent(this, ItemDetailActivity.class);
        intent.putExtra("itemId", item.getId());
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_new:
                showNewItemForm();
                return true;
            case R.id.action_new_category:
                showNewCategoryForm();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void showNewItemForm() {
        Intent intent = new Intent(this, NewItemActivity.class);
        startActivity(intent);
    }

    private void showNewCategoryForm() {
        Intent intent = new Intent(this, NewCategoryActivity.class);
        startActivity(intent);
    }
}
