package battlehyperskill;

public class Ship {
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
