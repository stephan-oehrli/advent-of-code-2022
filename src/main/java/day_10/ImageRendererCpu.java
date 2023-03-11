package day_10;

import java.util.List;

public class ImageRendererCpu extends Cpu {
    
    private final StringBuilder image = new StringBuilder();

    public String renderImage(List<String> inputs) {
        for (String input : inputs) {
            execute(parseCommand(input));
        }
        return image.toString();
    }

    @Override
    void onTick() {
        int currentPos = (cycle - 1) % 40;
        int spriteStart = registerX - 1;
        int spriteEnd = registerX + 1;
        boolean isSpriteOnCurrentPos = currentPos >= spriteStart && currentPos <= spriteEnd;
        image.append(isSpriteOnCurrentPos ? "#" : ".");
        if (cycle % 40 == 0) {
            image.append("\n");
        }
    }
}
