package com.kingofgranges.max.animeultimetv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class animeFragmentEpisode extends Fragment {

    private String[] episodes;
    private String[] link;

    public void setEpisode(String[] episodes, String[] link) {
        this.episodes = episodes;
        this.link = link;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_anime_episodes, container, false);

        final ListView listEpisode = (ListView) view.findViewById(R.id.listEpisode);
        final Context copyOfThis = getContext();
        listEpisode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                animeUltime au = new animeUltime();
                String videoLink = au.getVideoLink(MainActivity.mainUrlv5 + link[position]);
                Intent stream = new Intent(copyOfThis, animeStream.class);
                stream.putExtra("streamURL", videoLink);
                startActivity(stream);
            }
        });

        if(this.episodes == null)
            getActivity().onBackPressed();

        if(this.episodes != null)
            listEpisode.setAdapter(new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, this.episodes));
        return view;
    }

}
