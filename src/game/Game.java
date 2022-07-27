package game;

import java.awt.Color;
import javax.swing.JFrame;

public class Game {

    public static void main(String[] args) {
        Gameplay gameplay=new Gameplay();
        JFrame obj=new JFrame("SNAKE");
        obj.setBounds(100, 50, 800, 600);
        obj.setBackground(Color.DARK_GRAY);
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gameplay);
    }    
}
