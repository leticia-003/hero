import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.KeyStroke;

import javax.swing.*;
import java.io.IOException;

public class Game {
    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
    Screen screen;
    private int x = 10;
    private int y = 10;

    private Hero hero;
    private Arena arena;

    public Game() {
        try {
            hero = new Hero(new Position(10,10));
            arena = new Arena (80,25,hero);
            TerminalSize terminalSize = new TerminalSize(80, 25);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void draw() {
        try {
            screen.clear();
            arena.draw(screen);
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void run() {
        while (true) {
            try {
                draw ();
                KeyStroke key = screen.readInput();
                if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
                    screen.close();
                }
                else if (key.getKeyType() == KeyType.EOF) {
                    break;
                }
                else arena.processKey(key);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}