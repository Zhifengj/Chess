import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;


public class ChessTestTest {
    private static Board standardBoard;
    private static Piece genericPieceOnBoard;
    private static Game gameLogic;
    private static final int standardBoardDimension = 8;
    private static final int xGenericPieceLocation = 4;
    private static final int yGenericPieceLocation = 4;
    private static final int xEmptyPosition = 0;
    private static final int yEmptyPosition = 0;
    private static final int BLACK = 0;
    private static final int WHITE = 1;

    public void testRunStarted(Description description) throws Exception {
        System.out.println("Number of tests to execute: " + description.testCount());
    }

    public void testRunFinished(Result result) throws Exception {
        System.out.println("Number of tests executed: " + result.getRunCount());
    }

    public void testStarted(Description description) throws Exception {
        System.out.println("Starting: " + description.getMethodName());
    }

    public void testFinished(Description description) throws Exception {
        System.out.println("Finished: " + description.getMethodName());
    }

    public void testFailure(Failure failure) throws Exception {
        System.out.println("Failed: " + failure.getDescription().getMethodName());
    }

    public void testAssumptionFailure(Failure failure) {
        System.out.println("Failed: " + failure.getDescription().getMethodName());
    }

    public void testIgnored(Description description) throws Exception {
        System.out.println("Ignored: " + description.getMethodName());
    }

    /**
     * Sets up reused objects for all test cases
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setupBeforeClass() throws Exception {
        standardBoard = new Board(standardBoardDimension, standardBoardDimension);
        genericPieceOnBoard = new Piece(standardBoard, BLACK);
        gameLogic = new Game();
        genericPieceOnBoard.moveTo(xGenericPieceLocation, yGenericPieceLocation);
    }

    @After
    public void tearDown() throws Exception {
        standardBoard = new Board(standardBoardDimension, standardBoardDimension);
        genericPieceOnBoard = new Piece(standardBoard, BLACK);
        gameLogic = new Game();
        genericPieceOnBoard.moveTo(xGenericPieceLocation, yGenericPieceLocation);
    }

    /**
     * This test checks that the Chess Board is properly initialized.
     * Assuming rectangular chess board
     */
    /**
     * Makes sure to check that any out-of-bounds locations are rejected.
     */
    @Test
    public void testBoundsOfBoard() {
        assertFalse(standardBoard.isInBounds(8, 8));
        assertFalse(standardBoard.isInBounds(-1, -1));
        assertTrue(standardBoard.isInBounds(4, 7));
        assertTrue(standardBoard.isInBounds(0, 0));
        assertTrue(standardBoard.isInBounds(7, 7));
    }

    /**
     * Checks that the board can return pieces on the board.
     * If there is no chess piece at a given location, the return should be null.
     */


    /**
     * Test whether a generic chess piece can move to certain spots.
     */


    /**
     * Test to make sure pawns move properly.
     * <p>
     * Note: White pawns move up, Black pawns move down.
     */
    @Test
    public void testPawnMovements() {
        Pawn testPawn = new Pawn(standardBoard, WHITE, 3, 3);

        //One step
        assertTrue(testPawn.canMoveTo(2, 3));
        //Two step first move
        assertTrue(testPawn.canMoveTo(1, 3));
        //Three step
        assertFalse(testPawn.canMoveTo(0, 3));
        //Back step
        assertFalse(testPawn.canMoveTo(4, 3));

        // Diagonal with enemies
        Pawn testPawn2 = new Pawn(standardBoard, BLACK, 2, 2);
        assertTrue(testPawn.canMoveTo(2, 2));


        // Diagonal without enemies
        assertTrue(testPawn.canMoveTo(2, 2));
        // One step, black
        assertTrue(testPawn2.canMoveTo(3, 2));
        // back step, black
        assertFalse(testPawn2.canMoveTo(1, 2));

        //Invalid move, out-of-bounds
        testPawn.moveTo(0, 0);
        assertFalse(testPawn.canMoveTo(-1, 0));

        // Invalid move, partner in front
        testPawn.moveTo(3, 3);
        Pawn testPawn3 = new Pawn(standardBoard, WHITE, 2, 3);
        assertFalse(testPawn.canMoveTo(2, 3));

        // Two step, already moved
        testPawn3.moveTo(5, 5);
        assertFalse(testPawn3.canMoveTo(3, 5));
    }

    /**
     * Test to make sure knight moves properly.
     */
    @Test
    public void testKnightMovements() {
        Knight testKnight = new Knight(standardBoard, WHITE, 3, 3);

        // All 8 valid movements
        assertTrue(testKnight.canMoveTo(1, 2));
        assertTrue(testKnight.canMoveTo(1, 4));
        assertTrue(testKnight.canMoveTo(5, 2));
        assertTrue(testKnight.canMoveTo(5, 4));
        assertTrue(testKnight.canMoveTo(2, 1));
        assertTrue(testKnight.canMoveTo(4, 1));
        assertTrue(testKnight.canMoveTo(2, 5));
        assertTrue(testKnight.canMoveTo(4, 5));

        // same spot
        assertFalse(testKnight.canMoveTo(3, 3));

        // empty spot, but invalid movement
        assertFalse(testKnight.canMoveTo(4, 4));

        // out of bounds
        testKnight.moveTo(1, 1);
        assertFalse(testKnight.canMoveTo(-1, 0));

        // ally spot
        assertTrue(testKnight.canMoveTo(3, 2));
        Pawn testPawn = new Pawn(standardBoard, WHITE, 3, 2);
        assertFalse(testKnight.canMoveTo(3, 2));
    }

    /**
     * Test to make sure rook moves properly.
     */
    @Test
    public void testRookMovements() {
        Rook testRook = new Rook(standardBoard, WHITE, 1, 1);

        assertTrue(testRook.canMoveTo(5, 1));
        assertTrue(testRook.canMoveTo(1, 4));

        //diagonally
        assertFalse(testRook.canMoveTo(2, 2));

        // Units in the way
        Pawn testEnemyPawn = new Pawn(standardBoard, BLACK, 2, 1);
        Pawn testAllyPawn = new Pawn(standardBoard, WHITE, 1, 1);

        assertTrue(testRook.canMoveTo(2, 1));
        assertFalse(testRook.canMoveTo(3, 1));
        assertFalse(testRook.canMoveTo(1, 1));

        // Out of Bounds
        assertFalse(testRook.canMoveTo(1, -1));
    }

    /**
     * Test to make sure bishop moves properly.
     */
    @Test
    public void testBishopMovements() {
        Bishop testBishop = new Bishop(standardBoard, WHITE, 1, 1);

        //diagonally in both directions
        assertTrue(testBishop.canMoveTo(3, 3));
        assertTrue(testBishop.canMoveTo(0, 2));

        //horizontally/vertically
        assertFalse(testBishop.canMoveTo(2, 1));
        assertFalse(testBishop.canMoveTo(1, 2));

        // Units in the way
        Pawn testEnemyPawn = new Pawn(standardBoard, BLACK, 2, 2);
        Pawn testAllyPawn = new Pawn(standardBoard, WHITE, 2, 0);

        assertTrue(testBishop.canMoveTo(2, 2));
        assertFalse(testBishop.canMoveTo(3, 3));
        assertFalse(testBishop.canMoveTo(2, 0));

        // Out of Bounds
        assertFalse(testBishop.canMoveTo(-1, -1));
    }

    /**
     * Test to verify the Queen moves properly
     */
    @Test
    public void testQueenMovements() {
        Queen testQueen = new Queen(standardBoard, WHITE, 1, 1);

        // Test straight
        straightMovementCheck(testQueen);

        // Test diagonal
        diagonalMovementCheck(testQueen);
    }

    /**
     * Helper function for testing if a piece that moves
     * diagonally works properly (bishops, queen)
     *
     * @param testPiece - the test piece being tested
     */
    private void diagonalMovementCheck(Piece testPiece) {
        testPiece.moveTo(1, 1);
        assertTrue(testPiece.canMoveTo(3, 3));
        assertTrue(testPiece.canMoveTo(0, 2));

        // Units in the way
        Pawn testEnemyPawn = new Pawn(standardBoard, BLACK, 2, 2);
        Pawn testAllyPawn = new Pawn(standardBoard, WHITE, 2, 0);

        assertTrue(testPiece.canMoveTo(2, 2));
        assertFalse(testPiece.canMoveTo(3, 3));
        assertFalse(testPiece.canMoveTo(2, 0));

        // Out of Bounds
        assertFalse(testPiece.canMoveTo(-1, -1));
    }

    /**
     * Helper function for testing if a piece that moves
     * straight works properly (rook, queen)
     *
     * @param testPiece - the test piece being tested
     */
    private void straightMovementCheck(Piece testPiece) {
        testPiece.moveTo(1, 1);
        //forward and backward
        assertTrue(testPiece.canMoveTo(5, 1));
        assertTrue(testPiece.canMoveTo(1, 4));

        //diagonally
        //assertFalse(testPiece.canMoveTo(2, 2));

        // Units in the way
        Pawn testEnemyPawn = new Pawn(standardBoard, BLACK, 2, 1);
        Pawn testAllyPawn = new Pawn(standardBoard, WHITE, 1, 1);

        assertTrue(testPiece.canMoveTo(2, 1));
        assertFalse(testPiece.canMoveTo(3, 1));
        assertFalse(testPiece.canMoveTo(1, 1));

        // Out of Bounds
        assertFalse(testPiece.canMoveTo(1, -1));
    }

    /**
     * Test to verify the King moves properly
     */
    @Test
    public void testKingMovements() {
        King testKing = new King(standardBoard, WHITE, 1, 1);

        // Test all 8 valid spots
        assertTrue(testKing.canMoveTo(0, 0));
        assertTrue(testKing.canMoveTo(0, 1));
        assertTrue(testKing.canMoveTo(0, 2));
        assertTrue(testKing.canMoveTo(1, 0));
        assertTrue(testKing.canMoveTo(1, 2));
        assertTrue(testKing.canMoveTo(2, 0));
        assertTrue(testKing.canMoveTo(2, 1));
        assertTrue(testKing.canMoveTo(2, 2));

        // Test same spot
        assertFalse(testKing.canMoveTo(1, 1));

        // Test too many steps
        assertFalse(testKing.canMoveTo(3, 1));

        // Test out of bounds
        testKing.moveTo(0, 0);
        assertFalse(testKing.canMoveTo(-1, 0));

        // Ally unit in the way
        assertTrue(testKing.canMoveTo(1, 1));
        Pawn testPawn = new Pawn(standardBoard, WHITE, 1, 1);
        assertFalse(testKing.canMoveTo(1, 1));
    }


    /**
     * Test the pieces can be removed.
     */
    @Test
    public void removePieceTest() {
        genericPieceOnBoard.removePiece();
        assertFalse(genericPieceOnBoard.onBoard());
        assertTrue(standardBoard.isEmptyPosition(xGenericPieceLocation, yGenericPieceLocation));
    }

    /**
     * Checks that pieces can capture other pieces.
     * Capturing a piece also entails that the captured is removed from
     * the board.
     */
    @Test
    public void capturePieceTest() {
        Piece genericWhitePiece = new Piece(standardBoard, WHITE, 0, 0);
        genericPieceOnBoard.capturePiece(genericWhitePiece);

        assertFalse(genericWhitePiece.onBoard());
    }

    /**
     * Test to find checkmates
     */
    @Test
    public void checkmateFound() {
        King blackKing = gameLogic.getBlackKing();

        blackKing.moveTo(4, 4);
        Queen queen1 = gameLogic.addQueen(WHITE, 3, 3);
        Queen queen2 = gameLogic.addQueen(WHITE, 5, 5);

        assertTrue(gameLogic.isCheckmate(BLACK));

        gameLogic.removePiece(queen1);
        assertFalse(gameLogic.isCheckmate(BLACK));
    }

    /**
     * Test that a player has a legal move available.
     * <p>
     * /**
     * Test whether a king has been checked.
     */
    @Test
    public void kingChecked() {
        King blackKing = gameLogic.getBlackKing();
        blackKing.moveTo(0, 0);

        // Test checkmate
        Queen queen = gameLogic.addQueen(WHITE, 3, 0);
        assertTrue(gameLogic.isKingInCheck(BLACK));

        // Test non-checkmate
        gameLogic.removePiece(queen);
        assertFalse(gameLogic.isKingInCheck(BLACK));
    }
}

