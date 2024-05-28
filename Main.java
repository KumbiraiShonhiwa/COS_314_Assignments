public class Main {
    public static void main(String[] args) {
        // Long seed = System.currentTimeMillis();
        System.out.println("Seed: " + 1716635728436L); // 1716635728436
        GP geneticProgramming = new GP(1716635728436L); //1716635728436
        geneticProgramming.run();
    }
}