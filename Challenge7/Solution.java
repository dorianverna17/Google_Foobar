package Challenge7;

//import java.util.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Solution {
    static class Pair {
        int safety;
        int[] point;

        Pair(int safety, int[] point) {
            this.safety = safety;
            this.point = point;
        }

    }

    public static boolean listContains(ArrayList<int[]> list, int[] point) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i)[0] == point[0] && list.get(i)[1] == point[1]) {
                return true;
            }
        }
        return false;
    }

    public static void printPoint(int[] point) {
        System.out.print("[" + point[0] + " " + point[1] + "] ");
    }

    // functie care calculeaza distanta dintre 2 puncte
    private static double computeDistance(int[] your_position, int[] image) {
        return Math.sqrt((image[0] - your_position[0]) * (image[0] - your_position[0]) +
                (image[1] - your_position[1]) * (image[1] - your_position[1]));
    }

    public static ArrayList<Pair> computeFirstQuadrantPositions(int[] your_position, int distance,
                                                          int[] dimensions, int[] trainer_position) {
        ArrayList<Pair> list = new ArrayList<>();
        int max_x = your_position[0] + distance;
        int max_y = your_position[1] + distance;
        double times_x = Math.ceil((double)max_x / dimensions[0]);
        double times_y = Math.ceil((double)max_y / dimensions[1]);
        list.add(new Pair(0, trainer_position));
        int[] new_int = new int[] {trainer_position[0], trainer_position[1]};
        list.add(new Pair(0, new int[] {-new_int[0], new_int[1]}));
        list.add(new Pair(0, new int[] {-new_int[0], -new_int[1]}));
        list.add(new Pair(0, new int[] {new_int[0], -new_int[1]}));
        for (int i = 0; i < times_x; i++) {
            if (i != 0) {
                new_int = new int[]{new_int[0] + (i * dimensions[0] - new_int[0]) * 2,
                        list.get(0).point[1]};

                list.add(new Pair(0, new_int));
                list.add(new Pair(0, new int[] {-new_int[0], new_int[1]}));
                list.add(new Pair(0, new int[] {-new_int[0], -new_int[1]}));
                list.add(new Pair(0, new int[] {new_int[0], -new_int[1]}));
            }
            for (int j = 1; j < times_y; j++) {
                new_int = new int[] {new_int[0], new_int[1] + (j * dimensions[1] -
                        new_int[1]) * 2};

                list.add(new Pair(0, new_int));
                list.add(new Pair(0, new int[] {-new_int[0], new_int[1]}));
                list.add(new Pair(0, new int[] {-new_int[0], -new_int[1]}));
                list.add(new Pair(0, new int[] {new_int[0], -new_int[1]}));
            }
        }
        return list;
    }

    public static ArrayList<Pair> computeSafetyArray(int[] your_position, int[] dimensions, int distance) {
        return computeFirstQuadrantPositions(your_position, distance, dimensions, your_position);
    }

    public static int solution(int[] dimensions, int[] your_position, int[] trainer_position, int distance) {
        ArrayList<Pair> firstQuadrant = computeFirstQuadrantPositions(your_position, distance,
                dimensions, trainer_position);
        int nr = 0;
//        System.out.println(firstQuadrant.size());
        ArrayList<Pair> safetyArray = computeSafetyArray(your_position, dimensions, distance);
        for (int i = 0; i < safetyArray.size(); i++) {
            safetyArray.get(i).safety = 1;
        }
        firstQuadrant.addAll(safetyArray);
//        Collections.sort(firstQuadrant, new Comparator<Pair>() {
//            @Override
//            public int compare(Pair o1, Pair o2) {
//                if (computeDistance(o1.point, your_position) >= computeDistance(o2.point, your_position)) {
//                    return 1;
//                } else if (computeDistance(o1.point, your_position) < computeDistance(o2.point, your_position)) {
//                    return -1;
//                } else {
//                    return 0;
//                }
//            }
//        });
//        for (int i = 0; i < firstQuadrant.size(); i++) {
//            printPoint(firstQuadrant.get(i).point);
//        }
        firstQuadrant.sort(Comparator.comparing(o -> computeDistance(your_position, o.point)));
//        Collections.sort(firstQuadrant, new Comparator<Pair>() {
//            @Override
//            public int compare(Pair o1, Pair o2) {
//                if (computeDistance(o1.point, your_position) >= computeDistance(o2.point, your_position))
//                    return 1;
//                if (computeDistance(o1.point, your_position) < computeDistance(o2.point, your_position))
//                    return -1;
//                return 0;
//            }
//        });
        firstQuadrant.remove(0);
        HashMap<Double, Pair> map = new HashMap<>();
        for (int i = 0; i < firstQuadrant.size(); i++) {
            if (computeDistance(firstQuadrant.get(i).point, your_position) <= distance) {
                if (!map.containsKey(Math.atan2(firstQuadrant.get(i).point[1] -
                        your_position[1], firstQuadrant.get(i).point[0] - your_position[0]))) {
                    map.put(Math.atan2(firstQuadrant.get(i).point[1] -
                            your_position[1], firstQuadrant.get(i).point[0] - your_position[0]), firstQuadrant.get(i));
                    if (firstQuadrant.get(i).safety == 0)
                        nr++;
                }
            }
        }
//        for (int i = 0; i < firstQuadrant.size(); i++) {
//            if (computeDistance(firstQuadrant.get(i), your_position) <= distance) {
//                if (!set.containsKey(Math.atan2(firstQuadrant.get(i)[1] -
//                        your_position[1], firstQuadrant.get(i)[0] - your_position[0]))) {
//                    set.put(Math.atan2(firstQuadrant.get(i)[1] -
//                            your_position[1], firstQuadrant.get(i)[0] - your_position[0]), firstQuadrant.get(i));
//                    if (!listContains(safetyArray, firstQuadrant.get(i)))
//                        nr++;
//                }
//            }
//        }
//        for (int i = 0; i < firstQuadrant.size(); i++) {
//            printPoint(firstQuadrant.get(i));
//            System.out.println(computeDistance(firstQuadrant.get(i), your_position));
//        }
//        HashMap<Double, int[]> set = new HashMap<>();
//        for (int i = 0; i < firstQuadrant.size(); i++) {
//            if (!set.containsKey(Math.atan2(firstQuadrant.get(i)[1] - your_position[1], firstQuadrant.get(i)[0] - your_position[0]))) {
//                set.put(Math.atan2(firstQuadrant.get(i)[1] - your_position[1], firstQuadrant.get(i)[0] - your_position[0]), firstQuadrant.get(i));
//            } else {
//                nr++;
////                firstQuadrant.remove(i);
////                i--;
//            }
//        }
//        for (int i = 1; i < safetyArray.size(); i++) {
//            if (set.containsKey(Math.atan2(safetyArray.get(i)[1] - your_position[1], safetyArray.get(i)[0] - your_position[0])))
//                if (computeDistance(safetyArray.get(i), your_position) <= computeDistance(set.get(Math.atan2(safetyArray.get(i)[1] - your_position[1],
//                                safetyArray.get(i)[0] - your_position[0])), your_position))
//                    nr++;
//        }
//        nr++;
//        System.out.println(firstQuadrant.size());
//        for (int i = 1; i < safetyArray.size(); i++) {
//            if (set.containsKey(Math.atan2(safetyArray.get(i)[1] - your_position[1], safetyArray.get(i)[0] - your_position[0])))
//                if (computeDistance(safetyArray.get(i), your_position) <=
//                        computeDistance(set.get(Math.atan2(safetyArray.get(i)[1] - your_position[1],
//                                safetyArray.get(i)[0] - your_position[0])), your_position)) {
//                firstQuadrant.remove(set.get(Math.atan2(safetyArray.get(i)[1] - your_position[1],
//                        safetyArray.get(i)[0] - your_position[0])));
//            }
//        }
        return nr;
    }

    public static void main(String[] args) {
        int[] dimensions, your_position, trainer_position;
        int distance, nr;

        // Test 1
        dimensions = new int[] {3, 2};
        your_position = new int[] {1, 1};
        trainer_position = new int[] {2, 1};
        distance = 4;

        nr = solution(dimensions, your_position, trainer_position, distance);
        if (nr == 7)
            System.out.println("Test 1 passed");
        else System.out.println(nr);

        // Test 2
        dimensions = new int[] {23, 10};
        your_position = new int[] {6, 4};
        trainer_position = new int[] {3, 2};
        distance = 23;

        nr = solution(dimensions, your_position, trainer_position, distance);
        if (nr == 8)
            System.out.println("Test 2 passed");
        else System.out.println(nr);

        // Test 3
        dimensions = new int[] {2, 5};
        your_position = new int[] {1, 2};
        trainer_position = new int[] {1, 4};
        distance = 11;

        nr = solution(dimensions, your_position, trainer_position, distance);
        if (nr == 27)
            System.out.println("Test 3 passed");

        //Test 4
        dimensions = new int[] {300, 275};
        your_position = new int[] {150, 150};
        trainer_position = new int[] {185, 100};
        distance = 500;

        nr = solution(dimensions, your_position, trainer_position, distance);
        if (nr == 9)
            System.out.println("Test 4 passed");

        // Test 5
        dimensions = new int[] {10, 10};
        your_position = new int[] {4, 4};
        trainer_position = new int[] {3, 3};
        distance = 5000;

        nr = solution(dimensions, your_position, trainer_position, distance);
        if (nr == 739323)
            System.out.println("Test 5 passed");
        else System.out.println(nr);
    }
}
