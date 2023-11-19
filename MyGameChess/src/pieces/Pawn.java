package pieces;

import program.Board;
import java.awt.image.BufferedImage;

public class Pawn extends Piece {

    public boolean isFirstMove;
    public Pawn(Board board, int row, int col, boolean isWhite) {
        super(board);
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
        this.yPos = row * 50;
        this.xPos = col * 50;
        this.name = "Pawn";
        this.isFirstMove = true;
        this.sprite = sheet.getSubimage(5 * sheetScale, isWhite ? 0 : sheetScale,sheetScale,sheetScale).getScaledInstance(board.squareSize,board.squareSize, BufferedImage.SCALE_SMOOTH);
    }

    public boolean canMove(int newRow, int newCol) {
        // Проверка возможности хода для пешки
        if (isWhite) {
            if ((newCol == col && newRow == row - 1) || (isFirstMove && newCol == col && newRow == row - 2)) {
                isFirstMove = false;
                return true;
            }
            // Проверка возможности убийства фигуры другого цвета по диагонали
            if (Math.abs(newCol - col) == 1 && newRow == row - 1) {
                for (Piece piece : board.pieceList) {
                    if (piece.isWhite != isWhite && piece.row == newRow && piece.col == newCol) {
                        return true;
                    }
                }
            }
        } else { //то же самое для другого цвета
            if ((newCol == col && newRow == row + 1) ||  (isFirstMove && newCol == col && newRow == row + 2)) {
                isFirstMove = false;
                return true;
            }
            if (newRow == row + 1 && Math.abs(newCol - col) == 1) {
                for (Piece piece : board.pieceList) {
                    if (piece.isWhite != isWhite && piece.row == newRow && piece.col == newCol) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void move(int newRow, int newCol) {
        row = newRow;
        col = newCol;
        xPos = col * board.squareSize;
        yPos = row * board.squareSize;

        // Проверьте, дошла ли пешка до конца доски
        if ((isWhite && row == 0) || (!isWhite && row == 8 - 1)) {
            //превращение пешки в ферзя
            board.pieceList.remove(this);
            board.pieceList.add(new Queen(board, row, col, isWhite));
        }
    }

}
