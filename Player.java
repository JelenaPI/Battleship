package battlehyperskill;
import static battlehyperskill.Ship.convertToInt;
import java.io.IOException;
import java.util.Scanner;

public class Player {
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
            System.out.println("pogodjen O");
            enemyField.elements[shot[0]][shot[1]][0] = "X";
            myField.elements[shot[0]][shot[1]][1] = "X";
            System.out.println("IGRAC "+this.getName());
            for (Ship ship:enemy.getShips()) {
                
                System.out.println("brod  "+ship.getName());
                System.out.println(" ziv je brod "+ ship.isAlive());
                System.out.println(" brod je POGODJEN "+ ship.isShot(shot));
                System.out.println("FRONT END "+ ship.frontEnd[0]+" "+ship.frontEnd[1] );
                System.out.println("MY BOATS:FRONT END "+ this.aircraftCarrier.frontEnd[0]+" "+this.aircraftCarrier.frontEnd[1] );
                System.out.println("back END "+ ship.backEnd[0]+" "+ship.backEnd[1] );
                System.out.println("shot "+ shot[0]+" "+shot[1] );
                   
                if (ship.isShot(shot)){
                    ship.setShots();
                    System.out.println(" brod nije potopljen "+ ship.isAlive());
            
                    if (ship.isAlive()) {
                        System.out.println("You hit a ship! ");
                    } else {
                        if (isAlive()) {
                            System.out.println("You sank a ship!");
                        } else {
                            System.out.println("You sank the last ship. You won. Congratulations!");
                        }
                    }    
                }
            }            
        } else {
            System.out.println("nije pogodjen O");
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