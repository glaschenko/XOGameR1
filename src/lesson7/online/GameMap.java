package lesson7.online;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Random;

public class GameMap extends JPanel {

    private final int EMPTY_DOT = 0;
    private final int HUMAN_DOT = 1;
    private final int AI_DOT = 2;

    private final int STATE_DRAW = 0;
    private final int VERTICAL_WIN = 1;
    private final int HORIZONTAL_WIN = 2;
    private final int DIAGONAL_WIN = 3;
    private final int REVERSE_DIAGONAL_WIN = 4;
    private int stateWin;

    private int currentStateGameOver;
    private boolean isGameOver;


    public final Random RANDOM = new Random();

    public static final int GAME_MODE_HVH = 0;
    public static final int GAME_MODE_HVA = 1;
    public static int count = 0;

    private Image cross;
    private Image zero;
    private Image HORIZONTAL;
    private Image VERTICAL;
    private Image DIAGONAL;
    private Image REVERSE_DIAGONAL;

    private int gameMode;
    private int fieldSizeX;
    private int fieldSizeY;
    private int winLength;
    private Color colorMap = Color.WHITE;
    private int [][] field;
    private int cellWidth;
    private int cellHeight;
    private int dotI;
    private int dotJ;

    private boolean isExistMap;


    GameMap(){
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                    update(e);

            }
        });
        isExistMap = false;

    }
    void start(int gameMode, int fieldSizeX, int fieldSizeY, int winLength, Color colorMap){
        this.gameMode = gameMode;
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        this.winLength = winLength;
        this.colorMap = colorMap;
        setBackground(colorMap);
        count = 0;
        field = new int[fieldSizeX][fieldSizeY];
        isGameOver = false;
        isExistMap = true;
        repaint();
    }


    private void update(MouseEvent e){
        if (!isExistMap) return;
        if (isGameOver) return;


        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;
        System.out.println("X: " + cellX + ", Y:" + cellY);

        if (!isValidCell(cellX, cellY) || !isEmptyCell(cellX, cellY)) {
            return;
        }
        if(gameMode == GAME_MODE_HVH && count % 2 == 0) {
            player(cellY, cellX, AI_DOT);

        } else {
            player(cellY, cellX, HUMAN_DOT);

        }
        if(gameMode == GAME_MODE_HVA && isGameOver == false){
            aiTurn();
            repaint();

            if(checkWin(AI_DOT)){
                setGameOver(stateWin);
                return;
            }
            if(isFullMap()){
                setGameOver(STATE_DRAW);
                return;
            }
        }


    }

    private void setGameOver(int gameOverState){
        currentStateGameOver = gameOverState;
        isGameOver = true;
        repaint();
    }

    private void player(int cellY, int cellX, int characterSymbol){
        field[cellY][cellX] = characterSymbol;
        if(checkWin(characterSymbol)){
            setGameOver(stateWin);
            return;
        }
        if(isFullMap()){
            setGameOver(STATE_DRAW);
            return;
        }
        repaint();
    }

    private void render(Graphics g) throws IOException {
//
        if(!isExistMap) return;

        int width = getWidth();
        int height = getHeight();

        cellWidth = width / fieldSizeX;
        cellHeight = height / fieldSizeY;
        g.setColor(Color.BLACK);

        for (int i = 1; i < fieldSizeY; i++) {
            int y = i * cellHeight;
            g.drawLine(0, y, width, y);
        }
        for (int i = 1; i < fieldSizeX; i++) {
            int x = i * cellWidth;
            g.drawLine(x, 0, x, height);
        }
        cross = ImageIO.read(GameMap.class.getResourceAsStream("cross.png"));
        zero = ImageIO.read(GameMap.class.getResourceAsStream("zero.png"));
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isEmptyCell(x, y)) {
                    continue;
                }
                if (field[y][x] == HUMAN_DOT) {

                    g.drawImage(cross, x * cellWidth + 10, y * cellHeight + 10, cellWidth - 20, cellHeight - 20, null);

                } else if (field[y][x] == AI_DOT) {

                    g.drawImage(zero, x * cellWidth + 10, y * cellHeight + 10, cellWidth - 20, cellHeight - 20, null);


                }else {
                    throw new RuntimeException("Ошибка при отрисовке X: " + x + " Y: " + y);
                }

            }
        }
        count++;
        if(isGameOver){
           showGameOverState(g);
        }


    }

    private void showGameOverState(Graphics g) throws IOException {
        switch(currentStateGameOver) {
            case HORIZONTAL_WIN:
                HORIZONTAL = ImageIO.read(GameMap.class.getResourceAsStream("HORIZONTAL.png"));
                g.drawImage(HORIZONTAL, dotI * cellWidth - 30, dotJ * cellHeight + cellHeight / 2 - cellHeight / 8, cellWidth * winLength + 60, cellHeight / 4, null);
                break;
            case VERTICAL_WIN:
                VERTICAL = ImageIO.read(GameMap.class.getResourceAsStream("VERTICAL.png"));
                g.drawImage(VERTICAL, dotI * cellWidth + cellWidth / 2 - cellWidth /8, dotJ * cellHeight - 30, cellWidth / 4 , cellHeight * winLength + 60, null);
                break;
            case DIAGONAL_WIN:
                DIAGONAL = ImageIO.read(GameMap.class.getResourceAsStream("DIAGONAL.png"));
                for(int s = 0; s < winLength; s++)
                g.drawImage(DIAGONAL, (dotI + s) * cellWidth - 15, (dotJ + s) * cellHeight - 15, cellWidth + 15, cellHeight + 15,null);
                break;
            case REVERSE_DIAGONAL_WIN:
                REVERSE_DIAGONAL = ImageIO.read(GameMap.class.getResourceAsStream("REVERSE_DIAGONAL.png"));
                for(int s = 0; s < winLength; s++)
                    g.drawImage(REVERSE_DIAGONAL, (dotI + s) * cellWidth - 15, (dotJ - s) * cellHeight - 15, cellWidth + 15, cellHeight + 15,null);
                break;
            case STATE_DRAW:
                g.setColor(Color.WHITE);
                g.fillRect(120, 170, 250, 100);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Times New Roman", Font.ITALIC, 70));
                g.drawString("Ничья!", 150, getHeight() / 2);
                break;
            default:
                throw new RuntimeException("Непредвиденная ошибка: " + currentStateGameOver);
        }
    }

    private void aiTurn() {
        if (turnAIWinCell()) {
            return;
        }
        if (turnHumanWinCell()) {
            return;
        }
        int x;
        int y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isEmptyCell(x, y));
        field[y][x] = AI_DOT;
    }



    private boolean turnAIWinCell(){
        for(int i = 0; i < fieldSizeY; i++){
            for(int j = 0; j < fieldSizeX; j++){
                if(isEmptyCell(j, i)){
                    field[i][j] = AI_DOT;
                    if(checkWin(AI_DOT)){
                       return true;
                    }
                    field[i][j] = EMPTY_DOT;
                }


            }
        }
        return false;
    }



    private boolean turnHumanWinCell(){
        for(int i = 0; i < fieldSizeY; i++){
            for(int j = 0; j < fieldSizeX; j++){
                if(isEmptyCell(j, i)){
                    field[i][j] = HUMAN_DOT;
                    if(checkWin(HUMAN_DOT)){
                        field[i][j] = AI_DOT;
                       return true;
                    }
                    field[i][j] = EMPTY_DOT;
                }


            }
        }
        return false;
    }




    private boolean checkWin(int characterSymbol){
        for(int i = 0; i < fieldSizeX; i++){
            for(int j = 0; j < fieldSizeY; j++){
               if(checkLine(i, j, 1, 0, winLength, characterSymbol)){
                   stateWin = HORIZONTAL_WIN;
                   dotI = i;
                   dotJ = j;
                    return true;
               }
               if(checkLine(i, j, 0, 1, winLength, characterSymbol)){
                   stateWin = VERTICAL_WIN;
                   dotI = i;
                   dotJ = j;
                    return true;
               }
               if(checkLine(i, j, 1, 1, winLength, characterSymbol)){
                   stateWin = DIAGONAL_WIN;
                   dotI = i;
                   dotJ = j;
                    return true;
               }
               if(checkLine(i, j, 1, - 1, winLength, characterSymbol)){
                   stateWin = REVERSE_DIAGONAL_WIN;
                   dotI = i;
                   dotJ = j;
                    return true;
               }

            }
        }
        return false;
    }


    private boolean checkLine(int x, int y, int vx, int vy, int len, int characterSymbol){
        final int farX = x + (len - 1) * vx;
        final int farY = y + (len - 1) * vy;

        if(!isValidCell(farX, farY)){
            return false;
        }
        for(int i = 0; i < len; i++){
           if(field [y + i * vy][x + i * vx] != characterSymbol){
               return false;
           }

        }
        return true;

    }

    private boolean isFullMap(){
        for(int i = 0; i < fieldSizeX; i++){
            for(int j = 0; j < fieldSizeY; j++){
                if(field [i][j] == EMPTY_DOT){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    private boolean isEmptyCell(int x, int y) {
        return field[y][x] == EMPTY_DOT;
    }


    @Override
    protected void paintComponent(Graphics g){
    super.paintComponent(g);
        try {
            render(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
