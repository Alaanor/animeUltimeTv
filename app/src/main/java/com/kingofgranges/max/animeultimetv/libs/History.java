package com.kingofgranges.max.animeultimetv.libs;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class History {

    private final Context context;
    public final String fileSaveTarget = "history";

    public History(Context context){
        this.context = context;
    }

    public void addHistory(String histAnime, String histEpisode, String urlTarget){
        try {
            JSONArray currData = getHistoryAsJson();
            JSONObject newHist = new JSONObject();
            newHist.put("anime", histAnime);
            newHist.put("episode", histEpisode);
            newHist.put("url", urlTarget);
            newHist.put("date", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", context.getResources().getConfiguration().locale)).format(new Date()));
            currData.put(newHist);
            writeToFile(currData.toString(), fileSaveTarget);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String[][] getHistory(){
        JSONArray data = getHistoryAsJson();
        String[][] arrayOfArrays = new String[data != null ? data.length() : 0][];
        for (int i = 0; i < (data != null ? data.length() : 0); i++) {
            JSONObject innerJsonObject;
            try {
                innerJsonObject = (JSONObject) data.get(i);
                String[] stringArray = new String[4];
                for (int j = 0; j < innerJsonObject.length(); j++) {
                    stringArray[0] = (String) innerJsonObject.get("anime");
                    stringArray[1] = (String) innerJsonObject.get("episode");
                    stringArray[2] = (String) innerJsonObject.get("url");
                    stringArray[3] = (String) innerJsonObject.get("date");
                }
                arrayOfArrays[i] = stringArray;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String[][] invertedList = new String[arrayOfArrays.length][];
        for(int i = 0; i < arrayOfArrays.length; i ++) {
            invertedList[i] = arrayOfArrays[arrayOfArrays.length - 1 - i];
        }
        return invertedList;
    }

    private JSONArray getHistoryAsJson(){
        try {
            return new JSONArray(readFromFile(fileSaveTarget));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeToFile(String data, String file) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(file, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile(String file) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput(file);
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            writeToFile("[]", fileSaveTarget);
            ret = "[]";
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public void deleteHistory() {
        writeToFile("[]", fileSaveTarget);
    }
}
