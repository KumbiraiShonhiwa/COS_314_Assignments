public class Main {
    public static void main(String[] args) {
        long seed = System.currentTimeMillis();
        System.out.println("Seed_Value "+ seed);
        long seedFor1 = 1713993447724L;
        // GA ga = new GA( 0.03, 0.9, knapsack, seed);
        // ga.setGenerations(10);
        // ga.run("C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f1_l-d_kp_10_269");
        GAV2 gav2 = new GAV2(0.03, 0.9, seedFor1, 50, 100,50);
        gav2.run("C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f1_l-d_kp_10_269");
        // gav2.run("C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f2_l-d_kp_20_878");
        // gav2.run("C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f3_l-d_kp_4_20");
        // gav2.run("C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f4_l-d_kp_4_11");
        // gav2.run("C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f5_l-d_kp_15_375");
        // gav2.run("C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f6_l-d_kp_10_60");
        // gav2.run("C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f7_l-d_kp_7_50");
        // gav2.run("C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f8_l-d_kp_23_10000");
        // gav2.run("C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f9_l-d_kp_5_80");
        // gav2.run("C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/f10_l-d_kp_20_879");
        // gav2.run("C:/Users/Kumbirai/OneDrive/Documents/COS 314/Assignments/Assignment 2/Knapsack Instances_Updated(1)/Knapsack Instances_Updated(1)/Knapsack Instances/knapPI_1_100_1000_1");       
    }

}
