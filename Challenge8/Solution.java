package Challenge8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public static int solution(int[] banana_list) {
        // Your code here
        ArrayList<ArrayList<Integer>> matrix = createAdjancecyLists(banana_list);
        return banana_list.length - maxMatching(matrix);
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

    static int lca(int[] match, int[] base, int[] p, int a, int b) {
        boolean[] used = new boolean[match.length];
        while (true) {
            a = base[a];
            used[a] = true;
            if (match[a] == -1) break;
            a = p[match[a]];
        }
        while (true) {
            b = base[b];
            if (used[b]) return b;
            b = p[match[b]];
        }
    }

    static void markPath(int[] match, int[] base, boolean[] blossom, int[] p, int v, int b, int children) {
        for (; base[v] != b; v = p[match[v]]) {
            blossom[base[v]] = blossom[base[match[v]]] = true;
            p[v] = children;
            children = match[v];
        }
    }

    static int findPath(ArrayList<ArrayList<Integer>> graph, int[] match, int[] p, int root) {
        int n = graph.size();
        boolean[] used = new boolean[n];
        Arrays.fill(p, -1);
        int[] base = new int[n];
        for (int i = 0; i < n; ++i)
            base[i] = i;

        used[root] = true;
        int qh = 0;
        int qt = 0;
        int[] q = new int[n];
        q[qt++] = root;
        while (qh < qt) {
            int v = q[qh++];

            for (int to : graph.get(v)) {
                if (base[v] == base[to] || match[v] == to) continue;
                if (to == root || match[to] != -1 && p[match[to]] != -1) {
                    int curbase = lca(match, base, p, v, to);
                    boolean[] blossom = new boolean[n];
                    markPath(match, base, blossom, p, v, curbase, to);
                    markPath(match, base, blossom, p, to, curbase, v);
                    for (int i = 0; i < n; ++i)
                        if (blossom[base[i]]) {
                            base[i] = curbase;
                            if (!used[i]) {
                                used[i] = true;
                                q[qt++] = i;
                            }
                        }
                } else if (p[to] == -1) {
                    p[to] = v;
                    if (match[to] == -1)
                        return to;
                    to = match[to];
                    used[to] = true;
                    q[qt++] = to;
                }
            }
        }
        return -1;
    }

    public static int maxMatching(ArrayList<ArrayList<Integer>> matrix) {
        int n = matrix.size(), pv, ppv;
        int[] match = new int[n];
        Arrays.fill(match, -1);
        int[] p = new int[n];
        for (int i = 0; i < n; ++i) {
            if (match[i] == -1) {
                int v = findPath(matrix, match, p, i);
                while (v != -1) {
                    pv = p[v];
                    ppv = match[pv];
                    match[v] = pv;
                    match[pv] = v;
                    v = ppv;
                }
            }
        }

        int matches = 0;
        for (int i = 0; i < n; ++i)
            if (match[i] != -1)
                ++matches;
        return matches;
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
}
