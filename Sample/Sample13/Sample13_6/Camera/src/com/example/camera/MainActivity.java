package com.example.camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
//import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
//import android.content.DialogInterface.OnClickListener;
//import android.graphics.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements SurfaceHolder.Callback,OnClickListener{

	SurfaceView mySurfaceView;
	SurfaceHolder mySurfaceHold;
	Button button1;
	Button button2;
	Button button3;
	Camera mycamera;
	boolean isView=false;
	
	TextView mytv1;
	
	public static Camera isCameraAvailiable(){
		      Camera object = null;
		      try {
		         object = Camera.open(); 
		        
		         
		      }
		      catch (Exception e){
		      }
		      return object; 
	}
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
    			WindowManager.LayoutParams.FLAG_FULLSCREEN);
       
        setContentView(R.layout.activity_main);
        
        mySurfaceView=(SurfaceView)findViewById(R.id.mySurfaceView1);
        button1=(Button)findViewById(R.id.button1);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        mytv1=(TextView)findViewById(R.id.textView1);
        
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        mySurfaceHold=mySurfaceView.getHolder();
        mySurfaceHold.addCallback(this);
     //   mySurfaceHold.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        	
       // initCamera();
        mytv1.setText("oncreate");
      
        Toast.makeText(MainActivity.this, "oncreate", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==button1){
			initCamera();
			mytv1.setText("initCamera");
		}else if(v==button2){
			if(mycamera!=null&&isView){
				isView=false;			
				mycamera.stopPreview();
				mycamera.release();
				mycamera=null;
				mytv1.setText("close");
			}
		}else if(v==button3){
					
			mycamera.takePicture(null,null, myjpegCallback);
			mytv1.setText("snap");
		}
		
	}
	/*
	 * 
	 */
	String  strImgPath;

	public void saveBitmap(Bitmap bm) {
		   //Log.e(TAG, "����ͼƬ");
		String picName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";//��Ƭ����
		 strImgPath = Environment.getExternalStorageDirectory().toString() + "/CONSDCGMPIC/";   
		File f = new File(strImgPath, picName);
		   if (f.exists()) {
		    f.delete();
		   }
		   try {
		    FileOutputStream out = new FileOutputStream(f);
		    bm.compress(Bitmap.CompressFormat.PNG, 90, out);
		    out.flush();
		    out.close();
		   // Log.i(TAG, "�Ѿ�����");
		   } catch (FileNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   } catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   }

		 }


/*
 * 
 */
	@SuppressWarnings("deprecation")
	private void initCamera() {
		if(!isView){
			mycamera=isCameraAvailiable();
		}
		if(mycamera!=null&&!isView){
			try{
		//		Camera.Parameters myParameters=mycamera.getParameters();
		//		myParameters.setPictureFormat(PixelFormat.JPEG);
		//		myParameters.setPreviewSize(200, 200);
		//		mycamera.setParameters(myParameters);
				mycamera.setDisplayOrientation(90);//
				mycamera.setPreviewDisplay(mySurfaceHold);
				mycamera.startPreview();
			}
			catch(IOException e){
				e.printStackTrace();
				mytv1.setText(e.toString());
				
				 Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
			}
			isView=true;
		}
	
	}
	/*
	 * 
	 */
	ShutterCallback myShutterCallback=new ShutterCallback(){
		@Override
		public void onShutter() {			
		}		
	};
	PictureCallback myRawCallback=new PictureCallback(){
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {	
//			Bitmap bm=BitmapFactory.decodeByteArray(data, 0, data.length);
//			ImageView myImageView=(ImageView)findViewById(R.id.myImageView);
//			myImageView.setImageBitmap(bm);
//			isView=false;
//			mycamera.stopPreview();
//			mycamera.release();
//			mycamera=null;
//			initCamera();
		}	
	};
	/****/
	   Bitmap   adjustPhotoRotation(Bitmap bm, final int orientationDegree)
	   {

	               Matrix m = new Matrix();
	                m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);

	                try {

	              Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);

	                  return bm1;

	                 } catch (OutOfMemoryError ex) {
	                        }

	                  return null;

	   }

	/****/
	PictureCallback myjpegCallback=new PictureCallback(){

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Bitmap bm=BitmapFactory.decodeByteArray(data, 0, data.length);
			bm=adjustPhotoRotation( bm, 90);
			ImageView myImageView=(ImageView )findViewById(R.id.myImageView);
			myImageView.setImageBitmap(bm);
			
			saveBitmap(bm);//save pic to SD
			isView=false;
			mycamera.stopPreview();
			mycamera.release();
			mycamera=null;
			initCamera();
		}	
	};
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}





}
