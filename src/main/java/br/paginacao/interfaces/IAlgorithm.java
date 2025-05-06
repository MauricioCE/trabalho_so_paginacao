package br.paginacao.interfaces;

import br.paginacao.common.SimulationArgs;
import br.paginacao.records.SimulationResults;

public interface IAlgorithm {
    public SimulationResults run(SimulationArgs args);
}
