package br.ufrn.dimap.ttracker.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Member;
import java.net.URL;

import br.ufrn.dimap.ttracker.data.TestCoverageMapping;

public class FileUtil {
	
	public static void saveObjectToFile(Object obj, String fileDirectory, String fileName, String extension) {
		File dir = new File(fileDirectory);
		if(!dir.exists())
			dir.mkdir();
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(fileDirectory+"/"+fileName+"."+extension);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(obj);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe){
			ioe.printStackTrace();
		} finally {
			if(objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static Object loadObjectFromFile(String fileDirectory, String fileName, String extension) {
		File file = new File(fileDirectory+"/"+fileName+"."+extension);
		if(!file.exists())
			return null;
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			objectInputStream = new ObjectInputStream(fileInputStream);
			Object object = objectInputStream.readObject();
			objectInputStream.close();
			fileInputStream.close();
			return object;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(objectInputStream != null) {
				try {
					objectInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void saveTextToFile(String text, String fileDirectory, String fileName, String extension){
		fileDirectory = fileDirectory.replace('\\', '/');
		File dir = new File(fileDirectory);
		if (!dir.exists())
			dir.mkdirs();
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(fileDirectory+"/"+fileName+"."+extension);
			bw = new BufferedWriter(fw);
			bw.write(text);
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String loadTextFromFile(File file){
		String content = null;
		FileReader reader = null;
		try {
			reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			content = new String(chars);
			return content;
		} catch (IOException e) {
//			e.printStackTrace();
			return null;
		} finally {
			if(reader != null) {
				try {
						reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String getProjectFolderByResource(Class<?> aClass) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(getBuildFolderByResource(aClass)+"/projectFolder.txt"));
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return "C:/";
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public static Boolean getWasProjectBuildedByResource(Class<?> aClass, String projectName) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(getBuildFolderByResource(aClass)+"/Was"+projectName+"Builded.txt"));
			return (br.readLine().equals("1") ? true : false);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public static Integer getTestClassesSizeByResource(Class<?> aClass) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(getBuildFolderByResource(aClass)+"/testClassesSize.txt"));
			return Integer.valueOf(br.readLine());
		} catch (IOException e) {
			return -1;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public static String getTestCoverageMappingNameByResource(Class<?> aClass) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(getBuildFolderByResource(aClass)+"/testCoverageMappingName.txt"));
			return br.readLine();
		} catch (IOException e) {
			return "AllTests";
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public static Integer getTestCoverageMappingRevisionByResource(Class<?> aClass) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(getBuildFolderByResource(aClass)+"/revision.txt"));
			return Integer.valueOf(br.readLine());
		} catch (IOException e) {
			return 0;
		} catch(NumberFormatException nfe) {
			return 0;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public static String getResultFolderByResource(Class<?> aClass) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(getBuildFolderByResource(aClass)+"/resultFolder.txt"));
			return br.readLine();
		} catch (IOException e) {
			return "D:/result";
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public static String getProjectNameByResource(Class<?> aClass) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(getBuildFolderByResource(aClass)+"/projectName.txt"));
			return br.readLine();
		} catch (IOException e) {
			return "UnknowProjectName";
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public static String getBuildFolderByResource(Class<?> aClass) {
		if(aClass == null || aClass.getResource("") == null)
			System.out.println("Aqui!");
		String buildFolder = aClass.getResource("").toString();
		return buildFolder.substring(buildFolder.indexOf("/")+1,(buildFolder.length()-aClass.getPackage().getName().length()-2));
	}
	
}
