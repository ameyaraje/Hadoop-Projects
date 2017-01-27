import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class FileTestWithHadoop {
	
	static BufferedReader br = null;
	static FileSystem hdfs = null;
	static FileInputStream file = null;
	
	public static void main(String[] args) throws Exception {
		
		// Read node1
		int node1[] = new int[2];
		node1 = parseFile("/home/ameya/workspace/hdfs/src/node1.txt");
				
		// Read node 2

		int node2[] = new int[2];
		node2 = parseFile("/home/ameya/workspace/hdfs/src/node2.txt");	
		
		// Declare the parity nodes
		int[] node3 = new int[2];
		int[] node4 = new int[2];
		
		int a=node1[0]; 
		int b=node1[1]; 
		int c=node2[0]; 
		int d=node2[1]; 

		node3[0] = a^c;
		node3[1] = b^d;
		node4[0] = a^d;
		node4[1] = b^c^d;

		System.out.println("Values in Node 3 are : "+ node3[0] +" , "+node3[1]);
		System.out.println("Values in Node 4 are : "+ node4[0] +" , "+node4[1]);
				
		// Write parity numbers to files
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("node3.txt", "UTF-8");
			writer.println(node3[0]);
			writer.println(node3[1]);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			writer = new PrintWriter("node4.txt", "UTF-8");
			writer.println(node4[0]);
			writer.println(node4[1]);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// Push to HDFS
		uploadToHDFS("src/node1.txt","src/node2.txt");
		uploadToHDFS("node3.txt","node4.txt");
		
		
		// Download from HDFS and decode
		downloadFromHDFS();
		
	}
	
	public static int[] parseFile(String path) throws Exception {
		int[] result = new int[2];
		
		file = new FileInputStream(path);
		br = new BufferedReader(new InputStreamReader(file));
		String line = null;
		int i = 0;
		System.out.println("Node values are: ");
		while ((line = br.readLine()) != null) {
			result[i]=Integer.parseInt(line);
			System.out.println(result[i]);
			i++;
		}
		
		return result;
	}
	
	public static void uploadToHDFS(String file1Path, String file2Path) throws Exception {
		hdfs = FileSystem.get(new Configuration());
		Path homeDir=hdfs.getHomeDirectory();
		System.out.println("Home folder for Hadoop-" +homeDir);
		
		Path localPath1 = new Path(file1Path);
		System.out.println("File 1 path is " + file1Path);
		Path localPath2 = new Path(file2Path);
		System.out.println("File 1 path is " + file1Path);
		hdfs.copyFromLocalFile(localPath1, homeDir); 
		hdfs.copyFromLocalFile(localPath2, homeDir); 
		
		System.out.println("Files Uploaded");
		
	}
	
	public static void downloadFromHDFS() throws Exception {
		hdfs = FileSystem.get(new Configuration());
		Path homeDir=hdfs.getHomeDirectory();
		try {
			Path parPath1 = new Path(homeDir+"/node3.txt");
			Path parPath2 = new Path(homeDir+"/node4.txt");
			Path downloadTo = new Path("/home/ameya/workspace/hdfs/Retrieved");
			System.out.println("Target location- " + downloadTo);
			hdfs.copyToLocalFile(parPath1, downloadTo); 
			hdfs.copyToLocalFile(parPath2, downloadTo);    
			System.out.println("Success");
		}
		catch(Exception e){
			e.getMessage();
		}
		
		System.out.println("Files Downloaded");
		
		int[] Rnode3 = new int[2];
		int[] Rnode4 = new int[2];
		Rnode3 = parseFile("/home/ameya/workspace/hdfs/Retrieved/node3.txt");
		Rnode4 = parseFile("/home/ameya/workspace/hdfs/Retrieved/node4.txt");
		
		int[] recoveredNode1 = new int[2];
		int[] recoveredNode2 = new int[2];
		
		recoveredNode1[0] = Rnode3[0]^Rnode3[1]^Rnode4[1];
		recoveredNode1[1] = Rnode3[0]^Rnode3[1]^Rnode4[0]^Rnode4[1]^Rnode3[1];
		recoveredNode2[0] = Rnode3[0]^Rnode4[1]^Rnode3[1]^Rnode3[0];
		recoveredNode2[1] = Rnode3[0]^Rnode3[1]^Rnode4[0]^Rnode4[1];
		System.out.println("Recovered numbers are : " + recoveredNode1[0] + " , " + recoveredNode1[1]);
		System.out.println("Recovered numbers are : " + recoveredNode2[0] + " , " + recoveredNode2[1]);
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("RecoveredNode1.txt", "UTF-8");
			writer.println(recoveredNode1[0]);
			writer.println(recoveredNode1[1]);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			writer = new PrintWriter("RecoveredNode2.txt", "UTF-8");
			writer.println(recoveredNode2[0]);
			writer.println(recoveredNode2[1]);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}