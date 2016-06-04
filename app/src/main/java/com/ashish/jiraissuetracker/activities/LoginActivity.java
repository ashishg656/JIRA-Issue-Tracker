package com.ashish.jiraissuetracker.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.serverApi.AppRequestListener;
import com.ashish.jiraissuetracker.utils.DebugUtils;

/**
 * Created by Ashish on 04/06/16.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, AppRequestListener, RequestTags {

    EditText etUserName, etPassword, etUrl;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);

        etPassword = (EditText) findViewById(R.id.password);
        etUserName = (EditText) findViewById(R.id.username);
        etUrl = (EditText) findViewById(R.id.jiraurl);

        findViewById(R.id.loginbutton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginbutton:
                if (checkIfFormComplete() == null) {
                    sendVerifyUserLoginRequest();
                } else {
                    makeToast(checkIfFormComplete());
                }
                break;
        }
    }

    private void sendVerifyUserLoginRequest() {
        AppRequests.makeLoginRequest(etUrl.getText().toString(), etUserName.getText().toString().trim(), etPassword.getText().toString().trim(), this, this);
    }

    private String checkIfFormComplete() {
        String message = null;
        if (checkIfEdittextEmpty(etUserName)) {
            message = "Please enter userName";
        } else if (checkIfEdittextEmpty(etUserName)) {
            message = "Please enter password";
        }

        return message;
    }

    boolean checkIfEdittextEmpty(EditText editText) {
        if (editText.getText().toString().trim().length() == 0) {
            return true;
        } else
            return false;
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(LOGIN_REQUEST)) {
            progressDialog = ProgressDialog.show(this, "Logging In", "Please wait. Verifying credentials.", true, false);
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        DebugUtils.log("failed");
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        if (error instanceof NoConnectionError) {

        } else {

        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        DebugUtils.log("complete");
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
