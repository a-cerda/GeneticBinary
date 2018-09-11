package edu.dcc.acerda;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Andrés Cerda
 * Main class for instantiating the GeneticAlgorithm , has the targetNumber to reach
 * as well as the generator function and the fitness function for the algorithm.
 */
public class Main {

    public static void main(String[] args) {
        //This is the target number to reach, change it for a different target.
        int[] targetNumber = {1,1,1,0,0,0,1,0,1,0,1,0,0,0,1,1,1,1,0,1,1,1,0,0,0,1,1};

        /*
         * Fitness function, it returns the number of digits matching with the target for a given individual.
         * @see FitnessFunc
         */
        FitnessFunc fitnessFunction = (int[] individual) -> {
            int counter = 0;
            for (int i = 0; i < targetNumber.length; i++) {
                if(individual[i] == targetNumber[i]){
                    counter++;
                }
            }
            return counter;
        };
        /*
         * Generator Function, it generates a random population given the number of genes of each individual and a population size.
         * @see GeneratorFunc
         */
        GeneratorFunc generatorFunction = (int numberOfGenes, int populationSize) -> {
            //We instantiate a new random object in order to make the population random
            Random random = new Random();
            int[][] population = new int[populationSize][numberOfGenes];
            for (int i = 0; i < populationSize; i++) {
                for (int j = 0; j < numberOfGenes; j++) {
                    //each initial individual consists of random digits between 0 and 1
                    population[i][j] = random.nextInt(2);
                }
            }
            return population;
        };

        /*Instantiation of the Genetic Algorithm, change fields here to change the population size, mutation probability, and maximum number of iterations as seen on
          *@see GeneticAlgorithm
         */
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(1000,0.01,targetNumber.length,fitnessFunction,generatorFunction,100);
        //We save the result to display it later
        int[] result = geneticAlgorithm.run();
        System.out.println("The best solution found was: "+ Arrays.toString(result));

    }
}
