package com.example.handler;

public class MyThread extends Thread{

	MainActivity activity;
	int what =1;
	public MyThread(MainActivity activity){
		this.activity=activity;
	}
	public void run(){
	while(true){
		//	while(MainActivity.ImageState){
		if(MainActivity.imageState==0)
				activity.myHandler.sendEmptyMessage((what++)%11);
				try{
//				Thread.sleep(2000);
					Thread.sleep(MainActivity.speed);
				}
				catch(Exception e){
					e.printStackTrace();
				}
		//	}
		}
	}
}
