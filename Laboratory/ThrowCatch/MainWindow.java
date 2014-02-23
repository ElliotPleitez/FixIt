package ThrowCatch;

public class MainWindow {
    public static void main(String[] args) {
        Thrower thrower = new Thrower();
        Catcher catcher = new Catcher();
        
        thrower.addListener(catcher);
        thrower.Throw();
    }
}
