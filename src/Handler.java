import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	private boolean up=false, right=false, left= false, down = false;

	public void tick() {
		for(int i=0; i<object.size();i++) {
			GameObject tempObject= object.get(i);
			
			tempObject.tick();
		}
	}
	public void render(Graphics g) {
		for(int i=0; i<object.size();i++) {
			GameObject tempObject= object.get(i);
			
			tempObject.render(g);
		}
	}
	public boolean isUp() {
		return up;
	}
	public void setUp(boolean up) {
		this.up = up;
	}
	public boolean isRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	public boolean isLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}
	public boolean isDown() {
		return down;
	}
	public void setDown(boolean down) {
		this.down = down;
	}
	public void addObject(GameObject pObject) {
		object.add(pObject);
	}
	public void removeObject(GameObject pObject) {
		object.remove(pObject);
	}



}
