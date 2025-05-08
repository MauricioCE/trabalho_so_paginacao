package br.paginacao.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {
    /**
     * Splits a string into a list of elements using space as the delimiter
     * 
     * @param text String to split
     */
    public static ArrayList<String> splitStringSequence(String text) {
        return new ArrayList<>(List.of(text.split(" ")));
    }

    /**
     * Verifies if a string represents a valid integer
     * 
     * @param text String to validate
     */
    public static boolean isValidInteger(String text) {
        if (text == null || text.isEmpty())
            return false;

        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String generateRamdonLetters(int count) {
        StringBuilder resultado = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            char letra = (char) ('A' + random.nextInt(26));
            resultado.append(letra).append(" ");
        }

        return resultado.toString();
    }

    public static String generateRamdonNumbers(int count, int maxValue) {
        StringBuilder resultado = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            int numero = random.nextInt(maxValue) + 1;
            resultado.append(numero);
            if (i < count - 1) {
                resultado.append(" ");
            }
        }

        return resultado.toString();
    }

    public static int ramdonInt(int max) {
        Random random = new Random();
        return random.nextInt(max) + 1;
    }

    public static void createTxtWithLetters(int count) {
        List<Character> listaDeLetras = new ArrayList<>();
        Random random = new Random();
        String nomeArquivo = "/letters/letras.txt";

        try (FileWriter escritor = new FileWriter(nomeArquivo)) {
            System.out.println("Gerando letras e escrevendo no arquivo: " + nomeArquivo);

            for (int i = 0; i < count; i++) {
                int indice = random.nextInt(26);
                char letra = (char) ('A' + indice);
                listaDeLetras.add(letra);
                escritor.write(letra);
                escritor.write(" ");
            }
            System.out.println("Arquivo '" + nomeArquivo + "' criado com sucesso.");

        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

}
