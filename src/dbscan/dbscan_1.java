package dbscan;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class dbscan_1 extends JFrame{

		private int minPt=17;
		private	double eps=13.0D;// 半徑
		private ArrayList<Point> totalPoints;  // 聚簇結果  
		private ArrayList<ArrayList<Point>> resultClusters;   //噪聲數據     
		private ArrayList<Point> noisePoint;  
		int x=0;
		String tmpfilepath,knum,tmp ;
		double posX[] = new double[30000];
		double posY[] = new double[30000];
		Random rnd = new Random();
		JTextField jfd1 = new JTextField(5);
		JTextField jfd2 = new JTextField(5);
		JPanel pan = new JPanel(); 
		JLabel jlb1 = new JLabel("ESP：");
		JLabel jlb2 = new JLabel("Minpoint：");
		JButton btn1 = new JButton("file：");
		JButton btn2 = new JButton("start");
		JButton btn3 = new JButton("clear");
		ArrayList alt1 = new ArrayList();


		public void Initialize(int radius, int minpoint)
		{}
		
	    private void recursiveCluster(Point point, ArrayList<Point> parentCluster) {  
	        double distance = 0;  
	        ArrayList<Point> cluster;  
	  
	        // 如果已经访问过了，则跳过  
	        if (point.isVisited) {  
	            return;  
	        }  
	  
	        point.isVisited = true;  
	        cluster = new ArrayList<>();  
	        for (Point p2 : totalPoints) {  
	            // 过滤掉自身的坐标点  
	            if (point.isTheSame(p2)) {  
	                continue;  
	            }  
	  
	            distance = point.ouDistance(p2);  
	            if (distance <= eps) {  
	                // 如果聚类小于给定的半径，则加入簇中  
	                cluster.add(p2);  
	            }  
	        }  
	  
	        if (cluster.size() >= minPt) {  
	            // 将自己也加入到聚簇中  
	            cluster.add(point);  
	            // 如果附近的节点个数超过最下值，则加入到父聚簇中,同时去除重复的点  
	            addCluster(parentCluster, cluster);  
	  
	            for (Point p : cluster) {  
	                recursiveCluster(p, parentCluster);  
	            }  
	        }  
	    }  
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
	    private void removeFalseNoise(){  
	        ArrayList<Point> totalCluster = new ArrayList<>();  
	        ArrayList<Point> deletePoints = new ArrayList<>();  
	          
	        //将聚簇合并  
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
	    private void printClusters() {  
	        int i = 1;  
	        for (ArrayList<Point> pList : resultClusters) {  
	            System.out.print("聚簇" + (i++) + ":");  
	            for (Point p : pList) {  
	               // System.out.print(MessageFormat.format("({0},{1}) ", p.x, p.y));  
	            }  
	            System.out.println();  
	        }  
	          
	        System.out.println();  
	        System.out.print("噪声数据:");  
	        for (Point p : noisePoint) {  
	            System.out.print(MessageFormat.format("({0},{1}) ", p.x, p.y));  
	        }  
	        System.out.println();  
	    }  
	    
		void drawpoint()
		{
			for(int a=0;a<(Integer.valueOf((String)alt1.get(0))*2-2);a=a+2)
			{
				posX[x]=Double.parseDouble((String)alt1.get(a+2));
				posY[x]=Double.parseDouble((String)alt1.get(a+3));
			x++;
			}
			Graphics g1 = pan.getGraphics();
			Graphics2D g2 = (Graphics2D) g1; 
			g2.setColor(Color.BLACK);
			for(int i = 0 ; i < (Integer.valueOf((String)alt1.get(0))) ; i++)
			{
				//g2.draw(new Line2D.Double((posX[i]/12)*400, (posY[i]/12)*400, (posX[i]/12)*400, (posY[i]/12)*400));
				g2.draw(new Line2D.Double(posX[i] / 2, posY[i] / 2, posX[i] / 2, posY[i] / 2));
			}	
		}
		void fileread()
		{
			final JFileChooser jfc = new JFileChooser();
			jfc.setCurrentDirectory(new java.io.File("D:\\kms"));
			jfc.setApproveButtonText("確定");
			Component jLabel1 = null;
			jfc.showDialog(jLabel1, null); 
			File filePath = jfc.getSelectedFile();
			tmpfilepath = filePath.getPath().toString();
			ArrayList<String[]> dataArray = new ArrayList<String[]>();
			try
			{
				FileReader fr = null;
				fr = new FileReader(tmpfilepath);
				BufferedReader br = new BufferedReader(fr);
				int i = 0;
				while((tmp=br.readLine()) != null)
				{
					String str;    
					String[] tmpfirst;
					tmpfirst = tmp.split(" ");				
					alt1.add(tmpfirst[0]);
					i++;
					alt1.add(tmpfirst[1]);
					//System.out.println((String)alt.get(i-1)+" "+(String)alt.get(i));
					i++;		
					dataArray.add(tmpfirst);  
				}	   
			}
				catch (FileNotFoundException e1)
				{
						e1.printStackTrace();
				}
				catch (IOException e)
				{
					
					e.printStackTrace();
				}
			
			Point p;
			totalPoints = new ArrayList<>();
			for (String[] array : dataArray) 
			{
				p = new Point(array[0], array[1]);
				totalPoints.add(p);
			}
		}
		public void drawColor()
		{
			 Graphics g1 = this.pan.getGraphics();
			    Graphics2D g2 = (Graphics2D)g1;
			    g2.setColor(Color.WHITE);
			    for (int i = 0; i < Integer.valueOf((String)this.alt1.get(0)).intValue(); i++) {
			      g2.draw(new Line2D.Double(this.posX[i] / 2.0D, this.posY[i] / 2.0D, this.posX[i] / 2.0D, this.posY[i] / 2.0D));
			    }
			    ArrayList<Color> color = new ArrayList();
			    color.add(Color.BLUE);
			    color.add(Color.CYAN);
			    color.add(Color.GREEN);
			    color.add(Color.ORANGE);
			    color.add(Color.PINK);
			    color.add(Color.RED);
			    color.add(Color.YELLOW);
			    int i = 0;
			    for (ArrayList<Point> pList : resultClusters) { 
		           System.out.print(i);      	
		            for (Point p : pList) {  
		                //System.out.print(MessageFormat.format("({0},{1}) ", p.x, p.y)); 
		            	g2.setColor((Color)color.get(i));
		                g2.fill(new Ellipse2D.Double(p.x / 2, p.y /2,2, 2));
		             //   System.out.print(p);
		                
		            }  
		            i++;
		           // System.out.println();  
		        }  
			   /*  for (int i = 0; i < Integer.valueOf((String)this.alt1.get(0)).intValue(); i++) {
			     if (this.visit[i] > 0)
			      {
			        g2.setColor((Color)color.get(this.visit[i] % 7));
			        g2.fill(new Ellipse2D.Double(this.posX[i] / 2.0D, this.posY[i] / 2.0D, 3.0D, 3.0D));
			      }
			    }*/
		}
		private void dbscan_1(double eps, int minPt) {  
	        this.eps = eps;  
	        this.minPt = minPt;  
	       
	    }  		
		public dbscan_1()
		{
		    super("dbscan_1");
		    Container c = getContentPane();
			GridBagLayout gb = new GridBagLayout();
			GridBagConstraints gbc = new GridBagConstraints();
			GridBagConstraints gbc1 = new GridBagConstraints();
			c.setLayout(gb);
			//版面配置
			pan.setBackground(Color.WHITE);  
			pan.setPreferredSize(new Dimension(500,450)); 
			
			gbc1.gridx = 0; gbc1.gridy = 0 ;
			gbc1.gridheight = 4 ;
			gb.setConstraints(pan, gbc1);
			gbc.insets=new Insets(5,5,5,5);
			
			gbc.gridx = 5 ; gbc.gridy = 1 ;
			gb.setConstraints(jfd1, gbc);
			gbc.insets=new Insets(5,5,5,5);
			
		    gbc.gridx = 5;gbc.gridy = 2;
		    gb.setConstraints(this.jfd2, gbc);
		    gbc.insets = new Insets(5, 5, 5, 5);;
			
			gbc.gridx = 4; gbc.gridy = 1 ;
			gb.setConstraints(jlb1, gbc);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			
			gbc.gridx = 4;gbc.gridy = 2;
			gb.setConstraints(this.jlb2, gbc);
			gbc.fill = 2;
			
			gbc.insets=new Insets(5,5,5,5);
			gbc.gridx = 4 ; gbc.gridy = 0 ;
			gb.setConstraints(btn1, gbc);
			gbc.insets=new Insets(5,5,5,5);
			gbc.gridx = 4 ; gbc.gridy = 3 ;
			gb.setConstraints(btn2, gbc);
			gbc.insets=new Insets(5,5,5,5);
			gbc.anchor = GridBagConstraints.NORTH;
			gbc.gridx = 4 ; gbc.gridy = 4 ;
			gb.setConstraints(btn3, gbc);
			 gbc.gridx = 5;gbc.gridy = 2;

			 
			//物建梯加
			c.add(pan);
			c.add(jlb1);
			c.add(jlb2);
			c.add(jfd1);
			c.add(jfd2);
			c.add(btn1);
			c.add(btn2);
			c.add(btn3);
		
		btn1.addActionListener(new java.awt.event.ActionListener()
		{
	         public void actionPerformed(java.awt.event.ActionEvent evt) 
	         {
	               fileread();
	               drawpoint();
	         }
	    });
		btn2.addActionListener(new java.awt.event.ActionListener()
		{
	         public void actionPerformed(java.awt.event.ActionEvent evt) 
	         {
	        	 dbscan_1(Integer.parseInt(dbscan_1.this.jfd1.getText().toString()), 
	        	          Integer.parseInt(dbscan_1.this.jfd2.getText().toString()));
	        	       //drawColor();
	        	 dbScanCluster();
	        	 drawColor();
	         }

			
	    });
  
	  }
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		dbscan_1 tt = new dbscan_1();
		tt.setSize(850, 700);
		tt.setVisible(true);
  
	}

}
