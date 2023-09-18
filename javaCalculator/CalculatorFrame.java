import javax.swing.JFrame;

public class CalculatorFrame extends JFrame{

    CalculatorFrame(){

        CalculatorPanel panel= new CalculatorPanel();
        
        this.add(panel);
        this.setTitle("Calculator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
