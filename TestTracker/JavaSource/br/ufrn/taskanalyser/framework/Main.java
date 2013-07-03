package br.ufrn.taskanalyser.framework;

import java.io.File;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import com.thoughtworks.xstream.XStream;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MethodData mD = new MethodData("signature()");
		XStream xStream = new XStream();
		System.out.println(xStream.toXML(mD));
		File file = new File(".");
		try {
			System.out.println(file.getCanonicalPath().toString());
			ServletContext sC = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			System.out.println(sC.getRealPath("/"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
