package usi;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.management.modelmbean.InvalidTargetObjectTypeException;
import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class GamePanel extends JPanel implements ActionListener {
    int lastbirdcoor;
    int gamemode = 0; // 0 - пауза, 1 - ыгра
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    double yBirdCo = height / 2;
    Image bird;
    long StartTime;
    long speedstart;
    double g = 9.8;
    Date data = new Date();
    String datta = data.toString();
    LinkedList<pineapple> trubodur = new LinkedList<>();
    int birdwid = 50;
    int birdhe = 50;
    int score = 0;
    Image back;
    String gameScoreFile = "score.wav";
    boolean hah = true;
    int maxscore = readscorefromfile();


    GamePanel() {
        try {
            bird = ImageIO.read(GamePanel.class.getResource("bird.png"));
            back = ImageIO.read(GamePanel.class.getResource("backneckgroundkillingground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);


        if (gamemode == 0) {
            graphics.setColor(Color.orange);
            graphics.fillRect(0, 0, width, height);
            processScore();
            Font timesRoman = new Font("TimesRoman", Font.PLAIN, 100);
            graphics.drawString(String.valueOf(score), width/4, height/4);
            String pausetext = "Вах Вах Вах";
            graphics.setColor(Color.BLACK);
            FontMetrics metrics = graphics.getFontMetrics();
            graphics.setFont(timesRoman);
            graphics.drawString(pausetext, width / 2 - graphics.getFontMetrics().stringWidth(pausetext) / 2, height / 3);
            Font alt = new Font("Comic Sans MS", Font.ITALIC, 50);
            graphics.setFont(alt);
            String ntv = "- бандитский сериал по НТВ";
            graphics.drawString(ntv, width / 2 - graphics.getFontMetrics().stringWidth(ntv) / 2, height / 2);
            String q = "выйти можно на *кнопка удалена*";
            Font del = new Font("Georgia", Font.ITALIC, 20);
            graphics.setFont(del);
            double f = 1.5;
            graphics.drawString(q, width / 2 - graphics.getFontMetrics().stringWidth(ntv) / 2, (int) (height / f));
            graphics.drawString(datta, width / 10, (int) height / 10);
        }
        if (gamemode == 1) {
            graphics.drawImage(back, 0, 0, width, height, null);
            int calculatecoordinate = calculatecoordinate();
            System.out.println(score);
            graphics.drawImage(bird, width / 4, calculatecoordinate, null);
            processpine();
            processScore();
            graphics.setColor(Color.GREEN);
            Font timesRoman = new Font("TimesRoman", Font.PLAIN, 100);
            graphics.drawString(String.valueOf(score), width/4, height/4);
            drawpines(graphics);
            checklost(calculatecoordinate);
            if (checklost(calculatecoordinate) == false) {
                
            }
            if (checklost(calculatecoordinate) == true) {
                if (score > maxscore) {
                    saverecord();
                }
                if (maxscore>=score) {
                    hah = false;
                }
                gamemode = 2;

            }

        }
        if (gamemode == 2) {
            graphics.setColor(Color.pink);
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK);
            Font lol = new Font("Impact", Font.BOLD, 30);
            graphics.setFont(lol);
            String bread = "you are proigral. pozdravlayu";
            graphics.drawString(bread, width / 10, height / 2);
            Font kol = new Font("Terminal", Font.BOLD, 100);
            graphics.setColor(Color.black);
            graphics.setFont(kol);
            graphics.drawString(String.valueOf(score), (width - width/10)/2, height/2);
            if (hah == false) {
                graphics.setColor(Color.RED);
                String sorry = ("Old max score " + readscorefromfile());
                graphics.drawString(sorry, width/4, height/2);
            }
            if (hah = true) {
                graphics.setColor(Color.RED);
                String yeah = ("new max score " + readscorefromfile());
                graphics.drawString(yeah, width/4, height/2);
            }
        }
        javax.swing.Timer timer = new javax.swing.Timer(1000 / 60, this);
        timer.start();

    }

    int calculatecoordinate() {
        long time = System.currentTimeMillis() - StartTime;
        time = time / 60;
        lastbirdcoor = (int) (yBirdCo - speedstart * time + g * time * time / 2);
        return lastbirdcoor;
    }
    void processScore() {
        Iterator<pineapple> iterator = trubodur.iterator();
        while (iterator.hasNext()) {
            pineapple trubodur = iterator.next();
            int xpinecor = trubodur.calX();
            if (xpinecor < width / 4 && !trubodur.isCOUNTED) {
                trubodur.isCOUNTED = true;
                score++;
                return;
            }
        }
    }

    void reset() {
        trubodur.clear();
        yBirdCo = height / 2;
        score = 0;
        hah = true;
    }
    void saverecord() {
        try {
            PrintWriter dascore = new PrintWriter(gameScoreFile);
            dascore.write(String.valueOf(score));
            dascore.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    int readscorefromfile() {
        try {
            Scanner scanner = new Scanner(new FileInputStream(gameScoreFile));
            return scanner.nextInt();
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    boolean checklost(int topbirdedge) {
        int leftbirdedge = width / 4;
        int rightbirdedge = leftbirdedge + birdwid;
        int botbirdedge = topbirdedge + birdhe;
        Iterator<pineapple> iterator = trubodur.iterator();
        while (iterator.hasNext()) {
            pineapple trubodur = iterator.next();
            int leftpineedge = trubodur.calX();
            int rightpineedge = leftpineedge + pineapple.pinewidth;
            int toppineedge = trubodur.yGC - pineapple.gapH / 2;
            int botpineedge = trubodur.yGC + pineapple.gapH / 2;
            if (rightbirdedge > leftpineedge && rightbirdedge < rightpineedge ||
                    leftbirdedge > leftpineedge && leftbirdedge < rightpineedge) {
                if (topbirdedge < toppineedge || botbirdedge > botpineedge) {
                    return true;
                } else return false;
            }
        }
        return false;
    }
    // Генерирует ананасы
    void processpine() {
        if (trubodur.size() == 0) {
            trubodur.add(new pineapple(width + 50, height));
        } else {
            boolean addNewTrub = false;
            Iterator<pineapple> iterator = trubodur.iterator();
            while (iterator.hasNext()) {
                pineapple trubodur = iterator.next();
                int xCoor = trubodur.calX();
                if (xCoor + pineapple.pinewidth < 0) {
                    iterator.remove();
                }
                if (!iterator.hasNext() &&
                        xCoor + pineapple.pinewidth + pineapple.gapsize < width) {
                    addNewTrub = true;
                }
            }
            if (addNewTrub) {
                trubodur.add(new pineapple(width, height));
            }
        }
    }
    // Рисует Ананасы
    void drawpines(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        Iterator<pineapple> iterator = trubodur.iterator();
        while (iterator.hasNext()) {
            pineapple trubodur = iterator.next();
            int xpinecor = trubodur.calX();

            int ycoord = trubodur.yGC + pineapple.gapH / 2;

            graphics.fillRect(xpinecor, ycoord, pineapple.pinewidth, height - ycoord);
            int height2 = trubodur.yGC - pineapple.gapH / 2;
            graphics.fillRect(xpinecor, 0, pineapple.pinewidth, height2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
