package battle;
import static battle.Main.promptEnterKey;
import java.io.IOException;
import java.util.Scanner;
public class Main {



    public static void main(String[] args) throws Exception {
        Field field1 = new Field();
        field1.createField(); 
        Player firstPlayer = new Player();
        firstPlayer.setName("Player 1");
        firstPlayer.placeShips(field1);        
        
        Field field2 = new Field();
        field2.createField();
        Player secondPlayer = new Player();
        secondPlayer.setName("Player 2");
        secondPlayer.placeShips(field2);
         
        /*String[] coordinates = {"A1","A5","C1", "C4", "E1", "E3", "G1", "G3", "I8", "I9"};
        String[] coordinates2 = {"A6","A10","C7", "C10", "E8", "E10", "G8", "G10", "I9", "I10"};  
        firstPlayer.placeGivenShips(field1, coordinates);
        secondPlayer.placeGivenShips(field2, coordinates2);
*/
        startGame(field1, firstPlayer, field2, secondPlayer);
    }
    
    public static void startGame(Field field1, Player firstPlayer, Field field2, Player secondPlayer) {
        //System.out.println("The game starts!");
        
        Field myField = field1;
        Field enemyField = field2;        
        Player player = firstPlayer;
        Player enemy = secondPlayer;
          
        while (firstPlayer.isAlive() && secondPlayer.isAlive()) {
            myField.printShots();
            System.out.println("---------------------");
            myField.printShips();
            
            player.shot(enemy, myField, enemyField); 
            
            myField = changeField(myField, field1, field2);
            enemyField = changeField(myField, field1, field2);            
            player = changePlayer(player, enemy, firstPlayer, secondPlayer);
        }
    }
    
    
    public static Field changeField(Field field, Field field1, Field field2) {
        if (field == field1) {
            return field2;
        }
        return field1;
    }
    public static Player changePlayer(Player player, Player enemy, Player firstPlayer, Player secondPlayer) {
        if (player == firstPlayer) {
            enemy = firstPlayer;
            return secondPlayer;
        }
        enemy = secondPlayer;
        return firstPlayer;
    }
    public static void promptEnterKey() throws IOException {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    

class Player {
    private String name;
    public Ship aircraftCarrier;
    public Ship battleship;
    public Ship submarine;
    public Ship cruiser;
    public Ship destroyer;
    boolean alive;
    public Ship[] ships;
    
    Player() {
        this.aircraftCarrier = new Ship("Aircraft Carrier",5);
        this.battleship = new Ship("Battleship",4);
        this.submarine = new Ship("Submarine",3);
        this.cruiser = new Ship("Cruiser",3);
        this.destroyer = new Ship("Destroyer",2);
        this.ships = new Ship[5];
        this.ships[0] = aircraftCarrier;
        this.ships[1] = battleship;
        this.ships[2] = submarine;
        this.ships[3] = cruiser;
        this.ships[4] = destroyer;
        
        this.alive = aircraftCarrier.isAlive() || battleship.isAlive() || submarine.isAlive() || cruiser.isAlive() || destroyer.isAlive();
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
       
    public Ship[] getShips() {
        return this.ships;
    }
    
    public boolean isAlive() {
       return this.aircraftCarrier.isAlive()||this.battleship.isAlive()||this.submarine.isAlive()||this.cruiser.isAlive()||this.destroyer.isAlive();   
    }
    public boolean isAlive(Ship ship) {
       return ship.alive;   
    }
 
    
    public void shot(Player enemy, Field myField, Field enemyField) {
        Scanner sc = new Scanner(System.in);
        System.out.println(this.getName() + ", it's your turn:");
        int[] shot = convertToInt(sc.next());
       
        while(shot[0] + shot[1] > 100){
            System.out.println("Error! You entered wrong coordinates! Try again:\n");
            shot = convertToInt(sc.next());
        }
                
        if ("O".equals(enemyField.elements[shot[0]][shot[1]][0])){
            //System.out.println("pogodjen O");
            System.out.println("You hit a ship! ");
            enemyField.elements[shot[0]][shot[1]][0] = "X";
            myField.elements[shot[0]][shot[1]][1] = "X";
            for (Ship ship:enemy.getShips()) {
                if (ship.isShot(shot)){
                    ship.setShots();
                    if (ship.isAlive()) {
                        System.out.println("You hit a ship! ");
                    } else {
                        if (this.isAlive()) {
                            System.out.println("You sank a ship!");
                        } else {
                            System.out.println("You sank the last ship. You won. Congratulations!");
                        }
                    }    
                }
            }            
        } else {
            //System.out.println("nije pogodjen O");
            if ("X".equals(enemyField.elements[shot[0]][shot[1]][0])){
                System.out.println("pogodjen X");
                System.out.println("You hit a ship!");
            } else {
                System.out.println("pogodjen M");
                enemyField.elements[shot[0]][shot[1]][0] = "M";
                myField.elements[shot[0]][shot[1]][1] = "M";
                System.out.println("You missed!");    
            }
        }
        promptEnterKey();
    }
   
    
    public void placeShips(Field field)throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println(name + ", place your ships on the game field");
        System.out.println();
        field.printShips();
        for(Ship tmp: ships) {
            System.out.println("Enter the coordinates of the "+tmp.getName()+ " ("+ tmp.getLength()+" cells):");
                
            while(!tmp.placed){ 
                tmp.frontEnd = convertToInt(sc.next());
                tmp.backEnd = convertToInt(sc.next()); 
                int x;
                if (tmp.frontEnd[0] > tmp.backEnd[0]){
                    x = tmp.frontEnd[0];
                    tmp.frontEnd[0] = tmp.backEnd[0];
                    tmp.backEnd[0] = x;
                }
                if (tmp.frontEnd[1] > tmp.backEnd[1]){
                        x = tmp.frontEnd[1];
                        tmp.frontEnd[1] = tmp.backEnd[1];
                        tmp.backEnd[1] = x;
                }
                if ((tmp.frontEnd[0] + tmp.backEnd[0]+ tmp.frontEnd[1] + tmp.backEnd[1]) > 100){
                    System.out.println("Error! You entered the wrong coordinates! Try again:");
                } else{
                    if (tmp.frontEnd[0] != tmp.backEnd[0]&& tmp.frontEnd[1] != tmp.backEnd[1]) {
                        System.out.println("Error! Wrong ship location! Try again:");
                    } else{
                        if((Math.abs(tmp.backEnd[0] - tmp.frontEnd[0])+ 1 != tmp.getLength()) && (Math.abs(tmp.backEnd[1] - tmp.frontEnd[1])+1) != tmp.getLength()) {
                            System.out.println("Error! Wrong length of the " + tmp.getName() + "! Try again:");
                        } else {                
                            boolean toClose = false;
                            for (int i = tmp.frontEnd[0] - 1; i <= tmp.backEnd[0] + 1; i++) {
                                if(0 < i && i < 11){
                                    for(int j = tmp.frontEnd[1] - 1; j <= tmp.backEnd[1] + 1; j++) {
                                        if(0 < j && j <11 ) {
                                            if ( "O".equals(field.elements[i][j][0])){
                                                toClose = true;
                            }}}}}
                            if (toClose){
                                System.out.println("Error! You placed it too close to another one. Try again:");
                            } else {                
                                if (tmp.frontEnd[0] == tmp.backEnd[0]) {
                                    for (int j = tmp.frontEnd[1]; j <= tmp.backEnd[1]; j++) {
                                        field.elements[tmp.frontEnd[0]][j][0] = "O";
                                        tmp.placed = true;
                                }}
                                else {
                                    for(int i = tmp.frontEnd[0]; i <= tmp.backEnd[0]; i++) {
                                        field.elements[i][tmp.frontEnd[1]][0] = "O";
                                        tmp.placed = true;
                                    }
                                }                
                            }
                        }
                    }
                }
            }  field.printShips();
        }
        promptEnterKey();
    } 
    public static int[] convertToInt(String coord) {
        int i, j;
        switch(coord.charAt(0)) {
            case 'A': i = 1;
                break;
            case 'B': i = 2;
                break;
            case 'C': i = 3;
                break;
            case 'D': i = 4;
                break;
            case 'E': i = 5;
                break;
            case 'F': i = 6;
                break;
            case 'G': i = 7;
                break;
            case 'H': i = 8;
                break;
            case 'I': i = 9;
                break;
            case 'J': i = 10;
                break;
            default: 
                i = 100;
        }
        if(coord.length() == 2) {
            switch(coord.charAt(1)){
                case '1': j = 1;
                break;
                case '2': j = 2;
                break;
                case '3': j = 3;
                break;
                case '4': j = 4;
                break;
                case '5': j = 5;
                break;
                case '6': j = 6;
                break;
                case '7': j = 7;
                break;
                case '8': j = 8;
                break;
                case '9': j = 9;
                break;
                default:
                    j=100;
            }
        } else {
            if (coord.length() == 3 && coord.endsWith("10")) {
                j = 10;
            } else {
                j=100;
            } 
        }
        int[] a = new int[2];
        a[0]=i;
        a[1]=j;
        return a;
    }
    public boolean placeGivenShips(Field field, String[] coordinates)throws Exception {
        Scanner sc = new Scanner(System.in);
        field.printShips();
        int t = 0;
        for(Ship ship: ships) {
            ship.frontEnd = convertToInt(coordinates[t++]);
            ship.backEnd = convertToInt(coordinates[t++]);
            System.out.println("prednji kraj broda" + ship.frontEnd[0] + " "+ship.frontEnd[1]);                    
            System.out.println("zadnji kraj" + ship.backEnd[0] + " "+ship.backEnd[1]);                    
            
            if (ship.frontEnd[0] == ship.backEnd[0]) {
                for (int j = ship.frontEnd[1]; j <= ship.backEnd[1]; j++) {
                    field.elements[ship.frontEnd[0]][j][0] = "O";
                        ship.placed = true;
                    }
                }
            else {
                for(int i = ship.frontEnd[0]; i <= ship.backEnd[0]; i++) {
                    field.elements[i][ship.frontEnd[1]][0] = "O";
                    ship.placed = true;
                }
            }  
//NE TREBA DA STAMPA BRODOVE DOK IH POSTAVLJA        
//field.printShips();
        }
        promptEnterKey();
        return true;
    } 

    public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    



}


class Field {
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



class Ship {
    private final String name;
    private final int length;
    boolean alive;
    public boolean placed; 
    public int [] frontEnd;
    public int [] backEnd;
    private int shots; 
    
    Ship(String name,int length) {
        this.length = length;
        this.name = name;
        this.shots = 0;
        this.alive = true;
        this.placed = false;
    }
    
    public String getName() {
        return this.name;
    }
    public boolean isAlive() {
        return (this.shots < this.length);
    }
    public boolean isShot(int[] shot){
        return frontEnd[0] <= shot[0] && shot[0] <= backEnd[0] && frontEnd[1] <= shot[1] && shot[1] <= backEnd[1];               
    }
    public int getShots() {
        return this.shots;
    }
    public int[] getFrontEnd() {
        return frontEnd;
    }
    public int[] getBackEnd() {
        return frontEnd;
    }
    public int getLength() {
        return length;
    }     
    public void setShots() {
        this.shots++;
    }
    
    public static int[] convertToInt(String coord) {
        int i, j;
        switch(coord.charAt(0)) {
            case 'A': i = 1;
                break;
            case 'B': i = 2;
                break;
            case 'C': i = 3;
                break;
            case 'D': i = 4;
                break;
            case 'E': i = 5;
                break;
            case 'F': i = 6;
                break;
            case 'G': i = 7;
                break;
            case 'H': i = 8;
                break;
            case 'I': i = 9;
                break;
            case 'J': i = 10;
                break;
            default: 
                i = 100;//throw new IllegalArgumentException("Error! You entered the wrong coordinates! Try again:");
        }
        if(coord.length() == 2) {
            switch(coord.charAt(1)){//coord.length()-1)) {
                case '1': j = 1;
                break;
                case '2': j = 2;
                break;
                case '3': j = 3;
                break;
                case '4': j = 4;
                break;
                case '5': j = 5;
                break;
                case '6': j = 6;
                break;
                case '7': j = 7;
                break;
                case '8': j = 8;
                break;
                case '9': j = 9;
                break;
                default:
                    j=100;
            }
        } else {
            if (coord.length() == 3 && coord.endsWith("10")) {
                j = 10;
            } else {
                j=100;
            } 
        }
        int[] a = new int[2];
        a[0]=i;
        a[1]=j;
        return a;
    }

}


