public class Main {
  public static void main(String[] args) {
    long seed = System.currentTimeMillis();
    System.out.println("Seed_Value " + seed);
    long seedFor1 = 1714218475987L; // 1714218475987 1714297321485
    long seedFor2 = 1714220140932L; // 1714220140932
    long seedFor3 = 1714220838621L; // 1714220838621
    long seedFor4 = 1714221029026L; // 1714221029026
    long seedFor5 = 1714221945838L; // 1714221029026
    long seedFor6 = 1714222071696L; // 1714221029026
    long seedFor7 = 1714223490674L; // 1714223549079
    long seedFor8 = 1714222361507L; 
    long seedFor9 = 1714223359424L; // 1714223442639
    long seedFor10 = 1714307593175L;
    long seedFor11 = 1714223134451L;

    GAV2 gav2 = new GAV2(0.3, 0.9, seedFor1, 50, 269, 100);
    GAV2 gav2_1 = new GAV2(0.3, 0.9, seedFor2, 50, 878, 50);
    GAV2 gav2_2 = new GAV2(0.3, 0.9, seedFor3, 10, 20, 100);
    GAV2 gav2_3 = new GAV2(0.3, 0.9, seedFor4, 10, 11, 100);
    GAV2 gav2_4 = new GAV2(0.3, 0.9, seedFor5, 50, 375, 100);
    GAV2 gav2_5 = new GAV2(0.3, 0.9, seedFor6, 50, 60, 100);
    GAV2 gav2_6 = new GAV2(0.3, 0.9, seedFor7, 20, 50, 25);
    GAV2 gav2_7 = new GAV2(0.3, 0.9, seedFor8, 50, 10000, 100);
    GAV2 gav2_8 = new GAV2(0.3, 0.9, seedFor9, 5, 80, 100);
    GAV2 gav2_9 = new GAV2(0.3, 0.9, seedFor10, 50, 879, 100);
    GAV2 gav2_10 = new GAV2(0.3, 0.9, seedFor11, 50, 1000, 100);
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
