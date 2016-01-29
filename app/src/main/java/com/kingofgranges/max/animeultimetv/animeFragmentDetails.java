package com.kingofgranges.max.animeultimetv;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class animeFragmentDetails extends Fragment {

    private static String title;
    private static String synopsis;
    private static Bitmap cover;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_anime_details, container, false);

        final TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        final TextView txtSynopsis = (TextView) view.findViewById(R.id.txtSynopsis);
        final ImageView imgCover = (ImageView) view.findViewById(R.id.imgCover);

        txtTitle.setText(title);
        txtSynopsis.setText(synopsis);
        imgCover.setImageBitmap(cover);

        return view;
    }

    public void setDetails(String title, String synopsis, Bitmap cover) {
        animeFragmentDetails.title = title;
        animeFragmentDetails.synopsis = synopsis;
        animeFragmentDetails.cover = cover;
    }
}
