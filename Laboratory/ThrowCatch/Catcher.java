package ThrowCatch;

public class Catcher implements ThrowListener{
    @Override
    public void Catch() {
        System.out.println("I caught something!!");
    }

    @Override
	public void Drop() {
        System.out.println("I dropped the ball.");		
	}
}
