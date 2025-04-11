package JSON;

import com.google.gson.Gson;

public class JSON1 {
    public static void main(String[] args) {
        final String json = "{\"num\":46,\"nombre\":\"Miguel\",\"sueldo\":1200.00}";
        final Gson gson = new Gson();

        final Empleado empleado = gson.fromJson(json, Empleado.class);
        System.out.println(empleado);

        Empleado empleado1 = new Empleado(2, "Pedro", 1600.00);
        String rpjson = gson.toJson(empleado1);
        System.out.println(rpjson);
    }
}

class Empleado {
    int num;
    String nombre;
    double sueldo;

    public Empleado(int num, String nombre, double sueldo) {
        this.num = num;
        this.nombre = nombre;
        this.sueldo = sueldo;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    @Override
    public String toString() {
        return this.num + " - " + this.nombre + " - " + this.sueldo;
    }
}