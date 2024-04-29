INSTRUCTIONS FOR RUNNING THE GA AND GA LOCAL SEARCH MAINS

GA:
The main for the GA is called Main.java, in this main you find 11 seeding values (named seedFor1 - seedFor11) and one randomly generated seeding value (seed). The 11 eleven seeding values are to be used for each of the GAV2 (Genetic Algorithm Version 2) objects (named gav2 - gav2_10)

The parameters for the objects are (in this order): mutation rate, crossover rate, seed value, population size, maximum weight, number of generations.
Each object will call its run function which needs a passed in parameter which is a string for the file names of the Knapsack instances.

When testing please use absolute paths because during my testing my IDE could not find the text files when using a relative path. You may try to use the relative paths when testing a different outcome may arise. 

There is a text file called output.txt. The purpose of this textfile is to show the out put of the program after each object calls its run function. In this textfile, different knapsack solutions with their weight, value and fitness value will be display. Please note that this output is for the final generation. 

GA with Local Search:
The main for the GA is called MainForGA_LS.java, in this main you find 11 seeding values (named seedFor1 - seedFor11) and one randomly generated seeding value (seed). The 11 eleven seeding values are to be used for each of the GAV2 (Genetic Algorithm Version 2) objects (named gav2 - gav2_10)

The parameters for the objects are (in this order): mutation rate, crossover rate, seed value, population size, maximum weight, number of generations.
Each object will call its run function which needs a passed in parameter which is a string for the file names of the Knapsack instances.

When testing please use absolute paths because during my testing my IDE could not find the text files when using a relative path. You may try to use the relative paths when testing a different outcome may arise. 

There is a text file called output.txt. The purpose of this textfile is to show the out put of the program after each object calls its run function. In this textfile, different knapsack solutions with their weight, value and fitness value will be display. Please note that this output is for the final generation.

Terminal Output: 
Format 
Seed Value
Initial Population
Runtime
Best Solution

Text File Output:
Final Generation

Important Note:
I did bring this up with a tutor during one of the sessions and its that my output for the last generation for some of the Knapsack instances have a single optimal solution that optimal solution being th known optimum. I did find the issue that cause it, resolved it but I've realised that its a random occurance. I believe the first 4 Knapsack instances (with the current seeding values) reach the known optimum however all the knapsack solutions in that population have the same value.
The Runtime will be different from the report.