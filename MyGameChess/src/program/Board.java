package program;

import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class Board extends JPanel {
    public int squareSize = 50;
    int cols = 8;
    int rows = 8;
    public ArrayList<Piece> pieceList = new ArrayList<>();

    public Piece selectedPiece; // выбранная фигура
    private int initialX, initialY; // начальные координаты фигуры
    private int mouseX, mouseY; // текущие координаты мыши
    public Board(){
        this.setPreferredSize(new Dimension(cols*squareSize,rows*squareSize));
        addPieces();
        addListeners();
    }

    public void addListeners() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                // Поиск выбранной фигуры и сохранение начальных координат
                for (Piece piece : pieceList) {
                    if (mouseX >= piece.xPos && mouseX <= piece.xPos + squareSize &&
                            mouseY >= piece.yPos && mouseY <= piece.yPos + squareSize) {
                        selectedPiece = piece;
                        initialX = piece.xPos;
                        initialY = piece.yPos;
                        break;
                    }
                }
            }
            public void mouseReleased(MouseEvent e) {
                int newCol = (e.getX() - 10) / squareSize;
                int newRow = (e.getY() - 10) / squareSize;
                boolean canMove = true;
                Piece destroyedPiece = null;
                for (Piece piece : pieceList) {
                    if (piece != selectedPiece && piece.row == newRow && piece.col == newCol) {
                        if (piece.isWhite == selectedPiece.isWhite) {
                            canMove = false;
                        } else {
                            destroyedPiece = piece;
                            break;
                        }
                    }
                }
                if (selectedPiece != null && selectedPiece.canMove(newRow, newCol) && canMove && newCol >= 0 && newRow >= 0 && newCol < cols && newRow < rows) {
                    if (destroyedPiece != null) {
                        destroyedPiece.capture();
                    }
                    selectedPiece.move(newRow, newCol);
                } else {
                    // Если ход невозможен или за пределами доски, возвращаем фигуру на начальную позицию
                    selectedPiece.xPos = initialX;
                    selectedPiece.yPos = initialY;
                }
                selectedPiece = null;
                repaint();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                // Перемещение выбранной фигуры вместе с мышью
                if (selectedPiece != null) {
                    int newX = e.getX() - squareSize / 2;
                    int newY = e.getY() - squareSize / 2;
                    if (newX >= 0 && newY >= 0 && newX <= (cols - 1) * squareSize && newY <= (rows - 1) * squareSize) {
                        selectedPiece.xPos = newX;
                        selectedPiece.yPos = newY;
                    }
                    repaint();
                }
            }
        });
    }

    public void addPieces(){
        // Добавляем черные фигуры
        pieceList.add(new Rook(this, 0, 0, false));
        pieceList.add(new Knight(this, 0, 1, false));
        pieceList.add(new Bishop(this, 0, 2, false));
        pieceList.add(new Queen(this, 0, 3, false));
        pieceList.add(new King(this, 0, 4, false));
        pieceList.add(new Bishop(this, 0, 5, false));
        pieceList.add(new Knight(this, 0, 6, false));
        pieceList.add(new Rook(this, 0, 7, false));
        // Добавляем черные пешки
        for (int i = 0; i < 8; i++) {
            pieceList.add(new Pawn(this, 1, i, false));
        }

        // Добавляем белые фигуры
        pieceList.add(new Rook(this, 7, 0, true));
        pieceList.add(new Knight(this, 7, 1, true));
        pieceList.add(new Bishop(this, 7, 2, true));
        pieceList.add(new Queen(this, 7, 3, true));
        pieceList.add(new King(this, 7, 4, true));
        pieceList.add(new Bishop(this, 7, 5, true));
        pieceList.add(new Knight(this, 7, 6, true));
        pieceList.add(new Rook(this, 7, 7, true));
        // Добавляем белые пешки
        for (int i = 0; i < 8; i++) {
            pieceList.add(new Pawn(this, 6, i, true));
        }

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //рисование доски
        boolean white = true;
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                if(white) {
                    g.setColor(new Color(255, 213, 156));
                } else {
                    g.setColor(new Color(105, 46, 20));
                }
                g.fillRect(10 + x * squareSize, 10 + y * squareSize, squareSize, squareSize);
                // Добавляем черную границу
                g.setColor(Color.BLACK);
                g.drawRect(10 + x * squareSize, 10 + y * squareSize, squareSize, squareSize);

                white = !white;
            }
            white = !white;
        }

        for (Piece piece : pieceList){
            piece.paint(g2d);
        }
    }

}