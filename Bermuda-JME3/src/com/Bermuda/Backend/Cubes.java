package com.Bermuda.Backend;

import java.util.ArrayList;
import java.util.HashMap;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;

public class Cubes {
	
	private static ArrayList<Cube> cubes = new ArrayList<Cube>();
	private static HashMap<String, String> cubeVariants= new HashMap<String, String>();
	static
	{
	cubeVariants.put("earth","Textures/Terrain/splat/dirt.jpg");
	cubeVariants.put("grass","Textures/Terrain/splat/grass.jpg");
	}
	
	public static void create(int x, int y, int z, String cubeName)
	{
		Cube cube=new Cube(x,y,z,cubeVariants.get(cubeName));
		cubes.add(cube);
		cube.setId(cubes.size() -1);
		System.out.println("Size:"+cubes.size());
		BermudaApplication.getInstance().addCube(cube.getGeometry());
		System.out.println(cube.getGeometry().getName());
	}
	public static void remove(Geometry cube)
	{
		if(Integer.parseInt(cube.getName()) < cubes.size())
		{
		System.out.println(cube.getName());
		cubes.set(Integer.parseInt(cube.getName()),null);
		BermudaApplication.getInstance().removeCube(cube);
		}
	}
	public static void addResult(Mesh m)
	{
		Box b=(Box) m;
		Vector3f vector = b.getCenter();
		System.out.println("X-Achse"+vector.getX());
		System.out.println("y-Achse"+vector.getY());
		System.out.println("Z-Achse"+vector.getZ());
		int x=new Double(vector.getX()).intValue();
		int y=new Double(vector.getY()).intValue();
		int z=new Double(vector.getZ()).intValue();
		create(x,y+2,z,"earth");
	}
	public static void addResult(Vector3f vector)
	{
		int x=new Double(vector.getX()).intValue();
		int y=new Double(vector.getY()).intValue();
		int z=new Double(vector.getZ()).intValue();
		System.out.println(vector.getX());
		System.out.println(x);
		System.out.println(Math.ceil(vector.getX()));
		if(x%2!=0)
		{
			if(x>0)
			{
				x++;
			}
			else
			{
				x--;
			}
		}
		
		if(y%2!=0)
		{
			if(y>0)
			{
				y++;
			}
			else
			{
				y--;
			}
		}
		
		if(z%2!=0)
		{
			if(z>0)
			{
				z++;
			}
			else
			{
				z--;
			}
		}
		create(x,y,z,"earth");
	}
	
	public static void addStartCubes()
	{
		
	}
	/*
	public static void addResult(Vector3f vector)
	{
		System.out.println("X-Achse"+vector.getX());
		System.out.println("y-Achse"+vector.getY());
		System.out.println("Z-Achse"+vector.getZ());
		int x= new Double(Math.ceil(vector.getX())).intValue();
		if(x%2!=0)
		{
			x++;
		}
		int y= new Double(Math.ceil(vector.getY())).intValue();
		if(y%2!=0)
		{
			y++;
		}
		int z= new Double(Math.ceil(vector.getZ())).intValue();
		if(z%2!=0)
		{
			z++;
		}
		create(x,y,z,"grass");
		System.out.println("Hallo"+x+y+z);
	}
	
	*/
}
