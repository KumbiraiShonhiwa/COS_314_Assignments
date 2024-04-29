public class MainForGA_LS {
    public static void main(String[] args) {
        long seed = System.currentTimeMillis();
        System.out.println("Seed_Value " + seed);
        long seedFor1 = 1714327778122L; // 1714243429878
        long seedFor2 = 1714327534877L; // 1714243471759
        long seedFor3 = 1714326229992L;
        long seedFor4 = 1714327464514L;
        long seedFor5 = 1714327249413L;
        long seedFor6 = 1714327167465L;
        long seedFor7 = 1714326746755L;
        long seedFor8 = 1714326514503L;
        long seedFor9 = 1714326229992L; // 1714244836365
        long seedFor10 = 1714326229992L;
        long seedFor11 = 1714327978455L;
        GA_LS gav2 = new GA_LS(0.7, 0.9, seedFor1, 10, 269, 50);
        GA_LS gav2_1 = new GA_LS(0.3, 0.9, seedFor2, 50, 878, 50);
        GA_LS gav2_2 = new GA_LS(0.3, 0.9, seedFor3, 10, 20, 100);
        GA_LS gav2_3 = new GA_LS(0.7, 0.9, seedFor4, 10, 11, 5);
        GA_LS gav2_4 = new GA_LS(0.3, 0.9, seedFor5, 50, 375, 10);
        GA_LS gav2_5 = new GA_LS(0.6, 0.96, seedFor6, 20, 60, 10);
        GA_LS gav2_6 = new GA_LS(0.3, 0.9, seedFor7, 20, 50, 10);
        GA_LS gav2_7 = new GA_LS(0.3, 0.9, seedFor8, 50, 10000, 100);
        GA_LS gav2_8 = new GA_LS(0.3, 0.9, seedFor9, 5, 80, 100);
        GA_LS gav2_9 = new GA_LS(0.3, 0.9, seedFor10, 50, 879, 100);
        GA_LS gav2_10 = new GA_LS(0.3, 0.9, seedFor11, 100, 1000, 5);
        gav2.run(
                "C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f1_l-d_kp_10_269");
        gav2_1.run(
                "C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f2_l-d_kp_20_878");
        gav2_2.run(
                "C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f3_l-d_kp_4_20");
        gav2_3.run(
                "C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f4_l-d_kp_4_11");
        gav2_4.run(
                "C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f5_l-d_kp_15_375");
        gav2_5.run(
                "C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f6_l-d_kp_10_60");
        gav2_6.run(
                "C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f7_l-d_kp_7_50");
        gav2_7.run(
                "C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f8_l-d_kp_23_10000");
        gav2_8.run(
                "C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f9_l-d_kp_5_80");
        gav2_9.run(
                "C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f10_l-d_kp_20_879");
        gav2_10.run(
                "C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/knapPI_1_100_1000_1");
    }
}
