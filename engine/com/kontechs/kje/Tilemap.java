package com.kontechs.kje;

import de.hsharz.beaver.Beaver; //TODO: Remove

public class Tilemap {
	private Tileset tileset;
	private int[][] map;
	private int levelWidth;
	private int levelHeight;

	public Tilemap(int[][] map) {
		tileset = new Tileset();
		this.map = map;
		levelWidth = map.length;
		levelHeight = map[0].length;
	}
	
	public void render(Painter painter, int xleft, int ytop, int width, int height) {
		int xstart = Math.max(xleft / Tileset.TILE_WIDTH, 0);
		int xend = Math.min((xleft + width) / Tileset.TILE_WIDTH + 1, levelWidth);
		int ystart = Math.max(ytop / Tileset.TILE_HEIGHT, 0);
		int yend = Math.min((ytop + height) / Tileset.TILE_HEIGHT + 1, levelHeight);
		for (int x = xstart; x < xend; ++x) for (int y = ystart; y < yend; ++y) {
			tileset.render(painter, map[x][y], x * Tileset.TILE_WIDTH, y * Tileset.TILE_HEIGHT);
		}
	}
	
	public boolean collides(int x, int y) {
		if (x < 0 || x / Tileset.TILE_WIDTH >= levelWidth) return true;
		if (y < 0 || y / Tileset.TILE_HEIGHT >= levelHeight) return false;
		int value = map[x / Tileset.TILE_WIDTH][y / Tileset.TILE_HEIGHT];
		
		return Scene.getInstance().getTilesProperties()[value].isCollides();
		
//		switch (value) {
//		case 1:
//		case 6:
//		case 7:
//		case 8:
//		case 26:
//		case 33:
//		case 39:
//		case 48:
//		case 49:
//		case 50:
//		case 53:
//		case 56:
//		case 60:
//		case 61:
//		case 62:
//		case 63:
//		case 64:
//		case 65:
//		case 67:
//		case 68:
//		case 70:
//		case 74:
//		case 75:
//		case 76:
//		case 77:
//		case 84:
//		case 86:
//		case 87:
//			return true;
//		default:
//			return false;
//		}
	}
	
	public boolean collideright(Sprite sprite) {
		Rectangle rect = sprite.collisionRect();
		boolean collided = false;
		
		//TODO: Remove the mighty beaver hack
		//java.lang.System.out.println();
		if (collides(rect.x + rect.width + 5, rect.y + 1) || collides(rect.x + rect.width + 5, rect.y + rect.height - 1)) {
			//beaver hack
			if(sprite instanceof Beaver)
				//set the beaver the beaver speed back, so it looks like he does not move
				sprite.x -= ((Beaver) sprite).getBeaverSpeed();
			else
				sprite.x = (rect.x + rect.width) / Tileset.TILE_WIDTH * Tileset.TILE_WIDTH - rect.width;
			collided = true;
		}
		return collided;
	}
	
	public boolean collideleft(Sprite sprite) {
		Rectangle rect = sprite.collisionRect();
		boolean collided = false;
		if (collides(rect.x, rect.y + 1) || collides(rect.x, rect.y + rect.height - 1)) {
			sprite.x = (rect.x / Tileset.TILE_WIDTH + 1) * Tileset.TILE_WIDTH;
			collided = true;
		}
		return collided;
	}
	
	public boolean collidedown(Sprite sprite) {
		Rectangle rect = sprite.collisionRect();
		boolean collided = false;
		if (collides(rect.x + 1, rect.y + rect.height) || collides(rect.x + rect.width - 1, rect.y + rect.height)) {
			sprite.y = (rect.y + rect.height) / Tileset.TILE_HEIGHT * Tileset.TILE_HEIGHT - rect.height;
			collided = true;
		}
		return collided;
	}
	
	public boolean collideup(Sprite sprite) {
		Rectangle rect = sprite.collisionRect();
		boolean collided = false;
		if (collides(rect.x + 1, rect.y) || collides(rect.x + rect.width - 1, rect.y)) {
			sprite.y = (rect.y / Tileset.TILE_HEIGHT + 1) * Tileset.TILE_HEIGHT;
			collided = true;
		}
		return collided;
	}
	
	public int getWidth() {
		return levelWidth;
	}
}