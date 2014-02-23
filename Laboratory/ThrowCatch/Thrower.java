package ThrowCatch;

import java.util.*;

public class Thrower {
    public List<ThrowListener> listeners = new ArrayList<ThrowListener>();
    
    public void addListener(ThrowListener toAdd){
        listeners.add(toAdd);
    }

    public void Throw() {
        System.out.println("Something thrown..");
        
        Random random = new Random();
        for (ThrowListener ball : listeners){
        	if((random.nextInt((1 - 0) + 1) + 0) == 0){
        		ball.Drop();
        	}
        	else{
        		ball.Catch();
        	}
        }
    }
}
