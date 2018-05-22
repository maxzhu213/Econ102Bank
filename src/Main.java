import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    FileReader in;
    BufferedReader reader;
    List<String> questions, options, answers;
    List<Question> queries;



    Main() {

        questions = new ArrayList<>();
        options = new ArrayList<>();
        answers = new ArrayList<>();
        queries = new ArrayList<>();

        try {
            in = new FileReader(new File(System.getProperty("user.dir") + "\\src\\testbank.txt"));
            reader = new BufferedReader(in);

            String line;
            Question qu = new Question();
            boolean isQuestion = true;
            while ((line = reader.readLine()) != null) {
                if (line.length() > 1) {
                    if (Character.isDigit(line.charAt(0)) && line.contains(")")) {
                        isQuestion = true;
                    }

                    if (line.contains("Answer:")) {
                        qu.answer = line.charAt(line.length() - 1);
                        isQuestion = false;
                    } else if (line.charAt(1) == ')') {
                        qu.options.add(line);
                        isQuestion = false;
                    }

                    if (isQuestion) {
                        qu.question += "\n" + line;
                    }

                    if (qu.answer != 0) {
                        queries.add(qu);
                        qu = new Question();
                    }
                }

                //System.out.println(line);
            }


            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Main main = new Main();

        Scanner sc = new Scanner(System.in);
        Question qu = new Question();
        Random rand = new Random();
        int answered = 0;
        int right = 0;


        while (true) {
            do {
                qu = main.queries.get(rand.nextInt(main.queries.size()));
            } while (qu.question.contains("Refer"));

            System.out.println(qu.question);
            for (int i = 0; i < qu.options.size(); i++) {
                System.out.println(qu.options.get(i));
            }

            String input = sc.next();

            if (Character.toUpperCase(input.charAt(0)) == qu.answer) {
                System.out.println("Correct!\n");
                right++;
            } else if (input.charAt(0) == 'q' || input.charAt(0) == 'Q') {
                System.out.print("Exiting! By the way, the correct answer was ");
                System.out.println(qu.answer);
                break;
            } else {
                System.out.print(input + " is wrong! The correct answer was ");
                System.out.println(qu.answer);
            }
            answered++;
            System.out.printf("You have gotten %d / %d correct.\n", right, answered);
        }

        sc.close();
    }

}
