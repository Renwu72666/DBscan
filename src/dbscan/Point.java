package dbscan;

public class Point {
	// 坐標點橫坐標
	double x;
	// 坐標點縱坐標
	double y;
	// 此節點是否已經被訪問過
	boolean isVisited;

	public Point(String x, String y) {
		this.x = (Double.parseDouble(x));
		this.y = (Double.parseDouble(y));
		this.isVisited = false;
	}

	/**
	 * 計算當前點與制定點之間的歐式距離
	 * 
	 * @param p
	 *            待計算聚類的p點
	 * @return
	 */
	public double ouDistance(Point p) {
		double distance = 0;

		distance = (this.x - p.x) * (this.x - p.x) + (this.y - p.y)
				* (this.y - p.y);
		distance = Math.sqrt(distance);

		return distance;
	}

	/**
	 * 判斷2個坐標點是否為用個坐標點
	 * 
	 * @param p
	 *            待比較坐標點
	 * @return
	 */
	public boolean isTheSame(Point p) {
		boolean isSamed = false;

		if (this.x == p.x && this.y == p.y) {
			isSamed = true;
		}

		return isSamed;
	}
}