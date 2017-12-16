package models;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class SnakeDotPositionResponse {
	private List<Point> snakePoints = new ArrayList<Point>();
	private Point dotLocation;
	private boolean gameOver;
	
	public SnakeDotPositionResponse(List<Point> snakePoints, Point dotLocation, boolean gameOver) {
		super();
		this.snakePoints = snakePoints;
		this.dotLocation = dotLocation;
		this.gameOver = gameOver;
	}
	public List<Point> getSnakePoints() {
		return snakePoints;
	}
	public void setSnakePoints(List<Point> snakePoints) {
		this.snakePoints = snakePoints;
	}
	public Point getDotLocation() {
		return dotLocation;
	}
	public void setDotLocation(Point dotLocation) {
		this.dotLocation = dotLocation;
	}
	public boolean isGameOver() {
		return gameOver;
	}
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	
}
