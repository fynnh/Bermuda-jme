package com.Bermuda.Backend;


	 
	import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
	import com.jme3.material.Material;
import com.jme3.math.Ray;
	import com.jme3.math.Vector3f;
	import com.jme3.scene.Geometry;
	import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
	import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;
	 
	/** Sample 2 - How to use nodes as handles to manipulate objects in the scene.
	 * You can rotate, translate, and scale objects by manipulating their parent nodes.
	 * The Root Node is special: Only what is attached to the Root Node appears in the scene. */
	public class BermudaApplication extends SimpleApplication {
	 private static BermudaApplication instance;
	 private Node cubes;
	 private Node ground;
	 private Node root;
	 private  Geometry mark;
	 
	    public static void main(String[] args){
	    	BermudaApplication app = new BermudaApplication();
	        app.start();
	        instance = app;
	    }
	 
	    @Override
	    public void simpleInitApp() {
	        initCrossHairs(); // a "+" in the middle of the screen to help aiming
	        initKeys();       // load custom key mappings
	        initMark();       // a red sphere to mark the hit
	        root = new Node("root");
	        cubes = new Node("cubes");
	        ground = new Node("ground");
	        rootNode.attachChild(root);
	        root.attachChild(cubes); // put this node in the scene
	        root.attachChild(ground);
	 
	        /** Attach the two boxes to the *pivot* node. */
	       addCubes();
	       makeGround();


	    }
	    protected void makeGround() {

		    Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		    Texture texture= assetManager.loadTexture("Textures/Terrain/splat/grass.jpg");
		    material.setTexture("ColorMap", texture);
	    	for(int i=0;i<=100;i+=2)
	    	{
	    		for(int i2=-4;i2<=100;i2+=2)
	    		{
	    		    Box box = new Box(new Vector3f(50-i, -4, 50-i2), 1, 1, 1);
	    		    Geometry cube = new Geometry("ground", box);
	    		    cube.setMaterial(material);
	    		    ground.attachChild(cube);
	    		}
	    	}
	    }
	    
	    
	    private void initKeys() {
	        inputManager.addMapping("Shoot",
	          new KeyTrigger(KeyInput.KEY_SPACE), // trigger 1: spacebar
	          new MouseButtonTrigger(MouseInput.BUTTON_LEFT)); // trigger 2: left-button click
	        inputManager.addMapping("Add",
	  	          new MouseButtonTrigger(MouseInput.BUTTON_RIGHT)); // trigger 3: right-button click
	        inputManager.addListener(actionListener, "Shoot");
	        inputManager.addListener(actionListener, "Add");
	      }
	      /** Defining the "Shoot" action: Determine what was hit and how to respond. */
	      private ActionListener actionListener = new ActionListener() {
	     
	        public void onAction(String name, boolean keyPressed, float tpf) {
	          if (name.equals("Shoot") && !keyPressed) {
	        	 
	            // 1. Reset results list.
	            CollisionResults results = new CollisionResults();
	            // 2. Aim the ray from cam loc to cam direction.
	            Ray ray = new Ray(cam.getLocation(), cam.getDirection());
	            // 3. Collect intersections between Ray and Shootables in results list.
	           cubes.collideWith(ray, results);
	           
	            if(results.size()>0)
	            {
	            Cubes.remove(results.getClosestCollision().getGeometry());
	            }
	          
	            // 5. Use the results (we mark the hit object)
	            if (results.size() > 0) {
	              // The closest collision point is what was truly hit:
	              CollisionResult closest = results.getClosestCollision();
	              // Let's interact - we mark the hit with a red dot.
	              mark.setLocalTranslation(closest.getContactPoint());
	              rootNode.attachChild(mark);
	            } else {
	              // No hits? Then remove the red mark.
	              rootNode.detachChild(mark);
	            }
	          }
	          else if(name.equals("Add") && !keyPressed)
	          {
	        	  System.out.println("y");
	        	  // 1. Reset results list.
		            CollisionResults results = new CollisionResults();
		            // 2. Aim the ray from cam loc to cam direction.
		            Ray ray = new Ray(cam.getLocation(), cam.getDirection());
		            // 3. Collect intersections between Ray and Shootables in results list.
		            root.collideWith(ray, results);
		            // 4. Print the results
		            if(results.size()>0)
		            {
		            Cubes.addResult(results.getClosestCollision().getGeometry().getMesh());
		            }
		            /*System.out.println("----- Collisions? " + results.size() + "-----");
		            for (int i = 0; i < results.size(); i++) {
		              // For each hit, we know distance, impact point, name of geometry.
		              float dist = results.getCollision(i).getDistance();
		              Vector3f pt = results.getCollision(i).getContactPoint();
		              float hit = results.getCollision(i).getGeometry().getLocalTranslation().getX();
		             String name1 = results.getCollision(i).getGeometry().getName();
		              System.out.println("* Collision #" + i);
		              System.out.println("  You shot " +" x:x: "+hit+ " at " + pt +name1+ ", " + dist + " wu away.");
		              
		            }*/
		            // 5. Use the results (we mark the hit object)
		            if (results.size() > 0) {
		              // The closest collision point is what was truly hit:
		              CollisionResult closest = results.getClosestCollision();
		              // Let's interact - we mark the hit with a red dot.
		              mark.setLocalTranslation(closest.getContactPoint());
		              rootNode.attachChild(mark);
		            } else {
		              // No hits? Then remove the red mark.
		              rootNode.detachChild(mark);
		            }
	          }
	        }
	      };
	    
	      /** A red ball that marks the last spot that was "hit" by the "shot". */
	      protected void initMark() {
	        Sphere sphere = new Sphere(30, 30, 0.2f);
	        mark = new Geometry("BOOM!", sphere);
	        Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
	        mark_mat.setColor("Color", ColorRGBA.Red);
	        mark.setMaterial(mark_mat);
	      }
	     
	      /** A centred plus sign to help the player aim. */
	      protected void initCrossHairs() {
	        guiNode.detachAllChildren();
	        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
	        BitmapText ch = new BitmapText(guiFont, false);
	        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
	        ch.setText("+"); // crosshairs
	        ch.setLocalTranslation( // center
	          settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
	          settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
	        guiNode.attachChild(ch);
	      }

	    
	    
	    public void addCubes()
	    {
	    	Cubes.addStartCubes();
	    }
	    
	    
	    public static BermudaApplication getInstance()
	    {
	    	return instance;
	    }
	    
	    public AssetManager getAssetManager()
	    {
	    	return assetManager;
	    }
	    

	    public void removeCube(Geometry cube)
	    {
	    	cubes.detachChild(cube);
	    }

		public void addCube(Geometry cube) {
			cubes.attachChild(cube);
			
		}
}
