package br.paginacao.utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static ArrayList<String> splitStringSequence(String text) {
        return new ArrayList<>(List.of(text.split("\\||-")));
    }
}
