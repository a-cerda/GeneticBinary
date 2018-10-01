# Genetic Algorithm

### Description

--- 
This algorithm is what is called a *Genetic Algorithm*, it's purpose is to *"guess"* which number we have 
inputted via an evolutionary process based on darwinian selection.

It does so in several steps:  
1. Initialize population through a generator method.
2. Calculate the fitness of said population through a fitness method
3. Select the fittest individuals using the accumulation heuristic to breed a new individual
4. Perform a crossover and calculate gene mutation for every new individual
5. Repeat X number of times, or until the result has been reached

### Implementation

The implementation consists of 2 classes and 2 interfaces:  
1. The `Main` class, which is responsible for giving the target, as well as declaring the lambda methods for both
generating a population as well as the fitness calculator. This class also instantiates the next class 
to make the algorithm run
2. The `GeneticAlgorithm` class which handles the implementation of the genetic algorithm as described above.

Both classes are documented thoroughly

Both of the interfaces are `@FunctionalInterface` in order to be able to pass the methods required as an 
argument to the constructor of the `GeneticAlgorithm` class.
1. The `FitnessFunc` interface, defines that a fitness function must calculate a fitness when given
an individual as an array of integers, and the implementation gives the number of 
matched numbers between it and the `targetNumber`.
2. The `GeneratorFunc` interface, defines that a generator should give back an array of type 
`int[][]` representing the population, and it should be of size `populationSize` and with each individual (row)
having `numberOfGenes` genes (columns).





### Usage

___

To change the target number to reach by the algorithm, just change the variable `int[] targetNumber`  on the `main` 
function of the class `Main`

The parameters for the algorithm can also be changed, here's what each one does:
1. ` populationSize` controls the size of the population for each iteration
2. `mutationProb`  controls the probability of a gene mutation ocurring, value between 0.0 and 1.0 (recommended range 0.01 to 0.05)
3. `maxNumberOfIterations` Controls the maximum allowed number of iterations for the algorithm, once this limit is reached, the algorithm stops, even if it hasn't found the solution

          
          
         

**Author:** Andrés Cerda P.  
**Github:** [a-cerda](https://github.com/a-cerda)
