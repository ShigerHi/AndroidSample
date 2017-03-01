package com.example.sample3_1;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class MainActivity extends Activity {

	int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=(Button)findViewById(R.id.button1);
        button.setOnClickListener(
        		new View.OnClickListener() {
					
					public void onClick(View v) {
						LinearLayout ll=(LinearLayout)findViewById(R.id.lla);
						String msg=MainActivity .this.getResources().getString(R.string.button);
						Button tempbutton=new Button(MainActivity .this);
						tempbutton.setText(msg+(++count));
						tempbutton.setWidth(20);
						ll.addView(tempbutton);
						
					}
				});
    }


}
