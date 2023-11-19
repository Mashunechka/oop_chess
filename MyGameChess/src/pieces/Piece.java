package pieces;
import program.Board;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Piece {
    public int col,row;
    public int xPos,yPos;

    public boolean isWhite;
    public String name;

    BufferedImage sheet;
    {
        try{
            sheet = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("pieces.png")));
        }catch (IIOException e){
            e.printStackTrace();
        } catch (IOException e) {

        }
    }
    protected int sheetScale = sheet.getWidth()/6;

    Image sprite;
    Board board;
    public Piece(Board board){
        this.board = board;
    }

    public void paint(Graphics2D g2d){
        g2d.drawImage(sprite,10 + xPos,10 + yPos,null);
    }

    public boolean canMove(int newRow, int newCol) {
        return false;
    }

    public void move(int newRow, int newCol) {
    }
    public void capture() {
        if (this instanceof King) { // если текущая фигура — король
            JOptionPane.showMessageDialog(board, "Мат! Игра закончена.");
        }
        board.pieceList.remove(this); // удалить фигуру из списка фигур
    }

}
