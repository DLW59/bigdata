package com.dlw.bigdata.algorithm.GA;

/**
 * author dlw
 * date 2018/10/2.
 *
 * Individual类代表一个候选解，主要负责存储和操作一条染色体。
 * 请注意，Individual类也有两个构造方法。一个构造方法接受一个整数（代表染色体的长度），
 * 在初始化对象时将创建一条随机的染色体。另一个构造方法接受一个整数数组，用它作为染色体。
 *
 */
public class Individual {
    /**
     * 染色体
     */
    private int[] chromosome;
    /**
     * 适应度
     */
    private double fitness = -1;

    public Individual(int[] chromosome) {
        this.chromosome = chromosome;
    }

    public Individual(int chromosomeLength) {
        this.chromosome = new int[chromosomeLength];
        for (int gene = 0; gene < chromosomeLength; gene++) {
            if (0.5 < Math.random()) {
                this.setGene(gene, 1);
            } else {
                this.setGene(gene, 0);
            }
        }
    }
    public int[] getChromosome() {
        return this.chromosome;
    }
    public int getChromosomeLength() {
        return this.chromosome.length;
    }
    public void setGene(int offset, int gene) {
        this.chromosome[offset] = gene;
    }
    public int getGene(int offset) {
        return this.chromosome[offset];
    }
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    public double getFitness() {
        return this.fitness;
    }
    public String toString() {
        String output = "";
        for (int gene = 0; gene < this.chromosome.length; gene++) {
            output += this.chromosome[gene];
        }
        return output;
    }
}
