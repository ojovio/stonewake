package dev.stonewake.screens;

import com.badlogic.gdx.Gdx;
import dev.stonewake.Game;

import java.util.Stack;

public class ScreenManager {
    Stack<GameScreen> screens;

    public ScreenManager() {
        screens = new Stack<>();
    }

    public void push(Game game, GameScreen screen) {
        if (!screens.empty()) {
            screens.peek().pause();
        }

        game.setCurrentGameScreen(screen);

        screens.push(screen);

        screen.load();
        screen.start();
        screen.resume();
    }

    public void set(Game game, GameScreen screen) {
        screens.clear();

        push(game, screen);
    }

    public GameScreen pop() {
        GameScreen screen = screens.pop();

        if (!screens.isEmpty()) {
            screens.peek().resume();
        }
        else {
            Gdx.app.exit();
        }

        screen.hide();
        screen.dispose();

        return screen;
    }

    public GameScreen peek() {
        return screens.peek();
    }

    public Stack<GameScreen> getScreens() {
        return screens;
    }
}
