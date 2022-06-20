package battleship;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Player player1 = new Player( "Player 1");
        Player player2 = new Player( "Player 2");

        placedShips(player1);
        showPromptToAnotherPlayer();
        placedShips(player2);
        showPromptToAnotherPlayer();

        int countEndGame = 0;
        while (player1.checkEndGame() || player2.checkEndGame()) {
            if (countEndGame % 2 != 0) {
                player1.showSecretBoard();
                System.out.println("---------------------\n");
                player2.showBoard();
                System.out.println("\n" + player2.playerName + ", it's your turn:\n");
                if (startBattle(player1)) {
                    break;
                }
                showPromptToAnotherPlayer();
            } else {
                player2.showSecretBoard();
                System.out.println("---------------------");
                player1.showBoard();
                System.out.println("\n" + player1.playerName + ", it's your turn:\n");
                if (startBattle(player2)) {
                    break;
                }
                showPromptToAnotherPlayer();
            }
            countEndGame++;
        }

    }


    public static void placedShips(Player player) {

        System.out.println(player.playerName + ", place your ships to the game field");
        player.showBoard();
        System.out.println();

        System.out.print("Enter the coordinates of the Aircraft Carrier (5 cells):\n");
        player.createShip(sc, 5);
        player.showBoard();

        System.out.print("Enter the coordinates of the Battleship (4 cells):\n");
        player.createShip(sc, 4);
        player.showBoard();

        System.out.print("Enter the coordinates of the Submarine (3 cells):\n");
        player.createShip(sc, 3);
        player.showBoard();

        System.out.print("Enter the coordinates of the Cruiser (3 cells):\n");
        player.createShip(sc, 3);
        player.showBoard();

        System.out.print("Enter the coordinates of the Destroyer (2 cells):\n");
        player.createShip(sc, 2);
        player.showBoard();
    }

    static void showPromptToAnotherPlayer() {
        System.out.println("\nPress Enter and pass the move to another player");
        try {
            while ((char)System.in.read()!= '\n') {
                continue;
            }
        } catch (IOException e) {
            System.out.println("Пропустите ход");
        }
    }

    public static boolean startBattle(Player player) {

        String aim = sc.next();
        //конвертирует букву
        int x1 = player.converterNumber(aim);
        int z1;
        //конвертирует цифру, если = 10 то присваивает 9 т.к. работать с массивами

        if (x1 == 99) {
            System.out.println("Try again:\n");
        } else { // Проверка выходят ли координаты за пределы поля
            if (aim.length() > 2) {
                int i = Character.getNumericValue(aim.charAt(1)) + Character.getNumericValue(aim.charAt(2));
                if (i > 1) {
                    System.out.println("Error! You entered the wrong coordinates!\nTry again:\n");
                } else {
                    // если после буквы двузначное число
                    z1 = i == 1 ? 9 : Character.getNumericValue(aim.charAt(1)) - 1;
                    if (!"O".equals(player.gameBoard[x1][z1]) && !"X".equals(player.gameBoard[x1][z1])) {
                        player.secretBoard[x1][z1] = "M";
                        player.gameBoard[x1][z1] = "M";
                        System.out.println("\nYou missed!\n");
                    } else {
                        player.secretBoard[x1][z1] = "X";
                        player.gameBoard[x1][z1] = "X";
                        System.out.println("You hit a ship!");
                        if (!player.checkEndGame()) {
                            System.out.println("You sank the last ship. You won. Congratulations!");
                            return true;
                        } else {
                            if (!player.makeShotCoordinates(x1, z1)) {
                                System.out.println("You sank a ship! Specify a new target:");
                            }
                        }
                    }
                }
            } else {
                // если после буквы не двузначное число
                z1 = Character.getNumericValue(aim.charAt(1)) - 1;
                if (!"O".equals(player.gameBoard[x1][z1]) && !"X".equals(player.gameBoard[x1][z1])) {
                    player.secretBoard[x1][z1] = "M";
                    player.gameBoard[x1][z1] = "M";
                    System.out.println("\nYou missed!\n");
                } else {
                    player.secretBoard[x1][z1] = "X";
                    player.gameBoard[x1][z1] = "X";
                    System.out.println("You hit a ship!");
                    if (player.checkEndGame()) {
                        if (!player.makeShotCoordinates(x1, z1)) {
                            System.out.println("You sank a ship! Specify a new target:");
                        }
                    } else {
                        System.out.println("You sank the last ship. You won. Congratulations!");
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

