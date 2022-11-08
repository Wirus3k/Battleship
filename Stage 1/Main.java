package battleship;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.lang.Math;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);
    static char[][] plane = new char[10][10];
    static Ship[] ships = {new Ship("Aircraft Carrier", 5), new Ship("Battleship", 4),
            new Ship("Submarine", 3), new Ship("Cruiser", 3), new Ship("Destroyer", 2)};
    private static void emptyPlane() {
        for(int i = 0; i < plane.length; i++) {
            for(int j = 0; j < plane[0].length; j++) {
                plane[i][j] = '~';
            }
        }
    }

    private static void displayPlane(){
        for (int i = 0; i <= plane.length; i++) {
            System.out.println();
            for (int j = 0; j <= plane[0].length; j++) {
                if (i == 0 && j == 0) {
                    System.out.print(" ");
                } else if (i == 0) {
                    System.out.printf(" %d", j);
                } else if (j == 0) {
                    switch (i) {
                        case 1:
                            System.out.print("A");
                            break;
                        case 2:
                            System.out.print("B");
                            break;
                        case 3:
                            System.out.print("C");
                            break;
                        case 4:
                            System.out.print("D");
                            break;
                        case 5:
                            System.out.print("E");
                            break;
                        case 6:
                            System.out.print("F");
                            break;
                        case 7:
                            System.out.print("G");
                            break;
                        case 8:
                            System.out.print("H");
                            break;
                        case 9:
                            System.out.print("I");
                            break;
                        case 10:
                            System.out.print("J");
                            break;
                    }
                } else {
                    System.out.printf(" %c", plane[i - 1][j - 1]);
                }
            }
        }
        System.out.println();
        System.out.println();
    }

    private static void changePlane(int[] coordinatesConverter) {
        for(int i = coordinatesConverter[0]; i <= coordinatesConverter[2]; i++) {
            for(int j = coordinatesConverter[1]; j <= coordinatesConverter[3]; j++){
                plane[i][j] = 'O';
            }
        }
    }


    private static void setShips(){
        for (Ship ship : ships) {
            takeCoordinates(ship);
            System.out.println();
            displayPlane();
        }
    }

    private static void takeCoordinates(Ship ship) {
        boolean takeCoordinates = true;
        System.out.printf("Enter the coordinates of the %S (%d cells):%n", ship.name, ship.size);
        System.out.println();
        while (takeCoordinates){
            String[] coordinates = getString().toUpperCase().split(" ");

            if(coordinates.length == 2){
                coordinatesConverter(coordinates);
                if(range(coordinatesConverter(coordinates))) {
                    if (rangePossible(ship, coordinatesConverter(coordinates)) && !shipAround(coordinatesConverter(coordinates))) {
                        changePlane(coordinatesConverter(coordinates));
                        takeCoordinates = false;
                    }
                } else {
                    System.out.println();
                    System.out.println("Coordinates out of map! Try again: ");
                    System.out.println();
                }
            } else {
                System.out.println();
                System.out.println("Error! Wrong format! Try again: ");
                System.out.println();
            }
        }
    }

    private static String getString() {
        return scanner.nextLine();
    }

    private static int[] coordinatesConverter(String[] coordinates){
        try {
            int one = coordinates[0].charAt(0) - 65;
            String second = coordinates[0].substring(1);
            int two = Integer.parseInt(second) - 1;
            int three = coordinates[1].charAt(0) - 65;
            String fourth = coordinates[1].substring(1);
            int four = Integer.parseInt(fourth) - 1;


            return new int[]{Math.min(one, three), Math.min(two, four), Math.max(one, three), Math.max(two, four)};
        } catch (Exception e) {
            return new int[]{};
        }
    }



    private static boolean rangePossible(Ship ship, int[] coordinatesConverter){
        int a = coordinatesConverter[0];
        int b = coordinatesConverter[1];
        int c = coordinatesConverter[2];
        int d = coordinatesConverter[3];
        if (a > c || b > d) {
            System.out.println();
            System.out.println("Error! Wrong format of location (Start -> End of ship) ! Try again: ");
            System.out.println();
            return false;
        } else if (a == c && ship.size - 1 == Math.abs(b - d)) {
            return true;
        } else if (b == d && ship.size - 1 == Math.abs(a - c)) {
            return true;
        } else if (b != d && a != c) {
            System.out.println();
            System.out.println("Error! Wrong ship location! Try again: ");
            System.out.println();
            return false;
        } else {
            System.out.println();
            System.out.println("Error! Wrong length of the Submarine! Try again: ");
            System.out.println();
            return false;
        }
    }

    private static boolean shipAround(int[] coordinatesConverter) {
        boolean a = false;

        if (coordinatesConverter[0] == 0 && coordinatesConverter[1] == 0) {
            for (int i = coordinatesConverter[0]; i <= coordinatesConverter[2] + 1; i++) {
                for (int j = coordinatesConverter[1]; j <= coordinatesConverter[3] + 1; j++) {
                    if (plane[i][j] == 'O') {
                        a = true;
                        break;
                    }
                }
            }
        } else if (coordinatesConverter[2] == 9 && coordinatesConverter[3] == 9) {
            for (int i = coordinatesConverter[0] - 1; i <= coordinatesConverter[2]; i++) {
                for (int j = coordinatesConverter[1] - 1; j <= coordinatesConverter[3]; j++) {
                    if (plane[i][j] == 'O') {
                        a = true;
                        break;
                    }
                }
            }
        } else if (coordinatesConverter[0] == 0 && coordinatesConverter[1] == 9) {
            for (int i = coordinatesConverter[0]; i <= coordinatesConverter[2] + 1; i++) {
                for (int j = coordinatesConverter[1]; j <= coordinatesConverter[3] - 1; j++) {
                    if (plane[i][j] == 'O') {
                        a = true;
                        break;
                    }
                }
            }
        } else if (coordinatesConverter[2] == 0 && coordinatesConverter[3] == 9) {
            for (int i = coordinatesConverter[0] - 1; i <= coordinatesConverter[2]; i++) {
                for (int j = coordinatesConverter[1] + 1; j <= coordinatesConverter[3]; j++) {
                    if (plane[i][j] == 'O') {
                        a = true;
                        break;
                    }
                }
            }
        } else if (coordinatesConverter[0] == 0) {
            for (int i = coordinatesConverter[0]; i <= coordinatesConverter[2] + 1; i++) {
                for (int j = coordinatesConverter[1] - 1; j <= coordinatesConverter[3] + 1; j++) {
                    if (plane[i][j] == 'O') {
                        a = true;
                        break;
                    }
                }
            }
        } else if (coordinatesConverter[1] == 0) {
            for (int i = coordinatesConverter[0] - 1; i <= coordinatesConverter[2] + 1; i++) {
                for (int j = coordinatesConverter[1]; j <= coordinatesConverter[3] + 1; j++) {
                    if (plane[i][j] == 'O') {
                        a = true;
                        break;
                    }
                }
            }
        } else if (coordinatesConverter[2] == 9) {
            for (int i = coordinatesConverter[0] - 1; i <= coordinatesConverter[2]; i++) {
                for (int j = coordinatesConverter[1] - 1; j <= coordinatesConverter[3] + 1; j++) {
                    if (plane[i][j] == 'O') {
                        a = true;
                        break;
                    }
                }
            }
        } else if (coordinatesConverter[3] == 9) {
            for (int i = coordinatesConverter[0] - 1; i <= coordinatesConverter[2] + 1; i++) {
                for (int j = coordinatesConverter[1] - 1; j <= coordinatesConverter[3]; j++) {
                    if (plane[i][j] == 'O') {
                        a = true;
                        break;
                    }
                }
            }
        } else {
            for (int i = coordinatesConverter[0] - 1; i <= coordinatesConverter[2] + 1; i++) {
                for (int j = coordinatesConverter[1] - 1; j <= coordinatesConverter[3] + 1; j++) {
                    if (plane[i][j] == 'O') {
                        a = true;
                        break;
                    }
                }
            }
        }

        if (a) {
            System.out.println();
            System.out.println("Error! You placed it too close to another one. Try again:");
            System.out.println();
        }
        return a;
    }

    private static boolean range(int[] coordinatesConverter) {
        for (int number : coordinatesConverter) {
            if (number < 0 || number > 9)
                return false;
        }
        return true;
    }

    private static void game() {
        System.out.println("The game starts!");
        displayPlane();

    }

    public static void main(String[] args) {
        // Write your code here
        emptyPlane();
        displayPlane();
        setShips();
    }
}

