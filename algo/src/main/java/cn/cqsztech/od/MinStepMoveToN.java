package cn.cqsztech.od;

import java.util.Vector;

/**
 * 移动到位置n需要的最小的步数
 * 只能左移或者右移2或者3步
 */
public class MinStepMoveToN {
    public static void main(String[] args) {
        System.out.println(minStep(10));

    }

    public static int minStep(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 2;
        }
        Vector<Integer> dp = new Vector<>(n + 1);
        for (int i = 0; i <= n; i++) {
            dp.add(Integer.MAX_VALUE);
        }
        dp.set(0, 0);
        for (int i = 2; i <= n; i++) {
            if (i >= 2 && dp.get(i - 2) != Integer.MAX_VALUE) {
                dp.set(i, Math.min(dp.get(i), dp.get(i - 2) + 1));
            }
            if (i >= 3 && dp.get(i - 3)!= Integer.MAX_VALUE)
                dp.set(i, Math.min(dp.get(i), dp.get(i - 3) + 1));
        }

        return dp.get(n);
    }
}
