import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.*;

public class Button extends JButton {

  Number num;
  State state;

  public Button(Number n){
    super("" + n.getNumber());
    num = n;
    this.setPreferredSize(new Dimension(50, 50));
    addActionListener(new ActionListener(){

      @Override
      public void actionPerformed(ActionEvent e) {
        Taxman.playerTurn(num);
        System.out.println(num.getState());
        Window.change();
        Window.checkWin();
      }
    });
  }

  public Number getNumber(){
    return num;
  }

}
