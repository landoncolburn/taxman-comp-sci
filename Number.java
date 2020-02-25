public class Number {

  private int number;
  private State state;
  private boolean prime = true;

  public Number(int n){
    number = n;
    state = State.ACTIVE;
    for(int i = 2; i <= number/2; ++i){
      if(number % i == 0){
        prime = false;
        break;
      }
    }
    if(number == 1){
      prime = false;
    }
  }

  public State getState(){
    return state;
  }

  public int getNumber(){
    return number;
  }

  public void setState(State s){
    this.state = s;
  }

  public boolean isPrime(){
    return prime;
  }

}
