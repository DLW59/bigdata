package com.dlw.bigdata.algorithm.GA;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * author dlw
 * date 2018/10/2.
 * 遗传算法类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneticAlgorithm {

    /**
     * 种群大小
     */
    private int populationSize;
    /**
     * 变异率
     */
    private double mutationRate;
    /**
     * 交叉率
     */
    private double crossoverRate;
    /**
     * 精英数  （这些染色体不做交叉变异操作直接复制给下一代）
     */
    private int elitismCount;


    /**
     * 初始化种群类
     * @param chromosomeLength
     * @return
     */
    public Population initPopulation(int chromosomeLength) {
        Population population = new Population(this.populationSize, chromosomeLength);
        return population;
    }


    /**
     * 计算染色体的适应度
     * @param individual
     * @return
     */
    public double calcFitness(Individual individual) {

        int correctGenes = 0;

        for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
            if (individual.getGene(geneIndex) == 1) {
                correctGenes += 1;
            }
        }

        double fitness = (double) correctGenes / individual.getChromosomeLength();

        individual.setFitness(fitness);

        return fitness;
    }

    /**
     * 计算种群的适应度
     * @param population
     */
    public void evalPopulation(Population population) {
        double populationFitness = 0;

        for (Individual individual : population.getIndividuals()) {
            populationFitness += calcFitness(individual);
        }

        population.setPopulationFitness(populationFitness);
    }

    /**
     * 是否终止迭代
     * @param population
     * @return
     */
    public boolean isTerminationConditionMet(Population population) {
        for (Individual individual : population.getIndividuals()) {
            if (individual.getFitness() == 0.9) {
                return true;
            }
        }

        return false;
    }

    /**
     * 染色体交叉
     * @param population
     */
    public void crossPopulation(Population population) {
        List<Individual> individuals = Arrays.asList(population.getIndividuals());
        //总适应度
        double totalFitness = individuals.stream().mapToDouble(Individual::getFitness).count();
        individuals.sort(Comparator.comparingDouble(Individual::getFitness));
        Random random = new Random();
        int num = random.nextInt(populationSize + 1);
        List<Individual> crossIndividuals = null;
        for (int i = 0;i<num;i++) {
            crossIndividuals = new ArrayList<>(num);
            Individual individual = individuals.get(i);
            crossIndividuals.add(individual);
        }

        if (CollectionUtils.isEmpty(crossIndividuals) || crossIndividuals.size() == 1) {
            return;
        }

        population.setPopulation((Individual[]) crossIndividuals.toArray());


    }

}
