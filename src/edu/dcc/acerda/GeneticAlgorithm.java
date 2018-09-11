package edu.dcc.acerda;



import java.util.*;

/**
 * @author Andrés Cerda
 * Class for calculating a target number through a genetic algorithm
 * the number is represented via an array of integers with each element being either 0 or 1
 * thus representing a binary number. the number is unknown to the algorithm, and is assigned in the main function
 * @see Main
 */
public class GeneticAlgorithm {


    private int populationSize;
    private double mutationProbability;
    private int numberOfGenes;
    private FitnessFunc fitnessFunction;
    private int maxNumberOfIterations;
    private GeneratorFunc generatorFunction;
    private Random random = new Random();
    private int[][] population;
    private Individual[] individuals;
    private int[] regularFitness;
    private double[] accFitness;


    /**
     * Constructor for the GeneticAlgorithm class, defines what resources are available and parameters.
     * @param populationSize the size of the population
     * @param mutationProb the probability of a gene mutation ocurring
     * @param numberOfGenes the number of genes of an individual (in this case the length of the array representing the number)
     * @param fitnessFunction the function (lambda) used to evaluate an individual's fitness
     * @param generatorFunction the function (lambda) used to generate the population
     * @param maxNumberOfIterations the maximum allowed number of iterations for the algorithm
     */
    public GeneticAlgorithm(int populationSize, double mutationProb, int numberOfGenes, FitnessFunc fitnessFunction, GeneratorFunc generatorFunction, int maxNumberOfIterations){
        this.populationSize = populationSize;
        this.mutationProbability = mutationProb;
        this.numberOfGenes = numberOfGenes;
        this.fitnessFunction = fitnessFunction;
        this.generatorFunction = generatorFunction;
        this.maxNumberOfIterations = maxNumberOfIterations;
        this.individuals = new Individual[populationSize];
        this.regularFitness = new int[populationSize];
        this.accFitness = new double[populationSize];

    }


    /**
     * Method for generating the population, calls the generatorFunction "generate" method"
     * @see GeneratorFunc
     */
    public void generate(){
        this.population = generatorFunction.generate(numberOfGenes,populationSize);
    }

    /**
     * Method for calculating the fitness of each individual in the population
     * calls on each individual the fitnessFunction
     * @see FitnessFunc
     */
    public void calculateFitness(){
        int i = 0;
        for (int[] individual: this.population) {
            this.regularFitness[i] = fitnessFunction.fitnessCalculator(individual);
            i++;
        }
    }

    /**
     * Method for normalizing the fitnesses and sort the array in a highest to lowest manner.
     * makes new individuals for the individuals array
     */
    public void normalizeAndOrderFitness(){

        int i = 0;//a counter for putting the indexes on the individuals
        double sumOfAllFitness = 0.0; //sum of all the fitness values for normalizing
        sumOfAllFitness = Arrays.stream(this.regularFitness).sum();

        for (double fitness: this.regularFitness) {
            //asigning a new individual to each space on the array of individuals
            this.individuals[i] = new Individual(i,fitness/sumOfAllFitness);
            i++;
        }
        //now we sort the array in reverse order with a custom comparator, that returns the oposite of a normal one.
        Arrays.sort(this.individuals,
                (Individual i1, Individual i2) -> {
                    if (i1.fitness > i2.fitness) {
                        return -1;
                    } else if (i2.fitness > i1.fitness) {
                        return 1;
                    } else {
                        return 0;
                    }
                });
    }


    /**
     * Method to accummulate the fitness in order to leave it prepared for the selection of the fittest individuals
     * using the heuristic given on the homework
     */
    public void accumulateFitness(){

        //We initialize the values of the accumulated fitness array
        for (int i = 0; i < populationSize; i++) {
            accFitness[i] = this.individuals[i].fitness;
        }
        //At last, we accumulate the fitness
        for (int i = 1; i < populationSize; i++) {
            accFitness[i]= accFitness[i-1]+accFitness[i];
        }

    }


    /**
     * Method for selecting the fittesst individual based on the heuristic given in the homework
     * it selects a number at random and then compares it to the accumulated fitness of the individuals,
     * the first individual with a higher accumulated fitness than the random number is chosen and it's index returned.
     * @return the index of the fittest individual
     */
    public int selectFittest(){
        //we calculate a random value
        double n = random.nextDouble();
        //and check if the accumulated fitness of any individual is greater than it.
        if (n < this.accFitness[0]){
                return this.individuals[0].index;
        }
        for (int i = 1; i<accFitness.length; i++){
            if(n < accFitness[i]) {
                return this.individuals[i].index;
            }
        }
        return this.individuals[accFitness.length - 1].index;
    }

    /**
     * Method for producing offspring , it selects two parents, then a crossover point
     * and generates a child that has on the first part the genes of the first parent, and on
     * the second part the genes of the second parent, it also adds a mutation probability to each gene created
     * it is recomended for it to be low.
     * It does this until it fills every individual of the new generation and then that becomes the new population.
     */
    public void reproduce()
    {
        int[][] newPopulation = new int[populationSize][numberOfGenes];
        for (int i = 0; i < populationSize; i++) {
            int[] firstParent = population[selectFittest()];
            int[] secondParent = population[selectFittest()];

            int crossoverPoint = (int)(random.nextDouble()*10)%numberOfGenes;
            int[] child = new int[numberOfGenes];
            for (int j = 0; j < numberOfGenes; j++) {
                if (j<crossoverPoint){
                    child[j] = firstParent[j];
                }
                else {
                    child[j] = secondParent[j];
                }
                if(random.nextDouble()<mutationProbability){
                    child[j] = child[j] == 1 ? 0 : 1;
                }
            }
            newPopulation[i] = child;


        }
        this.population = newPopulation;
    }

    /**
     * Method for actually running the algorithm, it returns the first individual that matches
     * the target number i.e. the first individual with maximum fitness (it also prints the generation that it was found in)
     * if no individual achieves it by the  maximum number of iterations, then it returns the fittest at the time of stopping.
     * @return the fittest individual at the time of stopping
     */
    public int[] run(){
        generate();
        int[] result;
        for (int i = 0; i < this.maxNumberOfIterations; i++) {
            calculateFitness();
            normalizeAndOrderFitness();
            accumulateFitness();
            if(fitnessFunction.fitnessCalculator(population[this.individuals[0].index]) == numberOfGenes){
                System.out.println("The solution has been found on generation "+i+" and it is: "+Arrays.toString(population[this.individuals[0].index]));
                result = population[this.individuals[0].index];
                return result;
            }
            reproduce();
        }
        calculateFitness();
        normalizeAndOrderFitness();
        accumulateFitness();
        result = population[this.individuals[0].index];
        return result;
    }

    /**
     * @author Andrés Cerda
     * Class for representing an individual, it's used to keep track of the indexes between sorting.
     */
    class Individual{
        int index;
        double fitness;

        /**
         * Constructor for the individual
         * @param index the original index in the population
         * @param fitness the normalized fitness of the individual
         */
        public Individual(int index, double fitness) {
            this.index = index;
            this.fitness = fitness;

        }


    }
}

