package Challenge9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Solution {
    public static void print_matrix(boolean[][] g) {
        for (boolean[] booleans : g) {
            for (boolean aBoolean : booleans) {
                System.out.print(aBoolean + " ");
            }
            System.out.println();
        }
    }

    public static void print_vector(ArrayList<Integer> v) {
        for (Integer i : v) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void print_hashmap(HashMap<Integer, HashMap<Integer, HashSet<Integer>>> map) {

        for (Map.Entry<Integer, HashMap<Integer, HashSet<Integer>>> entry : map.entrySet()) {
            HashMap<Integer, HashSet<Integer>> m = entry.getValue();
            for (Map.Entry<Integer, HashSet<Integer>> en : m.entrySet()) {
                System.out.print(entry.getKey() + " ");
                System.out.print(en.getKey() + ": ");
                for (Integer s : en.getValue()) {
                    System.out.print(s + " ");
                }
                System.out.println();
            }
        }
    }

    public static int generate(int a, int b, int d) {
        int aux1, aux2, aux3, aux4;
        aux1 = a & ~(1 << d);
        aux2 = b & ~(1 << d);
        aux3 = a >> 1;
        aux4 = b >> 1;
        int a1, a2, a3, a4;
        a1 = aux1 & ~aux2 & ~aux3 & ~aux4;
        a2 = ~aux1 & aux2 & ~aux3 & ~aux4;
        a3 = ~aux1 & ~aux2 & aux3 & ~aux4;
        a4 = ~aux1 & ~aux2 & ~aux3 & aux4;
        return a1 | a2 | a3 | a4;
    }

    public static HashMap<Integer, HashMap<Integer, HashSet<Integer>>> build_map(int cols, ArrayList<Integer> nums) {
        HashMap<Integer, HashMap<Integer, HashSet<Integer>>> map = new HashMap<>();
        HashSet<Integer> set = new HashSet<>(nums);
        int aux;
        int nr = 1 << (cols + 1);
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nr; j++) {
                aux = generate(i, j, cols);
                if(set.contains(aux)) {
                    if (map.containsKey(aux)) {
                        HashMap<Integer, HashSet<Integer>> aux_m = map.get(aux);
                        HashSet<Integer> aux_s;
                        if (aux_m.containsKey(i)) {
                            aux_s = aux_m.get(i);
                        } else {
                            aux_s = new HashSet<>();
                        }
                        aux_s.add(j);
                        aux_m.put(i, aux_s);
                        map.put(aux, aux_m);
                    } else {
                        HashMap<Integer, HashSet<Integer>> m = new HashMap<>();
                        HashSet<Integer> s = new HashSet<>();
                        s.add(j);
                        m.put(i, s);
                        map.put(aux, m);
                    }
                }
            }
        }
        return map;
    }

    public static int solution(boolean[][] g) {
        int size_h = g.length, size_v = g[0].length;
        boolean[][] A = new boolean[size_v][size_h];
        for (int i = 0; i < size_h; i++) {
            for (int j = 0; j < size_v; j++) {
                A[j][i] = g[i][j];
            }
        }
        size_h = A.length;
        size_v = A[0].length;
        ArrayList<Integer> n = transform_numbers(A);
        HashMap<Integer, HashMap<Integer, HashSet<Integer>>> map = build_map(size_v, n);
        HashMap<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i < 1 << (size_v + 1); i++) {
            m.put(i, 1);
        }
        for (Integer r : n) {
            HashMap<Integer, Integer> d = new HashMap<>();
            for (Map.Entry<Integer, Integer> entry : m.entrySet()) {
                HashSet<Integer> set = map.get(r).get(entry.getKey());
                if (set != null) {
                    for (Integer a : set) {
                        if (d.containsKey(a)) {
                            d.put(a, d.get(a) + entry.getValue());
                        } else {
                            d.put(a, entry.getValue());
                        }
                    }
                }
            }
            m = d;
        }
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : m.entrySet()) {
            sum += entry.getValue();
        }
        return sum;
    }

    public static ArrayList<Integer> transform_numbers(boolean[][] A) {
        ArrayList<Integer> list = new ArrayList<>();
        int sum;
        for (int i = 0; i < A.length; i++) {
            sum = 0;
            for (int j = A[i].length - 1; j >= 0; j--) {
                if (A[i][j]) {
                    sum += Math.pow(2, A[i].length - 1 - j);
                }
            }
            list.add(sum);
        }
        return list;
    }

//    public static void main(String[] args) {
//        boolean[][] g = {{true, false, true},
//                        {false, true, true},
//                        {true, false, true}};
//        System.out.println(solution(g));
//    }
}