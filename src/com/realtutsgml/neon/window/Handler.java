package com.realtutsgml.neon.window;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.realtutsgml.neon.framework.GameObject;
import com.realtutsgml.neon.framework.ObjectId;
import com.realtutsgml.neon.objects.Block;
import com.realtutsgml.neon.objects.Flag;
import com.realtutsgml.neon.objects.Player;

public class Handler
{
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	private GameObject tempObject;
	
	private Camera cam;
	
	public Handler(Camera cam){
		this.cam = cam;
	}
	
	public void tick()
	{
		for(int i = 0; i < object.size(); i++)
		{
			tempObject = object.get(i);
			
			tempObject.tick(object);
		}
		
	}
	public void render(Graphics g){
	
		for(int i = 0; i < object.size(); i++)
		{
			tempObject = object.get(i);
			
			tempObject.render(g);
		}
	}


	public void LoadImageLevel(BufferedImage image){
		int w = image.getWidth();
		int h = image.getHeight();
	
		System.out.println("width, height: " + w + " " + h);
	
		for(int xx = 0; xx < h; xx++){
			for (int yy = 0; yy < w; yy++){
				int pixel = image.getRGB(xx,  yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
			
			
				if(red == 255 && green == 255 & blue == 255) addObject(new Block(xx*32, yy*32, 0, ObjectId.Block));
				if(red == 128 && green == 128 & blue == 128) addObject(new Block(xx*32, yy*32, 1, ObjectId.Block));
				if(red == 0 && green == 0 & blue == 255) addObject(new Player(xx*32, yy*32, this, cam, ObjectId.Player));
				if(red == 255 && green == 216 & blue == 0) addObject(new Flag(xx*32, yy*32, ObjectId.Flag));
		}
	}
}
	
	
	public void clearLevel(){
		object.clear();
	}
	
	public void addObject(GameObject object){
		this.object.add(object);
	}
	
	public void removeObject(GameObject object){
		this.object.remove(object);
	}
		

}
