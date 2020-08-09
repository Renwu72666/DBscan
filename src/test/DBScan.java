package test;

import java.util.*;


public class DBScan {
  float[][] distance=new float[10][10];
  int[] neighbours=new int[10];
  float[] distanceFromCore=new float[10];
  ArrayList<Point> corePoints;
  ArrayList<Point> dbscan ;
  private static int minPoints;
  private static int Radius;
 
  DBScan(int p,int r)
  {   minPoints=p;
      Radius=r;
   dbscan=new ArrayList<Point>();
   corePoints=new ArrayList<Point>();
  
  }
 
  public void Initialize()
  {System.out.println("reached here");
   Random rand=new Random(20);
   for (int i=0;i<10;i++)
    {
    Point p=new Point(rand.nextFloat()*10,rand.nextFloat()*10);
    dbscan.add(p);
    System.out.println(p.toString());
    }
  }
 
  public void algoDBSCAN()
  {
   //calculate distance of all
   for(int i=0;i<10;i++)
   {
    for(int j=0;j<10;j++)
    {
    distance[i][j]=Edistance(dbscan.get(i),dbscan.get(j));
    System.out.println("distances   "+distance[i][j]);
    }
   }
  int count;
      for(int k=0;k<10;k++)
      {count=0;
       for(int l=0;l<10;l++)
       {
        if(distance[k][l]<Radius)
        {
         count++;
        }
       }
       neighbours[k]=count;
       System.out.println("neighbours presents are   "+neighbours[k]);
       isCore(k);
      }
     
      for(int m=0;m<10;m++)
      {
       if(!isCore(m))
        {isBoundary(m);}
      }
      for(int n=0;n<10;n++)
      {
       if(!isBoundary(n))
        {isOutlier(n);}
      }
  }
  public boolean isCore(int k)
  { if(neighbours[k]>=minPoints)
   {System.out.println("Core point : "+dbscan.get(k).toString() );
   corePoints.add(dbscan.get(k));
   return true;}
  else
   return false;
     
  }
  public boolean isBoundary(int k)
  {
   if(neighbours[k]<minPoints)
     {
   for(int i=0;i<10;i++)
   {
    distanceFromCore[i]=Edistance(dbscan.get(k),corePoints.get(i));
       if(distanceFromCore[i]<=Radius)
       {System.out.println("Boundary point: "+dbscan.get(k).toString() );
         return true;
       } 
   }
 
      } 
    return false;
  }
  public boolean isOutlier(int k)
  { if(neighbours[k]<minPoints&& !isBoundary(k))
      {System.out.println("Outlier : "+dbscan.get(k).toString() );
      return true;
       }
      return false;
  }
  public float Edistance(Point p1,Point p2)
  {return (float)Math.sqrt((p1.getX()-p2.getX())*(p1.getX()-p2.getX())+(p1.getY()-p2.getY())*(p1.getY()-p2.getY()));
   }
 
  public void Print()
  {
   System.out.println("radius is"+Radius+" "+","+" "+"Minimum points are"+minPoints);
  }
 
  public static void main(String args[])
  {
   DBScan db=new DBScan(3,4);
  
   db.Initialize();
   db.algoDBSCAN();
   db.Print();
  }
}




