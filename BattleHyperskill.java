
package battlehyperskill;

import java.io.IOException;
import java.util.Scanner;

public class BattleHyperskill {

    public static void main(String[] args) throws Exception {
        Field field1 = new Field();
        field1.createField(); 
        Player firstPlayer = new Player();
        firstPlayer.setName("Player 1");
        //firstPlayer.placeShips(field1);        
        
        Field field2 = new Field();
        field2.createField();
        Player secondPlayer = new Player();
        secondPlayer.setName("Player 2");
        //secondPlayer.placeShips(field2);
         
        //String[] coordinates = {"A1","A5","C1", "C4", "E1", "E3", "G1", "G3", "I8", "I9"};
        //String[] coordinates2 = {"A6","A10","C7", "C10", "E8", "E10", "G8", "G10", "I9", "I10"};  
        String[] coordinates = {"F3","F7","A1", "D1", "B9", "D9", "J8", "J10", "I2", "J2"};
        String[] coordinates2 = {"H2","H6","F3", "F6", "D4", "D6", "F8", "H8", "C8", "D8"};  
    
        
        firstPlayer.placeGivenShips(field1, coordinates);
        secondPlayer.placeGivenShips(field2, coordinates2);

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
            player = changePlayer(player, firstPlayer, secondPlayer);
            enemy = changePlayer(enemy, firstPlayer, secondPlayer);
        
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
    public static Player changePlayer(Player player, Player firstPlayer, Player secondPlayer) {
        if (player == firstPlayer) {
            return secondPlayer;
        }
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
    
