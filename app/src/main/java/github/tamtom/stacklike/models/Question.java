package github.tamtom.stacklike.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tamtom on 16/05/16.
 */
public class Question implements JasonPupolator {
  private  Tag tags;
    private Owner mOwner;
    private int mScore;
    private long creationDate;
    String mLink;
    String mTitle;


    public Tag getTags() {
        return tags;
    }

    public Owner getOwner() {
        return mOwner;
    }

    public int getScore() {
        return mScore;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public String getLink() {
        return mLink;
    }

    public String getTitle() {
        return mTitle;
    }


    @Override
    public void populate(Object object) {

         if(object instanceof JSONObject){
           try {
                    JSONObject head = (JSONObject) object;
                    JSONArray jsonArray1 = head.getJSONArray("tags");
                    tags = new Tag();
                    tags.populate(jsonArray1);
                    mLink = head.getString("link");
                    mTitle = head.getString("title");
                    creationDate = head.getLong("creation_date");
                    mScore = head.getInt("score");
                    JSONObject owner = head.getJSONObject("owner");
                    mOwner = new Owner();
                    mOwner.populate(owner);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

