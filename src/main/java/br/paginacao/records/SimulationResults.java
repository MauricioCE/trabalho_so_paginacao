package br.paginacao.records;

import java.util.ArrayList;

public record SimulationResults(int faults, long duration, ArrayList<String> steps) {
}
