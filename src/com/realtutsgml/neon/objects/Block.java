/**
 * 
 */
package com.realtutsgml.neon.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.realtutsgml.neon.framework.GameObject;
import com.realtutsgml.neon.framework.ObjectId;
import com.realtutsgml.neon.framework.Texture;
import com.realtutsgml.neon.window.Game;

/**
 * @author krystal.maughan
 *
 */
public class Block extends GameObject
{
	
	Texture tex = Game.getInstance();
	private int type;
	
	public Block(float x, float y, int type, ObjectId id){
		super(x, y, id);
		this.type = type;
	}

	
	public void tick(LinkedList<GameObject> object) {
		
	}

	@Override
	public void render(Graphics g) 
	{
		if(type == 0) //dirt block
			g.drawImage(tex.block[0], (int)x, (int)y, null);
		if(type == 1) //grass block
			g.drawImage(tex.block[1], (int)x, (int)y, null);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}

}
