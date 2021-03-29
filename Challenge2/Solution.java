package Challenge2;

import java.util.Vector;

public class Solution {
    static int max = Integer.MIN_VALUE;

    public static int form_number(Vector<Integer> vector) {
        int nr = 0;
        for (Integer integer : vector) {
            nr = nr * 10 + integer;
        }
        return nr;
    }

    public static int back(Vector<Integer> solution, Vector<Integer> domain) {
        int number = form_number(solution);
        if (number % 3 == 0) {
            if (number > max)
                max = number;
        }
        for (int i = 0; i < domain.size(); i++) {
            Vector<Integer> domain_new = new Vector<>(domain);
            domain_new.remove(domain.get(i));
            Vector<Integer> solution_new = new Vector<>(solution);
            solution_new.add(domain.get(i));
            back(solution_new, domain_new);
        }
        return max;
    }

    public static int solution(int[] l) {
        Vector<Integer> solution = new Vector<>();
        Vector<Integer> domain = new Vector<>();
        for (int j : l) {
            domain.add(j);
        }
        return back(solution, domain);
    }

    public static void main(String[] args) {
        int[] arr = new int[] {3, 1, 4, 1, 5, 9};
        System.out.println(solution(arr));
    }
}