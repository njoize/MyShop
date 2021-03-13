package njoize.dkh.th.co.myshop;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;


/**
 * A simple {@link Fragment} subclass.
 */
public class AuthenFragment extends Fragment {


    public AuthenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Login Controller
        loginController();


    } // Main Method

    // Method onResume() จะทำงานเป็นอย่างแรก
    @Override
    public void onResume() {
        super.onResume();

        MyConstant myConstant = new MyConstant();

        SharedPreferences sharedPreferences = getActivity()
                .getSharedPreferences(myConstant.getSharePreferFileUserLogin(), Context.MODE_PRIVATE);
        boolean b = sharedPreferences.getBoolean("Remember", false);
        String jsonString = sharedPreferences.getString("JSON", "");


        if (b && (jsonString.length() != 0)) {
            Intent intent = new Intent(getActivity(), ServiceActivity.class);
            intent.putExtra("a_username", jsonString);
            startActivity(intent);
            getActivity().finish();
        }

    }

    private void loginController() {
        Button button = getView().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                EditText usernameEditText = getView().findViewById(R.id.edtUsername);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);

                String usernameString = usernameEditText.getText().toString().trim();
                String passwordString = passwordEditText.getText().toString().trim();
                MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity());

                if (usernameString.isEmpty() || passwordString.isEmpty()) {
//                    Have Space
                    myAlertDialog.normalDialog("ข้อมูลไม่ครบถ้วน","กรุณากรอก Username และ Password");

                } else {
//                    No Space
                    try {

                        GetUsername getUsername = new GetUsername(getActivity());
                        MyConstant myConstant = new MyConstant();

                        getUsername.execute(usernameString, myConstant.getUrlGetUsername());
                        String resultJson = getUsername.get();
                        Log.d("AuthenFragment","resultJson ==> " + resultJson);

//                        Check User and Pass
                        if (resultJson.equals("null")) {
                            myAlertDialog.normalDialog("Username ไม่ถูกต้อง", "กรุณากรอก Username ใหม่อีกครั้ง");
                        } else {

                            JSONArray jsonArray = new JSONArray(resultJson);
                            JSONObject jsonObject = jsonArray.getJSONObject(0); // ฐานข้อมูล row แรก


                            String firstnameString = jsonObject.getString("a_firstname");
                            String nameString = jsonObject.getString("a_name");
                            String userCatString = jsonObject.getString("a_cat");

                            String truePassword = jsonObject.getString("a_password");
                            Log.d("AuthenFragment", "truePassword ==> " + truePassword);

                            if (checkPassword(passwordString, jsonObject.getString("a_password"))) {

                                CheckBox checkBox = getView().findViewById(R.id.chbRememberMe);



                                SharedPreferences sharedPreferences = getActivity()
                                        .getSharedPreferences(myConstant.getSharePreferFileUserLogin(), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("Username", usernameString);
                                editor.putString("Firstname", firstnameString);
                                editor.putString("Name", nameString);
                                editor.putString("UserCat", userCatString);
                                editor.putBoolean("Remember", checkBox.isChecked());
                                editor.putString("JSON", resultJson);

                                editor.commit();



                                Intent intent = new Intent(getActivity(), ServiceActivity.class);
                                intent.putExtra("a_username", resultJson);
                                startActivity(intent);
                                getActivity().finish();
                            } else {
                                myAlertDialog.normalDialog("Password ไม่ถูกต้อง","กรุณากรอก Password ใหม่อีกครั้ง");
                            }


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } // if

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean checkPassword(String passwordString, String md5TruePasswordString) {

        boolean result = false;
        String tag = "AuthenFragment";
        Log.d(tag, "md5TruePassword ==> " + md5TruePasswordString);

        try {

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = messageDigest.digest(passwordString.getBytes(StandardCharsets.UTF_8));

            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : bytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            Log.d(tag, "md5UserPassword ==> " + stringBuilder);

            if (md5TruePasswordString.equals(stringBuilder.toString().trim())) {
                result = true;
                Log.d(tag, "Result True");
            }

            Log.d(tag, "result ==> " + result);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_authen, container, false);
    }

}