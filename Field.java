
package battlehyperskill;

public class Field {
    protected String[][][] elements;
    
    Field(){
        this.elements = new String[11][11][2];
    }
    
    public String[][][] getElements() {
        return this.elements;
    } 
    
    public  Field createField() {
        this.elements[0][0][0] =" ";
        this.elements[0][0][1] =" ";
        this.elements[0][1][0]= "1";
        this.elements[0][1][1]= "1";
        this.elements[0][2][0]= "2";
        this.elements[0][2][1]= "2";
        this.elements[0][3][0]= "3";
        this.elements[0][3][1]= "3";
        this.elements[0][4][0]= "4";
        this.elements[0][4][1]= "4";
        this.elements[0][5][0]= "5";
        this.elements[0][5][1]= "5";
        this.elements[0][6][0]= "6";
        this.elements[0][6][1]= "6";
        this.elements[0][7][0]= "7";
        this.elements[0][7][1]= "7";
        this.elements[0][8][0]= "8";
        this.elements[0][8][1]= "8";
        this.elements[0][9][0]= "9";
        this.elements[0][9][1]= "9";
        this.elements[0][10][0]= "10";
        this.elements[0][10][1]= "10";
        this.elements[0][0][0] =" ";
        this.elements[1][0][0]= "A";
        this.elements[2][0][0]= "B";
        this.elements[3][0][0]= "C";
        this.elements[4][0][0]= "D";
        this.elements[5][0][0]= "E";
        this.elements[6][0][0]= "F";
        this.elements[7][0][0]= "G";
        this.elements[8][0][0]= "H";
        this.elements[9][0][0]= "I";
        this.elements[10][0][0]= "J";
        this.elements[0][0][0] =" ";
        this.elements[1][0][1]= "A";
        this.elements[2][0][1]= "B";
        this.elements[3][0][1]= "C";
        this.elements[4][0][1]= "D";
        this.elements[5][0][1]= "E";
        this.elements[6][0][1]= "F";
        this.elements[7][0][1]= "G";
        this.elements[8][0][1]= "H";
        this.elements[9][0][1]= "I";
        this.elements[10][0][1]= "J";
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                this.elements[i][j][0] = "~";
                this.elements[i][j][1] = "~";  
            }
        }
        return this;
    }    

    public void printShips() {
        for (String[][] row : this.elements) {
            for (String[] element : row) {
                System.out.print(element[0] + " ");
            }
            System.out.println();
        }System.out.println();
    }
    public void printShots() {
        for (String[][] row : this.elements) {
            for (String element[] : row) {
                System.out.print(element[1] + " ");
            }
            System.out.println();
        } //System.out.println();
    }
    
}
