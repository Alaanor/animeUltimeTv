package com.kingofgranges.max.animeultimetv.tv;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.kingofgranges.max.animeultimetv.R;
import com.kingofgranges.max.animeultimetv.libs.animeUltime;
import org.json.JSONException;
import java.io.IOException;

public class TvActivity extends AppCompatActivity {

    public final animeUltime au = new animeUltime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                try {
                    updateSearch(s);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        return true;
    }

    public void updateSearch(String search) throws IOException, JSONException {
        String[] values = au.getSearchResult(search);
        final ListView listView = (ListView) findViewById(R.id.searchCompletion);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
        final Context context = this;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[][] data = au.getPageInformation(position);
                if (data == null) {
                    Toast.makeText(getApplicationContext(), "Error during the process for fetching data about the anime :/", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent details = new Intent(context, animeDetailsTv.class);
                details.putExtra("synopsis", data[0][0]);
                details.putExtra("img", data[0][1]);
                details.putExtra("title", data[0][2]);
                details.putExtra("episode", data[1]);
                details.putExtra("link", data[2]);
                startActivity(details);
            }
        });
    }

}
