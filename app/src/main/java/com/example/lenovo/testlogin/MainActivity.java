package com.example.lenovo.testlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.btnLogout)
    Button btnLogout;
    @Bind(R.id.txtName)
    TextView txtName;
    @Bind(R.id.txtGmail)
    TextView txtGmail;
    @Bind(R.id.txtID)
    TextView txtID;
    Profile profile;
    private String fist_name,last_name,id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (AccessToken.getCurrentAccessToken() == null) {
            goLoginScreem();
        }


    }

    @OnClick(R.id.btnLogout)
    public void logoutAction() {
        LoginManager.getInstance().logOut();
        goLoginScreem();
    }

    public void goLoginScreem() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
