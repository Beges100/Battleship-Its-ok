package battleship;

import java.util.Scanner;

public class Player {
    public String[][] gameBoard;
    protected final int SIZE_BOARD = 10;
    protected String[][] secretBoard;
    protected char[] columnNumber = {' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
    public String playerName;

    public Player(String playerName) {
        this.gameBoard = new String[SIZE_BOARD][SIZE_BOARD];
        this.secretBoard = new String[SIZE_BOARD][SIZE_BOARD];
        this.playerName = playerName;
        tilda();
    }

    public void tilda() {
        for (int i = 0; i < SIZE_BOARD; i++) {
            for (int j = 0; j < SIZE_BOARD; j++) {
                gameBoard[i][j] = "~";
                secretBoard[i][j] = "~";
            }
        }
    }

    public void showBoard() {
        System.out.print(" ");
        for (int i = 1; i < 11; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
        for (int i = 0; i < SIZE_BOARD; i++) {
            System.out.print(columnNumber[i + 1] + " ");
            for (int j = 0; j < SIZE_BOARD; j++) {
                System.out.print(gameBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int converterNumber(String num) {
        int ok = 99;
        for (int i = 0; i <= columnNumber.length - 1; i++) {
            if (columnNumber[i] == num.charAt(0)) {
                ok = i - 1;
            }
        }
        return ok;
    }

    //типо секретная доска
    public void showSecretBoard() {

        System.out.print(" ");
        for (int i = 1; i < 11; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
        for (int i = 0; i < SIZE_BOARD; i++) {
            System.out.print(columnNumber[i + 1] + " ");
            for (int j = 0; j < SIZE_BOARD; j++) {
                System.out.print(secretBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Создание кораблей
    public void createShip(Scanner sc, int size) throws ArrayIndexOutOfBoundsException {
        do {
            String num1 = sc.next();
            String num2 = sc.next();
            //конвертирует букву
            int x1 = converterNumber(num1);
            //конвертирует цифру, если = 10 то присваивает 9 т.к. работать с массивами
            int z1 = num1.length() > 2 ? 9 : Character.getNumericValue(num1.charAt(1)) - 1;
            int x2 = converterNumber(num2);
            int z2 = num2.length() > 2 ? 9 : Character.getNumericValue(num2.charAt(1)) - 1;

            // По разным строкам
            if (checkShips(x1, z1, x2, z2)) {
                if (x1 == x2 && z1 < z2) {
                    if ((z2 - z1 + 1) == size) {
                        for (int m = z1; m <= z2; m++) {
                            gameBoard[x1][m] = "O";
                        }
                        break;
                    } else {
                        System.out.print("\nError! Wrong length of the Submarine! Try again:\n");
                    }
                } else if (x1 == x2 && z1 > z2) {
                    if ((z1 - z2 + 1) == size) {
                        for (int m = z1; m >= z2; m--) {
                            gameBoard[x1][m] = "O";
                        }
                        break;
                    } else {
                        System.out.print("\nError! Wrong length of the Submarine! Try again:\n");
                    }
                } else if (x1 < x2 && z1 == z2) {
                    if ((x2 - x1 + 1) == size) {
                        for (int m = x1; m <= x2; m++) {
                            gameBoard[m][z1] = "O";
                        }
                        break;
                    } else {
                        System.out.println("\nError! Wrong length of the Submarine! Try again:\n");
                    }
                } else if (x1 > x2 && z1 == z2) {
                    if ((x1 - x2 + 1) == size) {
                        for (int m = x1; m >= x2; m--) {
                            gameBoard[m][z1] = "O";
                        }
                        break;
                    } else {
                        System.out.print("\nError! Wrong length of the Submarine! Try again:\n");
                    }
                } else {
                    System.out.print("\nError! Wrong ship location! Try again: \n");
                }
            } else {
                System.out.print("\n Error! You placed it too close to another one. Try again: \n");
            }
        } while (true);
    }

    // Проверка кораблей вокруг
    public boolean checkShips(int x1, int z1, int x2, int z2) {
        boolean checkShipBoolean = true;
        int checkX1, checkZ1, checkX2, checkZ2;

        if (x1 == x2) {
            checkX1 = x1 > 0 ? x1 - 1 : 0;
            checkX2 = x2 < 9 ? x2 + 1 : 9;
            checkZ1 = makeCheckedCoordinates(z1, z2);
            checkZ2 = makeCheckedCoordinates(z2, z1);
        } else if (z1 == z2) {
            checkZ1 = z1 > 0 ? (z1 - 1) : 0; // убрал - 1
            checkZ2 = z2 < 9 ? (z2 + 1) : 9; // +2 вместо +1
            checkX1 = makeCheckedCoordinates(x1, x2);
            checkX2 = makeCheckedCoordinates(x2, x1);
        } else {
            checkX1 = makeCheckedCoordinates(x1, x2);
            checkZ1 = makeCheckedCoordinates(z1, z2);
            checkX2 = makeCheckedCoordinates(x2, x1);
            checkZ2 = makeCheckedCoordinates(z2, z1);
        }

        if (checkX1 <= checkX2) {
            for (int i = checkX1; i <= checkX2; i++ ) {
                for (int j = checkZ1; j <= checkZ2; j++) {
                    //gameBoard[i][j] = "K";
                    if (gameBoard[i][j].equals("O")) {
                        checkShipBoolean = false;
                        break;
                    }
                }
            }
        } else {
            for (int i = checkX2; i <= checkX1; i++ ) {
                for (int j = checkZ1; j <= checkZ2; j++) {
                    //gameBoard[i][j] = "K";
                    if (gameBoard[i][j].equals("O")) {
                        checkShipBoolean = false;
                        break;
                    }
                }
            }
        }
        return checkShipBoolean;
    }

    // Создание координат для проверки кораблей вокруг
    public int makeCheckedCoordinates(int a, int b) {
        if (a <= b) {
            if (a <= 1) {
                return 0;
            } else {
                return a - 1;
            }
        } else {
            if (a >= 9) {
                return 9;
            } else {
                return a + 1;
            }
        }
    }

    //Этим методом я проверил наличие продолжения корабля рядом с точкой выстрела
    public boolean makeShotCoordinates(int x, int z) {
        int x1 = x < 9 ? x + 1 : 9;
        int x2 = x > 0 ? x - 1 : 0;
        int z1 = z < 9 ? z + 1 : 9;
        int z2 = z > 0 ? z - 1 : 0;

        return (!"O".equals(gameBoard[x1][z])) &&
                (!"O".equals(gameBoard[x2][z])) &&
                (!"O".equals(gameBoard[x][z1])) &&
                (!"O".equals(gameBoard[x][z2])) &&
                (!"X".equals(gameBoard[x][z]));
    }

    public boolean checkEndGame() {
        boolean endGame = false;
        for (int i = 0; i <= SIZE_BOARD - 1; i++ ) {
            for (int j = 0; j <= SIZE_BOARD - 1; j++) {
                if (gameBoard[i][j].equals("O")) {
                    endGame = true;
                    break;
                }
            }
        }
        return  endGame;
    }

}


