package fr.iutinfo.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fr.iutinfo.bins.Level;

public class SerializeTools {


	/*public static String serializeLevel(Level level) {
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream so = new ObjectOutputStream(bo);
			so.writeObject(level);
			so.flush();
			return bo.toString();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "";
	}
	
	public static Level deserializeLevel(String serializedLevel) {
		try {
		     byte b[] = serializedLevel.getBytes(); 
		     ByteArrayInputStream bi = new ByteArrayInputStream(b);
		     ObjectInputStream si = new ObjectInputStream(bi);
		     Level obj = (Level) si.readObject();
		 } catch (Exception e) {
		     System.out.println(e);
		 }
		return null;
	}*/
	
}
