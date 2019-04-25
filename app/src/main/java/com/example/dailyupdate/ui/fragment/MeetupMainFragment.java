package com.example.dailyupdate.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyupdate.BuildConfig;
import com.example.dailyupdate.R;
import com.example.dailyupdate.data.MeetupEvent;
import com.example.dailyupdate.data.MeetupEventResponse;
import com.example.dailyupdate.networking.MeetupService;
import com.example.dailyupdate.networking.RetrofitInstance;
import com.example.dailyupdate.ui.adapter.MeetupEventAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeetupMainFragment extends Fragment {

    @BindView(R.id.main_recycler_view)
    RecyclerView recyclerView;
    private int searchCategoryNumber = 34; // Category "Tech"
    private String API_KEY = BuildConfig.MEETUP_API_KEY;
    private String searchKeyword;
    private String sortBy;
    private String searchLocation;
    SharedPreferences sharedPref;

    public MeetupMainFragment() {
    }

    public static MeetupMainFragment newInstance() {
        return new MeetupMainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_layout, container, false);
        ButterKnife.bind(this, rootView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        searchKeyword = sharedPref.getString(getString(R.string.pref_meetup_edittext_key), "");
        sortBy = sharedPref.getString(getString(R.string.pref_meetup_sort_key),
                getString(R.string.pref_meetup_sort_default));
        searchLocation = sharedPref.getString(getString(R.string.pref_meetup_location_key), "");

        retrieveMeetupEvents();
        return rootView;
    }

    private void retrieveMeetupEvents() {
        MeetupService meetupService =
                RetrofitInstance.getMeetupRetrofitInstance().create(MeetupService.class);

        Call<MeetupEventResponse> meetupEventCall = meetupService.getMeetupEventList(API_KEY,
                searchLocation, sortBy, searchCategoryNumber, searchKeyword);
        meetupEventCall.enqueue(new Callback<MeetupEventResponse>() {
            @Override
            public void onResponse(Call<MeetupEventResponse> call,
                                   Response<MeetupEventResponse> response) {
                MeetupEventResponse meetupEventResponse = response.body();
                List<MeetupEvent> meetupEventList = meetupEventResponse.getMeetupEventsList();

                MeetupEventAdapter meetupEventAdapter = new MeetupEventAdapter(getContext(),
                        meetupEventList);
                recyclerView.setAdapter(meetupEventAdapter);
            }

            @Override
            public void onFailure(Call<MeetupEventResponse> call, Throwable t) {
                //TODO: handle failure
            }
        });
    }

}
