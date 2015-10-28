package cn.seu.edu.integrabilityevaluator.dbconnect;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChangeabilityConnector extends DBConnector{

	private String projectName;
	private String version;	
	private Connection connect = null;
	
	public ChangeabilityConnector(String projectName, String version){
		super();
		this.projectName = projectName;
		this.version = version;
		connect = getConnection();		
	}

	public void importNameUpatedate(String packageName, String importName) {
		try {

			Statement stmt = connect.createStatement();
			String sql = "insert into `" + dBname + "`.`pkgCouplingInfo` (`pkgName`, `projName`, `verID`, `importPkgName`) VALUES ('"
					+ packageName
					+ "','"
					+ projectName
					+ "','"					
					+ version
					+ "','" 
					+ importName + "')";

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
			String sql = "SELECT pkgName FROM `" + dBname + "`.`pkgCouplingInfo` where projName = '"
					+ projectName
					+ "' and verID = '"
					+ version
					+ "' group by pkgName ";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString("pkgName"));
				System.out.println(rs.getString("pkgName"));
				}	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getpackageName error");
		}
		
		return list;
	}
	
	//传出耦合计数
	public int packageEfferentCouplingsCount(String packageName){
		int ce = 0;
		try {
			Statement stmt = connect.createStatement();

			// / efferent  couplings 被该包依赖的外部包数目
			String cestr = "Select  count(distinct importPkgName) as result FROM " + dBname + ".pkgCouplingInfo where pkgname = '"
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
			String cestr = "Select  distinct importPkgName as result FROM " + dBname + ".pkgCouplingInfo where pkgName = '"
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
	
	//传入耦合计数
	public int packageAfferentCouplingsCount(String packageName){
		int ca = 0;
		try {
			Statement stmt = connect.createStatement();

			// / afferent  couplings 该包依赖的外部包数目
			String castr = "Select  count(distinct pkgName) as result FROM " + dBname + ".pkgCouplingInfo where importPkgName = '"
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
			String castr = "Select  distinct pkgName as result FROM " + dBname + ".pkgCouplingInfo where importPkgName = '"
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
	
	
	
	public ArrayList<String> packageChangeabilityInfo(String packageName){
		
		ArrayList<String> rStrings = new ArrayList<String>();
		try {
			Statement stmt = connect.createStatement();

			// / efferent  couplings 被该包依赖的外部包数目
			String cestr = "Select  count(distinct importPkgName) as result FROM " + dBname + ".pkgCouplingInfo where pkgName = '"
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

			cestr = "Select  distinct importPkgName as result FROM " + dBname + ".pkgCouplingInfo where pkgName = '"
					+ packageName
					+ "' and verID = '"
					+ version
					+ "' and projName = '" + projectName + "'";
			rs = stmt.executeQuery(cestr);
			while (rs.next()) {
				String str = rs.getString("result");
				rStrings.add(str);
			}

			String castr = "Select  count(distinct pkgName) as result FROM " + dBname + ".pkgCouplingInfo where importPkgName = '"
					+ packageName
					+ "' and verID = '"
					+ version
					+ "' and projName = '" + projectName + "'";


			rs = stmt.executeQuery(castr);
			while (rs.next()) {
				ca = rs.getInt("result");
				rStrings.add("ca: " + ca);
			}

			castr = "Select  distinct pkgName as result FROM " + dBname + ".pkgCouplingInfo where importPkgName = '"
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
