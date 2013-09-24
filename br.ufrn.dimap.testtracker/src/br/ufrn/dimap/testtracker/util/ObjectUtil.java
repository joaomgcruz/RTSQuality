package br.ufrn.dimap.testtracker.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectUtil {

	public static Object isSerializable(Object object) {
		try {
			new ObjectOutputStream(new ByteArrayOutputStream()).writeObject(object);
			return object;
		} catch (IOException e) {
			return object.toString();
		}
	}
	
}
