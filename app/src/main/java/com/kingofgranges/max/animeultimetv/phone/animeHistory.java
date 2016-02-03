package com.kingofgranges.max.animeultimetv.phone;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.kingofgranges.max.animeultimetv.R;
import com.kingofgranges.max.animeultimetv.libs.History;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class animeHistory extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        refreshList();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Context context = this;
        switch (item.getItemId()){
            case R.id.action_history_delete:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                History hist = new History(context);
                                hist.deleteHistory();
                                refreshList();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                refreshList();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Voulez-vous vraiment supprimer tout l'historique ?").setPositiveButton("Oui", dialogClickListener)
                        .setNegativeButton("Non", dialogClickListener).show();
                break;

            case R.id.action_history_refresh:
                refreshList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshList(){
        History hist = new History(this);
        final String[][] histData = hist.getHistory();

        ListView itemList = (ListView) findViewById(R.id.listView);
        List<Map<String, String>> data = new ArrayList<>();
        for (String[] element : histData) {
            Map<String, String> anime = new HashMap<>(2);
            anime.put("title", element[1] + " " + element[0]);
            anime.put("date", element[3]);
            data.add(anime);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"title", "date"},
                new int[] {android.R.id.text1,
                        android.R.id.text2});
        itemList.setAdapter(adapter);

        final Context context = this;
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                History hist = new History(context);
                hist.addHistory(histData[position][0], histData[position][1], histData[position][2]);

                Intent stream = new Intent(context, animeStream.class);
                stream.putExtra("streamURL", histData[position][2]);
                startActivity(stream);
            }
        });
    }

}
