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
    int rowChosen;
    int seatNumberChosen;
    String[][] cinemaSeats;

    public enum State {
        SHOW_MENU, SHOW_SEATS, CHOOSE_ROW, CHOOSE_SEAT_NUMBER, SHOW_STATISTICS, EXIT
    }

    State state;

    public Cinema(int numRows, int numSeatsPerRow) {
        this.state = State.SHOW_MENU;
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

        while (cinema.state != State.EXIT) {
            cinema.processState();
            if (cinema.state == State.SHOW_MENU || cinema.state == State.CHOOSE_ROW ||
                    cinema.state == State.CHOOSE_SEAT_NUMBER) {
                cinema.processRequest(scanner.next());
            } else {
                cinema.state = State.SHOW_MENU;
            }
        }
    }

    private void printCinema() {
        System.out.println();
        System.out.println("Cinema:");
        for (String[] cinemaSeat : this.cinemaSeats) {
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

    private void chooseTicket(int numRows, int numSeatsPerRow, int rowChosen, int seatNumber) {
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
        System.out.println();
        System.out.println("Ticket price: $" + ticketPrice);
        this.cinemaSeats[rowChosen][seatNumber] = "B ";
        this.purchasedTickets++;
        this.currentIncome += ticketPrice;
    }

    private void showStatistics() {
        System.out.println();
        System.out.println("Number of purchased tickets: " + this.purchasedTickets);
        double percentage = (this.purchasedTickets * 1.00) / (this.numRows * this.numSeatsPerRow);
        percentage *= 100;
        System.out.printf("Percentage: %.2f%c", percentage, '%');
        System.out.println();
        System.out.println("Current income: $" + this.currentIncome);
        int totalSeats = this.numSeatsPerRow * this.numRows;
        if (totalSeats <= 60) {
            System.out.println("Total income: $" + totalSeats * ticketPriceTypeI);
        } else {
            int priorityRows = this.numRows / 2;
            int totalIncome = (priorityRows * ticketPriceTypeI +
                    (this.numRows - priorityRows) * ticketPriceTypeII) * this.numSeatsPerRow;
            System.out.println("Total income: $" + totalIncome);
        }
    }

    private void processState() {
        switch(this.state) {
            case SHOW_MENU:
                printMenu();
                break;
            case SHOW_STATISTICS:
                this.showStatistics();
                break;
            case SHOW_SEATS:
                this.printCinema();
                break;
            case CHOOSE_ROW:
                System.out.println();
                System.out.println("Enter a row number:");
                break;
            case CHOOSE_SEAT_NUMBER:
                System.out.println("Enter a seat number in that row:");
                break;
            default:
                break;
        }
    }

    private void processRequest(String request) {
        int input = Integer.parseInt(request);
        switch(this.state) {
            case SHOW_MENU:
                switch(input) {
                    case 1:
                        this.state = State.SHOW_SEATS;
                        break;
                    case 2:
                        this.state = State.CHOOSE_ROW;
                        break;
                    case 3:
                        this.state = State.SHOW_STATISTICS;
                        break;
                    case 0:
                        this.state = State.EXIT;
                        break;
                    default:
                        System.out.println("Invalid option, please choose again!");
                        break;
                }
                break;
            case CHOOSE_ROW:
                this.rowChosen = input;
                this.state = State.CHOOSE_SEAT_NUMBER;
                break;
            case CHOOSE_SEAT_NUMBER:
                if (this.rowChosen > 0 && this.rowChosen < this.cinemaSeats.length &&
                        input > 0 && input < this.cinemaSeats[this.rowChosen].length) {
                    this.seatNumberChosen = input;
                    if (this.cinemaSeats[this.rowChosen][this.seatNumberChosen].equals("B ")) {
                        System.out.println();
                        System.out.println("That ticket has already been purchased");
                        this.state = State.CHOOSE_ROW;
                    } else {
                        chooseTicket(this.numRows, this.numSeatsPerRow, this.rowChosen,
                                this.seatNumberChosen);
                        this.state = State.SHOW_MENU;
                    }
                } else {
                    System.out.println();
                    System.out.println("Wrong input!");
                    this.state = State.CHOOSE_ROW;
                }
                break;
            default:
                break;
        }
    }
}