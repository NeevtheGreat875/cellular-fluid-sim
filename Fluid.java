public class Fluid {

    int SCREENSIZE = 5;
    Attribute densities;

    Fluid(){
        densities = new Attribute("density", SCREENSIZE, SCREENSIZE);
    }

    void setup(){
        densities.set(10, 2, 2);
    }

    void step(){
        setborderattr();
        diffuse(0.5);
        display();
    }

    void diffuse(double k){
        double[][] A = new double[SCREENSIZE*SCREENSIZE][SCREENSIZE*SCREENSIZE];
        double[] B = new double[SCREENSIZE*SCREENSIZE];

        for(int i = 0; i < SCREENSIZE; i++){
            for(int j = 0; j < SCREENSIZE; j++){
                if(j==0 || i == 0 || i == SCREENSIZE-1 || j == SCREENSIZE-1){
                    continue;
                }

                A[densities.map(i, j)][densities.map(i, j+1)] = -0.25*k;
                A[densities.map(i, j)][densities.map(i, j-1)] = -0.25*k;
                A[densities.map(i, j)][densities.map(i+1, j)] = -0.25*k;
                A[densities.map(i, j)][densities.map(i-1, j)] = -0.25*k;
                A[densities.map(i, j)][densities.map(i, j)] = 1+k;
            }

            B = densities.values;

            GaussSidel solver = new GaussSidel(5);
            double[] X = solver.solve(A, B);

            densities.values = X;
        }
    }

    void setborderattr(){
        for(int i = 0; i < SCREENSIZE; i++){
            densities.set(0, 0, i);
            densities.set(0, i, 0);
            densities.set(0, SCREENSIZE-1, i);
            densities.set(0, i, SCREENSIZE-1);
        }
    }

    void display(){
        System.out.print("\033[H\033[2J");
        System.out.flush();

        densities.show();
        // for(int i = 0; i < SCREENSIZE; i++){
        //     for(int j = 0; j < SCREENSIZE; j++){
        //         if(densities.get(i, j)>3){
        //             System.out.print("# ");
        //         }
        //         else{
        //             System.out.print("  ");
        //         }
        //     }
        //     System.out.println();
        // }
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
