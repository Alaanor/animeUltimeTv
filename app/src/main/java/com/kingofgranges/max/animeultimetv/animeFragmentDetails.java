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

    private String title;
    private String synopsis;
    private Bitmap cover;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_anime_details, container, false);

        final TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        final TextView txtSynopsis = (TextView) view.findViewById(R.id.txtSynopsis);
        final ImageView imgCover = (ImageView) view.findViewById(R.id.imgCover);

        txtTitle.setText(this.title);
        txtSynopsis.setText(this.synopsis);
        imgCover.setImageBitmap(this.cover);

        return view;
    }

    public void setDetails(String title, String synopsis, Bitmap cover) {
        this.title = title;
        this.synopsis = synopsis;
        this.cover = cover;
    }
}
