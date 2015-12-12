package cn.edu.seu.integrabilityevaluator.dbconnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class SubstitutabilityConnector {

	private Connection connect = null;
	private String projectName;
	private String version;

	/*
	 * public ClassChangeabilityConnector(){ super(); connect = DBConnector.getConnection();
	 * }
	 */

	public SubstitutabilityConnector(String project, String version) {
		// TODO Auto-generated constructor stub
		//super();

		this.projectName = project;
		this.version = version;
	}

	public void importNameUpatedate(String packageName, String className,
			String importName, String importclassName) {
		Statement stmt = null;
		ResultSet rs = null;
		connect = DBConnector.getConnection();
		try {

			stmt = connect.createStatement();
			String sql = "insert into `t_classcouplinginfo` (`pkgName`, `className`, `projName`, `verID`, `importPkgName`, `importclassname`) VALUES ('"
					+ packageName + "','" + className + "','" + projectName
					+ "','" + version + "','" + importName + "','"
					+ importclassName + "')";

			
			stmt.executeUpdate(sql);
			

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("insert failed!");
		} finally {
			DBConnector.closeFun(rs, stmt, connect);
		}

	}

	public ArrayList<String> getpackageName() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		connect = DBConnector.getConnection();
		try {

			stmt = connect.createStatement();
			String sql = "SELECT pkgname FROM t_classcouplinginfo where ProjName = '" + projectName
					+ "' and verID = '" + version + "' group by pkgname";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString("pkgname"));
				// System.out.println(rs.getString("pkgname"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			DBConnector.closeFun(rs, stmt, connect);
		}

		return list;

	}

	// **********************包耦合关系获取方法*************************//

	// 传出耦合计数
	public int packageEfferentCouplingsCount(String packageName) {
		int ce = 0;
		connect = DBConnector.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connect.createStatement();

			// / efferent couplings 被该包依赖的外部包数目
			String cestr = "Select  count(distinct importPkgName) as result FROM t_classcouplinginfo where pkgname != importpkgname and pkgname = '"
					+ packageName
					+ "' and verID = '"
					+ version
					+ "' and projName = '" + projectName + "'";

			rs = stmt.executeQuery(cestr);
			while (rs.next()) {
				ce = rs.getInt("result");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed to run changeability query!");
			ce = -1;
		} finally {
			DBConnector.closeFun(rs, stmt, connect);
		}
		return ce;
	}

	public ArrayList<String> packageEffernetCouplingslist(String packageName) {
		connect = DBConnector.getConnection();
		ArrayList<String> rStrings = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		try {

			stmt = connect.createStatement();
			String cestr = "Select  distinct importPkgName as result FROM t_classcouplinginfo where pkgname != importpkgname and  pkgName = '"
					+ packageName + "' and verID = '" + version
					+ "' and projName = '" + projectName + "'";
			rs = stmt.executeQuery(cestr);
			while (rs.next()) {
				String str = rs.getString("result");
				rStrings.add(str);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed to run changeability query!");
		} finally {
			DBConnector.closeFun(rs, stmt, connect);
		}
		return rStrings;
	}

	// 传入耦合计数
	public int packageAfferentCouplingsCount(String packageName) {
		int ca = 0;
		connect = DBConnector.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connect.createStatement();

			// / afferent couplings 该包依赖的外部包数目
			String castr = "Select  count(distinct pkgName) as result FROM t_classcouplinginfo where pkgname != importpkgname and  importPkgName = '"
					+ packageName + "' and verID = '" + version
					+ "' and projName = '" + projectName + "'";


			rs = stmt.executeQuery(castr);
			while (rs.next()) {
				ca = rs.getInt("result");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed to run changeability query!");
			ca = -1;
		} finally {
			DBConnector.closeFun(rs, stmt, connect);
		}
		return ca;
	}

	public ArrayList<String> packageAffernetCouplingslist(String packageName) {
		connect = DBConnector.getConnection();
		ArrayList<String> rStrings = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		try {

			stmt = connect.createStatement();
			String castr = "Select  distinct pkgName as result FROM t_classcouplinginfo where pkgname != importpkgname and  importPkgName = '"
					+ packageName + "' and verID = '" + version
					+ "' and projName = '" + projectName + "'";
			rs = stmt.executeQuery(castr);
			while (rs.next()) {
				String str = rs.getString("result");
				rStrings.add(str);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed to run changeability query!");
		} finally {
			DBConnector.closeFun(rs, stmt, connect);
		}
		return rStrings;
	}

	public ArrayList<String> class_packageEffernetCouplingslist(
			String packageName) {
		connect = DBConnector.getConnection();
		ArrayList<String> rStrings = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connect.createStatement();
			String cestr = "Select   distinct importpkgName, importClassName  FROM t_classcouplinginfo where pkgname != importpkgname and pkgName = '"
					+ packageName
					+ "' and verID = '"
					+ version
					+ "' and projName = '" + projectName + "'";
			rs = stmt.executeQuery(cestr);
			while (rs.next()) {
				String str = rs.getString("importpkgName");
				str += "." + rs.getString("importclassname");
				rStrings.add(str);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out
					.println("failed to run substitutability class_package query!");
		} finally {
			DBConnector.closeFun(rs, stmt, connect);
		}
		return rStrings;
	}

	public ArrayList<String> class_packageAffernetCouplingslist(
			String packageName) {
		connect = DBConnector.getConnection();
		ArrayList<String> rStrings = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connect.createStatement();
			String castr = "Select  distinct pkgName, className FROM t_classcouplinginfo where pkgname != importpkgname and importPkgName = '"
					+ packageName + "' and verID = '" + version
					+ "' and projName = '" + projectName + "'";
			rs = stmt.executeQuery(castr);
			while (rs.next()) {
				String str = rs.getString("pkgname");
				str += "." + rs.getString("classname");
				rStrings.add(str);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out
					.println("failed to run substitutability class_package query!");
		} finally {
			DBConnector.closeFun(rs, stmt, connect);
		}
		return rStrings;
	}

	// *******************类耦合关系获取方法*********************************//
	public ArrayList<String> getClassName() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		connect = DBConnector.getConnection();
		try {

			stmt = connect.createStatement();
			String sql = "SELECT distinct pkgname, classname FROM t_classcouplinginfo where verID = '" + version
					+ "' and projName = '" + projectName + "'";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String str = rs.getString("pkgname") + "#"
						+ rs.getString("classname");
				list.add(str);
				System.out.println(rs.getString("pkgname") + "$"
						+ rs.getString("classname"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getClassName run failed!");
		} finally {
			DBConnector.closeFun(rs, stmt, connect);
		}

		return list;
	}

	public ArrayList<String> classEffernetCouplingslist(String className) {

		String[] pkgclassName = className.split("#");
		connect = DBConnector.getConnection();
		ArrayList<String> rStrings = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connect.createStatement();
			String cestr = "Select distinct importpkgName, importClassName  FROM t_classcouplinginfo where pkgName = '"
					+ pkgclassName[0]
					+ "' and className = '"
					+ pkgclassName[1]
					+ "' and verID = '"
					+ version
					+ "' and projName = '"
					+ projectName + "'";
			rs = stmt.executeQuery(cestr);
			while (rs.next()) {
				String str = rs.getString("importpkgName");
				str += "." + rs.getString("importclassname");
				rStrings.add(str);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out
					.println("failed to run class efferent substitutability query!");
		} finally {
			DBConnector.closeFun(rs, stmt, connect);
		}
		return rStrings;
	}

	public ArrayList<String> classAffernetCouplingslist(String className) {
		connect = DBConnector.getConnection();
		String[] pkgclassName = className.split("#");
		ArrayList<String> rStrings = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connect.createStatement();
			String castr = "Select  distinct pkgName, className FROM t_classcouplinginfo where importpkgName = '"
					+ pkgclassName[0] + "' and importClassName = '"
					+ pkgclassName[1] + "' and verID = '" + version
					+ "' and projName = '" + projectName + "'";
			rs = stmt.executeQuery(castr);
			while (rs.next()) {
				String str = rs.getString("pkgname");
				str += "." + rs.getString("classname");
				rStrings.add(str);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out
					.println("failed to run class afferent substitutability query!");
		} finally {
			DBConnector.closeFun(rs, stmt, connect);
		}
		return rStrings;
	}
}
