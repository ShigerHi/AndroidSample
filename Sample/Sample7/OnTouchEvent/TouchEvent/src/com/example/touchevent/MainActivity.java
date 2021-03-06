package com.example.touchevent;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;


public class MainActivity extends Activity {
	
	MyView myview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myview =new MyView(this);
        setContentView(myview);
    }
    public boolean onTouchEvent(MotionEvent event){
    	switch(event.getAction()){
    	case MotionEvent.ACTION_DOWN:
    		myview.x=(int)event.getX();
    		myview.y=(int)event.getY()-52;
    		myview.postInvalidate();
    		//myview.draw();
    		break;
    	case MotionEvent.ACTION_MOVE:
    		myview.x=(int)event.getX();
    		myview.y=(int)event.getY()-52;
    		myview.postInvalidate();
    		//myview.draw();
    		break;
    	case MotionEvent.ACTION_UP:
    		myview.x=-100;
    		myview.y=-100;	
    		myview.postInvalidate();
    		//myview.draw();
    		break;
    		
    	}
		return super.onTouchEvent(event);
    	
    }

    class MyView extends View{
    	//Paint paint;
    	Paint p;
    	private int x=50;
    	private int y=50;
    	private int a=50;
    	private int x_xin;
    	private int y_xin;
    	private Boolean B = true;
    	private Canvas canvas;
    	private SurfaceHolder sfh;
    	private String str = "Hello world!";
		public MyView(Context context) {
			
			super(context);
		//	paint=new Paint();
			p=new Paint();
			
		}
		
	
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			if (holder.isCreating()) {
				y = this.getMeasuredHeight() / 2;
				x = this.getMeasuredWidth() / 2;
				x_xin = x;
				y_xin = y;
				Log.d("x", "" + x);
				Log.d("y", "" + y);
				// 在surfaceCreat方法中可以获取view的长度和宽度，因为此时surface已经生成
				// 在此类的构造方法中获取长度和宽度将返回0，因为Callback方法重写了surface的生成。

				B = true;
				draw();
				// th = new Thread(this);
				// th.start();
				Log.d("thread_start", "start");
			}
		}
		/**/
		protected void onDraw(Canvas canvas){

			canvas.drawColor(Color.WHITE);
			canvas.drawCircle(x, y, 30, p);
			super.onDraw(canvas);
		}
    	
		/**/
		void draw() {

			try {

				canvas = sfh.lockCanvas();
				if (canvas != null) {
					canvas.drawColor(Color.WHITE);
					canvas.drawCircle(x, y, 30, p);
					canvas.drawText(str, x, y + 50, p);

				}

			} catch (Exception e) {
				Log.v("Bug", "This is a bug.");

			} finally {
				if (canvas != null) {
					sfh.unlockCanvasAndPost(canvas);
				}

			}

		}

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
