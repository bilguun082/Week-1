package com.example;

import com.indvd00m.ascii.render.Render;
import com.indvd00m.ascii.render.api.ICanvas;
import com.indvd00m.ascii.render.api.IContextBuilder;
import com.indvd00m.ascii.render.api.IRender;
import com.indvd00m.ascii.render.elements.Label;
import com.indvd00m.ascii.render.elements.Rectangle;

import java.util.HashMap;
import java.util.Map;

/**
 * HelloWorld — Multilingual greeter that renders a greeting in an ASCII art box.
 *
 * <p>Usage:
 * <pre>
 *   java -jar hello-world.jar &lt;name&gt; [language]
 * </pre>
 *
 * <p>Supported language codes (case-insensitive):
 * <ul>
 *   <li>{@code hun} — Hungarian (default): "Szia, &lt;name&gt;!"</li>
 *   <li>{@code eng} — English:             "Hello, &lt;name&gt;!"</li>
 *   <li>{@code hin} — Hindi:               "Namaste, &lt;name&gt;!"</li>
 *   <li>{@code deu} — German:              "Hallo, &lt;name&gt;!"</li>
 *   <li>{@code fra} — French:              "Bonjour, &lt;name&gt;!"</li>
 * </ul>
 */
public class HelloWorld {

    /** Maps lower-cased language codes to their greeting prefixes. */
    private static final Map<String, String> GREETINGS = new HashMap<>();

    static {
        GREETINGS.put("hun", "Szia");       // Hungarian  (default)
        GREETINGS.put("eng", "Hello");      // English
        GREETINGS.put("hin", "Namaste");    // Hindi
        GREETINGS.put("deu", "Hallo");      // German
        GREETINGS.put("fra", "Bonjour");    // French
    }

    /** Default language code when none is provided by the user. */
    private static final String DEFAULT_LANGUAGE = "hun";

    public static void main(String[] args) {
        // ── Argument validation ───────────────────────────────────────────────
        if (args.length == 0) {
            System.err.println("Usage: java -jar hello-world.jar <name> [language]");
            System.err.println("Supported languages: " + GREETINGS.keySet());
            System.exit(1);
        }

        String name     = args[0];
        String langCode = (args.length >= 2) ? args[1].toLowerCase() : DEFAULT_LANGUAGE;

        // ── Language lookup ───────────────────────────────────────────────────
        if (!GREETINGS.containsKey(langCode)) {
            System.err.println("Error: Unknown language code \"" + args[1] + "\".");
            System.err.println("Supported languages: " + GREETINGS.keySet());
            System.exit(2);
        }

        String greeting = GREETINGS.get(langCode) + ", " + name + "!";

        // ── ASCII Render ──────────────────────────────────────────────────────
        // Build a canvas wide enough to hold the greeting with padding.
        // The Label is centered inside a Rectangle border.
        int width  = greeting.length() + 6;  // 2 border chars + 2 spaces padding each side
        int height = 5;                       // top border + empty + text + empty + bottom border

        IRender render = new Render();
        IContextBuilder builder = render.newBuilder();
        builder.width(width).height(height);

        // Outer decorative rectangle border
        builder.element(new Rectangle());

        // Greeting text placed at x=2, y=2 (inside the border, middle row)
        builder.element(new Label(greeting, 2, 2, width - 4));

        ICanvas canvas = render.render(builder.build());
        System.out.println(canvas.getText());
    }
}
