package com.example.zoom;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

public class GL1Activity extends Activity implements OnTouchListener {
	
	GLSurfaceView ourSurface;
	float x,y;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		ourSurface = new GLSurfaceView(this);
		ourSurface.setOnTouchListener(this);
		x=0;
		y=0;
		ourSurface.setRenderer(new GL1Renderer()); //links to GL1Renderer
		setContentView(ourSurface);
	}

	@Override
	protected void onPause() {
		super.onPause();
		ourSurface.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		ourSurface.onResume();		
		}
	

	@Override
	public boolean onTouch(View OurSurface, MotionEvent event) {
		x = event.getX();
		y = event.getY();
		return false;
		} 

	public class GL1Renderer implements  Renderer {
		private GL1Square square;
		float zoomz=0;
		public GL1Renderer (){
			square = new GL1Square();
		}
		
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig eglconfig) {
		gl.glDisable(GL10.GL_DITHER);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		gl.glClearColor(.1f, .1f, .1f, 1); //painting background
		gl.glClearDepthf(1f);	
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			// TODO Auto-generated method stub
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			GLU.gluLookAt(gl, 0, 0, -5, 0, 0, 0, 0, 3, 0);
			if (x != 0 && y != 0){	
				
			gl.glTranslatef(0, 0, zoomz);
				if (zoomz >-5) {
				zoomz-=.1;	
					}			
				if (zoomz<-5 && x>0 && y>0){
					x = 0;
					y = 0;
					zoomz =0;
				}
			};
			square.draw(gl);
			/*if (x>0 && y>0){
				x = 0;
				y = 0;
			}*/
			}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			// TODO Auto-generated method stub
			gl.glViewport(0, 0, width, height);
			float ratio = (float) width/height;
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glFrustumf(-ratio, ratio, -1, 1, 1, 25); 
			}
		}





	
	}

	

		



