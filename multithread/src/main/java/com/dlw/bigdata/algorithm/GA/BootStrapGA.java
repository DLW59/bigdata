package com.dlw.bigdata.algorithm.GA;

/**
 * author dlw
 * date 2018/10/2.
 * GA 引导类
 */
public class BootStrapGA {

    public static void main(String[] args) {

        /**
         * 创建一个遗传类实例
         */
        GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.01, 0.95, 0);
        Population population = ga.initPopulation(50);
        ga.evalPopulation(population);
        int generation = 1;

        while (!ga.isTerminationConditionMet(population)) {
            System.out.println("Best solution: " + population.getFittest(0).toString());

            // 进行染色体交叉
            // TODO!
            ga.crossPopulation(population);

            // 变异
            // TODO!

            // Evaluate population
            ga.evalPopulation(population);

            // Increment the current generation
            generation++;
        }

        System.out.println("Found solution in " + generation + "generations");
        System.out.println("Best solution: " + population.getFittest(0).toString());
    }
}
