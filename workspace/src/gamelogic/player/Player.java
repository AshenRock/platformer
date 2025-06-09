package gamelogic.player;

import java.awt.Color;
import java.awt.Graphics;

import gameengine.PhysicsObject;
import gameengine.graphics.MyGraphics;
import gameengine.hitbox.RectHitbox;
import gamelogic.Main;
import gamelogic.level.Level;
import gamelogic.tiles.Tile;

public class Player extends PhysicsObject{
	public float walkSpeed = 1000;
	public float jumpPower = 1350;
	public boolean isJumping = false;
	public static int jumpCount = 0;
	public boolean jumpwait = false;
	

	public Player(float x, float y, Level level) {
	
		super(x, y, level.getLevelData().getTileSize(), level.getLevelData().getTileSize(), level);
		int offset =(int)(level.getLevelData().getTileSize()*0.1); //hitbox is offset by 10% of the player size.
		this.hitbox = new RectHitbox(this, offset,offset, width -offset, height - offset);
	}

	@Override
	public void update(float tslf) {
		super.update(tslf);
		
		movementVector.x = 0;
		if(PlayerInput.isLeftKeyDown()) {
			movementVector.x = -walkSpeed;
			if(Level.Touchingwater){
				movementVector.x = -walkSpeed * 2;
			}
		}
		if(PlayerInput.isRightKeyDown()) {
			movementVector.x = +walkSpeed;
			if(Level.Touchingwater){
				movementVector.x = +walkSpeed * 2;
			}
		}
		if(PlayerInput.isJumpKeyDown()&& isJumping == false && jumpwait) {
			movementVector.y = -jumpPower;
			jumpCount++;
			jumpwait = false;
		}
		if(PlayerInput.isJumpKeyDown() == false){
			jumpwait = true;
		}
		if(jumpCount>=2){
			isJumping = true;
		}
		if  (jumpCount < 2){
			isJumping = false;
		}
		
		if(collisionMatrix[BOT] != null){
			jumpCount = 0;
		} 
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		MyGraphics.fillRectWithOutline(g, (int)getX(), (int)getY(), width, height);
		
		if(Main.DEBUGGING) {
			for (int i = 0; i < closestMatrix.length; i++) {
				Tile t = closestMatrix[i];
				if(t != null) {
					g.setColor(Color.RED);
					g.drawRect((int)t.getX(), (int)t.getY(), t.getSize(), t.getSize());
				}
			}
		}
		
		hitbox.draw(g);
	}
}
