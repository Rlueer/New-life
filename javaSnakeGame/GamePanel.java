//import java.awt.*;  // tüm kübütü alıyor 
//import java.awt.event.*; 
//import javax.swing.*;
//import java.util.Random;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics; // parça parça 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;// bissürü timer var dikkatli seç

public class GamePanel extends JPanel implements ActionListener{
     
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE=25;
    static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY=75;
    final int x[]=new int[GAME_UNITS/25];
    final int y[]=new int[GAME_UNITS/25];
    int bodyParts=6;
    int applesEaten;
    int appleX,appleY;
    char direction='R';
    boolean running = false;
    Timer timer;
    Random random;


    GamePanel(){
        random= new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.orange);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running=true;
        timer=new Timer(DELAY, this);
        timer.start();

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running){

            /*for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }*/
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            
            g.setColor(new Color(random.nextInt(255),0,0));
                    
            for(int i=0;i<bodyParts;i++){
                if(i==0){
                    //g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else{
                    //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten, (SCREEN_WIDTH-metrics.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
        
        }
        else{
            gameOver(g);
        }
    }
    public void newApple(){
        appleX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
        for(int i=bodyParts;i>0;i--){   //burada bas haric kisimlar bir önündekinin konumuna geciyor
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch(direction){  //burada basi hareket ettiriyoruz
            case 'U':
                y[0]=y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;
        }
    }
    public void checkApple(){
        if(x[0]==appleX && y[0]==appleY){
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }
    public void checkCollisions(){
        // kafanın vücüda çarpma durumu
        for(int i=bodyParts;i>0;i--){
            if((x[0] == x[i] )&& (y[0]==y[i])){
                running=false;
            }
        }
        // kafanın kenarlara çarpma durumu
        if((x[0]<0) || (y[0]<0) || (x[0]>SCREEN_WIDTH) || (y[0]>SCREEN_HEIGHT)){
            running=false;
        }
        if(running==false){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        //Game Over text 
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 50));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH-metrics.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
        g.setColor(Color.blue);
        g.drawString("Game Over", (SCREEN_WIDTH-metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
        
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();  
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT,KeyEvent.VK_A:
                    if(direction!='R'){ //180 derece dönmesin diye
                        direction='L';
                    }
                break;
                case KeyEvent.VK_RIGHT,KeyEvent.VK_D:
                    if(direction!='L'){ //180 derece dönmesin diye
                        direction='R';
                    }
                break;
                case KeyEvent.VK_UP,KeyEvent.VK_W:
                    if(direction!='D'){ //180 derece dönmesin diye
                        direction='U';
                    }
                break;
                case KeyEvent.VK_DOWN,KeyEvent.VK_S:
                    if(direction!='U'){ //180 derece dönmesin diye
                        direction='D';  
                    }
                break;
                
            }
        }
    }

}