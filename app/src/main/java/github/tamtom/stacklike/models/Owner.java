package github.tamtom.stacklike.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tamtom on 16/05/16.
 */
public class Owner implements JasonPupolator {
    private String displayName;
    private String profileImage;
    @Override
    public void populate(Object object) {
        JSONObject object1 = (JSONObject) object;
        try {
            displayName = object1.getString("display_name");
            profileImage = object1.getString("profile_image");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
