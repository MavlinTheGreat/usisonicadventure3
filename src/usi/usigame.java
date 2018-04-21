package usi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;

public class usigame extends JFrame implements KeyListener {
    static GamePanel component = new GamePanel();
    public static void main(String[] args) {
        usigame game = new usigame();
        game.setResizable(true);
        game.setLocation(0, 0);
//        game.setAlwaysOnTop(true);
        game.setTitle("Усы: великое приключение");
        game.setExtendedState(game.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        game.getSize(Toolkit.getDefaultToolkit().getScreenSize());
        game.setDefaultCloseOperation(EXIT_ON_CLOSE);
        game.setUndecorated(true);

        game.addKeyListener(game);

        usigame.component = new GamePanel();
        game.add(game.component);
        game.setVisible(true);

    }

    private void getSize(int i, int i1) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_DELETE){
            System.out.println("Зря вы вышли... Я will back");
            System.exit(0);
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.out.println("Пользователь хочет уйти. Не выпускать.");
        }
        if (component.gamemode==0) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
                System.out.println("Игра на выживание началась");
                component.StartTime = System.currentTimeMillis();
                component.gamemode = 1;
            }
        } else if (component.gamemode == 0 || component.gamemode == 2) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
                component.reset();
                System.out.println("Игра на выживание началась");
                component.StartTime = System.currentTimeMillis();
                component.gamemode = 1;
            }
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
            component.speedstart = 30;
            component.yBirdCo = component.lastbirdcoor;
            component.StartTime = System.currentTimeMillis();
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_P) {
            System.out.println("Остановите, всем надо выйти. Хватит угонять поезд!");
            component.gamemode = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
