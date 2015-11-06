package cn.edu.seu.integrabilityevaluator.parser;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import cn.edu.seu.integrabilityevaluator.model.JarClassModel;
import cn.edu.seu.integrabilityevaluator.model.JarMethodModel;

public class AnalysisJarFile {
	
	public static List<JarClassModel> jarClassModels = new LinkedList<>();
	
	static class MyClassLoader extends URLClassLoader {

        public MyClassLoader(URL[] urls) {
            super(urls);
        }

        public MyClassLoader(URL[] urls, ClassLoader parent) {
            super(urls, parent);
        }

        public void addJar(URL url) {
            this.addURL(url);
        }

    }
	
	public static List<String> getDependJarFiles(String filepath){
		List<String> jarFileList = new ArrayList<>();		
		File file = new File(filepath);		
		if (file.isDirectory() == true) {
		    	readJarFiles(file,jarFileList);
		}
    	return jarFileList;	
	
	}
	
	private static void readJarFiles(File file, List<String> fList){
    	if (file != null) {
			if (file.isDirectory()) {
				File f[] = file.listFiles();
				if (f != null) {
					for (int i = 0; i < f.length; i++) {
						readJarFiles(f[i],fList);
					}
				}
			} else if(file.getName().endsWith(".jar")){
				try {
					fList.add(file.getCanonicalPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    }
	
	public static List<JarClassModel> getJarMethod(String jarFile,String dependfile) throws Exception {
		
        String NORMAL_METHOD= "waitequalsnotifynotifyAlltoStringhashCodegetClass"; 
        List<JarClassModel> jarClassModels = new ArrayList<>();
 //       List<String[]> a = new ArrayList<String[]>();


/*        MyClassLoader myclassLoader = new MyClassLoader(urls,null);
*/        
        try {
            //通过jarFile 和JarEntry得到所有的类
            JarFile jar = new JarFile(jarFile);//
            Enumeration<JarEntry> e = jar.entries();
            while (e.hasMoreElements()) {
                JarEntry entry = (JarEntry) e.nextElement();
                //entry.getMethod()
                if (entry.getName().indexOf("META-INF") < 0) {
                    String sName = entry.getName();
                    String substr[] = sName.split("/");
                    String pName = "";
                    for (int i = 0; i < substr.length - 1; i++) {
                        if (i > 0)
                            pName = pName + "." + substr[i];
                        else
                            pName = substr[i];
                    }
                    //System.out.println(pName);
                    if (sName.indexOf(".class") < 0)
                    {
                        sName = sName.substring(0, sName.length() - 1);
                    }
                    else
                    {
                         //通过URLClassLoader.loadClass方法得到具体某个类 
                    	URL[] urls=new URL[]{};
                    	MyClassLoader myClassLoader=new MyClassLoader(urls,null);
                        if (dependfile != null) {
							List<String> dependjarlist = getDependJarFiles(dependfile);
	                    	for (String string : dependjarlist) {
								URL url=new URL("file:"+string);
								myClassLoader.addJar(url);
							}
						}
                    	                   	
                       
                        URL url1=new URL("file:"+jarFile);
                        myClassLoader.addJar(url1);
                        
                        String ppName = sName.replace("/", ".").replace(".class", "");
                        Class<?> myClass = null;
                        try {
							 myClass = myClassLoader.loadClass(ppName);
						} catch (Exception e2) {
							// TODO: handle exception
							System.out.println("class not found exception");
				        	e2.printStackTrace();
						}
                       
                        //通过getMethods得到类中包含的方法
                        
	                     System.out.println("------------------------------------------------");
	                     System.out.println("sName:"+sName);
	                     System.out.println("pName:"+pName);
	                     System.out.println("ppName:"+ppName);
						
	                     Method m[] = myClass.getMethods();
	                     if (m.length>0) {
	                    	JarClassModel jarClassModel = new JarClassModel(pName,ppName);
					
	                        for(int i=0; i<m.length; i++){
	                            String sm = m[i].getName();
	                            
	                            if (NORMAL_METHOD.indexOf(sm) <0)
	                            {
	                            	JarMethodModel jarMethodModel = new JarMethodModel(sm);
//	                            	System.out.println("sm:"+sm);
	                            	Type[] parameter = m[i].getGenericParameterTypes();
	                            	List<String> paraList = new ArrayList<String>();
	                            	for (int j = 0; j < parameter.length; j++) {
	                            		if (parameter[j].toString().contains(" ")) {
//	                            			System.out.println(parameter[j].toString().split(" ")[0]);
//											System.out.println(parameter[j].toString().split(" ")[1]);
											paraList.add(parameter[j].toString().split(" ")[1]);
										}else {
//											System.out.println("para:"+parameter[j].toString());
											paraList.add(parameter[j].toString());
										}                       		
										
									}
	                            	jarMethodModel.setParameters(paraList);
	                            	jarClassModel.addmethod(jarMethodModel);
	                            }
	                            
	                        }
	                        jarClassModels.add(jarClassModel);
	                     }
                        
//	                      System.out.println("------------------------------------------------");
                    }

                }
            }
            return jarClassModels;
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            //e1.printStackTrace();
        } catch (NullPointerException e2){
        	//e2.printStackTrace();
        } catch (Exception e) {
			// TODO: handle exception
        	//e.printStackTrace();
		}
        return jarClassModels;
    }
}
