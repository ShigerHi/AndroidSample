package com.example.sd_data;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;


public class MainActivity extends Activity {
	public static TextView tv;
	TextView tv2;
	ScrollView sv;
	String mysdPath;
	public static String myFilePath;
	String tvcontent;
	public static String myPictureSourse;
	public String myMusicSourse;

	MediaPlayer mp;
	File myfile;
	 ImageView myiv;
	 String img_string;
	int btnmusic_n=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnmusic=(Button)findViewById(R.id.button1);
        Button btnlist=(Button)findViewById(R.id.List);
        Button btnpicture=(Button)findViewById(R.id.Picture);
        tv=(TextView)findViewById(R.id.textView1);
        tv2=(TextView)findViewById(R.id.textView2);
        myiv=(ImageView)findViewById(R.id.imageView1);
        sv=new ScrollView(this);
        
        mysdPath=getsdcardpath();
        myFilePath=mysdPath+"/A_MySdData1";
       
        tv.setText(myFilePath);
        mp=new MediaPlayer();
        
       
        myfile=new File(myFilePath);    //new my file
        if(!myfile.exists()) 	myfile.mkdir();//

        myPictureSourse=myFilePath+"/h2.jpg";
        myMusicSourse=myFilePath+"/music.mp3";
        /*
         * 
         */
       
        btnmusic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				btnmusic_n++;
				btnmusic_n%=3;
				if(btnmusic_n==1)
					try{
					//mp.setDataSource("/storage/sdcard/music.mp3");
					//mp.setDataSource("/storage/emulated/0/sdData/music.mp3");		
					mp.setDataSource(myMusicSourse);	
					
					mp.prepare();
					mp.start();
	
					tv.setText("����׼���С�����");
					
			
					}
					catch(Exception e){
					e.printStackTrace();				
					}
				else if(btnmusic_n==2){
					tv.setText("������ͣ�С�����");
					mp.pause();
				}
				else if(btnmusic_n==0){
					tv.setText("���������С�����");
					mp.reset();
				}				
			}
		});
        /*
         * 
         */
        btnpicture.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try{
	
					tv.setText("ͼƬ�л�������");
					
					Bitmap bm=BitmapFactory.decodeFile(myPictureSourse);
					myiv.setImageBitmap(bm);
					int width=bm.getWidth();
					int height=bm.getHeight();
					tv.setText("width:"+width+"height:"+height);
					img_string=sendPhoto(myiv);
//					tv2.setText(img_string);
//					sv.addView(tv2);
//					setContentView(sv);
					
				}
				catch(Exception e){
					e.printStackTrace();				}
			}
		});
	/* */   
        btnlist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {			
				tv.setText("list���������");
		//        	Intent serverIntent = new Intent(this, DeviceListActivity.class);
		//			startActivityForResult(serverIntent, 1);		
			}
		});      
    }
    /**
    * ��ͼƬת����ʮ�������ַ���
    * @param photo
    * @return
    */
    public static String sendPhoto(ImageView photo) {
    Drawable d = photo.getDrawable();
    Bitmap bitmap=((BitmapDrawable)d).getBitmap();
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 30, stream);// (0 - 100)ѹ���ļ�//30ѹ��70%
    byte[] bt = stream.toByteArray();
    String photoStr = byte2hex(bt);
    return photoStr;
    }  
    /**
    * ������ת�ַ���
    * @param b
    * @return
    */
    public static String byte2hex(byte[] b) 
    {
    StringBuffer sb = new StringBuffer();
    String stmp = "";
    for (int n = 0; n < b.length; n++) {
    stmp = Integer.toHexString(b[n] & 0XFF);
    if (stmp.length() == 1) {
    sb.append("0" + stmp);
    } else {
    sb.append(stmp);
    }
    }
    return sb.toString();
    }    
/*
 * 
 */
 // ��ȡ�ļ��б�����
 	private void getFileDir(String path) {
 		//showXPath.setText(path);//��ʾ��ǰ·��
 		//items = new ArrayList<String>();
 		//paths = new ArrayList<String>();
 		// ��ȡ��ǰ·���µ��ļ�
 		File presentFile = new  File(path);
 		File[] files = presentFile.listFiles();
 	/*	
 		if (! path.equals(rootPath)) {
 			// ���ظ�Ŀ¼
 			items.add("back to /");
 			paths.add(rootPath);
 			// ������һ��Ŀ¼
 			items.add("back previous");
 			paths.add(presentFile.getParent());
 		}
 	*/
 		// ���ӵ�ǰ·���µ����е��ļ�����·��
 		for (File f : files) {
 			tvcontent+=(f.getName());
 			//paths.add(f.getPath());
 		}
 		
 		// �����б�������
 		//setListAdapter(new FileListAdapter(FileManagerActivity.this, items, paths));
 	}
/*
 * get SD path
 */
    public static String getsdcardpath(){
    	String sdPath="";
    	sdPath=Environment.getExternalStorageDirectory()
    			.getAbsolutePath();
    	return sdPath;
    }
/*
 * (android.view.Menu)
 */
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
        	tv.setText("Menu,Work");
        	getFileDir(myFilePath);
        	tv.setText(tvcontent);
        	
        	Intent serverIntent = new Intent(this, FileListActivity.class);
			startActivityForResult(serverIntent, 1);
            return true;
        }
        else if(id == R.id.BlueTeeth){
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
}