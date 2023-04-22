package uni.fmi.masters.moneylaundryapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import uni.fmi.masters.moneylaundryapp.LoginActivity;
import uni.fmi.masters.moneylaundryapp.MainActivity;
import uni.fmi.masters.moneylaundryapp.R;
import uni.fmi.masters.moneylaundryapp.adapter.LeaderboardAdapter;
import uni.fmi.masters.moneylaundryapp.entity.GameResultEntity;
import uni.fmi.masters.moneylaundryapp.entity.UserEntity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaderboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaderboardFragment extends Fragment {

    RecyclerView recyclerView;
    LeaderboardAdapter adapter;
    TextView weekTV;
    ArrayList<GameResultEntity> results;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        recyclerView = view.findViewById(R.id.leaderboardRV);
        weekTV = view.findViewById(R.id.weekTV);

        results = new ArrayList<>();
        adapter = new LeaderboardAdapter(results);
        recyclerView.setAdapter(adapter);

        new GetResultsAsyncTask().execute();

        return view;
    }


    public LeaderboardFragment() {
        // Required empty public constructor
    }

    public static LeaderboardFragment newInstance(String param1, String param2) {
        LeaderboardFragment fragment = new LeaderboardFragment();
        return fragment;
    }


    private class GetResultsAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        public GetResultsAsyncTask() {
            dialog = new ProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Loading score data....");
            dialog.show();
            results.clear();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String urlString = "http://10.0.0.22:8989/Leaderboard";
            HttpURLConnection urlConnection = null;

            try{
                URL url = new URL(urlString);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream =
                        new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(stream));

                String line = reader.readLine();

                if(line != null){
                    JSONArray jsonArr = new JSONArray(line);

                    for(int i = 0; i < jsonArr.length(); i++){

                        JSONObject jsonOb = jsonArr.getJSONObject(i);
                        GameResultEntity gameResult = new GameResultEntity();
                        gameResult.setUsername(jsonOb.getString("Username"));
                        gameResult.setScore(jsonOb.getInt("Score"));
                        gameResult.setWeek(jsonOb.getInt("Week"));
                        gameResult.setCreationDate(new Date(jsonOb.getLong("CreationDate")));

                        results.add(gameResult);
                    }

                }

            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            dialog.hide();


            adapter.results = results;

            adapter.notifyDataSetChanged();

        }


    }



}