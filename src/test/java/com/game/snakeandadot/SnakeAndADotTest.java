package com.game.snakeandadot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.intThat;
import static org.mockito.Mockito.ignoreStubs;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.junit.Test;

import com.game.snakeandadot.game.Snake;
import com.game.snakeandadot.game.SnakeAndADot;

public class SnakeAndADotTest {
	@Test
	public void testGameBoardInitialized(){
		SnakeAndADot snakeAndADot1 = new SnakeAndADot(10, 20);
		assertEquals(10, snakeAndADot1.getGridHeight());
		assertEquals(20, snakeAndADot1.getGridWidth());
		SnakeAndADot snakeAndADot2 = new SnakeAndADot(30, 10);
		assertEquals(30, snakeAndADot2.getGridHeight());
		assertEquals(10, snakeAndADot2.getGridWidth());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMinimumHeightValidation(){
		new SnakeAndADot(9, 20);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMinimumWidthValidation(){
		new SnakeAndADot(20, 9);
	}
	
	@Test
	public void testNextMoveSuccessfulWithNullDirection(){
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		snakeAndADot.start();
		assertTrue(snakeAndADot.moveSuccessful(null));
	}
	
	@Test
	public void testSizeOfSnakeOnGameStart(){
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		snakeAndADot.start();
		assertTrue(snakeAndADot.getSnake().getLength() == 1);
	}
	
	@Test
	public void testSnakeShouldNotExistBeforeGameStarts(){
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		assertTrue(snakeAndADot.getSnake() == null);
	}
	
	@Test
	public void testGetNextDotPositionReturnAdifferentWhenCalledAgain(){
		int gridHeight = 10, gridWidth = 10;
		SnakeAndADot snakeAndADot = new SnakeAndADot(gridHeight, gridWidth);
		List<Point> snakePoints = new ArrayList<Point>();
		for (int i = 1; i < gridHeight; i++){
			for (int j = 4; j< gridWidth; j++){
				snakePoints.add(new Point(i, j));
			}
		}
		Snake snake = new Snake(snakePoints);
		snakeAndADot.start(snake);
		Point oldDotPosition  = snakeAndADot.getNextDotPosition();
		Point newDotPosition = snakeAndADot.getNextDotPosition();
		assertTrue(!newDotPosition.equals(oldDotPosition));
		assertTrue(!snake.bodyContains(newDotPosition));
		assertTrue(!snake.getHeadPosition().equals(newDotPosition));
		assertTrue(newDotPosition.getX() < gridHeight);
		assertTrue(newDotPosition.getY() < gridWidth);
	}
	
	@Test
	public void testSnakeShouldGrowIfItHitsADot(){
		Snake snake = new Snake();
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		snakeAndADot.start(snake);
		snakeAndADot.setDot(new Point(0,1));
		assertTrue(snakeAndADot.moveSuccessful());
		assertEquals(snakeAndADot.getSnake().getLength(), 2);
		assertEquals(snakeAndADot.getSnake().getHeadPosition(), new Point(0,1));
	}
	
	@Test 
	public void testDotShouldNotBeSetBeforeTheGameStarts(){
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		assertTrue(snakeAndADot.getDot() == null);
	}
	
	@Test
	public void testDotShouldBeSetWhenTheGameIsStartedByPassingASnake(){
		int gridHeight = 10, gridWidth = 10;
		SnakeAndADot snakeAndADot = new SnakeAndADot(gridHeight, gridWidth);
		List<Point> snakePoints = new ArrayList<Point>();
		for (int i = 0; i < gridHeight; i++){
			for (int j = 0; j< gridWidth; j++){
				if (!(i==0 && j==0)){
					snakePoints.add(new Point(i, j));
				}
			}
		}
		Snake snake = new Snake(snakePoints);
		snakeAndADot.start(snake);
		assertEquals(snakeAndADot.getDot(), new Point(0,0));
	}
	
	@Test 
	public void testDotShouldBeSetWhenTheGameIsStartedWithoutPassingASnake(){
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		snakeAndADot.start();
		Point dotPosition = snakeAndADot.getDot();
		assertTrue(!(dotPosition == null));
		assertTrue(!dotPosition.equals(new Point(0,0)));
	}
	
	@Test
	public void testDotPosShouldChangeWhenTheSnakeGrowsAfterHittingADot(){
		int gridHeight = 10, gridWidth = 10;
		SnakeAndADot snakeAndADot = new SnakeAndADot(gridHeight, gridWidth);
		List<Point> snakePoints = new ArrayList<Point>();
		for (int i = 0; i < gridHeight; i++){
			for (int j = 0; j< gridWidth; j++){
				if (!(i==0 && j==0) && !(i==9 && j==9)){
					snakePoints.add(new Point(i, j));
				}
			}
		}
		Snake snake = new Snake(snakePoints);
		snakeAndADot.start(snake);
		snakeAndADot.setDot(new Point(9,9));
		snakeAndADot.moveSuccessful();
		assertEquals(snakeAndADot.getDot(), new Point(0,0));
	}
	
	@Test
	public void testDotPosShouldChangeWhenTheSnakeGrowsAfterHittingADotAfterTurning(){
		int gridHeight = 10, gridWidth = 10;
		SnakeAndADot snakeAndADot = new SnakeAndADot(gridHeight, gridWidth);
		List<Point> snakePoints = new ArrayList<Point>();
		snakePoints.add(new Point(1, 0));
		Snake snake = new Snake(snakePoints);
		snakeAndADot.start(snake);
		snakeAndADot.setDot(new Point(0,0));
		snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.UP);
		assertTrue(!snakeAndADot.getDot().equals(new Point(0,0)));
	}
	
	@Test
	public void testDotPosShouldNotChangeIfTheSnakeDoesnotHitADot(){
		int gridHeight = 10, gridWidth = 10;
		SnakeAndADot snakeAndADot = new SnakeAndADot(gridHeight, gridWidth);
		List<Point> snakePoints = new ArrayList<Point>();
		snakePoints.add(new Point(1, 0));
		Snake snake = new Snake(snakePoints);
		snakeAndADot.start(snake);
		snakeAndADot.setDot(new Point(0,0));
		snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.DOWN);
		assertTrue(snakeAndADot.getDot().equals(new Point(0,0)));
		snakeAndADot.moveSuccessful();
		assertTrue(snakeAndADot.getDot().equals(new Point(0,0)));
	}
	
	@Test
	public void testSnakeDoesnotGrowIfItDoesNotHitADot(){
		Snake snake = new Snake();
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		snakeAndADot.start(snake);
		snakeAndADot.setDot(new Point(0,2));
		snakeAndADot.moveSuccessful();
		assertEquals(snakeAndADot.getSnake().getLength(), 1);
		assertEquals(snakeAndADot.getSnake().getHeadPosition(), new Point(0,1));
	}
	
	@Test
	public void testSnakeGrowsIfHitsADotAfterTurning(){
		Snake snake = new Snake();
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		snakeAndADot.start(snake);
		snakeAndADot.setDot(new Point(1,0));
		snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.DOWN);
		assertEquals(snakeAndADot.getSnake().getLength(), 2);
		assertEquals(snakeAndADot.getSnake().getHeadPosition(), new Point(1,0));
		assertTrue(snakeAndADot.getSnake().bodyContains(new Point(0, 0)));
		
		snakeAndADot.setDot(new Point(1,1));
		snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.RIGHT);
		assertEquals(snakeAndADot.getSnake().getLength(), 3);
		assertEquals(snakeAndADot.getSnake().getHeadPosition(), new Point(1,1));
		assertTrue(snakeAndADot.getSnake().bodyContains(new Point(0, 0)));
		assertTrue(snakeAndADot.getSnake().bodyContains(new Point(1, 0)));
		
		snakeAndADot.moveSuccessful();
		assertEquals(snakeAndADot.getSnake().getLength(), 3);
		assertEquals(snakeAndADot.getSnake().getHeadPosition(), new Point(1,2));
		
		snakeAndADot.setDot(new Point(0,2));
		snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.UP);
		assertEquals(snakeAndADot.getSnake().getLength(), 4);
		assertEquals(snakeAndADot.getSnake().getHeadPosition(), new Point(0,2));
		assertTrue(snakeAndADot.getSnake().bodyContains(new Point(1,0)));
		assertTrue(snakeAndADot.getSnake().bodyContains(new Point(1, 1)));
		assertTrue(snakeAndADot.getSnake().bodyContains(new Point(1, 2)));
		
		snakeAndADot.setDot(new Point(0,1));
		snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.LEFT);
		assertEquals(snakeAndADot.getSnake().getLength(), 5);
		assertEquals(snakeAndADot.getSnake().getHeadPosition(), new Point(0,1));
		assertTrue(snakeAndADot.getSnake().bodyContains(new Point(0,2)));
		assertTrue(snakeAndADot.getSnake().bodyContains(new Point(1,0)));
		assertTrue(snakeAndADot.getSnake().bodyContains(new Point(1, 1)));
		assertTrue(snakeAndADot.getSnake().bodyContains(new Point(1, 2)));
	}
	
	
	@Test
	public void testSnakeShouldNotGrowsIfItsDoesNotHitsADotAfterTurning(){
		Snake snake = new Snake();
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		snakeAndADot.start(snake);
		snakeAndADot.setDot(new Point(10,10));
		snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.DOWN);
		assertEquals(snakeAndADot.getSnake().getLength(), 1);
		assertEquals(snakeAndADot.getSnake().getHeadPosition(), new Point(1,0));
		snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.RIGHT);
		assertEquals(snakeAndADot.getSnake().getLength(), 1);
		assertEquals(snakeAndADot.getSnake().getHeadPosition(), new Point(1,1));
		snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.UP);
		assertEquals(snakeAndADot.getSnake().getLength(), 1);
		assertEquals(snakeAndADot.getSnake().getHeadPosition(), new Point(0,1));
		snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.LEFT);
		assertEquals(snakeAndADot.getSnake().getLength(), 1);
		assertEquals(snakeAndADot.getSnake().getHeadPosition(), new Point(0,0));
	}
	
	@Test
	public void testSnakeDiesIfHitsRightBoundry(){
		List<Point> snakePoints = new ArrayList<Point>();
		snakePoints.add(new Point(0,9));
		Snake snake = new Snake(snakePoints);
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		snakeAndADot.start(snake);
		assertTrue(!snakeAndADot.moveSuccessful());
	}
	
	@Test
	public void testSnakeDiesIfHitsLeftBoundry(){
		List<Point> snakePoints = new ArrayList<Point>();
		snakePoints.add(new Point(0,1));
		Snake snake = new Snake(snakePoints);
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		snakeAndADot.start(snake);
		snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.DOWN);
		snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.LEFT);
		assertTrue(!snakeAndADot.moveSuccessful());
	}
	
	@Test
	public void testSnakeDiesIfHitsTopBoundry(){
		List<Point> snakePoints = new ArrayList<Point>();
		snakePoints.add(new Point(0,1));
		Snake snake = new Snake(snakePoints);
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		snakeAndADot.start(snake);
		snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.UP);
		assertTrue(!snakeAndADot.moveSuccessful());
	}
	
	@Test
	public void testSnakeDiesIfHitsBottomBoundry(){
		List<Point> snakePoints = new ArrayList<Point>();
		snakePoints.add(new Point(8,0));
		Snake snake = new Snake(snakePoints);
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		snakeAndADot.start(snake);
		snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.DOWN);
		assertTrue(!snakeAndADot.moveSuccessful());
	}
	
	@Test
	public void testSnakeDiesIfHitsTopBoundryAfterChangingDirection(){
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		snakeAndADot.start();
		assertTrue(!snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.UP));
	}
	
	@Test
	public void testSnakeDiesIfHitsBottomBoundryAfterChangingDirection(){
		List<Point> snakePoints = new ArrayList<Point>();
		snakePoints.add(new Point(9,0));
		Snake snake = new Snake(snakePoints);
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		snakeAndADot.start(snake);
		assertTrue(!snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.DOWN));
	}
	
	@Test
	public void testSnakeDiesIfHitsRightBoundryAfterChangingDirection(){
		List<Point> snakePoints = new ArrayList<Point>();
		snakePoints.add(new Point(0,9));
		Snake snake = new Snake(snakePoints);
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		snakeAndADot.start(snake);
		assertTrue(snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.DOWN));
		assertTrue(!snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.RIGHT));
	}
	
	@Test
	public void testSnakeDiesIfHitsLeftBoundryAfterChangingDirection(){
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		snakeAndADot.start();
		assertTrue(snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.DOWN));
		assertTrue(!snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.LEFT));
	}
	
	@Test
	public void testSnakeShouldNotDieIfItDoesnotHitBoundryAfterChangingDirection(){
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		snakeAndADot.start();
		assertTrue(snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.DOWN));
	}
	
	@Test
	public void testSnakeShouldLiveIfDoesnotHitBoundry(){
		List<Point> snakePoints = new ArrayList<Point>();
		snakePoints.add(new Point(0,8));
		Snake snake = new Snake(snakePoints);
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		snakeAndADot.start(snake);
		assertTrue(snakeAndADot.moveSuccessful());
	}
	
	@Test
	public void testSnakeDiesIfItHitsItself(){
		List<Point> snakePoints = new ArrayList<Point>();
		snakePoints.add(new Point(0,8)); //BODY POINT
		snakePoints.add(new Point(1,8)); //BODY POINT
		snakePoints.add(new Point(1,7)); //HEAD
		Snake snake = new Snake(snakePoints);
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		snakeAndADot.start(snake);
		assertTrue(!snakeAndADot.moveSuccessful());
	}
	
	@Test
	public void testSnakeDiesIfItHitsItselfAfterChangingDirection(){
		List<Point> snakePoints = new ArrayList<Point>();
		snakePoints.add(new Point(0,8)); //BODY POINT
		snakePoints.add(new Point(0,9)); //BODY POINT
		snakePoints.add(new Point(1,9)); //HEAD
		Snake snake = new Snake(snakePoints);
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		snakeAndADot.start(snake);
		assertTrue(!snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.UP));
	}
	
	@Test
	public void testSnakeLivesIfItDoesnotItselfAfterMoving(){
		List<Point> snakePoints = new ArrayList<Point>();
		snakePoints.add(new Point(0,8)); //BODY POINT
		snakePoints.add(new Point(0,9)); //BODY POINT
		snakePoints.add(new Point(1,8)); //HEAD
		Snake snake = new Snake(snakePoints);
		SnakeAndADot snakeAndADot = new SnakeAndADot(10, 10);
		snakeAndADot.start(snake);
		assertTrue(snakeAndADot.moveSuccessful(Snake.MOVEMENT_DIR.UP));
	}
}
