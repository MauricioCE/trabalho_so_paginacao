package br.paginacao.test_cases;

import br.paginacao.common.SimulationArgs;
import br.paginacao.utils.Utils;

public class TestCases {

        public static SimulationArgs randomCase(int pageQueueSize) {
                pageQueueSize = Math.abs(pageQueueSize);
                // int memorySize = (int) (pageQueueSize * 0.1);
                // int clockInterruption = (int) (pageQueueSize * 0.01);

                int memorySize = Utils.ramdonInt(1000);
                int clockInterruption = Utils.ramdonInt(100);

                return new SimulationArgs(
                                memorySize,
                                clockInterruption,
                                Utils.generateRamdonNumbers(pageQueueSize, 500),
                                "");
        }

        public static SimulationArgs case01 = new SimulationArgs(
                        5,
                        3,
                        "F I J G I B C F C B E A G E C C G I F E D G I A B I A H B E B H J C F G G E G D J J C I E H B B C I",
                        "0 0 0 0 0");

        public static SimulationArgs case02 = new SimulationArgs(
                        5,
                        5,
                        "G D G C G G C C G I C G A A I J C E A C I A E H G I E J B A F H B D E D E I I A A I A B J A E E J B",

                        "G D 0 0 A");

        public static SimulationArgs case03 = new SimulationArgs(
                        5,
                        10,
                        "J G G D G G J H F D H F F G C A I H D E A E E G B I B J I F A J E J G E G F D I I H A E G H G H E A",
                        "F E I D A");

        public static SimulationArgs case04() {
                return randomCase(1000);
        }

        public static SimulationArgs case05 = randomCase(10000);

        public static SimulationArgs case06 = randomCase(100000);

}
