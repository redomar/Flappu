package com.redomar.flappu;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

public class Main implements Runnable{

	//Setting private variables such as screen size etc...
	private int width = 854;
	private int height = 480;
	private String title = "Flappu";

	private boolean running = false;
	private Thread thread;

	public void start(){
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public void run(){
		try {
			// Sets the display frame to the size and names the window to Flappu.
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			
			/*
			 * Sets OpenGL version to 3.3
			 * There is a bug in LWJGL 2.9.1 where context(3, 3)
			 * in Mac will run OpenGL 2.1, So here I set context(3, 2)
			 * and should run the OpenGL 3.3+ on Mac.
			 */
			ContextAttribs context = new ContextAttribs(3, 3);
			if(System.getProperty("os.name").contains("Mac")){
				context = new ContextAttribs(3, 2);
			}
			
			// Loads the display to memory and opens it in a window.
			Display.create(new PixelFormat(), context.withProfileCore(true));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		while(running){
			// Updates the display every tick.
			Display.update();
			if(Display.isCloseRequested()){
				running = false;
			}
		}
		
		// Unloads the display from memory and closes the window.
		Display.destroy();
	}

	public static void main(String[] args) {
		// Starts a thread in the start() class and sets runnable to true.
		new Main().start();
	}

}
