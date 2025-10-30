public class Fluid {

    int SCREENSIZE = 60;
    Attribute DENSITIES;
    Attribute VX;
    Attribute VY;
    char[] SHADER = {' ', '-', '.', ':', '=', '+', '*', '#', '%', '@'};
    double MAXDISPLAYDENSITY = 11;

    Fluid(){
        DENSITIES = new Attribute("density", SCREENSIZE, SCREENSIZE);
        VX = new Attribute("Velocity X", SCREENSIZE, SCREENSIZE);
        VY = new Attribute("Velocity Y", SCREENSIZE, SCREENSIZE);
    }

    void setup(){
        drawattr(15, 2, 20, DENSITIES, 10);
        drawattr(15, 2, 20, VY, 4);
        // drawattr(20, 2, 20, VX, 5);
        // setborderattr();
    }

    void step(){
        drawattr(18, 1, 25, VY, 2);
        drawattr(18, 1, 25, VX, 2);

        diffuse(VX, 10, 0.05);
        diffuse(VY, 10, 0.05);
        project(VX, VY, 20);

        advect(VX, 0.1);
        advect(VY, 0.1);

        project(VX, VY, 20);

        advect(DENSITIES, 0.1);
        
        display();

        try {
            Thread.sleep(32);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    void project(Attribute attrx, Attribute attry, int iter){

        setborderattr();

        Attribute divergence = new Attribute("Divergence", SCREENSIZE, SCREENSIZE);

        for(int i = 1; i < SCREENSIZE-1; i++){
            for(int j = 1; j < SCREENSIZE-1; j++){
                double dy = attry.get(i, j+1) - attry.get(i, j-1);
                double dx = attrx.get(i+1, j) - attrx.get(i-1, j);
                double d = (dx+dy)/2.0;
                divergence.set(d, i, j);
            }
        }

        //p(x,y)=[sum(surrounding p(x,y)) - d(x,y)]/4

        Attribute pressures = new Attribute("Pressures", SCREENSIZE, SCREENSIZE);

        for (int i = 0; i < iter; i++) {
            for(int x = 1; x < SCREENSIZE-1; x++){
                for(int y = 1; y < SCREENSIZE-1; y++){
                    double s = pressures.get(x+1, y) + pressures.get(x-1, y) + pressures.get(x, y+1) + pressures.get(x, y-1);
                    double p = (s-divergence.get(x, y))/4.0;
                    pressures.set(p, x, y);
                }
            }
        }

        for(int x = 1; x < SCREENSIZE-1; x++){
            for(int y = 1; y < SCREENSIZE-1; y++){
                double gx = (pressures.get(x+1, y) - pressures.get(x-1, y))/2;
                double gy = (pressures.get(x, y+1) - pressures.get(x, y-1))/2;
                attrx.values[attrx.map(x, y)] -= gx;
                attry.values[attrx.map(x, y)] -= gy;
            }
        }

    }

    void advect(Attribute _attr, double _dt){
        Attribute attr = new Attribute("", SCREENSIZE, SCREENSIZE);
        attr.values = _attr.values.clone();

        for(int px = 1; px < SCREENSIZE-2; px++){
            for(int py = 1; py < SCREENSIZE-2; py++){

                double _px = px - VX.get(px, py)*_dt;
                double _py = py - VY.get(px, py)*_dt;
                

                int px_f = (int)Math.floor(_px);
                int py_f = (int)Math.floor(_py);
                double px_fract = _px-px_f;
                double py_fract = _py-py_f;

                double z1 = lerp(attr.get(px_f, py_f), attr.get(px_f+1, py_f), px_fract);
                double z2 = lerp(attr.get(px_f, py_f+1), attr.get(px_f+1, py_f+1), px_fract);

                double d_n = lerp(z1, z2, py_fract);

                attr.set(d_n, px, py);
            }
        }

        _attr.values = attr.values;
    }

    double lerp(double _a, double _b, double _k){
        return _a + _k*(_b-_a);
    }

    void diffuse(Attribute attr, int iter, double k){
        setborderattr();
        
        double[] X = attr.values.clone();

        for(int n = 0; n < iter; n++){
            for(int i = 1; i < SCREENSIZE-2; i++){
                for(int j = 1; j < SCREENSIZE-2; j++){
                    double sum_n = X[attr.map(i+1, j)] + X[attr.map(i, j+1)] + X[attr.map(i-1, j)] + X[attr.map(i, j-1)];
                    double d_c = attr.values[attr.map(i, j)];
                    X[attr.map(i, j)] = ( d_c + (0.25*k*sum_n) ) / (1.0 + k);
                }
            }
        }

        attr.values = X;
        setborderattr();
    }

    void setborderattr(){
        for(int i = 0; i < SCREENSIZE; i++){
            VX.set(0, i, 0);
            VX.set(0, i, SCREENSIZE-1);

            VY.set(0, 0, i);
            VY.set(0, SCREENSIZE-1, i);

            DENSITIES.set(0, 0, i);
            DENSITIES.set(0, SCREENSIZE-1, i);
            DENSITIES.set(0, i, 0);
            DENSITIES.set(0, i, SCREENSIZE-1);
        }
    }

    void display(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        // DENSITIES.show();

        // DENSITIES.show();
        for(int i = 0; i < SCREENSIZE; i++){
            for(int j = 0; j < SCREENSIZE; j++){
                System.out.print(SHADER[(int)(DENSITIES.get(i, j)*(SHADER.length/MAXDISPLAYDENSITY))] + " ");
            }
            System.out.println();
        }
    }

    void drawattr(int x, int y, int size, Attribute attr, double v){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                attr.set(v, x+i, y+j);
            }
        }
    }

    public static void main(String[] args) {
        Fluid sim = new Fluid();
        sim.setup();
        while(true){
            sim.step();
        }
    }
}
