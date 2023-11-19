package pieces;

import program.Board;

import java.awt.image.BufferedImage;

public class Bishop extends Piece {
    public Bishop(Board board, int row, int col, boolean isWhite) {
        super(board);
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
        this.yPos = row * 50;
        this.xPos = col * 50;
        this.name = "Bishop";
        this.sprite = sheet.getSubimage(2 * sheetScale, isWhite ? 0 : sheetScale,sheetScale,sheetScale).getScaledInstance(board.squareSize,board.squareSize, BufferedImage.SCALE_SMOOTH);
    }

    public boolean canMove(int newRow, int newCol) {
        // Проверка возможности хода для слона
        if (Math.abs(newRow - row) == Math.abs(newCol - col)) {
            return true;
        }
        return false;
    }
    public void move(int newRow, int newCol) {
        row = newRow;
        col = newCol;
        xPos = col * board.squareSize;
        yPos = row * board.squareSize;
    }

}
