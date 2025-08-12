import org.w3c.dom.Attr;

public class Fluid {

    int SCREENSIZE = 20;
    Attribute densities;

    Fluid(){
        densities = new Attribute("density", SCREENSIZE, SCREENSIZE);
    }

    void setup(){
        densities.set(10, 5, 5);
    }

    void step(){
        display();
    }

    void display(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for(int i = 0; i < SCREENSIZE; i++){
            for(int j = 0; j < SCREENSIZE; j++){
                if(densities.get(i, j)>3){
                    System.out.print("# ");
                }
                else{
                    System.out.print("  ");
                }
            }
            System.err.println();
        }
    }
    
    /*
    class attribute
    - data
    - 1D array format
    - map 2D to 1D index
    - access attributes
    - set attribute

    class simulation
    - display()
    - parameters
    - step()

    MAP 2D denisity feild to 1D array X
    X = next densities [Nx1]

    A will be a array of -1/4s and 0s multiplied by K
    A = -K(surrounding densities) [NxN]

    current densities
    B = current densities [Nx1]

    solve for X using A and B
    */


    public static void main(String[] args) {
        Fluid sim = new Fluid();
        sim.setup();
        while(true){
            sim.step();
        }
    }
}
