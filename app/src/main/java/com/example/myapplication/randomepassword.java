package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class randomepassword extends AppCompatActivity {
    EditText text;
    TextView listtext;
    SeekBar seekBar;
    Button generateList;
    int seekcount=0;
    View v;
    Dialog dialog;
    List <String> password_list=new ArrayList<String>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // seek bar intialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomepassword);
       text=findViewById(R.id.mainText);
        seekBar=findViewById(R.id.seekBar);
        listtext =findViewById(R.id.listNum);
        generateList=findViewById(R.id.generateList);

        dialog = new Dialog(randomepassword.this);
        dialog.setContentView(R.layout.pop_up);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    111);
        }



        //Generating list of passsord We use a dialog box to popup the list view items


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                listtext.setText(Integer.toString(i));
                seekcount=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    Switch upr,lwr,num,syml;
    Boolean upper=true;
    Boolean lower=true;
    Boolean number=true;
    Boolean symbols=true;
    int length=0;

    // function to copy the password to clipboard
    @SuppressLint("ResourceType")
    public void copyToClipBoard(View view){


        EditText text=(EditText) findViewById(R.id.mainText);
        try {
            if(text.getText().length()>0) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipdata = ClipData.newPlainText("text", text.getText().toString());
                clipboard.setPrimaryClip(clipdata);
                Toast.makeText(this, "password is copied to clipboard", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(this, "Operation failed", Toast.LENGTH_SHORT).show();
        }catch (Exception e){}
    }
    String U="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String L="abcdefghijklmnopqrstuvwxyz";
    String N="0123456789";
    String S="!@#$%^&*()_+/*?";
    // Function to generate password
    @SuppressLint("SuspiciousIndentation")
    public void generate(View view){

        upr=findViewById(R.id.switch1);
        lwr=findViewById(R.id.switch2);
        num=findViewById(R.id.switch3);
        syml=findViewById(R.id.switch4);
        upper=upr.isChecked();
        lower=lwr.isChecked();
        number=num.isChecked();
        symbols=syml.isChecked();
        EditText len=findViewById(R.id.length);
        if(len.getText().length()>0)
        length=(int)Integer.parseInt(String.valueOf(len.getText()));
        if(upper || lower || number || symbols){
            int m=0;
            String password="";
            while(length>0){
                if(m>3) m=0;
                if(upper && m==0){
                    int ran= (int) (Math.random()*100)%U.length();
                    System.out.println(ran);
                    password+=U.charAt(ran);
                    length--;
                }else if(lower && m==1){
                    int ran= (int) (Math.random()*100)%L.length();
                    password+=L.charAt(ran);
                    length--;
                }else if(number && m==2){
                    int ran= (int) (Math.random()*100)%N.length();
                    System.out.println(ran);
                    password+=N.charAt(ran);
                    length--;
                }else if(symbols && m==3){
                    int ran= (int) (Math.random()*100)%S.length();
                    System.out.println(ran);
                    password+=S.charAt(ran);
                    length--;
                }
                 m++;
            }
            password_list.add(password);
            text.setText(password);
        }else {
            text.setText("");
            Toast.makeText(this, "Please select atleast one option", Toast.LENGTH_SHORT).show();
        }
    }
    public void clear(View view){
        text.setText("");
    }

    public void onClick(View view) {

       String tmp=text.getText().toString();
        password_list.clear();

        ListView listView = dialog.findViewById(R.id.list);
        ArrayAdapter<String> arr = new ArrayAdapter<String>(getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, password_list);
        if (seekcount == 0) {
            password_list.add("Please select a value from the parameter");
        } else {
            int temp = seekcount;
            while (temp > 0) {
                generate(v);

                temp--;
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipdata = ClipData.newPlainText("text", password_list.get(i));
                    clipboard.setPrimaryClip(clipdata);
                    Toast.makeText(randomepassword.this, "password copied to clipboard", Toast.LENGTH_SHORT).show();
                }
            });

            text.setText(tmp);

        }
        listView.setAdapter(arr);
        dialog.show();

    }
    public void save(View v) {
        if(seekcount>0) {


            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        }
    }

}