package edu.dcc.acerda;

/**
 * @author Andrés Cerda
 * Interface for a generator lambda, used for generating a population for the GeneticAlgorithm
 * @see GeneticAlgorithm
 */
@FunctionalInterface
public interface GeneratorFunc {
    /**
     * Lambda Function to generate a population
     * @param numberOfGenes the number of genes of each individual
     * @param populationSize the size of the population
     * @return population, an int[][]
     * @see GeneticAlgorithm
     */
    int[][] generate(int numberOfGenes, int populationSize);
}
