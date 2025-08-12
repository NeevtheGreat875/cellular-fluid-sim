public class GaussSidel
{
    int max_iterations;
    
    GaussSidel(int _max_iter){
        max_iterations = _max_iter;
    }

    
    double[] solve(double[][] A, double[] B){
        int n = A.length;
        double[] X = new double[n];
        int iterations = 0;

        do{
            for(int i = 0; i < n; i++){
                double sum = 0;
                for(int j = 0; j < n; j++){
                    if(j == i){
                        continue;
                    }
                    sum += X[j]*A[i][j];
                }
                X[i] = (B[i] - sum)/A[i][i];
            }
            iterations++;
        } while (iterations < max_iterations);

        return X;
    }
    
    void state(){
        for(int i = 0; i < n; i++){
            System.out.println(X[i]);
        }
    }
    
    public static void main(String[] args){
        double[][] A = {
            {10, -9, 1},
            {1, 9, -1},
            {-5, -9, 11},
        };
        double[] B = {6,0,10};
        
        GaussSidel solver = new GaussSidel(10);
        double[] X = solver.solve(A, B);
        
        for (double d : X) {
            System.out.println(d);
        }
    }
}