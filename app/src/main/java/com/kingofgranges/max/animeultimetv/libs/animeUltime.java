package com.kingofgranges.max.animeultimetv.libs;

import android.text.Html;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class animeUltime {

    public static final String mainUrlv5 = "http://v5.anime-ultime.net/";
    public static int maxResult = 25;
    private JSONArray data;
    private String[] link;

    public String[] getSearchResult(String search) {
        String url = "https://v5.anime-ultime.net/MenuSearch.html";
        String[] result = new String[]{};
        try {
            this.data = NetworkUtilities.readJsonFromHttpPost(url, search);
            int k = 0;
            for (int j = 0; j < (this.data != null ? this.data.length() : 0); j++) {
                JSONObject jsonObj = this.data.getJSONObject(j);
                if (!jsonObj.getString("format").equals("OST"))
                    k++;
            }
            result = new String[k];
            this.link = new String[k];
            for (int i = 0, l = 0; i < (this.data != null ? this.data.length() : 0); i++) {
                JSONObject jsonObj = this.data.getJSONObject(i);
                if (!jsonObj.getString("format").equals("OST")) {
                    result[l] = Html.fromHtml(Html.fromHtml(jsonObj.getString("title")).toString()).toString();
                    this.link[l++] = Html.fromHtml(Html.fromHtml(jsonObj.getString("url")).toString()).toString();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String[][] getPageInformation(int offset) {
        try {
            String rawDom = NetworkUtilities.readHtmlFromUrl(this.link[offset]);
            Document dom = Jsoup.parse(rawDom);

            Element divFiche = dom.select("div.fiche").first();
            Element divSynopsis = divFiche.select("div[data-target=synopsis]").first();
            Element divImg = divFiche.select("img").first();
            Element ulList = dom.select("ul.ficheDownload").first();
            Elements liList = ulList.select("li.file");
            Element divTitle = dom.select("div.title > h1").first();

            String[] list = new String[liList.size()];
            String[] link = new String[liList.size()];
            int i = 0;
            for (Element item : liList) {
                list[i] = item.select("div.number").first().text();
                link[i] = item.select("a[href]").first().attr("href");
                i++;
            }

            String synopsis = divSynopsis.text();
            String img = divImg.attr("src");
            String title = divTitle.text().replaceAll("vostfr", "");

            String[][] result = new String[3][liList.size() < 2 ? 3 : liList.size()];
            result[0][0] = synopsis;
            result[0][1] = img;
            result[0][2] = title;
            result[1] = list;
            result[2] = link;
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getVideoLink(String urlTarget) {
        try {
            String rawDom;
            rawDom = NetworkUtilities.readHtmlFromUrl(urlTarget);
            Document dom = Jsoup.parse(rawDom);

            String url = null;
            String val;
            JSONObject val2;
            try {
                Elements val3 = dom.select("div#leftContainer");
                val3 = val3.select("script");
                val = val3.html();
                val = val.replaceAll("^.*var.ddl_links.=.", "");
                val2 = new JSONObject(val);
                try {
                    val2 = val2.getJSONObject("mp4");
                } catch(JSONException d){
                    val2 = val2.getJSONObject("webm");
                }

                try {
                    url = val2.getString("1080p");
                } catch (JSONException e) {
                    try {
                        url = val2.getString("720p");
                    } catch (JSONException f) {
                        try {
                            url = val2.getString("480p");
                        } catch (JSONException g) {
                            try {
                                url = val2.getString("320p");
                            } catch (JSONException h) {
                                val = val3.html();
                                val = val.replaceAll("^.*var.ddl_links.=.", "");
                                val2 = new JSONObject(val);
                                val = val2.getString("webm");
                                val = val.replaceAll("\".*?\".*?\"", "");
                                val = val.replaceAll("\\{", "").replaceAll("\\}", "");
                                url = val.replaceAll("\"", "").replaceAll("\\\\/", "/");
                            }
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return url;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
