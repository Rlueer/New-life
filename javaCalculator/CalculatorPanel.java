import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;


public class CalculatorPanel extends JPanel implements ActionListener{
     
    static final int SCREEN_WIDTH = 400;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE=25;
    boolean working = false;
    Timer timer ;
    int answer=-1;

    String  a[]={"7","4","1","DEL","8","5","2","0","9","6",
        "3",",","C","-","+","*","/","="};
    String[] equation=new String[100];
    int counter=0;

    CalculatorPanel(){

        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.setLayout(null);

        addButtons();
        startCalculator();
        
    }
    public void addButtons(){

        JButton[] buttons = new JButton[18];
        int counter=0;
        for(int i = 0; i < 3; i++) {
            for(int j=0;j<4;j++){
                buttons[i] = new JButton(a[counter++]);
                buttons[i].setFont(new Font("Ink Free", Font.BOLD, 30));
                buttons[i].setBounds(100*i, 200+100*j, 100, 100);
                buttons[i].setBackground(Color.lightGray);
                buttons[i].setActionCommand(a[counter-1]);
                buttons[i].addActionListener(this);

                this.add(buttons[i]);
            }
        }
        for(int i=0;i<6;i++){
            buttons[i] = new JButton(a[12+i]);
            buttons[i].setFont(new Font("Ink Free", Font.BOLD, 30));
            buttons[i].setBounds(300, 200+i*67, 100, 67);
            buttons[i].setBackground(Color.lightGray);
            buttons[i].setActionCommand(a[12+i]);
            buttons[i].addActionListener(this);
            this.add(buttons[i]);
        }
    }
    public void startCalculator(){
        working=true;
        timer=new Timer(200, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(working){
            for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }
            g.setColor(Color.blue);
            g.drawRoundRect(0,25,450,125,25,25);
            g.setFont(new Font("Ink Free", Font.BOLD, 50));
            for(int i=0;i<counter;i++){
                g.drawString(equation[i],50+10*4*i,100);
            }
            
            if(answer!=-1){
                g.drawString(Integer.toString(answer),50+10*4*counter,100);
            }
        }
    }
    public int create_equation(String[] X,int size){
        String first_part=new String();
        String function=new String();
        int f=-1,s=-1;
        for(int i=0;i<size;i++){
            if(X[i]=="-" ||X[i]=="+" ||X[i]=="/" ||X[i]=="*"||X[i]==","){
                function=X[i];
                if(f==-1){
                    f= Integer.valueOf(first_part);
                    first_part="";
                }
            }
            else{
                first_part=first_part+X[i];
            }

            if(f!=-1 && s==-1 && i==size-1){
                s=Integer.valueOf(first_part);
                if(function=="-")  f=f-s;
                else if(function=="+")f=f+s;
                else if(function=="/")f=f/s; 
                else if(function=="*")f=f*s;
                  
                s=-1;
            }
        }
        
        return f;
    }
    @Override
    public void actionPerformed(ActionEvent e){

        for(int i=0;i<18;i++){
            if(e.getActionCommand()=="=" ||e.getActionCommand()=="C"){
                answer=create_equation(equation,counter); 
                if(e.getActionCommand()=="C"){ answer=-1;}
                counter=0;
                return;
            }
            else if(e.getActionCommand()==a[i]){
                answer=-1;
                equation[counter]=a[i];
                counter++;   
            }
            else if(e.getActionCommand()=="DEL"){
                counter--;
                return;
            }
            repaint(); 
        }
    }
}