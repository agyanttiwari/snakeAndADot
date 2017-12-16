package com.game.snakeandadot.controllers;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.game.snakeandadot.game.Snake;
import com.game.snakeandadot.game.SnakeAndADot;
import com.game.snakeanddot.models.SnakeDotPositionResponse;
import com.game.snakeanddot.models.startGameRequest;

@RestController
public class SnakeGameController {
	
	SnakeAndADot snakeAndADot;
	boolean gameStarted = false;
		
	@RequestMapping(method = RequestMethod.POST, path="/start")
	SnakeDotPositionResponse startGame(@RequestBody startGameRequest startGameRequest) {
		snakeAndADot = new SnakeAndADot(startGameRequest.getHeight(), startGameRequest.getWidth());
		snakeAndADot.start();
		this.gameStarted = true;
		return new SnakeDotPositionResponse(snakeAndADot.getSnake().getSnakePoints(), snakeAndADot.getDot(), false);
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/up")
	SnakeDotPositionResponse moveUp() {
		return this.moveSnake(Snake.MOVEMENT_DIR.UP);
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/down")
	SnakeDotPositionResponse moveDown() {
		return this.moveSnake(Snake.MOVEMENT_DIR.DOWN);
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/left")
	SnakeDotPositionResponse moveLeft() {
		return this.moveSnake(Snake.MOVEMENT_DIR.LEFT);
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/right")
	SnakeDotPositionResponse moveRight() {
		return this.moveSnake(Snake.MOVEMENT_DIR.RIGHT);
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/straight")
	SnakeDotPositionResponse moveStraight() {
		return this.moveSnake(null);
	}
	
	private SnakeDotPositionResponse moveSnake(Snake.MOVEMENT_DIR dir) {
		boolean gameOver = false;
		if (this.gameStarted) {
			if (dir == null) {
				gameOver = !snakeAndADot.moveSuccessful();
			} else {
				gameOver = !snakeAndADot.moveSuccessful(dir);
			}
			this.gameStarted = !gameOver;
			return this.getSnakePositionResponse(gameOver);
		}
		throw new IllegalArgumentException("Game not started.");
	}
	
	private SnakeDotPositionResponse getSnakePositionResponse(boolean gameOver){
		return new SnakeDotPositionResponse(this.snakeAndADot.getSnake().getSnakePoints(), this.snakeAndADot.getDot(), gameOver);
	}
	
	
	@ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
	void handleBadRequests(HttpServletResponse response) throws IOException {
	    response.sendError(HttpStatus.BAD_REQUEST.value());
	}
	
}
