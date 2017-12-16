package com.game.snakeandadot.game;

import java.awt.Point;
import java.util.Random;

public class SnakeAndADot {
	private final int MIN_HEIGHT_WIDTH_ALLOWED = 10;
	private int gridHeight;
	private int gridWidth;
	private Snake snake;
	private Point dotLocation;
	static final Random randomGenerator = new Random();

	public SnakeAndADot(int height, int width) {
		if (height >= MIN_HEIGHT_WIDTH_ALLOWED && width >= MIN_HEIGHT_WIDTH_ALLOWED) {
			this.gridHeight = height;
			this.gridWidth = width;
		} else {
			throw new IllegalArgumentException(
					"Height and Width should be set greater than : " + MIN_HEIGHT_WIDTH_ALLOWED);
		}
	}

	public int getGridHeight() {
		return gridHeight;
	}

	public int getGridWidth() {
		return gridWidth;
	}

	public void start() {
		snake = new Snake();
		this.setDot(getNextDotPosition());
	}

	public void start(Snake snake) {
		this.snake = snake;
		this.setDot(getNextDotPosition());
	}

	public Snake getSnake() {
		return snake;
	}

	public void setDot(Point dotLocation) {
		this.dotLocation = dotLocation;
	}

	public boolean moveSuccessful(Snake.MOVEMENT_DIR direction) {
		Point nextHeadPosition = snake.getNextHeadPosition(direction);
		return moveSuccessful(nextHeadPosition);
	}

	public boolean moveSuccessful() {
		Point nextHeadPosition = snake.getNextHeadPosition();
		return moveSuccessful(nextHeadPosition);
	}
	
	private boolean moveSuccessful(Point newHeadPosition){
		if (!boundryHit(newHeadPosition)) {
			if (newHeadPosition.equals(dotLocation)) {
				snake.grow();
				this.setDot(getNextDotPosition());
			} else {
				snake.move();
			}
			return !hitItself(newHeadPosition);
		} else {
			return false;
		}
	}
	
	private boolean hitItself(Point newHeadPosition){
		return snake.bodyContains(newHeadPosition);
	}
	
	private boolean boundryHit(Point newHeadPosition){
		int xPos = (int) newHeadPosition.getX();
		int yPos = (int) newHeadPosition.getY();
		return (yPos < 0 || yPos >= this.getGridWidth() || xPos < 0 || xPos >= this.getGridHeight());
	}

	public Point getNextDotPosition() {
		Point dotPosition;
		do {
			dotPosition = getRandomPoint();
		}
		while(dotPosition.equals(this.dotLocation) || this.snake.bodyContains(dotPosition) || snake.getHeadPosition().equals(dotPosition));	
		return dotPosition;
	}
	
	private Point getRandomPoint(){
		int randomX = randomGenerator.nextInt(this.getGridHeight());
		int randomY = randomGenerator.nextInt(this.getGridWidth());
		return new Point(randomX, randomY);
	}

	public Point getDot() {
		return dotLocation;
	}

}
