package Challenge7;

import java.util.ArrayList;

public class Sol {
    static ArrayList<int[]> list;
    static ArrayList<int[]> aux;
    static ArrayList<int[]> list2;
    static ArrayList<int[]> list_safety;

    public static boolean listContains(ArrayList<int[]> list, int[] point) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i)[0] == point[0] && list.get(i)[1] == point[1]) {
                return true;
            }
        }
        return false;
    }

    public static int solution(int[] dimensions, int[] your_position, int[] trainer_position, int distance) {
        list = new ArrayList<int[]>();
        aux = new ArrayList<int[]>();
        list2 = new ArrayList<int[]>() {
            @Override
            public boolean contains(Object o) {
                if (o instanceof int[]) {
                    int[] arr = (int[]) o;
                    for (int[] ints : this) {
                        if (arr[0] == ints[0] &&
                                arr[1] == ints[1])
                            return true;
                    }
                }
                return false;
            }
        };
        list_safety = new ArrayList<>();
        calculate(dimensions, your_position, trainer_position, distance);
        return checkDirection(your_position, trainer_position, dimensions);
    }

    public static void printPoint(int[] point) {
        System.out.print("[" + point[0] + " " + point[1] + "] ");
    }

    public static boolean checkPoints(int[] point1, int[] point3, int[] your_position) {
        double dist1 = computeDistance(point1, your_position);
        double dist2 = computeDistance(point1, point3);
        double dist3 = computeDistance(your_position, point3);
        double max = Math.max(dist2, dist1);
        max = Math.max(max, dist3);
        if (max == dist3) {
            if (Math.abs(max - dist1 - dist2) < 0.01) {
                return true;
            }
        }
        if (max == dist2) {
            if (Math.abs(max - dist1 - dist3) < 0.01) {
                return false;
            }
        }
        if (max == dist1) {
            if (Math.abs(max - dist3 - dist2) < 0.01) {
                return true;
            }
        }
        return false;
    }

    public static boolean onTheSameLine(int[] point1, int[] point3, int[] your_position) {
        double dist1 = computeDistance(point1, your_position);
        double dist2 = computeDistance(point1, point3);
        double dist3 = computeDistance(your_position, point3);
        double max = Math.max(dist2, dist1);
        max = Math.max(max, dist3);
        int ok = 0;
        if (Math.abs(max - dist3) < 0.001) {
            if (Math.abs(max - dist1 - dist2) < 0.001)
                ok = 1;
        }
        if (Math.abs(max - dist2) < 0.001) {
            if (Math.abs(max - dist1 - dist3) < 0.001)
                ok = 1;
        }
        if (Math.abs(max - dist1) < 0.001) {
            if (Math.abs(max - dist3 - dist2) < 0.001)
                ok = 1;
        }
        if (ok == 1)
            return true;
        return false;
    }

    public static boolean checkCorner(int[] point, int[] your_position, int[] dimensions, int[] trainer_position) {
        // first corner
        int[] first = new int[] {dimensions[0], dimensions[1]};
        if (onTheSameLine(point, your_position, first))
            if (point[0] >= your_position[0] && point[1] >= your_position[1] &&
                    !onTheSameLine(your_position, trainer_position, point))
                return true;
        // second corner
        int[] second = new int[] {dimensions[0], 0};
        if (onTheSameLine(point, your_position, second))
            if (point[0] >= your_position[0] && point[1] <= your_position[1] &&
                    !onTheSameLine(your_position, trainer_position, point))
                return true;
        // third corner
        int[] third = new int[] {0, 0};
        if (onTheSameLine(point, your_position, third))
            if (point[0] <= your_position[0] && point[1] <= your_position[1] &&
                    !onTheSameLine(your_position, trainer_position, point))
                return true;
        // fourth corner
        int[] fourth = new int[] {0, dimensions[1]};
        if (onTheSameLine(point, your_position, fourth))
            if (point[0] <= your_position[0] && point[1] >= your_position[1] &&
                    !onTheSameLine(your_position, trainer_position, point))
                return true;
        return false;
    }

    public static int checkDirection(int[] your_position, int[] trainer_position, int[] dimensions) {
        ArrayList<int[]> aux_list = new ArrayList<int[]>(aux) {
            @Override
            public boolean contains(Object o) {
                if (o instanceof int[]) {
                    int[] arr = (int[]) o;
                    for (int[] ints : this) {
                        if (arr[0] == ints[0] &&
                                arr[1] == ints[1])
                            return true;
                    }
                }
                return false;
            }
        };
        for (int i = 0; i < aux.size(); i++) {
            for (int j = i + 1; j < aux.size(); j++) {
                if (checkPoints(aux.get(i), aux.get(j), your_position)) {
                    aux_list.remove(aux.get(j));
                }
            }
            if (checkCorner(aux.get(i), your_position, dimensions, trainer_position))
                aux_list.remove(aux.get(i));
        }
        ArrayList<int[]> auxiliary = new ArrayList<>();
        for (int i = 1; i < list_safety.size(); i++) {
            for (int j = 1; j < aux_list.size(); j++) {
                if (checkPoints(list_safety.get(i), aux_list.get(j), your_position))
                    if (!auxiliary.contains(aux_list.get(j)) &&
                            computeDistance(list_safety.get(i), your_position) <=
                                    computeDistance(aux_list.get(j), your_position)) {
                        auxiliary.add(aux_list.get(j));
                    }
            }
        }
        for (int i = 0; i < auxiliary.size(); i++) {
            aux_list.remove(auxiliary.get(i));
        }
        for (int i = 0; i < aux_list.size(); i++) {
            printPoint(aux_list.get(i));
        }
        return aux_list.size();
    }

    private static double computeDistance(int[] your_position, int[] image) {
        return Math.sqrt(Math.pow(image[0] - your_position[0], 2) +
                Math.pow(image[1] - your_position[1], 2));
    }

    private static void calculate(int[] dimensions, int[] your_position, int[] trainer_position, int distance) {
        int[] north, south, west, east;
        ArrayList<int[]> queue = new ArrayList<>();
        int[] image;
        int dist;
        double auxiliary_distance;
        queue.add(trainer_position);
        list.add(trainer_position);
        while (!queue.isEmpty()) {
            north = new int[2];
            south = new int[2];
            west = new int[2];
            east = new int[2];
            image = queue.get(queue.size() - 1);
            queue.remove(queue.size() - 1);
            aux.add(image);
            // relative to the north wall
            dist = dimensions[1] - image[1];
            north[0] = image[0];
            north[1] = image[1] + 2 * dist;
            auxiliary_distance = computeDistance(your_position, north);
            if (!listContains(list, north) && (auxiliary_distance <= distance && auxiliary_distance > 0.01)) {
                queue.add(0, north);
                list.add(north);
            }
            // relative to the south wall
            dist = image[1];
            south[0] = image[0];
            south[1] = image[1] - 2 * dist;
            auxiliary_distance = computeDistance(your_position, south);
            if (!listContains(list, south) && (auxiliary_distance <= distance && auxiliary_distance > 0.01)) {
                queue.add(0, south);
                list.add(south);
            }
            // relative to the left wall
            dist = image[0];
            west[0] = image[0] - 2 * dist;
            west[1] = image[1];
            auxiliary_distance = computeDistance(your_position, west);
            if (!listContains(list, west) && (auxiliary_distance <= distance && auxiliary_distance > 0.01)) {
                queue.add(0, west);
                list.add(west);
            }
            // relative to the right wall
            dist = dimensions[0] - image[0];
            east[0] = image[0] + 2 * dist;
            east[1] = image[1];
            auxiliary_distance = computeDistance(your_position, east);
            if (!listContains(list, east) && (auxiliary_distance <= distance && auxiliary_distance > 0.01)) {
                queue.add(0, east);
                list.add(east);
            }
        }
        queue.add(your_position);
        while (!queue.isEmpty()) {
            north = new int[2];
            south = new int[2];
            west = new int[2];
            east = new int[2];
            image = queue.get(0);
            queue.remove(0);
            if (list2.contains(image))
                continue;
            else
                list2.add(image);
            if (image[0] != your_position[0] || image[1] != your_position[1]) {
                auxiliary_distance = computeDistance(your_position, image);
                if (auxiliary_distance > distance || auxiliary_distance < 0.01)
                    continue;
            }
            list_safety.add(image);
            // relative to the north wall
            dist = dimensions[1] - image[1];
            north[0] = image[0];
            north[1] = image[1] + 2 * dist;
            queue.add(north);
            // relative to the south wall
            dist = image[1];
            south[0] = image[0];
            south[1] = image[1] - 2 * dist;
            queue.add(south);
            // relative to the left wall
            dist = image[0];
            west[0] = image[0] - 2 * dist;
            west[1] = image[1];
            queue.add(west);
            // relative to the right wall
            dist = dimensions[0] - image[0];
            east[0] = image[0] + 2 * dist;
            east[1] = image[1];
            queue.add(east);
        }
    }

    public static void main(String[] args) {
//        int[] dimensions = new int[] {3, 2};
//        int[] your_position = new int[] {1, 1};
//        int[] trainer_position = new int[] {2, 1};
//        int distance = 1000;
//        int[] dimensions = new int[] {23, 10};
//        int[] your_position = new int[] {6, 4};
//        int[] trainer_position = new int[] {3, 2};
//        int distance = 23;
        int[] dimensions = new int[] {2, 5};
        int[] your_position = new int[] {1, 2};
        int[] trainer_position = new int[] {1, 4};
        int distance = 11;
//        int[] dimensions = new int[] {300, 275};
//        int[] your_position = new int[] {150, 150};
//        int[] trainer_position = new int[] {185, 100};
//        int distance = 500;
//        int[] dimensions = new int[] {10, 10};
//        int[] your_position = new int[] {4, 4};
//        int[] trainer_position = new int[] {3, 3};
//        int distance = 5000;
        System.out.println(solution(dimensions, your_position, trainer_position, distance));
    }
}
