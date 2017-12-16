package com.game.snakeandadot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Test;

import com.game.snakeandadot.game.Snake;
import com.game.snakeandadot.game.Snake.MOVEMENT_DIR;

public class SnakeTest {
	@Test
	public void testInitialSnakeSize() {
		Snake snake = new Snake();
		assertEquals(1, snake.getLength());
	}

	@Test
	public void testInitialSnakeDirection() {
		Snake snake = new Snake();
		assertEquals(Snake.MOVEMENT_DIR.RIGHT, snake.getDirection());
	}

	@Test
	public void testInitialPosition() {
		Snake snake = new Snake();
		assertEquals(new Point(0, 0), snake.getHeadPosition());
	}
	
	@Test
	public void testSnakeNextHeadPositionForOppositeDirection() {
		Snake snake = new Snake();
		
		snake.move(Snake.MOVEMENT_DIR.DOWN);
		assertEquals(new Point(2, 0), snake.getNextHeadPosition(Snake.MOVEMENT_DIR.UP));
		snake.move();
		assertEquals(new Point(2, 0), snake.getHeadPosition());
		assertEquals(snake.getDirection(), Snake.MOVEMENT_DIR.DOWN);
		
		snake.move(Snake.MOVEMENT_DIR.RIGHT);
		assertEquals(new Point(2, 2), snake.getNextHeadPosition(Snake.MOVEMENT_DIR.LEFT));
		snake.move();
		assertEquals(new Point(2, 2), snake.getHeadPosition());
		assertEquals(snake.getDirection(), Snake.MOVEMENT_DIR.RIGHT);
		
		snake.move(Snake.MOVEMENT_DIR.UP);
		assertEquals(new Point(0, 2), snake.getNextHeadPosition(Snake.MOVEMENT_DIR.DOWN));
		snake.move();
		assertEquals(new Point(0, 2), snake.getHeadPosition());
		assertEquals(snake.getDirection(), Snake.MOVEMENT_DIR.UP);
		
		snake.move(Snake.MOVEMENT_DIR.LEFT);
		assertEquals(new Point(0, 0), snake.getNextHeadPosition(Snake.MOVEMENT_DIR.RIGHT));
		snake.move();
		assertEquals(new Point(0, 0), snake.getHeadPosition());
		assertEquals(snake.getDirection(), Snake.MOVEMENT_DIR.LEFT);
	}
	
	@Test
	public void testSnakeNextHeadPositionWithNullAsDirection() {
		Snake snake = new Snake();
		assertEquals(new Point(0, 1), snake.getNextHeadPosition(null));
		
	}
	
	@Test
	public void testSnakeHeadPositionse() {
		Snake snake = new Snake();
		assertEquals(new Point(1, 0), snake.getNextHeadPosition(Snake.MOVEMENT_DIR.DOWN));
		snake.move();
		assertEquals(new Point(1, 0), snake.getHeadPosition());		
		assertEquals(snake.getDirection(), Snake.MOVEMENT_DIR.DOWN);
		
		assertEquals(new Point(1, 1), snake.getNextHeadPosition(Snake.MOVEMENT_DIR.RIGHT));
		snake.move();
		assertEquals(new Point(1, 1), snake.getHeadPosition());
		assertEquals(snake.getDirection(), Snake.MOVEMENT_DIR.RIGHT);
		
		assertEquals(new Point(0, 1), snake.getNextHeadPosition(Snake.MOVEMENT_DIR.UP));
		snake.move();
		assertEquals(new Point(0, 1), snake.getHeadPosition());
		assertEquals(snake.getDirection(), Snake.MOVEMENT_DIR.UP);
		
		assertEquals(new Point(0, 0), snake.getNextHeadPosition(Snake.MOVEMENT_DIR.LEFT));
		snake.move();
		assertEquals(new Point(0, 0), snake.getHeadPosition());
		assertEquals(snake.getDirection(), Snake.MOVEMENT_DIR.LEFT);
		
	}
	
	@Test
	public void testSnakeHeadMovements() {
		Snake snake = new Snake();
		snake.move(Snake.MOVEMENT_DIR.DOWN);
		assertEquals(new Point(1, 0), snake.getHeadPosition());
		assertEquals(snake.getDirection(), Snake.MOVEMENT_DIR.DOWN);
		snake.move(Snake.MOVEMENT_DIR.RIGHT);
		assertEquals(new Point(1, 1), snake.getHeadPosition());
		assertEquals(snake.getDirection(), Snake.MOVEMENT_DIR.RIGHT);
		snake.move(Snake.MOVEMENT_DIR.UP);
		assertEquals(new Point(0, 1), snake.getHeadPosition());
		assertEquals(snake.getDirection(), Snake.MOVEMENT_DIR.UP);
		snake.move(Snake.MOVEMENT_DIR.LEFT);
		assertEquals(new Point(0, 0), snake.getHeadPosition());
		assertEquals(snake.getDirection(), Snake.MOVEMENT_DIR.LEFT);
	}

	@Test
	public void testSnakeCurrentDirectionWithoutMovement() {
		Snake snake = new Snake();
		snake.move(Snake.MOVEMENT_DIR.DOWN);
		snake.move();
		assertEquals(new Point(2, 0), snake.getHeadPosition());
		assertEquals(snake.getDirection(), Snake.MOVEMENT_DIR.DOWN);
		
		snake.move(Snake.MOVEMENT_DIR.RIGHT);
		snake.move();
		assertEquals(new Point(2, 2), snake.getHeadPosition());
		assertEquals(snake.getDirection(), Snake.MOVEMENT_DIR.RIGHT);
		
		snake.move(Snake.MOVEMENT_DIR.UP);
		snake.move();
		assertEquals(new Point(0, 2), snake.getHeadPosition());
		assertEquals(snake.getDirection(), Snake.MOVEMENT_DIR.UP);
		
		snake.move(Snake.MOVEMENT_DIR.LEFT);
		snake.move();
		assertEquals(new Point(0, 0), snake.getHeadPosition());
		assertEquals(snake.getDirection(), Snake.MOVEMENT_DIR.LEFT);
	}
	
	@Test
	public void testSnakeOppositeDirectionMovement() {
		Snake snake = new Snake();
		snake.move(Snake.MOVEMENT_DIR.DOWN);
		snake.move(Snake.MOVEMENT_DIR.UP);
		assertEquals(new Point(2, 0), snake.getHeadPosition());
		assertEquals(snake.getDirection(), Snake.MOVEMENT_DIR.DOWN);
		
		snake.move(Snake.MOVEMENT_DIR.RIGHT);
		snake.move(Snake.MOVEMENT_DIR.LEFT);
		assertEquals(new Point(2, 2), snake.getHeadPosition());
		assertEquals(snake.getDirection(), Snake.MOVEMENT_DIR.RIGHT);
		
		snake.move(Snake.MOVEMENT_DIR.UP);
		snake.move(Snake.MOVEMENT_DIR.DOWN);
		assertEquals(new Point(0, 2), snake.getHeadPosition());
		assertEquals(snake.getDirection(), Snake.MOVEMENT_DIR.UP);
		
		snake.move(Snake.MOVEMENT_DIR.LEFT);
		snake.move(Snake.MOVEMENT_DIR.RIGHT);
		assertEquals(new Point(0, 0), snake.getHeadPosition());
		assertEquals(snake.getDirection(), Snake.MOVEMENT_DIR.LEFT);
	}
	
	@Test
	public void testSnakeContainsPoint(){
		Snake snake = new Snake();
		snake.grow();
		assertTrue(snake.bodyContains(new Point(0,0)));
	}
	@Test
	public void testSnakeDoesNotContainPoint(){
		Snake snake = new Snake();
		assertTrue(!snake.bodyContains(new Point(0,1)));
	}
	@Test
	public void testSnakeGrowrth(){
		Snake snake = new Snake();
		snake.grow();
		assertEquals(2, snake.getLength());
		assertTrue(snake.bodyContains(new Point(0,0)));
		assertEquals(snake.getHeadPosition(), new Point(0,1));
	}
	
	@Test
	public void testSnakeMovementWithNullAsDirection(){
		Snake snake = new Snake();
		snake.move(null);
		assertEquals(snake.getHeadPosition(), new Point(0,1));
	}
	
	@Test
	public void testMovementOfAGrownSnake(){
		Snake snake = new Snake();
		snake.grow();
		assertEquals(2, snake.getLength());
		assertEquals(new Point(0,1), snake.getHeadPosition());
		assertTrue(snake.bodyContains(new Point(0,0)));
		assertEquals(new Point(1,1), snake.getNextHeadPosition(MOVEMENT_DIR.DOWN));
		snake.move(MOVEMENT_DIR.DOWN);
		assertEquals(2, snake.getLength());
		assertEquals(new Point(1,1), snake.getHeadPosition());
		assertTrue(snake.bodyContains(new Point(0,1)));
	}
}
