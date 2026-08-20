package net.novabruteforce;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.text.Text;

public class BruteForceScreen extends Screen {
    private TextFieldWidget ipField;
    private TextFieldWidget nameField;
    private TextFieldWidget passwordField;
    private ButtonWidget startButton;
    private BruteForceBot bot;
    private TextWidget statusText;

    public BruteForceScreen() {
        super(Text.literal("BruteForce"));
    }

    @Override
    protected void init() {
        this.addDrawableChild(TextWidget.builder(Text.literal("IP Сервера:"), textRenderer).position(20, 30).build());
        this.addDrawableChild(TextWidget.builder(Text.literal("Имя:"), textRenderer).position(20, 60).build());
        this.addDrawableChild(TextWidget.builder(Text.literal("Пароль:"), textRenderer).position(20, 90).build());

        ipField = new TextFieldWidget(textRenderer, 120, 25, 200, 20, Text.literal("IP"));
        nameField = new TextFieldWidget(textRenderer, 120, 55, 200, 20, Text.literal("Имя"));
        passwordField = new TextFieldWidget(textRenderer, 120, 85, 200, 20, Text.literal("Пароль"));
        passwordField.setEditable(false);

        this.addDrawableChild(ipField);
        this.addDrawableChild(nameField);
        this.addDrawableChild(passwordField);

        startButton = ButtonWidget.builder(
            Text.literal("Начать брут"),
            button -> {
                if (bot == null || !bot.isRunning()) {
                    String ip = ipField.getText();
                    String name = nameField.getText();
                    if (!ip.isEmpty() && !name.isEmpty()) {
                        bot = new BruteForceBot(ip, name, this);
                        bot.start();
                        startButton.setMessage(Text.literal("Остановить"));
                    }
                } else {
                    bot.stop();
                    startButton.setMessage(Text.literal("Начать брут"));
                }
            }
        ).dimensions(120, 120, 200, 20).build();

        this.addDrawableChild(startButton);

        ButtonWidget backButton = ButtonWidget.builder(
            Text.literal("Назад"),
            button -> client.setScreen(new MultiplayerScreen(new TitleScreen()))
        ).dimensions(20, 160, 100, 20).build();
        this.addDrawableChild(backButton);

        statusText = TextWidget.builder(Text.literal("Готов"), textRenderer).position(20, 150).build();
        this.addDrawableChild(statusText);
    }

    public void updatePassword(String password) {
        passwordField.setText(password);
    }

    public void updateStatus(String status) {
        statusText.setMessage(Text.literal(status));
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
