package br.ufrn.dimap.ttracker.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectUtil {

	public static Object isSerializable(Object object) {
		ByteArrayOutputStream bAOS = null;
		try {
			bAOS = new ByteArrayOutputStream();
			new ObjectOutputStream(bAOS).writeObject(object);
			return object;
		} catch (IOException e) {
			return object.toString();
		} finally {
			if(bAOS != null) {
				try {
					bAOS.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
