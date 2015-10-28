package cn.seu.edu.integrabilityevaluator.dbconnect;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ClassChangeabilityConnector extends DBConnector{

	private Connection connect = null;
	private String projectName;
	private String version;
	
/*	public ClassChangeabilityConnector(){
		super();
		connect = getConnection();
	}*/

	public ClassChangeabilityConnector(String project, String version) {
		// TODO Auto-generated constructor stub
		super();
		connect = getConnection();
		this.projectName = project;
		this.version = version;
	}

	public void importNameUpatedate(String packageName, String className,  String importName,String importclassName) {
		try {

			Statement stmt = connect.createStatement();
			String sql = "insert into `" + dBname + "`.`class_packageinfo` (`pkgName`, `className`, `projName`, `verID`, `importPkgName`, `importclassname`) VALUES ('"
					+ packageName
					+ "','"					
					+ className
					+ "','"
					+ projectName
					+ "','"
					+ version
					+ "','" 
					+ importName  
					+ "','"
					+ importclassName + "')";

			try {
				stmt.executeUpdate(sql);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("already insert!");
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("insert failed!");
		}

	}
	
	
	public ArrayList<String> getpackageName(){
		ArrayList<String> list = new ArrayList<String>();
		try {

			Statement stmt = connect.createStatement();
			String sql = "SELECT pkgname FROM " + dBname + ".class_packageinfo where ProjName = '"
						+ projectName 
						+ "' and verID = '"
						+ version 
						+ "' group by pkgname";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString("pkgname"));
				//System.out.println(rs.getString("pkgname"));
				}	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return list; 

	}
	//分析耦合度在class级别
	public ArrayList<String> getClassName(String projectName, String version){
		ArrayList<String> list = new ArrayList<String>();
		try {

			Statement stmt = connect.createStatement();
			String sql = "SELECT distinct pkgname, classname FROM " + dBname + ".class_packageinfo where verID = '"					
					+ version
					+ "' and projName = '" 
					+ projectName + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String str = rs.getString("pkgname") + "$" + rs.getString("classname");
				list.add(str);
				System.out.println(rs.getString("pkgname")+"$"+rs.getString("classname"));
				}	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getClassName run failed!");
		}
		
		return list;
	}
	
	public ArrayList<String> ClassChangeabilityInfo(String packageName,String className){
		
		ArrayList<String> rStrings = new ArrayList<String>();
		try {
			Statement stmt = connect.createStatement();

			// / couple efferent
			String cestr = "Select  count(distinct importpkgName, importClassName) as result FROM " + dBname + ".class_packageinfo where pkgname = '"
					+ packageName
					+ "' and className = '"
					+ className
					+ "' and verID = '"
					+ version
					+ "' and projName = '" 
					+ projectName + "'";

			rStrings.add(packageName+"$"+className);

			ResultSet rs;
			int ce = 0;
			int ca = 0;

			rs = stmt.executeQuery(cestr);
			while (rs.next()) {
				ce = rs.getInt("result");
				rStrings.add("ce: " + ce);
			}

			cestr = "Select  distinct importpkgName, importClassName  FROM " + dBname + ".class_packageinfo where pkgname = '"
					+ packageName
					+ "' and verID = '"
					+ version
					+ "' and projName = '" + projectName + "'";
			rs = stmt.executeQuery(cestr);
			while (rs.next()) {
				String str = rs.getString("importpkgName")+"$" +rs.getString("importClassName");
//				rStrings.add(str);
			}

			String castr = "Select  count(distinct pkgName, className) as result FROM " + dBname + ".class_packageinfo where importpkgname = '"
					+ packageName
					+ "' and importClassName = '"
					+ className
					+ "' and verID = '"
					+ version
					+ "' and projName = '" 
					+ projectName + "'";


			rs = stmt.executeQuery(castr);
			while (rs.next()) {
				ca = rs.getInt("result");
				rStrings.add("ca: " + ca);
			}

			castr = "Select  distinct pkgName, className FROM " + dBname + ".class_packageinfo where importpkgname = '"
					+ packageName
					+ "' and importClassName = '"
					+ className
					+ "' and verID = '"
					+ version
					+ "' and projName = '" + projectName + "'";
			rs = stmt.executeQuery(castr);
			while (rs.next()) {
				String str = rs.getString("pkgName")+"$" +rs.getString("ClassName");
//				rStrings.add(str);
			}

			double changeability = 1.0*ce/(ca+ce);
			DecimalFormat df = new DecimalFormat("0.00");
			rStrings.add("changeability:"+df.format(changeability));

			System.out.println("-------------------------------------------------------------");
			for (String string : rStrings) {
				System.out.println(string);
			}
			System.out.println("-------------------------------------------------------------");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed to run changeability query!");
		}	
		
		return rStrings;
	}	
	
	

		
	//传出耦合计数
	public int packageEfferentCouplingsCount(String packageName){
		int ce = 0;
		try {
			Statement stmt = connect.createStatement();

			// / efferent  couplings 被该包依赖的外部包数目
			String cestr = "Select  count(distinct importPkgName) as result FROM " + dBname + ".class_packageinfo where pkgname = '"
					+ packageName
					+ "' and verID = '"
					+ version
					+ "' and projName = '" + projectName + "'";

			ResultSet rs;
			rs = stmt.executeQuery(cestr);
			while (rs.next()) {
				ce = rs.getInt("result");
			}	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed to run changeability query!");
			ce = -1;
		}		
		return ce;
	}
	
	
	
	public ArrayList<String> packageEffernetCouplingslist(String packageName){

		ArrayList<String> rStrings = new ArrayList<String>();
		
		try {
			ResultSet rs;
			Statement stmt = connect.createStatement();
			String cestr = "Select  distinct importPkgName as result FROM " + dBname + ".class_packageinfo where pkgName = '"
					+ packageName
					+ "' and verID = '"
					+ version
					+ "' and projName = '" + projectName + "'";
			rs = stmt.executeQuery(cestr);
			while (rs.next()) {
				String str = rs.getString("result");
				rStrings.add(str);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed to run changeability query!");
		}		
		return rStrings;
	}
	
	public ArrayList<String> class_packageEffernetCouplingslist(String packageName){

		ArrayList<String> rStrings = new ArrayList<String>();
		
		try {
			ResultSet rs;
			Statement stmt = connect.createStatement();
			String cestr = "Select   distinct importpkgName, importClassName  FROM " + dBname + ".class_packageinfo where pkgName = '"
					+ packageName
					+ "' and verID = '"
					+ version
					+ "' and projName = '" + projectName + "'";
			rs = stmt.executeQuery(cestr);
			while (rs.next()) {
				String str = rs.getString("importpkgName");
				str += "." +  rs.getString("importclassname");
				rStrings.add(str);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed to run changeability query!");
		}		
		return rStrings;
	}
	
	
	public ArrayList<String> classImportpkg(String packageName){

		ArrayList<String> rStrings = new ArrayList<String>();
		
		try {
			ResultSet rs;
			Statement stmt = connect.createStatement();
			String cestr = "Select   distinct importpkgName, importClassName,classname  FROM " + dBname + ".class_packageinfo where pkgName = '"
					+ packageName
					+ "' and verID = '"
					+ version
					+ "' and projName = '" + projectName + "'";
			rs = stmt.executeQuery(cestr);
			while (rs.next()) {
				String str = rs.getString("classname")+"+";
				str += rs.getString("importpkgName");
				str += "." +  rs.getString("importclassname");
				rStrings.add(str);				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed to run changeability query!");
		}		
		return rStrings;
	}
	
	//传入耦合计数
	public int packageAfferentCouplingsCount(String packageName){
		int ca = 0;
		try {
			Statement stmt = connect.createStatement();

			// / afferent  couplings 该包依赖的外部包数目
			String castr = "Select  count(distinct pkgName) as result FROM " + dBname + ".class_packageinfo where importPkgName = '"
					+ packageName
					+ "' and verID = '"
					+ version
					+ "' and projName = '" + projectName + "'";


			ResultSet rs;
			rs = stmt.executeQuery(castr);
			while (rs.next()) {
				ca = rs.getInt("result");
			}	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed to run changeability query!");
			ca = -1;
		}		
		return ca;
	}
	
	
	public ArrayList<String> packageAffernetCouplingslist(String packageName){

		ArrayList<String> rStrings = new ArrayList<String>();
		
		try {
			ResultSet rs;
			Statement stmt = connect.createStatement();
			String castr = "Select  distinct pkgName as result FROM " + dBname + ".class_packageinfo where importPkgName = '"
					+ packageName
					+ "' and verID = '"
					+ version
					+ "' and projName = '" + projectName + "'";
			rs = stmt.executeQuery(castr);
			while (rs.next()) {
				String str = rs.getString("result");
				rStrings.add(str);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed to run changeability query!");
		}		
		return rStrings;
	}
	
	public ArrayList<String> class_packageAffernetCouplingslist(String packageName){

		ArrayList<String> rStrings = new ArrayList<String>();
		
		try {
			ResultSet rs;
			Statement stmt = connect.createStatement();
			String castr = "Select  distinct pkgName, className FROM " + dBname + ".class_packageinfo where importPkgName = '"
					+ packageName
					+ "' and verID = '"
					+ version
					+ "' and projName = '" + projectName + "'";
			rs = stmt.executeQuery(castr);
			while (rs.next()) {
				String str = rs.getString("pkgname");
				str += "." + rs.getString("classname");
				rStrings.add(str);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed to run changeability query!");
		}		
		return rStrings;
	}
	
	
	
	public ArrayList<String> packageChangeabilityInfo(String packageName){
		
		ArrayList<String> rStrings = new ArrayList<String>();
		try {
			Statement stmt = connect.createStatement();

			// / efferent  couplings 被该包依赖的外部包数目
			String cestr = "Select  count(distinct importPkgName) as result FROM " + dBname + ".class_packageinfo where pkgName = '"
					+ packageName
					+ "' and verID = '"
					+ version
					+ "' and projName = '" + projectName + "'";

			rStrings.add(packageName);

			ResultSet rs;
			int ce = 0;
			int ca = 0;

			rs = stmt.executeQuery(cestr);
			while (rs.next()) {
				ce = rs.getInt("result");
				rStrings.add("ce: " + ce);
			}

			cestr = "Select  distinct importPkgName as result FROM " + dBname + ".class_packageinfo where pkgName = '"
					+ packageName
					+ "' and verID = '"
					+ version
					+ "' and projName = '" + projectName + "'";
			rs = stmt.executeQuery(cestr);
			while (rs.next()) {
				String str = rs.getString("result");
				rStrings.add(str);
			}

			String castr = "Select  count(distinct pkgName) as result FROM " + dBname + ".class_packageinfo where importPkgName = '"
					+ packageName
					+ "' and verID = '"
					+ version
					+ "' and projName = '" + projectName + "'";


			rs = stmt.executeQuery(castr);
			while (rs.next()) {
				ca = rs.getInt("result");
				rStrings.add("ca: " + ca);
			}

			castr = "Select  distinct pkgName as result FROM " + dBname + ".class_packageinfo where importPkgName = '"
					+ packageName
					+ "' and verID = '"
					+ version
					+ "' and projName = '" + projectName + "'";
			rs = stmt.executeQuery(castr);
			while (rs.next()) {
				String str = rs.getString("result");
				rStrings.add(str);
			}

			double changeability = 1.0*ce/(ca+ce);
			DecimalFormat df = new DecimalFormat("0.00");
			rStrings.add("changeability:"+df.format(changeability));

			System.out.println("-------------------------------------------------------------");
			for (String string : rStrings) {
				System.out.println(string);
			}
			System.out.println("-------------------------------------------------------------");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed to run changeability query!");
		}
		
		
		return rStrings;
	}


}
