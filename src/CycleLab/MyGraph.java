package CycleLab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.eclipse.jdt.internal.compiler.impl.Constant;

public class MyGraph {

	private int vexnum;// node num
	private int arcnum;// edge num
	private final static int MAX_Vertex_Num = 200;
	private String[] vexs = new String[MAX_Vertex_Num];
	private int[][] arcs = new int[MAX_Vertex_Num][MAX_Vertex_Num];

	public void createGraph() {
		/*
		 * VexType first; VexType Secend;
		 */

		String first;
		String secend;
		Scanner scanner = new Scanner(System.in);

		System.out.println("请输入顶点数:");
		vexnum = scanner.nextInt();
		System.out.println("请输入边数:");
		arcnum = scanner.nextInt();
		System.out.println("请输入各个顶点值：");

		for (int i = 0; i < vexnum; i++) {
			vexs[i] = scanner.next();
		}

		// 初始化邻接矩阵
		for (int i = 0; i < arcnum; i++) {
			for (int j = 0; j < arcnum; j++) {
				arcs[i][j] = 0;
			}
		}

		System.out.println("请输入边的信息:");

		for (int i = 0; i < arcnum; i++) {
			first = scanner.next();
			secend = scanner.next();

			// 如果边有权值的话，则还应该输入权值
			int x = locateVex(first);
			int y = locateVex(secend);
			arcs[x][y] = 1;// 如果是有权的话，这里应该是arc[x][y]=权值
		}

	}

	public void createGraph(ArrayList<CouplingNode> packageNodeList) {
		// TODO Auto-generated method stub
		String first;
		String second;
		
		
		
		vexnum = packageNodeList.size();
		System.out.println("请输入顶点数:" + vexnum);
		
		
		for (CouplingNode couplingNode : packageNodeList) {
			arcnum += couplingNode.getAfferents().size();
			
		}		
		System.out.println("请输入边数:" + arcnum);

		
		System.out.println("请输入各个顶点值：");


		
		for (int i = 0; i < vexnum; i++) {
			vexs[i] = packageNodeList.get(i).getName();
		}

		// 初始化邻接矩阵
		for (int i = 0; i < arcnum; i++) {
			for (int j = 0; j < arcnum; j++) {
				arcs[i][j] = 0;
			}
		}

		System.out.println("处理边的信息...");
		
		for (CouplingNode couplingNode : packageNodeList) {
			second = couplingNode.getName();		
			for (String node : couplingNode.getAfferents()) {
				first = node.substring(4);
				System.out.println(first+" "+second);
				int x = locateVex(first);
				int y = locateVex(second);
				arcs[x][y] = 1;// 如果是有权的话，这里应该是arc[x][y]=权值
			}
		}
	}

	/*
	 * 参数：v：表示顶点向量中一个值 函数返回值：函数返回v在顶点向量中的下标
	 */
	public int locateVex(String v) {
		for (int i = 0; i < vexnum; i++) {
			if (vexs[i].equals(v)) {
				return i;
			}
		}
		return -1;
	}

	/*
	 * 检查图中是不是有环 思想： 能进行拓扑排序，则无环，反之有环
	 */
	public void checkCircle() {

		int count = 0;
		int top = -1;
		int[] stack = new int[MAX_Vertex_Num];
		boolean[] inStack = new boolean[MAX_Vertex_Num];
		boolean[] visited = new boolean[MAX_Vertex_Num];

		for (int i = 0; i < vexnum; i++) {
			if (!visited[i]) {
				deepFirstSearch(i, visited, stack, top, inStack, count);
			}
		}
	}

	private void deepFirstSearch(int x, boolean[] visited, int[] stack,
			int top, boolean[] inStack, int count) {
		visited[x] = true;
		stack[++top] = x;
		inStack[x] = true;
		for (int i = 0; i < vexnum; i++) {
			if (arcs[x][i] != 0)// 有边
			{
				if (!inStack[i]) {
					deepFirstSearch(i, visited, stack, top, inStack, count);
				} else // 条件成立，表示下标为x的顶点到 下标为i的顶点有环
				{
					count++;
					System.out.print("第" + count + "环为:");

					// 从i到x是一个环，top的位置是x，下标为i的顶点在栈中的位置要寻找一下
					// 寻找起始顶点下标在栈中的位置
					int t = 0;
					for (t = top; stack[t] != i; t--)
						;
					// 输出环中顶点
					for (int j = t; j <= top; j++) {
						System.out.print(vexs[stack[j]]);
					}
					System.out.println();

				}
			}
		}
		// 处理完结点后，退栈
		top--;
		inStack[x] = false;
	}

	int main() {
		// MGraph<char,int> G;
		MyGraph G = new MyGraph();
		G.createGraph();
		G.checkCircle();
		System.exit(0);
		return 1;
	}

}

/*
 * public class MyGraph {
 * 
 * private int vexnum;//node num private int arcnum;// edge num private final
 * static int MAX_Vertex_Num = 20; private int[] vexs = new int[MAX_Vertex_Num];
 * private int[][] arcs = new int[MAX_Vertex_Num][MAX_Vertex_Num];
 * 
 * public void createGraph(){ VexType first; VexType Secend;
 * 
 * int first; int secend; Scanner scanner = new Scanner(System.in);
 * 
 * System.out.println("请输入顶点数:"); vexnum = scanner.nextInt();
 * System.out.println("请输入边数:"); arcnum = scanner.nextInt();
 * System.out.println("请输入各个顶点值：");
 * 
 * for (int i=0;i<vexnum;i++) { vexs[i] = scanner.nextInt();; }
 * 
 * //初始化邻接矩阵 for (int i=0;i<arcnum;i++) { for (int j=0;j<arcnum;j++) {
 * arcs[i][j]=0; } }
 * 
 * System.out.println("请输入边的信息:");
 * 
 * for (int i=0;i<arcnum;i++) { first = scanner.nextInt(); secend =
 * scanner.nextInt();
 * 
 * //如果边有权值的话，则还应该输入权值 int x = locateVex(first); int y = locateVex(secend);
 * arcs[x][y]=1;//如果是有权的话，这里应该是arc[x][y]=权值 }
 * 
 * }
 * 
 * 
 * 参数：v：表示顶点向量中一个值 函数返回值：函数返回v在顶点向量中的下标
 * 
 * public int locateVex(int v) { for (int i=0;i<vexnum;i++) { if (vexs[i]==v) {
 * return i; } } return -1; }
 * 
 * 
 * 检查图中是不是有环 思想： 能进行拓扑排序，则无环，反之有环
 * 
 * public void checkCircle() {
 * 
 * int count = 0; int top = -1; int[] stack = new int[MAX_Vertex_Num]; boolean[]
 * inStack = new boolean[MAX_Vertex_Num]; boolean[] visited = new
 * boolean[MAX_Vertex_Num];
 * 
 * for (int i = 0; i < vexnum; i++) { if (!visited[i]) {
 * deepFirstSearch(i,visited,stack,top,inStack,count); } } }
 * 
 * private void deepFirstSearch(int x,boolean[] visited,int[] stack,int
 * top,boolean[] inStack,int count) { visited[x]=true; stack[++top]=x;
 * inStack[x]=true; for (int i=0;i<vexnum;i++) { if (arcs[x][i]!=0)//有边 { if
 * (!inStack[i]) { deepFirstSearch(i,visited,stack,top,inStack,count); } else
 * //条件成立，表示下标为x的顶点到 下标为i的顶点有环 { count++; System.out.print("第" + count + "环为:");
 * 
 * //从i到x是一个环，top的位置是x，下标为i的顶点在栈中的位置要寻找一下 //寻找起始顶点下标在栈中的位置 int t=0; for
 * (t=top;stack[t]!=i;t--); //输出环中顶点 for (int j=t;j<=top;j++) {
 * System.out.print(vexs[stack[j]]); } System.out.println();
 * 
 * } } } //处理完结点后，退栈 top--; inStack[x]=false; }
 * 
 * int main() { //MGraph<char,int> G; MyGraph G = new MyGraph();
 * G.createGraph(); G.checkCircle(); System.exit(0); return 1; } }
 */