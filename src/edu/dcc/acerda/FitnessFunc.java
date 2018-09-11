package edu.dcc.acerda;

/**
 * @author Andrés Cerda
 * Interface for a lambda expression containing a fitness function for the
 * @see GeneticAlgorithm to use.
 */
@FunctionalInterface
public interface FitnessFunc {
    /**
     * Function to calculate the fitness of an individual
     * @param individual the individual for whom we will calculate the fitness
     * @return int fitness: the fitness of the individual
     */
    int fitnessCalculator(int[] individual);
}
