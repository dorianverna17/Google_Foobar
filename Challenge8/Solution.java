package Challenge8;

import java.util.ArrayList;
import java.util.Arrays;

public class Solution {
    public static int solution(int[] banana_list) {
        // Your code here
        ArrayList<ArrayList<Integer>> matrix = createAdjancecyLists(banana_list);
        print(matrix);
        return banana_list.length - blossom(matrix);
    }

    public static void print(ArrayList<ArrayList<Integer>> matrix) {
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++)
                System.out.print(matrix.get(i).get(j) + " ");
            System.out.println();
        }
    }

    public static ArrayList<ArrayList<Integer>> createAdjancecyLists(int[] bananas) {
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
        for (int i = 0; i < bananas.length; i++) {
            matrix.add(new ArrayList<>());
        }
        for (int i = 0; i < bananas.length; i++) {
            for (int j = i; j < bananas.length; j++) {
                if (loop(bananas[i], bananas[j]) && i != j) {
                    matrix.get(i).add(j);
                    matrix.get(j).add(i);
                }
            }
        }
        return matrix;
    }

    public static int blossom(ArrayList<ArrayList<Integer>> matrix) {
        int matches = 0, node, size;
        int max = getMaxList(matrix);
        int checks = matrix.get(max).size();
        int[] second_min = new int[2];
        while (matrix.size() > 1 && checks >= 1) {
            node = getMinList(matrix);
            if (matrix.get(node).size() < 1)
                matrix.remove(node);
            else {
                size = matrix.get(matrix.get(node).get(0)).size() + 1;
                second_min[0] = size;
                second_min[1] = 1;
                for (int i = 0; i < matrix.get(node).size(); i++) {
                    if (matrix.get(matrix.get(node).get(i)).size() < second_min[0]) {
                        second_min[0] = matrix.get(matrix.get(node).get(i)).size();
                        second_min[1] = matrix.get(node).get(i);
                    }
                    for (int check = 0; check < matrix.get(matrix.get(node).get(i)).size(); check++) {
                        if (matrix.get(matrix.get(node).get(i)).get(check) == node) {
                            matrix.get(matrix.get(node).get(i)).remove(check);
                            break;
                        }
                    }
                }
                for (int i = 0; i < matrix.get(second_min[1]).size(); i++) {
                    for (int check = 0; check < matrix.get(matrix.get(second_min[1]).get(i)).size(); check++) {
                        if (matrix.get(matrix.get(second_min[1]).get(i)).get(check) == second_min[1]) {
                            matrix.get(matrix.get(second_min[1]).get(i)).remove(check);
                            break;
                        }
                    }
                }
                matrix.remove(node);
                matrix.remove(second_min[1]);
                matches += 2;
                if (matrix.size() > 1) {
                    max = getMaxList(matrix);
                    checks = matrix.get(max).size();
                }
            }
        }
        return matches;
    }

    private static int getMaxList(ArrayList<ArrayList<Integer>> matrix) {
        int max = Integer.MIN_VALUE;
        int key = -1;
        for (int i = 0; i < matrix.size(); i++) {
            if (max < matrix.get(i).size()) {
                max = matrix.get(i).size();
                key = i;
            }
        }
        return key;
    }

    private static int getMinList(ArrayList<ArrayList<Integer>> matrix) {
        int min = Integer.MAX_VALUE;
        int key = -1;
        for (int i = 0; i < matrix.size(); i++) {
            if (min > matrix.get(i).size()) {
                min = matrix.get(i).size();
                key = i;
            }
        }
        return key;
    }

    public static int greatestCommonDivisor(int x, int y) {
        while (y != 0) {
            int remainder = x % y;
            x = y;
            y = remainder;
        }
        return x;
    }

    public static boolean loop(int x, int y) {
        int sum = (x + y) / greatestCommonDivisor(x, y);
        return !((sum & (sum - 1)) == 0);
    }

    public static void main(String[] args) {
        int[] banana_list = new int[] {1, 7, 3, 21, 13, 19};
        System.out.println(solution(banana_list));
    }
}
