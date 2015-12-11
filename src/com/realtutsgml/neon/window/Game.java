/**
 * 
 */
package com.realtutsgml.neon.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.realtutsgml.neon.framework.GameObject;
import com.realtutsgml.neon.framework.KeyInput;
import com.realtutsgml.neon.framework.ObjectId;
import com.realtutsgml.neon.framework.Texture;
import com.realtutsgml.neon.objects.Block;
import com.realtutsgml.neon.objects.Flag;
import com.realtutsgml.neon.objects.Player;


/**
 * @author krystal.maughan
 *
 */
public class Game extends Canvas implements Runnable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6261436164361361187L;
	
	private boolean running = false;
	public Thread thread;
	
	public static int WIDTH, HEIGHT;
	
	private BufferedImage level, clouds = null;
	
	//Object
	Handler handler;
	Camera cam;
	static Texture tex;
	
	Random rand = new Random();
	
	public static int LEVEL = 1;
	
	private void init()
	{
		WIDTH = getWidth();
		HEIGHT = getHeight();
		
		tex = new Texture();
		
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("/level1.png"); // loading the level
		clouds = loader.loadImage("/clouds3.png"); // Loading background
		
		cam = new Camera(0, 0);
		handler = new Handler(cam);
		
		
		handler.LoadImageLevel(level);
		
		//handler.addObject(new Player(100, 100, handler, ObjectId.Player));
		//handler.createLevel();
		
		this.addKeyListener(new KeyInput(handler));
	}
	
	
	
	public synchronized void start(){
		
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
		
	}

	public void run()
	{
		init();
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
 		}
		
	}
	
	private void tick()
	{
		handler.tick();
		for(int i = 0; i < handler.object.size(); i++)
		{
			if(handler.object.get(i).getId() == ObjectId.Player)
			{
				cam.tick(handler.object.get(i));
			}
		
		}
		
	}
	
	private void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		//////////////////////////////////
	
		//Draw Here
		g.setColor(new Color(153, 204, 255));
		g.fillRect(0,0,getWidth(), getHeight());
		
		for(int xx = 0; xx < clouds.getWidth() * 2; xx += clouds.getWidth())
			g.drawImage(clouds,  xx,  1,  this);
		
		g2d.translate(cam.getX(), cam.getY());//begin of cam
		
		handler.render(g);
		
		
		g2d.translate(-cam.getX(), -cam.getY());//end of cam
		
		//////////////////////////////////
		
		g.dispose();
		bs.show();
		
	}
	
	public static Texture getInstance(){
		return tex;
	}
	
	public static void main(String args[]){
		new Window(800, 600, "Neon Plaform Game Prototype", new Game());
	}
}
