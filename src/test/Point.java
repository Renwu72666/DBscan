package test;
public class Point {

	 private double X;
	 private double Y;
	 boolean isCore;
	 boolean isBound;
	 boolean isOutlier;
	 
	 Point()
	 {
	  X=0; Y=0;
	 }
	 Point(double x,double y)
	 {
	  X=x;
	  Y=y;
	 }
	 
	 public double getX() {
	  return X;
	 }
	 public void setX(float x) {
	  X = x;
	 }
	 public double getY() {
	  return Y;
	 }
	 public void setY(float y) {
	  Y = y;
	 }
	 
	 public String toString()
	 {
	  return "("+this.X+","+this.Y+")";
	 }
	}