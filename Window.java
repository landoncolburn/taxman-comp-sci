import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Window extends JFrame {

  JPanel controls = new JPanel();
  public static ArrayList<Button> buttons = new ArrayList<Button>();

  public Window(int n){
    Taxman.init(n);
    setPreferredSize(new Dimension(1000, 600));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    for(Number nu : Taxman.numbers){
      buttons.add(new Button(nu));
      controls.add(buttons.get(buttons.size()-1));
    }
    add(controls);
    pack();
  }

  public static void change(){
    buttons.forEach((e)->{
      if(e.getNumber().getState() != State.ACTIVE){
        e.setEnabled(false);
      }
    });
  }

  public static void checkWin(){
    boolean activeRemain = false;
    for(Button b : buttons){
      if(b.getNumber().getState() == State.ACTIVE){
        activeRemain = true;
      }
    }
    if(!activeRemain){
      for(Number n : Taxman.numbers){
        if(n.getState() == State.UNREACHABLE){
          Taxman.tax(n);
        }
      }
      change();
      Taxman.printFinal();
    }
  }

  public static void main(String[] args) {
    new Window(25);
  }

}
