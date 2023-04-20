package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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

public class randomepassword extends AppCompatActivity implements AdapterInterface{
    EditText text,len;
    TextView listtext;
    SeekBar seekBar;
    Button generateList;
    Switch upr,lwr,num,syml;
    int seekcount=0;
    View v;
    Dialog dialog;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;

    List <String> password_list=new ArrayList<String>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomepassword);
       text=findViewById(R.id.mainText);
        seekBar=findViewById(R.id.seekBar);
        listtext =findViewById(R.id.listNum);
        generateList=findViewById(R.id.generateList);
        len=findViewById(R.id.length);

        upr=findViewById(R.id.switch1);
        lwr=findViewById(R.id.switch2);
        num=findViewById(R.id.switch3);
        syml=findViewById(R.id.switch4);
        SharedPreferences pre=getSharedPreferences("OPTIONS",MODE_PRIVATE);
        upr.setChecked(pre.getBoolean("Upper",true));
        lwr.setChecked(pre.getBoolean("Lower",true));
        num.setChecked(pre.getBoolean("Number",true));
        syml.setChecked(pre.getBoolean("Symbols",true));
        seekBar.setProgress(pre.getInt("SeekBar",0));
        len.setText(pre.getString("Length","8"));
        listtext.setText(Integer.toString(pre.getInt("SeekBar",0)));
        //For saving prefrence button

        Button pref=findViewById(R.id.prefrencebtn);
        pref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pre=getSharedPreferences("OPTIONS",MODE_PRIVATE);
                SharedPreferences.Editor editor=pre.edit();
                editor.putBoolean("Upper",upr.isChecked());
                editor.putBoolean("Lower",lwr.isChecked());
                editor.putBoolean("Number",num.isChecked());
                editor.putBoolean("Symbols",syml.isChecked());
                editor.putInt("SeekBar",seekBar.getProgress());
                editor.putString("Length",len.getText().toString());
                editor.apply();

                Toast.makeText(randomepassword.this, "Preffrence Saved", Toast.LENGTH_SHORT).show();
            }
        });

        //pop up Dialog box

        dialog = new Dialog(randomepassword.this);
        dialog.setContentView(R.layout.pop_up);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams
                .MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;

        recyclerView=dialog.findViewById(R.id.list);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                listtext.setText(Integer.toString(i));
                seekcount=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

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


        upper=upr.isChecked();
        lower=lwr.isChecked();
        number=num.isChecked();
        symbols=syml.isChecked();

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

        String tmp = text.getText().toString();
        password_list.clear();

        if (seekcount == 0) {
            Toast.makeText(this," Please select a value from the parameter", Toast.LENGTH_SHORT).show();
        } else {
            int temp = seekcount;
            while (temp > 0) {
                generate(v);

                temp--;
            }
           adapter=new RecyclerAdapter(this,password_list,this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            dialog.show();

        }
    }
    public void save(View v) {

            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();

    }

    // Recycler view Button click listener to copy the item
    @Override
    public void onButtonClickListener(int pos) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipdata = ClipData.newPlainText("text", password_list.get(pos));
        clipboard.setPrimaryClip(clipdata);
        Toast.makeText(this, "Password is copied", Toast.LENGTH_SHORT).show();
    }
  // reset button refrence
    public void reset(View v){
        len.setText("8");
        generate(new View(this));
        upr.setChecked(true);
        lwr.setChecked(true);
        num.setChecked(true);
        syml.setChecked(true);
        seekBar.setProgress(0);
        text.setText("");
        SharedPreferences pre=getSharedPreferences("OPTIONS",MODE_PRIVATE);
        SharedPreferences.Editor editor=pre.edit();
        editor.putBoolean("Upper",true);
        editor.putBoolean("Lower",true);
        editor.putBoolean("Number",true);
        editor.putBoolean("Symbols",true);
        editor.putInt("SeekBar",0);
        editor.putString("Length","8");
        editor.apply();


    }
}