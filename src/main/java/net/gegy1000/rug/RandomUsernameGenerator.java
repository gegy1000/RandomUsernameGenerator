package net.gegy1000.rug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUsernameGenerator
{
    private static List<String> dictionary = new ArrayList<String>();

    public static void main(String[] args) throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(RandomUsernameGenerator.class.getResourceAsStream("/dictionary.txt")));

        String line;

        while ((line = in.readLine()) != null)
        {
            dictionary.add(line);
        }

        in.close();

        int amount = 5000;
		
        Random rand = new Random();

        for (int i = 0; i < amount; i++)
        {
            System.out.println(generateValidRandomUsername(rand));
        }
    }

    public static String generateValidRandomUsername(Random rand)
    {
        String username;

        while (!isValid(username = generateRandomUsername(rand)));

        return username;
    }

    private static boolean isValid(String word)
    {
        return word != null && !dictionary.contains(word);
    }

    private static boolean isVowel(char c)
    {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    public static String generateRandomUsername(Random random)
    {
        String firstWord = getRandomStartWord(random);

        int lastConsanantIndex;

        for (lastConsanantIndex = firstWord.length() - 1; lastConsanantIndex > 0; lastConsanantIndex--)
        {
            if (!isVowel(firstWord.charAt(lastConsanantIndex)))
            {
                break;
            }
        }

        String secondWordStart = firstWord.substring(lastConsanantIndex);

        String wordStartingWith = getWordStartingWith(secondWordStart, random);

        if (wordStartingWith != null)
        {
            return firstWord.substring(0, lastConsanantIndex) + wordStartingWith;
        }
        else
        {
            return null;
        }
    }

    private static String getWordStartingWith(String start, Random random)
    {
        List<String> possibleStarts = new ArrayList<String>();

        for (String word : dictionary)
        {
            if (word.startsWith(start))
            {
                possibleStarts.add(word);
            }
        }

        if (possibleStarts.size() > 0)
        {
            return possibleStarts.get(random.nextInt(possibleStarts.size()));
        }

        return null;
    }

    private static String getRandomStartWord(Random random)
    {
        String word = getRandomWord(random);

        boolean cont = true;

        while (cont)
        {
            word = getRandomWord(random);
            cont = isVowel(word.charAt(word.length() - 1));
        }

        return word;
    }

    private static String getRandomWord(Random random)
    {
        return dictionary.get(random.nextInt(dictionary.size()));
    }
}
