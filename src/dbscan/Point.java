package dbscan;

public class Point {
	// �����I���
	double x;
	// �����I�a����
	double y;
	// ���`�I�O�_�w�g�Q�X�ݹL
	boolean isVisited;

	public Point(String x, String y) {
		this.x = (Double.parseDouble(x));
		this.y = (Double.parseDouble(y));
		this.isVisited = false;
	}

	/**
	 * �p���e�I�P��w�I�������ڦ��Z��
	 * 
	 * @param p
	 *            �ݭp��E����p�I
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
	 * �P�_2�ӧ����I�O�_���έӧ����I
	 * 
	 * @param p
	 *            �ݤ�������I
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