2013/08
University of Tsukuba
Graduate School of Systems and Information Engineering
Department of Computer Science
Special Lectures in Computational Sciences III - Machine Learning and Evolutionary Algorithms

Assignment: Solve the Unbounded Knapsack Problem using Genetic Algorithms.
Description: A group of friends goes to a restaurant with a budget specified budget. They want their order to be as close as possible to (but within) the budget. In addition, they want their order to be served as fast as possible.

Considerations regarding the proposed solution:
1 - The representation of the genomes is given by a list of objects containing the item name, value, time and quantity.
2 - The population is initialized with a random quantity for each item.
3 - It is performed a "repair" operation on the genomes whose order value is greater than the total budget. The repair is done by decreasing the quantity of random items in the genome.
4 - The population is ordered, and the genomes are evaluated.
5 - The best individuals are inserted in the elite list in order to be directly chosen to the next generation. The selected individuals are also inserted in a histogram, in order to keep track of how many times a given elite genome was chosen. The elitism strategy was chosen to maintain a biased selection towards the best individuals.
6 - The remaining individuals are subjected to crossover using tournament. The tournament selection was chosen to balance the bias introduced by the elitism strategy.
7 - After the crossover, it is performed a mutation on the new population (which includes the elites from the previous generation and the individuals generated in the crossover process. The mutation consists in changing the quantities of items in an order.
8 - The individuals are then repaired to guarantee that the order value is within the budget.
9 - The steps 4~8 are repeated for a specified number of generations or until a elite individual is found a set number of times.
