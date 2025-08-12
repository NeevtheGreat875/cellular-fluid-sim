import java.lang.Math;
import java.util.function.Function;

public class Matrix {

    int rows;
    int cols;
    double[][] matrix;

    Matrix(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.matrix = new double[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.matrix[i][j] = 0;
            }
        }
    }

    Matrix(double[][] in){
        this.rows = in.length;
        this.cols = in[0].length;
        this.matrix = in;
    }

    void show(){
        System.out.println();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(this.matrix[i][j]+"  ");
            }
            System.out.println();
        }
    }

    void randomize(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.matrix[i][j] = Math.random()-0.5;
            }
        }
    }

    static Matrix dot(Matrix A, Matrix B){
        if(A.cols != B.rows){
            System.out.println("Unoperable Matrices For Dot Product");
            return null;
        }

        Matrix out = new Matrix(A.rows, B.cols);

        for (int curr_row = 0; curr_row < out.rows; curr_row++) {
            for (int curr_col = 0; curr_col < out.cols; curr_col++) {
                double sum = 0;

                for(int i = 0; i < A.cols; i++){
                    sum += A.matrix[curr_row][i] * B.matrix[i][curr_col];
                }

                out.matrix[curr_row][curr_col] = sum;
            }
        }
        
        return out;
    }

    void multiply(Matrix B){
        if(this.cols != B.cols && this.rows != B.rows){
            System.out.println("Unoperable Matrices For Hadamard Product");
            return;
        }


        for (int curr_row = 0; curr_row < rows; curr_row++) {
            for (int curr_col = 0; curr_col < cols; curr_col++) {
                this.matrix[curr_row][curr_col] *= B.matrix[curr_row][curr_col];
            }
        }
    }
    void multiply(double B){
        for (int curr_row = 0; curr_row < rows; curr_row++) {
            for (int curr_col = 0; curr_col < cols; curr_col++) {
                this.matrix[curr_row][curr_col] *= B;
            }
        }
    }

    Matrix T(){
        Matrix out = new Matrix(this.cols, this.rows);

        for (int i = 0; i < out.rows; i++) {
            for (int j = 0; j < out.cols; j++) {
                out.matrix[i][j] = this.matrix[j][i];
            }
        }

        return out;
    }

    void add(Matrix A){
        if(A.rows != this.rows || this.cols != A.cols){
            System.out.println("Not Compatible For Addition");
            return;
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.matrix[i][j] = A.matrix[i][j] + this.matrix[i][j];
            }
        }
    }

    void substract(Matrix A){
        if(A.rows != this.rows || this.cols != A.cols){
            System.out.println("Not Compatible For Substraction");
            return;
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.matrix[i][j] = this.matrix[i][j] - A.matrix[i][j];
            }
        }
    }

    static Matrix map(Matrix A,Function<Double,Double> func){
        Matrix B = new Matrix(A.rows, A.cols);
        for(int r = 0; r < A.matrix.length; r++){
            for(int e = 0; e < A.matrix[r].length; e++){
                B.matrix[r][e] = func.apply(A.matrix[r][e]);
            }
        }
        return B;
    }

    static Matrix substract(Matrix A, Matrix B){

        if(B.rows != A.rows || B.cols != A.cols){
            System.out.println("Not Compatible For Substraction");
            return null;
        }
        
        Matrix out = new Matrix(A.rows, A.cols);
        
        for (int i = 0; i < out.rows; i++) {
            for (int j = 0; j < out.cols; j++) {
                out.matrix[i][j] = A.matrix[i][j] - B.matrix[i][j];
            }
        }

        return out;
    }

    public void shape(){
        System.out.println("("+this.rows+","+this.cols+")");
    }

    public static Matrix softmax(Matrix in){
        Matrix out = new Matrix(in.rows, in.cols); 
        double sum = 0;
        
        for(int row = 0; row < in.rows; row++ ) {
            for(int col = 0; col < in.cols; col++ ) {
                sum += Math.exp(in.matrix[row][col]);
            }
        }

        for(int row = 0; row < in.rows; row++ ) {
            for(int col = 0; col < in.cols; col++ ) {
                out.matrix[row][col] = Math.exp(in.matrix[row][col]) / sum;
            }
        }

        return out;
    }

    public static Matrix dsoftmax(Matrix in){
        Matrix out = new Matrix(in.rows, in.rows); 
        
        for(int row = 0; row < in.rows; row++ ) {
            for(int col = 0; col < in.rows; col++ ) {
                out.matrix[row][col] = in.matrix[row][0] * (((row==col)?1:0) - in.matrix[row][0]);
            }
        }

        return out;
    }
    public double mean(){
        double out = 0;
        int count = 0;
        for(int row = 0; row < this.rows; row++ ) {
            for(int col = 0; col < this.cols; col++ ) {
                out += this.matrix[row][col];
                count++;
            }
        }
        out /= count;
        return out;
    }
    public double max(){
        double m = 0;
        for(int row = 0; row < this.rows; row++ ) {
            for(int col = 0; col < this.cols; col++ ) {
                if(m<this.matrix[row][col]){
                    m = this.matrix[row][col];
                }
            }
        }
        return m;
    }
    public int max(String mode){
        int m = 0;
        for(int row = 0; row < this.rows; row++ ) {
            for(int col = 0; col < this.cols; col++ ) {
                if(this.matrix[m][col]<this.matrix[row][col]){
                    m = row;
                }
            }
        }
        return m;
    }
    void add(double a){
        for(int row = 0; row < this.rows; row++ ) {
            for(int col = 0; col < this.cols; col++ ) {
                this.matrix[row][col] += a;
            }
        }
    }
}
