package com.example.animation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends Activity {
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn=(Button)findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        	
        		i=i+1;
        		i=i%2;
        		ImageView iv=(ImageView)findViewById(R.id.imageView1);
        		iv.setBackgroundResource(R.anim.frame_ani);
        		//iv.setMaxHeight(maxHeight)
        		AnimationDrawable ad=(AnimationDrawable)iv.getBackground();
        		if(i==1)
        		ad.start();
        		if(i==0)
        		ad.stop();
        	}
        	
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
