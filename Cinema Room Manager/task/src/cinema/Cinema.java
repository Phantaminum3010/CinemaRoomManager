package cinema;

import java.util.Scanner;

public class Cinema {
    final static int ticketPriceTypeI = 10;
    final static int ticketPriceTypeII = 8;
    final static int seatsLimitForPrice = 60;
    int purchasedTickets;
    int currentIncome;
    int numRows;
    int numSeatsPerRow;
    String[][] cinemaSeats;

    public Cinema(int numRows, int numSeatsPerRow) {
        this.purchasedTickets = 0;
        this.currentIncome = 0;
        this.numRows = numRows;
        this.numSeatsPerRow = numSeatsPerRow;
        this.cinemaSeats = new String[numRows + 1][numSeatsPerRow + 1];
        for (int i = 0; i < this.cinemaSeats.length; i++) {
            for (int j = 0; j < this.cinemaSeats[i].length; j++) {
                if (i == 0 && j == 0) {
                    this.cinemaSeats[i][j] = "  ";
                } else if (i == 0) {
                    this.cinemaSeats[i][j] = j + " ";
                } else if (j == 0) {
                    this.cinemaSeats[i][j] = i + " ";
                } else {
                    this.cinemaSeats[i][j] = "S ";
                }
            }
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int numRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int numSeatsPerRow = scanner.nextInt();

        Cinema cinema = new Cinema(numRows, numSeatsPerRow);

        printMenu();
        int input = scanner.nextInt();
        while (input != 0) {
            switch(input) {
                case 1:
                    printCinema(cinema.cinemaSeats);
                    break;
                case 2:
                    System.out.println();
                    System.out.println("Enter a row number:");
                    int rowChosen = scanner.nextInt();
                    System.out.println("Enter a seat number in that row:");
                    int seatNumber = scanner.nextInt();
                    while (rowChosen < 0 || rowChosen > cinema.cinemaSeats.length - 1 || seatNumber < 0 ||
                            seatNumber > cinema.cinemaSeats[rowChosen].length - 1 ||
                            cinema.cinemaSeats[rowChosen][seatNumber].equals("B ")) {
                        System.out.println();
                        if (rowChosen < 0 || rowChosen > cinema.cinemaSeats.length - 1 || seatNumber < 0 ||
                                seatNumber > cinema.cinemaSeats[rowChosen].length - 1) {
                            System.out.println("Wrong input!");
                        } else {
                            System.out.println("That ticket has already been purchased!");
                        }
                        System.out.println();
                        System.out.println("Enter a row number:");
                        rowChosen = scanner.nextInt();
                        System.out.println("Enter a seat number in that row:");
                        seatNumber = scanner.nextInt();
                    }
                    chooseTicket(numRows, numSeatsPerRow, rowChosen, seatNumber, cinema);
                    break;
                case 3:
                    System.out.println();
                    System.out.println("Number of purchased tickets: " + cinema.purchasedTickets);
                    double percentage = (cinema.purchasedTickets * 1.00) / (cinema.numRows * cinema.numSeatsPerRow);
                    percentage *= 100;
                    System.out.printf("Percentage: %.2f%c", percentage, '%');
                    System.out.println();
                    System.out.println("Current income: $" + cinema.currentIncome);
                    int totalSeats = cinema.numSeatsPerRow * cinema.numRows;
                    if (totalSeats <= 60) {
                        System.out.println("Total income: $" + totalSeats * ticketPriceTypeI);
                    } else {
                        int priorityRows = cinema.numRows / 2;
                        int totalIncome = (priorityRows * ticketPriceTypeI +
                                (cinema.numRows - priorityRows) * ticketPriceTypeII) * cinema.numSeatsPerRow;
                        System.out.println("Total income: $" + totalIncome);
                    }
                    break;
                default:
                    break;
            }
            printMenu();
            input = scanner.nextInt();
        }
    }

    private static void printCinema(String[][] cinemaSeats) {
        System.out.println();
        System.out.println("Cinema:");
        for (String[] cinemaSeat : cinemaSeats) {
            for (String s : cinemaSeat) {
                System.out.print(s);
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    private static void chooseTicket(int numRows, int numSeatsPerRow, int rowChosen, int seatNumber, Cinema cinema) {
        int ticketPrice;

        int totalSeats = numSeatsPerRow * numRows;
        if (totalSeats <= seatsLimitForPrice) {
            ticketPrice = ticketPriceTypeI;
        } else {
            if (rowChosen <= numRows / 2) {
                ticketPrice = ticketPriceTypeI;
            } else {
                ticketPrice = ticketPriceTypeII;
            }
        }
        System.out.println("Ticket price: $" + ticketPrice);
        cinema.cinemaSeats[rowChosen][seatNumber] = "B ";
        cinema.purchasedTickets++;
        cinema.currentIncome += ticketPrice;
    }
}