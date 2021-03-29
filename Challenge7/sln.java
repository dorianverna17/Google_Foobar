package Challenge7;
//import java.util;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

/*
* public class Solution {
    public static int solution(int[] dimensions, int[] your_position, int[] trainer_position, int distance) {
        //Your code here
    }
}
* */
class Point{
    public int x, y;
    public int type;
    public Point(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
    public Double distance(Point point) {
        return Math.sqrt((x - point.x) * (x - point.x) + (y - point.y) * (y - point.y));
    }
    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
public class sln {
    public static Point shooter;
    public static HashSet<Double> angles = new HashSet<>();
    public static ArrayList<Point> copies = new ArrayList<>();

    public static int solution(int[] dimensions, int[] your_position, int[] trainer_position, int distance) {
        shooter = new Point(your_position[0], your_position[1], 1);

        int sizeCopiesOnOX = (int) Math.ceil((float)(your_position[0] + distance) / dimensions[0]);
        int sizeCopiesOnOY = (int) Math.ceil((float) (your_position[1] + distance) / dimensions[1]);
//        System.out.print(sizeCopiesOnOX + " "+ sizeCopiesOnOY);

        int xAdd, xtrainer_positionAdd, yAdd, ytrainer_positionAdd;

        for (int i = 0; i < sizeCopiesOnOX; i++) {
            if (i % 2 == 0) {
                xAdd = your_position[0];
                xtrainer_positionAdd = trainer_position[0];
            } else {
                xAdd = dimensions[0] - your_position[0];
                xtrainer_positionAdd = dimensions[0] - trainer_position[0];
            }
            for (int j = 0; j < sizeCopiesOnOY; j++) {
                if (j % 2 == 0) {
                    yAdd = your_position[1];
                    ytrainer_positionAdd = trainer_position[1];
                } else {
                    yAdd = dimensions[1] - your_position[1];
                    ytrainer_positionAdd = dimensions[1] - trainer_position[1];
                }
                Point pShooter = new Point(dimensions[0] * i + xAdd, dimensions[1] * j + yAdd, 1);
                Point ptrainer_position = new Point(dimensions[0] * i + xtrainer_positionAdd, dimensions[1] * j + ytrainer_positionAdd, 0);

                copies.add(pShooter);
                copies.add(new Point(-pShooter.x, pShooter.y, 1));
                copies.add(new Point(pShooter.x, -pShooter.y,1));
                copies.add(new Point(-pShooter.x, -pShooter.y,1));

                copies.add(ptrainer_position);
                copies.add(new Point(-ptrainer_position.x, ptrainer_position.y, 0));
                copies.add(new Point(ptrainer_position.x, -ptrainer_position.y,0));
                copies.add(new Point(-ptrainer_position.x, -ptrainer_position.y,0));
            }
        }
        copies.remove(0);
        copies.sort(Comparator.comparing(o -> (o.distance(shooter))));

        //System.out.println(copies);
        int count = 0;
        System.out.println(copies.size());
        for (Point p : copies)
            if (p.distance(shooter) <= distance) {
                if (angles.add(Math.atan2(p.y - shooter.y, p.x - shooter.x)))
                    if (p.type == 0) {
                        count++;
                    }
            }
//        System.out.print(angles);
//        System.out.println("raspunsul este: " + count);
        return count;
    }
    public static void main(String[] args) {
        int[] dimensions = {300, 275};
        int[] your_position = {150, 150};

        dimensions = new int[] {3, 2};
        your_position = new int[] {1, 1};
        int[] trainer_position = new int[] {2, 1};
        int distance = 4;
//        System.out.println(solution(dimensions, your_position, trainer_position, distance));
//        System.out.println(solution(new int[]{300, 275}, new int[]{150, 150}, new int[]{185, 100}, 500));

        // Test 5
        dimensions = new int[] {10, 10};
        your_position = new int[] {4, 4};
        trainer_position = new int[] {3, 3};
        distance = 5000;

        int nr = solution(dimensions, your_position, trainer_position, distance);
        if (nr == 739323)
            System.out.println("Test 5 passed");
        else System.out.println(nr);
    }
}
