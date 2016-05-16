package github.tamtom.stacklike;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import github.tamtom.stacklike.models.Question;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
  private   RecyclerView mQueRecyclerView;
    private ProgressBar mProgressBar;
    ArrayList<Question> mQuestions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Lookup the recyclerview in activity layout
        mQueRecyclerView    = (RecyclerView) findViewById(R.id.questions);
        mQueRecyclerView.setVisibility(View.GONE);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
            new getQuestionFromInternet().execute(new String[]{"https://api.stackexchange.com/2.2/search/advanced?order=desc&sort=activity&accepted=False&answers=0&tagged=android&site=stackoverflow"});
    }
    private void initResc(){
        QuestionAdapter adapter = new QuestionAdapter(mQuestions);
        // Attach the adapter to the recyclerview to populate items
        mQueRecyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        mQueRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    class getQuestionFromInternet extends AsyncTask<String , Void,String>{
        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {
            mQuestions = new ArrayList<>();
            Request request = new Request.Builder()
                    .url(params[0])
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject root = new JSONObject(s);
                JSONArray items  = root.getJSONArray("items");
                for (int i = 0; i < items.length(); i++) {
                    Question q = new Question();
                    q.populate(items.getJSONObject(i));
                    mQuestions.add(q);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressBar.setVisibility(View.GONE);
            mQueRecyclerView.setVisibility(View.VISIBLE);
            initResc();
        }
    }
    class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
         private ArrayList<Question> mQuestions;
        public QuestionAdapter(ArrayList<Question> mQuestions){
            this.mQuestions = mQuestions;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View QuestionView =getLayoutInflater().inflate(R.layout.item_question,parent,false);
            return new ViewHolder(QuestionView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Question question = mQuestions.get(position);
         holder.bind(question,position);
        }

        @Override
        public int getItemCount() {
            return mQuestions.size();
        }

        // Provide a direct reference to each of the views within a data item
        // Used to cache the views within the item layout for fast access
         class ViewHolder extends RecyclerView.ViewHolder {
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            public TextView mDisplayName;
            public ImageView mLikeButton;
            public ImageView mShareButton;
            public ImageView mProfilePicture;
            public TextView mTitle;
            public TextView mScore;
            private Question mQuestion;
            private TextView duration;
            private CardView cardView;

            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);
                mDisplayName = (TextView) itemView.findViewById(R.id.owner_name);
                mLikeButton = (ImageView) itemView.findViewById(R.id.like_button);
                mShareButton = (ImageView) itemView.findViewById(R.id.share_button);
                mProfilePicture = (ImageView) itemView.findViewById(R.id.profile_image);
                mTitle = (TextView) itemView.findViewById(R.id.title_question);
                mScore = (TextView) itemView.findViewById(R.id.score);
                duration = (TextView) itemView.findViewById(R.id.minutes_ago);
                cardView = (CardView) itemView;
            }

            public void bind(Question Question, int position) {
                this.mQuestion = Question;
                mDisplayName.setText(mQuestion.getOwner().getDisplayName());

                Picasso.with(getApplicationContext()).load(mQuestion.getOwner().getProfileImage()).into(mProfilePicture);
                mTitle.setText(mQuestion.getTitle());
                mScore.setText(mQuestion.getScore()+"");
                duration.setText(new Date(mQuestion.getCreationDate()*1000).getMinutes()+"mins");
if (position%2==0){
    cardView.setCardBackgroundColor(Color.parseColor("#464646"));
}
                else {
    cardView.setCardBackgroundColor(Color.parseColor("#03A100"));
                }

            }
        }
    }

}
