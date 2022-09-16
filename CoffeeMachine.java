package machine;

import java.util.Scanner;
import java.util.Arrays;

class InnerCoffeeMachine {

    public static State state = State.START;
    public static int[] ingredientsAndMoney = new int[]{400, 540, 120, 9, 550};
    public enum State {
        START,
        WAITING_ACTION,
        CHOOSING_COFFEE_TYPE,
        FILLING_WATER,
        FILLING_MILK,
        FILLING_COFFEE,
        FILLING_CUPS,
        EXIT;
    }

    public static void inputHandle(String action) {
        switch (state) {
            case START:
                printActions();
                break;
            case WAITING_ACTION:
                actionHandle(action);
                break;
            case FILLING_WATER:
            case FILLING_MILK:
            case FILLING_COFFEE:
            case FILLING_CUPS:
                fill(action);
                break;
            case CHOOSING_COFFEE_TYPE:
                buy(action);
                break;

        }
    }

    public static void printActions() {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        state = State.WAITING_ACTION;
    }
    public static void actionHandle(String action){
        switch (action) {
            case "take":
                take();
                break;
            case "fill":
                fill("");
                break;
            case "buy":
                buy("");
                break;
            case "remaining":
                printState();
                break;
            case "exit":
                state = State.EXIT;
        }
    }

    public static String checkerOfResources(int[] a, int[] b) {
        boolean flag = true;
        for (int i = 0; i < a.length; i++) {
            if (a[i] - b[i] < 0) {
                switch (i) {
                    case 0:
                        System.out.println("Sorry, not enough water!");
                        break;
                    case 1:
                        System.out.println("Sorry, not enough milk!");
                        break;
                    case 2:
                        System.out.println("Sorry, not enough coffee beans!");
                        break;
                    case 3:
                        System.out.println("Sorry, not enough disposable cups!");
                        break;
                }
                flag = false;
            }
        }
        String result = flag ? "I have enough resources, making you a coffee!" : "No";
        return result;
    }

    public static void ingredientsNeeded() {
        String question_the_cups = "Write how many cups of coffee you will need: ";
        Scanner scanner = new Scanner(System.in);

        System.out.println(question_the_cups);
        int numberOfCups = scanner.nextInt();
        String textIngredients = String.format("For %d cups of coffee you will need:\n" +
                "%d ml of water\n" +
                "%d ml of milk\n" +
                "%d g of coffee beans", numberOfCups, numberOfCups * 200, numberOfCups * 50, numberOfCups * 15);
        System.out.println(textIngredients);
    }

    public static void howManyCupsCanBeDone() {
        String water = "Write how many ml of water the coffee machine has:";
        String milk = "Write how many ml of milk the coffee machine has:";
        String coffee = "Write how many grams of coffee beans the coffee machine has:";
        String cups = "Write how many cups of coffee you will need:";
        String yesOnly = "Yes, I can make that amount of coffee";
        Scanner scanner = new Scanner(System.in);

        System.out.println(water);
        int waterAmount = scanner.nextInt();
        System.out.println(milk);
        int milkAmount = scanner.nextInt();
        System.out.println(coffee);
        int coffeeAmount = scanner.nextInt();
        System.out.println(cups);
        int cupsAmount = scanner.nextInt();

        int a[] = {waterAmount / 200, milkAmount / 50, coffeeAmount / 15};
        Arrays.sort(a);
        int maxCups = a[0];
        if (maxCups == cupsAmount) {
            System.out.println(yesOnly);
        } else if (maxCups > cupsAmount) {
            System.out.printf("Yes, I can make that amount of coffee (and even %d more than that)", maxCups - cupsAmount);
        } else {
            System.out.printf("No, I can make only %d cup(s) of coffee", maxCups);
        }
    }

    public static void buy(String coffee) {
        if (state == State.WAITING_ACTION) {
            System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
            state = State.CHOOSING_COFFEE_TYPE;
        } else {
            switch (coffee) {
                case "1":
                    int[] b = new int[]{250, 0, 16, 1, 0};
                    if (checkerOfResources(ingredientsAndMoney, b).equals("I have enough resources, making you a coffee!")) {
                        ingredientsAndMoney[0] -= 250;
                        ingredientsAndMoney[2] -= 16;
                        ingredientsAndMoney[3] -= 1;
                        ingredientsAndMoney[4] += 4;
                        System.out.println("I have enough resources, making you a coffee!");
                    }
                    break;
                case "2":
                    int[] c = new int[]{350, 75, 20, 1, 0};
                    if (checkerOfResources(ingredientsAndMoney, c).equals("I have enough resources, making you a coffee!")) {
                        ingredientsAndMoney[0] -= 350;
                        ingredientsAndMoney[1] -= 75;
                        ingredientsAndMoney[2] -= 20;
                        ingredientsAndMoney[3] -= 1;
                        ingredientsAndMoney[4] += 7;
                        System.out.println("I have enough resources, making you a coffee!");
                    }
                    break;
                case "3":
                    int[] d = new int[]{200, 100, 12, 1, 0};
                    if (checkerOfResources(ingredientsAndMoney, d).equals("I have enough resources, making you a coffee!")) {
                        ingredientsAndMoney[0] -= 200;
                        ingredientsAndMoney[1] -= 100;
                        ingredientsAndMoney[2] -= 12;
                        ingredientsAndMoney[3] -= 1;
                        ingredientsAndMoney[4] += 6;
                        System.out.println("I have enough resources, making you a coffee!");
                    }
                    break;
                case "back":
                    break;
            }
            printActions();
        }
    }

    public static void fill(String number) {
        if (state.name().equals("FILLING_CUPS")) {
            ingredientsAndMoney[3] += Integer.parseInt(number);
            printActions();
        } else if (state.name().equals("WAITING_ACTION")) {
            System.out.println("Write how many ml of water you want to add:");
            state = State.FILLING_WATER;
        } else if (state.name().equals("FILLING_WATER")) {
            ingredientsAndMoney[0] += Integer.parseInt(number);
            System.out.println(" Write how many ml of milk you want to add:");
            state = State.FILLING_MILK;
        } else if (state.name().equals("FILLING_MILK")) {
            ingredientsAndMoney[1] += Integer.parseInt(number);
            System.out.println("Write how many grams of coffee beans you want to add:");
            state = State.FILLING_COFFEE;
        } else {
            ingredientsAndMoney[2] += Integer.parseInt(number);
            System.out.println("Write how many disposable cups you want to add:");
            state = State.FILLING_CUPS;
        }
    }

    public static void take() {
        System.out.println("I gave you $" + ingredientsAndMoney[4] + "\n");
        ingredientsAndMoney[4] = 0;
        printActions();
    }

    public static void printState() {
        int[] a = ingredientsAndMoney;
        String text = String.format("The coffee machine has:\n" +
                "%d ml of water\n" +
                "%d ml of milk\n" +
                "%d g of coffee beans\n" +
                "%d disposable cups\n" +
                "$%d of money", a[0], a[1], a[2], a[3], a[4]);
        System.out.println(text);
        printActions();
    }

}

public class CoffeeMachine {
    public static void main(String[] args) {
        String text = "Starting to make a coffee\nGrinding coffee beans\nBoiling water\nMixing boiled water with crushed coffee beans\nPouring coffee into the cup\nPouring some milk into the cup\nCoffee is ready!";
        Scanner scanner = new Scanner(System.in);
        InnerCoffeeMachine coffeeMachine = new InnerCoffeeMachine();
        coffeeMachine.inputHandle("");
        do {
            coffeeMachine.inputHandle(scanner.nextLine());
        }
        while (!coffeeMachine.state.name().equals("EXIT"));
    }
}
