import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Player extends GameObject{

	private Handler handler;
	private Game game;
	
	private BufferedImage player_knife;
	private BufferedImage[] player_image= new BufferedImage[19];
	private BufferedImage[] player_feet= new BufferedImage[14];
	Animation anim;
	Animation anim_feet;
	private enum Equip{
		Gun(),
		Knife(),
		Rifle();
	}
	
	public Player(int x, int y, ID id,Handler handler,Game game,SpriteSheet ss) {
		super(x, y, id,ss);
		this.handler = handler;
		this.game = game;
		
		BufferdImageLoader loader = new BufferdImageLoader();
		player_image[0] =loader.loadImage("/survivor-move_handgun_0.png");
		player_image[1] =loader.loadImage("/survivor-move_handgun_1.png");
		player_image[2] =loader.loadImage("/survivor-move_handgun_2.png");
		player_image[3] =loader.loadImage("/survivor-move_handgun_3.png");
		player_image[4] =loader.loadImage("/survivor-move_handgun_4.png");
		player_image[5] =loader.loadImage("/survivor-move_handgun_5.png");
		player_image[6] =loader.loadImage("/survivor-move_handgun_6.png");
		player_image[7] =loader.loadImage("/survivor-move_handgun_7.png");
		player_image[8] =loader.loadImage("/survivor-move_handgun_8.png");
		player_image[9] =loader.loadImage("/survivor-move_handgun_9.png");
		player_image[10] =loader.loadImage("/survivor-move_handgun_10.png");
		player_image[11] =loader.loadImage("/survivor-move_handgun_11.png");
		player_image[12] =loader.loadImage("/survivor-move_handgun_12.png");
		player_image[13] =loader.loadImage("/survivor-move_handgun_13.png");
		player_image[14] =loader.loadImage("/survivor-move_handgun_14.png");
		
		player_feet[0] = loader.loadImage("/survivor-run_0.png");
		player_feet[1] = loader.loadImage("/survivor-run_1.png");
		player_feet[2] = loader.loadImage("/survivor-run_3.png");
		player_feet[3] = loader.loadImage("/survivor-run_4.png");
		player_feet[4] = loader.loadImage("/survivor-run_6.png");
		player_feet[5] = loader.loadImage("/survivor-run_7.png");
		player_feet[6] = loader.loadImage("/survivor-run_9.png");
		player_feet[7] = loader.loadImage("/survivor-run_10.png");
		player_feet[8] = loader.loadImage("/survivor-run_12.png");
		player_feet[9] = loader.loadImage("/survivor-run_13.png");
		player_feet[10] = loader.loadImage("/survivor-run_14.png");
		player_feet[11] = loader.loadImage("/survivor-run_16.png");
		player_feet[12] = loader.loadImage("/survivor-run_18.png");
		player_feet[13] = loader.loadImage("/survivor-run_19.png");
		
		player_knife = loader.loadImage("/survivor-idle_knife_19.png");
		
		anim = new Animation(3, player_image[0],player_image[1],player_image[2],player_image[3],player_image[4],player_image[5],player_image[6],player_image[7],player_image[8],player_image[9],player_image[10],player_image[11],player_image[12],player_image[13]);
		anim_feet = new Animation(3,player_feet[0], player_feet[1],player_feet[2],player_feet[3],player_feet[4],player_feet[5],player_feet[6],player_feet[7],player_feet[8],player_feet[9],player_feet[10],player_feet[11],player_feet[12],player_feet[13]);
	}

	public void tick() {
		x += velX;
		y += velY;
		
		collision();
		
		//movement
		if(handler.isUp())velY = -5;
		else if(!handler.isDown()) velY =0;
		
		if(handler.isDown())velY = 5;
		else if(!handler.isUp()) velY =0;
		
		if(handler.isRight())velX = 5;
		else if(!handler.isLeft()) velX =0;
		
		if(handler.isLeft())velX = -5;
		else if(!handler.isRight()) velX =0;
		
		anim.runAnimation();
		anim_feet.runAnimation();

	}
	
    public void collision() { //collision handling. Runs 60 times a second
        LinkedList<GameObject> löschListe = new LinkedList<GameObject>();
        for(GameObject tempObject : handler.object) { //check all game objects
            
            if(tempObject.getId()==ID.Block) { //if its a Block
                if(getBounds().intersects(tempObject.getBounds())) { //if Block intersects this
                    x +=velX *-1;
                    y +=velY *-1;
                }
            }
            if(tempObject.getId()==ID.Crate) { //if its a Crate
                if(getBounds().intersects(tempObject.getBounds())) { //if Crate intersects this
                    game.ammo+=10;
                    löschListe.add(tempObject);
                }
            }
            if(tempObject.getId()==ID.Enemy) { //if its an Enemy
                if(getBounds().intersects(tempObject.getBounds())) { //if Enemy intersects this
                    game.hp--;
                }
            }
        }
        for(GameObject g : löschListe){
            handler.removeObject(g);
        }
    }
    
	public void render(Graphics g) {
		if(velX==0 && velY==0 && game.ammo!=0) {
			g.drawImage(player_feet[6],x,y+10,50,50,null);
			g.drawImage(player_image[0],x,y,64,64,null);
			rotate(g);
		}
		else if(game.ammo!=0) {
			anim_feet.drawPlayerFeetAnimation(g,x,y+10,0);
			anim.drawPlayerAnimation(g, x, y, 0);
		}
		if(velX==0 && velY==0 && game.ammo==0) {
			g.drawImage(player_feet[6],x,y+10,50,50,null);
			g.drawImage(player_knife,x,y,64,64,null);
		}
		else if(game.ammo==0) {
			anim_feet.drawPlayerFeetAnimation(g,x,y+10,0);
			g.drawImage(player_knife,x,y,64,64,null);
		}
			
			
		
	}

	public Rectangle getBounds() {
		return new Rectangle(x+10,y+15,43,40);
	}
	public void rotate(Graphics g) {
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		int MX = (int) b.getX();
		int MY = (int) b.getY();

		double angle = Math.atan2(y - MY, x - MX) - Math.PI / 2;

		((Graphics2D)g).rotate(angle, y, x);
	}

}
