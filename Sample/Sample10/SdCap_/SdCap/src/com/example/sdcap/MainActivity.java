package com.example.sdcap;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn=(Button)findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(Environment.getExternalStorageState().equals(Environment.
						MEDIA_MOUNTED)){
					File filePath=Environment.getExternalStorageDirectory();
					StatFs stat=new StatFs(filePath.getPath());
					long blockSize =stat.getBlockSize();
					float totalBlocks=stat.getBlockCount();
					int sizeInMb=(int)(blockSize*totalBlocks)/1024/1024;
					long availableBlocks=stat.getAvailableBlocks();
					float percent =availableBlocks/totalBlocks;
					percent=percent*1000;
					float sizeUsed=(float)(sizeInMb*(1000-percent)/1000.0f);
					float sizeinUsed=(float)(sizeInMb*(percent)/1000.0f);
					
					TextView t=(TextView)findViewById(R.id.textView1);
					t.setText("SD��ʹ�������\n������:"+sizeInMb+"M.\n����"+sizeUsed+"M\n "+
					(1000-percent)/10.0f+"%\n����:"+sizeinUsed+"M\n "+percent/10.f+"%.");
				}
				else{
					Toast.makeText(MainActivity.this, "�Բ��������sd��", 
							Toast.LENGTH_LONG).show();
							
				}
				
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