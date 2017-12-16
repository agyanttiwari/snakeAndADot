package com.game.snakeandadot.game;

import java.util.List;

import java.awt.Point;
import java.util.ArrayList;

public class Snake {
	private MOVEMENT_DIR currentSnakeDirection = MOVEMENT_DIR.RIGHT;
	private List<Point> snakePoints = new ArrayList<Point>();
	private boolean nextPositionCalculated = false;
	private Point nextHeadPosition;

	public int getLength() {
		return snakePoints.size();
	}

	public Snake() {
		Point snakeHead = new Point(0, 0);
		this.snakePoints.add(snakeHead);
	}

	public Snake(List<Point> snakePoints) {
		this.snakePoints = snakePoints;
	}

	public void grow() {
		snakePoints.add(this.getNextHeadPosition());
		nextPositionCalculated = false;
	}

	public void move(MOVEMENT_DIR direction) {
		if (direction != null && !isDirectionToOppositeToCurrentDirection(direction)) {
			currentSnakeDirection = direction;
		}
		this.move();
	}

	public void move() {
		this.snakePoints.add(this.getNextHeadPosition());
		this.snakePoints.remove(0);
		nextPositionCalculated = false;
	}

	public Point getHeadPosition() {
		return snakePoints.get(snakePoints.size() - 1);
	}

	public enum MOVEMENT_DIR {
		RIGHT, LEFT, UP, DOWN
	}

	public Point getNextHeadPosition(MOVEMENT_DIR direction) {
		if (direction != null && !isDirectionToOppositeToCurrentDirection(direction)) {
			currentSnakeDirection = direction;
		}
		return this.getNextHeadPosition();
	}

	public Point getNextHeadPosition() {
		if (nextPositionCalculated) {
			return nextHeadPosition;
		} else {
			nextHeadPosition = (Point) this.getHeadPosition().clone();
			switch (currentSnakeDirection) {
			case RIGHT:
				nextHeadPosition.translate(0, 1);
				break;
			case LEFT:
				nextHeadPosition.translate(0, -1);
				break;
			case UP:
				nextHeadPosition.translate(-1, 0);
				break;
			case DOWN:
				nextHeadPosition.translate(1, 0);
				break;
			}
			nextPositionCalculated = true;
			return nextHeadPosition;
		}
	}

	private boolean isDirectionToOppositeToCurrentDirection(MOVEMENT_DIR direction) {
		switch (direction) {
		case RIGHT:
			return currentSnakeDirection == MOVEMENT_DIR.LEFT;
		case LEFT:
			return currentSnakeDirection == MOVEMENT_DIR.RIGHT;
		case UP:
			return currentSnakeDirection == MOVEMENT_DIR.DOWN;
		case DOWN:
			return currentSnakeDirection == MOVEMENT_DIR.UP;
		}
		return false;
	}

	public MOVEMENT_DIR getDirection() {
		return currentSnakeDirection;
	}

	public boolean bodyContains(Point point) {
		return snakePoints.subList(0, snakePoints.size() - 1).contains(point);
	}
}
