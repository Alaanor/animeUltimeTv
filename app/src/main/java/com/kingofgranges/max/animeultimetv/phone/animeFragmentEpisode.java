package com.kingofgranges.max.animeultimetv.phone;

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

import com.kingofgranges.max.animeultimetv.R;
import com.kingofgranges.max.animeultimetv.common.animeUltime;

public class animeFragmentEpisode extends Fragment {

    private static String[] episodes;
    private static String[] link;

    public void setEpisode(String[] episodes, String[] link) {
        animeFragmentEpisode.episodes = episodes;
        animeFragmentEpisode.link = link;
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
                String videoLink = au.getVideoLink(animeUltime.mainUrlv5 + link[position]);
                Intent stream = new Intent(copyOfThis, animeStream.class);
                stream.putExtra("streamURL", videoLink);
                startActivity(stream);
            }
        });

        if(episodes == null)
            getActivity().onBackPressed();

        if(episodes != null)
            listEpisode.setAdapter(new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, episodes));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
