package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
public class randomepassword extends AppCompatActivity {
    EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomepassword);
       text=findViewById(R.id.mainText);
    }
    Switch upr,lwr,num,syml;
    Boolean upper=true;
    Boolean lower=true;
    Boolean number=true;
    Boolean symbols=true;
    int length;
    // function to copy the password to clipboard
    public void copyToClipBoard(View view){
        EditText text=(EditText) findViewById(R.id.mainText);
        try {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipdata = ClipData.newPlainText("text", text.getText().toString());
            clipboard.setPrimaryClip(clipdata);
            if(text.getText().length()>0)
            Toast.makeText(this, "password is copied to clipboard", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "Operation failed", Toast.LENGTH_SHORT).show();
        }catch (Exception e){}
    }
    String U="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String L="abcdefghijklmnopqrstuvwxyz";
    String N="0123456789";
    String S="!@#$%^&*()_+/*?";
    // Function to generate password
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
            text.setText(password);
        }else {
            text.setText("");
            Toast.makeText(this, "Please select atleast one option", Toast.LENGTH_SHORT).show();
        }
    }
    public void clear(View view){
        text.setText("");
    }
}