import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BasketAnalyzerSolution {

    public static void main(String[] args) {

        //Added CSV file absolute path as a Run time parameter
        String filePath = args[0];
        List<Fruit> fruitList = readCSV(filePath);

        //Once get FruitList from CSV file
        //Task 1. Get Total Number Of Fruits
        System.out.println("1. Total number of fruit: " + getTotalFruits(fruitList));
        //Task 2. Get Total Number Of Unique Fruit Types
        System.out.println("2. Total types of fruit: " + getTotalTypesOfFruits(fruitList));
        //Task 3. The number of each type of fruit in descending order
        System.out.println("3. The number of each type of fruit in descending order:");
        Map<String, Integer> fruitCounts = countFruits(fruitList);
        fruitCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
        //Task 4. The characteristics (size, color, shape, etc.) of each fruit by type
        System.out.println("4. The characteristics of each fruit by type:");
        characteristicsByType(fruitList);

        //Task 5. Have any fruit been in the basket for over 3 days
        System.out.println("5. Have any fruit been in the basket for over 3 days");
        getFruitsOverThreeDays(fruitList);

    }

    public static List<Fruit> readCSV(String filePath) {
        List<Fruit> fruitList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the first line
                }
                String[] data = line.split(",");
                String name = data[0].trim();
                int size = Integer.parseInt(data[1].trim());
                String color = data[2].trim();
                String shape = data[3].trim();
                int days = Integer.parseInt(data[4].trim());

                Fruit fruit = new Fruit(name, size, color, shape, days);
                fruitList.add(fruit);
            }
        } catch (IOException e) {
            System.out.println("Unable to read data from file. "+filePath);
            e.printStackTrace();
        }
        return fruitList;
    }

    public static int getTotalFruits(List<Fruit> fruitList) {
        return fruitList.stream().mapToInt(Fruit::getSize).sum();
    }

    public static int getTotalTypesOfFruits(List<Fruit> fruitList) {
        Set<String> uniqueFruits = new HashSet<>();
        for (Fruit fruit : fruitList) {
            uniqueFruits.add(fruit.getName());
        }
        return uniqueFruits.size();
    }

    public static Map<String, Integer> countFruits(List<Fruit> fruitList) {
        Map<String, Integer> fruitCounts = new HashMap<>();
        for (Fruit fruit : fruitList) {
            String name = fruit.getName();
            fruitCounts.put(name, fruitCounts.getOrDefault(name, 0) + fruit.getSize());
        }
        return fruitCounts;
    }

    public static void characteristicsByType(List<Fruit> fruits) {
        Map<String, Map<String, List<Fruit>>> groupedFruits = new HashMap<>();

        for (Fruit fruit : fruits) {
            String type = fruit.getName();
            String key = fruit.getColor() + ", " + fruit.getShape();

            if (!groupedFruits.containsKey(type)) {
                groupedFruits.put(type, new HashMap<>());
            }

            Map<String, List<Fruit>> typeGroup = groupedFruits.get(type);

            if (!typeGroup.containsKey(key)) {
                typeGroup.put(key, new ArrayList<>());
            }

            typeGroup.get(key).add(fruit);
        }

        for (Map.Entry<String, Map<String, List<Fruit>>> entry : groupedFruits.entrySet()) {
            String type = entry.getKey();
            Map<String, List<Fruit>> typeGroups = entry.getValue();

            for (Map.Entry<String, List<Fruit>> typeEntry : typeGroups.entrySet()) {
                String colorShape = typeEntry.getKey();
                List<Fruit> typeFruits = typeEntry.getValue();
                int totalSize = typeFruits.stream().mapToInt(Fruit::getSize).sum();

                System.out.println(totalSize + " " + type + ": " + colorShape);
            }
        }
    }

    public static void getFruitsOverThreeDays(List<Fruit> fruits) {
        Map<String, Integer> totalSizes = new HashMap<>();

        for (Fruit fruit : fruits) {
            if (fruit.getDays() > 3) {
                String key = fruit.getName();
                totalSizes.put(key, totalSizes.getOrDefault(key, 0) + fruit.getSize());
            }
        }

        List<String> result = totalSizes.entrySet().stream()
                .map(entry -> entry.getValue() + " " + entry.getKey())
                .collect(Collectors.toList());

        System.out.println(String.join(", ", result) + " are over 3 days old");
    }

}
