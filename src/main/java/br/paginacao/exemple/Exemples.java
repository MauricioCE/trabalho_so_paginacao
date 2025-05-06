package br.paginacao.exemple;

import br.paginacao.common.SimulationArgs;

public class Exemples {

        public static SimulationArgs test01 = new SimulationArgs(
                        5,
                        50,
                        10,
                        3,
                        "A|B|C|D|E|F|G|H|I|J",
                        "F|I|J|G|I|B|C|F|C|B|E|A|G|E|C|C|G|I|F|E|D|G|I|A|B|I|A|H|B|E|B|H|J|C|F|G|G|E|G|D|J|J|C|I|E|H|B|B|C|I",
                        "L|E|L|E|E|L|E|E|E|L|E|L|L|L|E|L|E|L|L|E|L|L|E|E|L|L|L|L|L|L|E|E|E|E|L|E|E|E|E|E|L|L|E|L|E|L|E|E|E|L",
                        "0|0|0|0|0");

        public static SimulationArgs test02 = new SimulationArgs(
                        5,
                        50,
                        10,
                        5,
                        "A|B|C|D|E|F|G|H|I|J",
                        "G|D|G|C|G|G|C|C|G|I|C|G|A|A|I|J|C|E|A|C|I|A|E|H|G|I|E|J|B|A|F|H|B|D|E|D|E|I|I|A|A|I|A|B|J|A|E|E|J|B",
                        "E|L|L|E|L|E|L|L|L|L|E|E|L|L|E|E|E|L|L|E|E|E|L|E|E|E|L|L|E|E|L|E|L|E|E|E|L|E|E|L|L|L|E|E|E|E|E|E|E|L",
                        "G|D|0|0|A");

        public static SimulationArgs test03 = new SimulationArgs(
                        5,
                        50,
                        10,
                        10,
                        "A|B|C|D|E|F|G|H|I|J",
                        "J|G|G|D|G|G|J|H|F|D|H|F|F|G|C|A|I|H|D|E|A|E|E|G|B|I|B|J|I|F|A|J|E|J|G|E|G|F|D|I|I|H|A|E|G|H|G|H|E|A",
                        "L|E|E|L|L|L|E|E|E|E|L|L|E|L|E|L|E|L|L|E|L|E|L|E|E|L|L|L|L|L|L|L|L|L|L|L|E|E|E|E|E|L|E|E|L|L|E|E|E|L",
                        "F|E|I|D|A");

        public static SimulationArgs test04 = new SimulationArgs(
                        2,
                        50,
                        10,
                        10,
                        "A|B|C|D|E|F|G|H|I|J",
                        "J|G|G|D|G|G|J|H|F|D|H|F|F|G|C|A|I|H|D|E|A|E|E|G|B|I|B|J|I|F|A|J|E|J|G|E|G|F|D|I|I|H|A|E|G|H|G|H|E|A|A|J|E|J|G|E|G|F|D|I|I|H|A|E|G|H|G|H|E|A|J|G|G|D|G|G|J|H|F|D|H|F|F|G|C|A|I|H|D|E|A|E|E|G|B|I|B|J|I|F",
                        "L|E|E|L|L|L|E|E|E|E|L|L|E|L|E|L|E|L|L|E|L|E|L|E|E|L|L|L|L|L|L|L|L|L|L|L|E|E|E|E|E|L|E|E|L|L|E|E|E|L|L|L|L|L|L|L|L|E|E|E|E|E|L|E|E|L|L|E|E|E|L|L|E|E|L|L|L|E|E|E|E|L|L|E|L|E|L|E|L|L|E|L|E|L|E|E|L|L|L|L",
                        "F|E");

}
