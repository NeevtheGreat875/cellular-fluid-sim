public class Attribute {
    double[] values;
    String name;
    int sizex;
    int sizey;
    int size;

    Attribute(String _name, int _sizex, int _sizey){
        name = _name;
        sizex = _sizex;
        sizey = _sizey;
        size = _sizex*sizey;
        values = new double[size];
    }

    int map(int _x, int _y){
        return sizex*_x + _y;
    }

    void set(double _val, int _x, int _y){
        values[map(_x, _y)] = _val;
    }

    double get(int _x, int _y){
        return values[map(_x, _y)];
    }

    void show(){
        for(int i = 0 ; i < sizex; i++){
            for(int j = 0; j < sizey; j++){
                System.out.print((int)values[map(i, j)] + " ");
            }
            System.out.println();
        }
    }
}
