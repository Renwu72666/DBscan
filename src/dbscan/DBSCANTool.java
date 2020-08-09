package dbscan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;

public class DBSCANTool {
	// 測試數據文件地址
	private String filePath;
	// 簇掃描半徑
	private double eps;
	// 最小包含點數閾值
	private int minPts;
	// 所有的數據坐標點
	private ArrayList<Point> totalPoints;
	// 聚簇結果
	private ArrayList<ArrayList<Point>> resultClusters;
	//噪聲數據
	private ArrayList<Point> noisePoint;

	public DBSCANTool(String filePath, double eps, int minPts) {
		this.filePath = filePath;
		this.eps = eps;
		this.minPts = minPts;
		readDataFile();
	}

	/**
	 * 從文件中讀取數據
	 */
	public void readDataFile() {
		File file = new File(filePath);
		ArrayList<String[]> dataArray = new ArrayList<String[]>();

		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String str;
			String[] tempArray;
			while ((str = in.readLine()) != null) {
				tempArray = str.split(" ");
				dataArray.add(tempArray);
			}
			in.close();
		} catch (IOException e) {
			e.getStackTrace();
		}

		Point p;
		totalPoints = new ArrayList<>();
		for (String[] array : dataArray) {
			p = new Point(array[0], array[1]);
			totalPoints.add(p);
		}
	}

	/**
	 * 遞歸的尋找聚簇
	 * 
	 * @param pointList
	 *            當前的點列表
	 * @param parentCluster
	 *            父聚簇
	 */
	private void recursiveCluster(Point point, ArrayList<Point> parentCluster) {
		double distance = 0;
		ArrayList<Point> cluster;

		// 如果已經訪問過了，則跳過
		if (point.isVisited) {
			return;
		}

		point.isVisited = true;
		cluster = new ArrayList<>();
		for (Point p2 : totalPoints) {
			// 過濾掉自身的坐標點
			if (point.isTheSame(p2)) {
				continue;
			}

			distance = point.ouDistance(p2);
			if (distance <= eps) {
				// 如果聚類小於給定的半徑，則加入簇中
				cluster.add(p2);
			}
		}

		if (cluster.size() >= minPts) {
			// 將自己也加入到聚簇中
			cluster.add(point);
			// 如果附近的節點個數超過最下值，則加入到父聚簇中,同時去除重複的點
			addCluster(parentCluster, cluster);

			for (Point p : cluster) {
				recursiveCluster(p, parentCluster);
			}
		}
	}

	/**
	 * 往父聚簇中添加局部簇坐標點
	 * 
	 * @param parentCluster
	 *            原始父聚簇坐標點
	 * @param cluster
	 *            待合併的聚簇
	 */
	private void addCluster(ArrayList<Point> parentCluster,
			ArrayList<Point> cluster) {
		boolean isCotained = false;
		ArrayList<Point> addPoints = new ArrayList<>();

		for (Point p : cluster) {
			isCotained = false;
			for (Point p2 : parentCluster) {
				if (p.isTheSame(p2)) {
					isCotained = true;
					break;
				}
			}

			if (!isCotained) {
				addPoints.add(p);
			}
		}

		parentCluster.addAll(addPoints);
	}

	/**
	 * dbScan算法基於密度的聚類
	 */
	public void dbScanCluster() {
		ArrayList<Point> cluster = null;
		resultClusters = new ArrayList<>();
		noisePoint = new ArrayList<>();
		
		for (Point p : totalPoints) {
			if(p.isVisited){
				continue;
			}
			
			cluster = new ArrayList<>();
			recursiveCluster(p, cluster);

			if (cluster.size() > 0) {
				resultClusters.add(cluster);
			}else{
				noisePoint.add(p);
			}
		}
		removeFalseNoise();
		
		printClusters();
	}
	
	/**
	 * 移除被錯誤分類的噪聲點數據
	 */
	private void removeFalseNoise(){
		ArrayList<Point> totalCluster = new ArrayList<>();
		ArrayList<Point> deletePoints = new ArrayList<>();
		
		//將聚簇合併
		for(ArrayList<Point> list: resultClusters){
			totalCluster.addAll(list);
		} 
		
		for(Point p: noisePoint){
			for(Point p2: totalCluster){
				if(p2.isTheSame(p)){
					deletePoints.add(p);
				}
			}
		}
		
		noisePoint.removeAll(deletePoints);
	}

	/**
	 * 輸出聚類結果
	 */
	private void printClusters() {
		int i = 1;
		for (ArrayList<Point> pList : resultClusters) {
			System.out.print("聚簇" + (i++) + ":");
			for (Point p : pList) {
				System.out.print(MessageFormat.format("({0},{1}) ", p.x, p.y));
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.print("噪聲數據:");
		for (Point p : noisePoint) {
			System.out.print(MessageFormat.format("({0},{1}) ", p.x, p.y));
		}
		System.out.println();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filePath = "D:\\kms\\DS1.txt";  
        //簇掃描半徑  
        double eps = 15;  
        //最小包含點數閾值  
        int minPts = 15;            
        DBSCANTool tool = new DBSCANTool(filePath, eps, minPts);  
        tool.dbScanCluster();  
	}
}
