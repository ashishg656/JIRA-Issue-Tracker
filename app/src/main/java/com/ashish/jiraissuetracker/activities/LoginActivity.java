package com.ashish.jiraissuetracker.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.objects.login.LoginObjectResponse;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.serverApi.AppRequestListener;
import com.ashish.jiraissuetracker.utils.DebugUtils;
import com.ashish.jiraissuetracker.utils.UIUtils;
import com.ashish.jiraissuetracker.utils.VolleyUtils;

/**
 * Created by Ashish on 04/06/16.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, AppRequestListener, RequestTags {

    EditText etUserName, etPassword, etUrl;
    ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    FrameLayout loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);

        etPassword = (EditText) findViewById(R.id.password);
        etUserName = (EditText) findViewById(R.id.username);
        etUrl = (EditText) findViewById(R.id.jiraurl);
        loginButton = (FrameLayout) findViewById(R.id.loginbutton);

        loginButton.setOnClickListener(this);
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
        } else if (checkIfEdittextEmpty(etPassword)) {
            message = "Please enter password";
        } else if (checkIfEdittextEmpty(etUrl)) {
            message = "Please enter Base url";
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
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

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
            showNoInternetDialog();
        } else {
            showWrongPasswordDialog("Invalid credentials. Please verify that you entered correct details and try logging in again.");
        }
    }

    private void showWrongPasswordDialog(String message) {
        alertDialog = new AlertDialog.Builder(this).setCancelable(true)
                .setMessage(message)
                .setTitle("Failed to login")
                .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        try {
            LoginObjectResponse loginObject = (LoginObjectResponse) VolleyUtils.getResponseObject(response, LoginObjectResponse.class);

            String profileImage = null;
            try {
                profileImage = loginObject.getAvatarUrls().get48x48();
                if (profileImage != null) {
                    profileImage = profileImage.substring(0, profileImage.length() - 2);
                    profileImage = profileImage + "400";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            ZPreferences.setUserEmail(this, loginObject.getEmailAddress());
            ZPreferences.setUserName(this, loginObject.getDisplayName());
            ZPreferences.setUserProfileID(this, loginObject.getName());
            ZPreferences.setUserImageURL(this, profileImage);
            if (!loginObject.getActive()) {
                showWrongPasswordDialog("It seems that your account is no longer active. Please contact your company's administrator to reactivate your account or contact JIRA support");
                return;
            }

            ZPreferences.setIsUserLogin(this, true);

            moveToHomeActivity();
        } catch (Exception e) {
            e.printStackTrace();
            makeToast("Some error occured. Please try again");
        }
    }

    private void moveToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    void showNoInternetDialog() {
        alertDialog = new AlertDialog.Builder(this).setCancelable(true)
                .setMessage("Please check your internet connection and try again.")
                .setTitle("Unable to connect")
                .setNeutralButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        UIUtils.openAndroidSettingsScreen(LoginActivity.this);
                    }
                }).setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        loginButton.performClick();
                    }
                }).show();
    }
}
