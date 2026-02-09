import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Stack;

public class HtmlAnalyzer {

    public static void main(String[] args) {
        if (args.length == 0) return;
        
        String urlString = args[0];

        try {
            
            String htmlContent = fetchHtml(urlString);
            
            analyzeHtml(htmlContent);
            
        } catch (IOException e) {
            
            System.out.println("URL connection error");
        }
    }

    private static String fetchHtml(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000); 
        connection.setReadTimeout(5000);

        int status = connection.getResponseCode();
        
        if (status != 200) {
            throw new IOException("HTTP Error: " + status);
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    private static void analyzeHtml(String html) {
        String[] lines = html.split("\n");
        Stack<String> tagStack = new Stack<>();
        
        String deepestText = "";
        int maxDepth = -1;
        
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (isOpeningTag(line)) {
                tagStack.push(extractTagName(line));
            } else if (isClosingTag(line)) {
                String tagName = extractTagName(line);
                
                if (tagStack.isEmpty() || !tagStack.peek().equals(tagName)) {
                    System.out.println("malformed HTML");
                    return;
                }
                tagStack.pop();
            } else {
                
                if (tagStack.size() > maxDepth) {
                    maxDepth = tagStack.size();
                    deepestText = line;
                }
            }
        }

        if (!tagStack.isEmpty()) {
            System.out.println("malformed HTML");
        } else {
            System.out.println(deepestText);
        }
    }

    private static boolean isOpeningTag(String line) {
        return line.startsWith("<") && line.endsWith(">") && !line.startsWith("</");
    }

    private static boolean isClosingTag(String line) {
        return line.startsWith("</") && line.endsWith(">");
    }

    private static String extractTagName(String tag) {
        return tag.replaceAll("[<>/]", "");
    }
}