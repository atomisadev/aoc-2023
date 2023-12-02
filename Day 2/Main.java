import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

class Main {
  private static class Draw {
    int red, green, blue;

    Draw(int red, int green, int blue) {
      this.red = red;
      this.green = green;
      this.blue = blue;
    }
  }

  private static class Game {
    int id;
    List<Draw> draws;

    Game(int id, List<Draw> draws) {
      this.id = id;
      this.draws = draws;
    }
  }

  public static void main(String args[]) throws Exception {
    List<Game> games = parseGames("./Day 2/input.txt");

    int possibleGameSum = solvePart1(games);
    System.out.println("The total IDs of possible games :" + possibleGameSum);

    int totalPower = solvePart2(games);
    System.out.println("The total power of possible games: " + totalPower);
  }

  private static List<Game> parseGames(String file) throws IOException {
    List<Game> games = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line; int id = 1;
      while ((line = br.readLine()) != null) {
        String[] drawAsText = line.split(":")[1].split(";");
        List<Draw> draws = new ArrayList<>();
        for (String drawText : drawAsText) {
          String[] cubes = drawText.split(",");
          int red = 0, green = 0, blue = 0;
          for (String cube : cubes) {
            String[] splitCube = cube.trim().split(" ");
            switch (splitCube[1]) {
              case "red": red = Integer.parseInt(splitCube[0]); break;
              case "green": green = Integer.parseInt(splitCube[0]); break;
              case "blue": blue = Integer.parseInt(splitCube[0]); break;
            }
          }
          draws.add(new Draw(red, green, blue));
        }
        games.add(new Game(id++, draws));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return games;
  }

  private static int solvePart1(List<Game> games) {
    int possibleGameSum = 0;
    Predicate<Draw> possible = draw -> draw.red <= 12 && draw.green <= 13 && draw.blue <= 14;
    for (Game game : games) {
      if (game.draws.stream().allMatch(possible)) {
        possibleGameSum += game.id;
      }
    }

    return possibleGameSum;
  }

  private static int solvePart2(List<Game> games) {
    int totalPower = 0;
    for (Game game : games) {
        int maxRed = game.draws.stream().mapToInt(draw -> draw.red).max().getAsInt();
        int maxGreen = game.draws.stream().mapToInt(draw -> draw.green).max().getAsInt();
        int maxBlue = game.draws.stream().mapToInt(draw -> draw.blue).max().getAsInt();
        totalPower += maxRed * maxGreen * maxBlue;
    }
    return totalPower;
}
}