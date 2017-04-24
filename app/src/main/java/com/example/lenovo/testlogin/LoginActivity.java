package com.example.lenovo.testlogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String id,email,cover,name,first_name,last_name,age_range,link,gender,locale,picture,timezone,updated_time,verified;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.btnFb);
        loginButton.setTextSize(17);

        loginButton.setReadPermissions("email","public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest data_request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonResponse, GraphResponse graphResponse) {

                                JSONObject object = graphResponse.getJSONObject();
                                if (graphResponse != null && graphResponse.getJSONObject()!= null) {


                                    try {
                                        id = graphResponse.getJSONObject().getString("id");
                                        first_name = graphResponse.getJSONObject().getString("first_name");
                                        last_name = graphResponse.getJSONObject().getString("last_name");
                                        email = graphResponse.getJSONObject().getString("email");
                                        gender = graphResponse.getJSONObject().getString("gender");
                                        locale = graphResponse.getJSONObject().getString("locale");
                                        link = graphResponse.getJSONObject().getString("link");
                                        picture = graphResponse.getJSONObject().getJSONObject("picture").getJSONObject("data").getString("url");
                                        //Log.d("Picture",picture);
                                        Toast.makeText(getApplicationContext(),picture,Toast.LENGTH_LONG).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Map<String,String> params = new HashMap<>();
                                    params.put("firstName",first_name);
                                    params.put("lastName",last_name);
                                    params.put("email",email);
                                    if( gender!= null)
                                        params.put("gender",gender.equals("male")?"m":"f");
                                    String ciudad = "lima";
                                    if(locale!=null)
                                        ciudad = locale;
                                    params.put("city",ciudad);
                                    params.put("profileURL",link);
                                    params.put("avatarURL",picture);
                                    params.put("identifier",id);
                                    params.put("type","2");
                                    //JoinnusLoginFB(VariablesGlobales.URL_BASE_SERVICIOS+"app-register-local",params);
                                    User user = new User();
                                    user.setParseJSON(graphResponse.getJSONObject());
                                    goMainScreem();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields","email,name,id,cover,first_name,last_name,age_range,link,gender,locale,picture,timezone,updated_time,verified");
                data_request.setParameters(parameters);
                data_request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getApplicationContext(), R.string.onCancel,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getApplicationContext(), R.string.onError,Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void goMainScreem() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
