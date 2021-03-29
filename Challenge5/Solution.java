package Challenge5;

public class Solution {
    public static int solution(int[] l) {
        int nr = 0;
        for (int i = 0; i < l.length - 2; i++) {
            for (int j = i + 1; j < l.length - 1; j++) {
                if (l[j] % l[i] == 0) {
                    for (int k = j + 1; k < l.length; k++) {
                        if (l[k] % l[j] == 0)
                            nr++;
                    }
                }
            }
        }
        return nr;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {1, 2, 3, 4, 5, 6};
        System.out.println(solution(arr));
    }
}
