package github.tamtom.stacklike.models;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by tamtom on 16/05/16.
 */
public class Tag implements JasonPupolator{
    ArrayList<String> tags;

    public ArrayList<String> getTags() {
        return tags;
    }

    @Override
    public void populate(Object object) {
        tags = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) object;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                tags.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
