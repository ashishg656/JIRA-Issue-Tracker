package com.ashish.jiraissuetracker.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.adapters.IssuesFragmentListAdapter;
import com.ashish.jiraissuetracker.application.AppApplication;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.objects.issues.SearchListingResponseObject;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.requests.AppUrls;
import com.ashish.jiraissuetracker.serverApi.AppRequestListener;
import com.ashish.jiraissuetracker.utils.UIUtils;
import com.ashish.jiraissuetracker.utils.VolleyUtils;

/**
 * Created by Ashish on 23/06/16.
 */
public class SearchByTextActivity extends BaseActivity implements AppRequestListener, View.OnClickListener {

    FrameLayout clearButton;
    EditText editText;
    IssuesFragmentListAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    String textSearching;
    SwipeRefreshLayout swipeRefreshLayout;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_by_text_activity_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setProgressAndErrorLayoutVariables();

        FrameLayout toolbarView = (FrameLayout) getLayoutInflater().inflate(R.layout.search_by_text_activity_toolbar_view, null, false);
        toolbar.addView(toolbarView);
        ViewGroup.LayoutParams params = toolbarView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        toolbarView.setLayoutParams(params);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        clearButton = (FrameLayout) toolbarView.findViewById(R.id.clear_search_box);
        editText = (EditText) toolbarView.findViewById(R.id.search_by_text_edittext);
        clearButton.setOnClickListener(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.SwipeRefreshLayout);

        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary, R.color.green_color_primary, R.color.red_color_primary);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 3) {
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }

                    timer = new CountDownTimer(2000, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            performSearch();
                        }
                    }.start();
                }

                if (charSequence.length() == 0) {
                    clearButton.setVisibility(View.GONE);
                } else {
                    clearButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                performSearch();
            }
        });
    }

    private void performSearch() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        textSearching = editText.getText().toString().trim();
        AppApplication.getInstance().getRequestQueue().cancelAll(RequestTags.SEARCH_BY_TEXT);

        String url = ZPreferences.getBaseUrl(this) + AppUrls.getSearchByTextUrl(textSearching);
        AppRequests.makeSearchByTextRequest(url, this, this);
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equals(RequestTags.SEARCH_BY_TEXT)) {
            if (adapter == null) {
                hideErrorLayout();
                showProgressLayout();
                hideSwipeRefreshLayout();
            } else {
                hideErrorLayout();
                hideProgressLayout();
                showSwipeRefreshLayout();
            }
        }
    }

    void showSwipeRefreshLayout() {
        swipeRefreshLayout.setRefreshing(true);
    }

    void hideSwipeRefreshLayout() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equals(RequestTags.SEARCH_BY_TEXT)) {
            showErrorLayout();
            hideProgressLayout();
            hideSwipeRefreshLayout();

            UIUtils.hideSoftKeyboard(this);
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equals(RequestTags.SEARCH_BY_TEXT)) {
            hideErrorLayout();
            hideProgressLayout();
            hideSwipeRefreshLayout();

            UIUtils.hideSoftKeyboard(this);

            SearchListingResponseObject issuesData = (SearchListingResponseObject) VolleyUtils.getResponseObject(response, SearchListingResponseObject.class);
            setAdapterData(issuesData);
        }
    }

    private void setAdapterData(SearchListingResponseObject issuesData) {
        if (adapter == null) {
            adapter = new IssuesFragmentListAdapter(issuesData.getIssues(), this, false);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.changeDataSet(issuesData.getIssues(), false);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear_search_box:
                editText.setText("");
                clearButton.setVisibility(View.GONE);
                break;
        }
    }
}
