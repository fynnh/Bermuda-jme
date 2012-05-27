package com.Bermuda.Backend;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.JmeSystem;
import com.jme3.texture.Texture;

public class Cube extends Geometry{
	
	private Box box;
	private Geometry cube;
	private Material material;
	private Texture texture;
	private int id;
	
	AssetManager assetManager;

	public Cube(float x, float y, float z, String filepath)
	{
		AssetManager assetManager = BermudaApplication.getInstance().getAssetManager();

	    box = new Box(new Vector3f(x, y, z), 1, 1, 1);
	    cube = new Geometry(String.valueOf(id), box);
	    material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
	    texture= assetManager.loadTexture(filepath);
	    material.setTexture("ColorMap", texture);
	    cube.setMaterial(material);
	}
	public Geometry getGeometry()
	{
		return cube;
	}
	public void setId(int id)
	{
		this.id=id;
		cube.setName(String.valueOf(id));
	}
	   
}
