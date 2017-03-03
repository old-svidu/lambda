import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by root on 03.03.17.
 */
public class Util {


    private static AtomicInteger sum = new AtomicInteger(0);

    public static void parse(String string) {

    }

    public static BufferedReader getBufferedReaderFromPath(String path) throws IOException, IllegalArgumentException {
        BufferedReader br;
        if (path.matches("^(http:\\/|https:\\/|ftp:\\/).+$")) {
            br = new BufferedReader(new InputStreamReader(new URL(path).openStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        }
        return br;
    }

    public static void parseFromResource(String path)  {
        try (BufferedReader br = Util.getBufferedReaderFromPath(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                Util.addToSum(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void addToSum(String string) {
        Scanner scanner = new Scanner(string);
        scanner.useDelimiter(" ");
        while (scanner.hasNext()) {
            String word = scanner.next();
            int number = Integer.parseInt(word);
            if (number > 0 && number % 2 == 0) {
                sum.getAndAdd(number);
                System.out.println(sum.get());
            }
        }
    }


    public static void parseFromResources(String[] resources){
        Arrays.stream(resources).forEach(resource -> {
            new Thread(()->Util.parseFromResource(resource)).start();
        });
    }
}


