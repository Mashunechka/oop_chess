package pieces;
import program.Board;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class King extends Piece{
    public King(Board board,int row ,int col ,boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * 50;
        this.yPos = row * 50;
        this.isWhite = isWhite;
        this.name = "King";

        this.sprite = sheet.getSubimage(0 * sheetScale, isWhite ? 0 : sheetScale,sheetScale,sheetScale).getScaledInstance(board.squareSize,board.squareSize, BufferedImage.SCALE_SMOOTH);
    }
    public boolean canMove(int newRow, int newCol) {
        // Проверка возможности хода для короля
        if (Math.abs(newCol - col) <= 1 && Math.abs(newRow - row) <= 1) {
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
