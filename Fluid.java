public class Fluid {

    int SCREENSIZE = 35;
    Attribute DENSITIES;
    char[] SHADER = {' ', '-', '.', ':', '=', '+', '*', '#', '%', '@'};
    double MAXDISPLAYDENSITY = 9;

    Fluid(){
        DENSITIES = new Attribute("density", SCREENSIZE, SCREENSIZE);
    }

    void setup(){
        DENSITIES.set(10, 2, 2);
    }

    void step(){
        setborderattr();
        diffuse(1);
        display();

        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    void diffuse(double k){
        double[][] A = new double[SCREENSIZE*SCREENSIZE][SCREENSIZE*SCREENSIZE];
        double[] B = new double[SCREENSIZE*SCREENSIZE];

        for(int i = 0; i < SCREENSIZE; i++){
            for(int j = 0; j < SCREENSIZE; j++){
                A[DENSITIES.map(i, j)][DENSITIES.map(i, j)] = 1+k;

                if(j==0 || i == 0 || i == SCREENSIZE-1 || j == SCREENSIZE-1){
                    continue;
                }

                A[DENSITIES.map(i, j)][DENSITIES.map(i, j+1)] = -0.25*k;
                A[DENSITIES.map(i, j)][DENSITIES.map(i, j-1)] = -0.25*k;
                A[DENSITIES.map(i, j)][DENSITIES.map(i+1, j)] = -0.25*k;
                A[DENSITIES.map(i, j)][DENSITIES.map(i-1, j)] = -0.25*k;
                
            }
        }

        

        B = DENSITIES.values;

        GaussSidel solver = new GaussSidel(25);
        double[] X = solver.solve(A, B);

        DENSITIES.values = X;
    }

    void setborderattr(){
        for(int i = 0; i < SCREENSIZE; i++){
            DENSITIES.set(10, 0, i);
            DENSITIES.set(10, i, 0);
            // DENSITIES.set(0, SCREENSIZE-1, i);
            // DENSITIES.set(0, i, SCREENSIZE-1);
        }
    }

    void display(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        for(int i = 0; i < SCREENSIZE; i++){
            for(int j = 0; j < SCREENSIZE; j++){
                System.out.print( SHADER[ (int)(DENSITIES.get(i, j) * ( SHADER.length / MAXDISPLAYDENSITY )) ] + " " );
            }
            System.out.println();
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
