package utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ValidationResults {
    private static final Path REPORT_PATH = Path.of("reports", "validation-results.xml");
    private static final String EMPTY_REPORT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><VALIDATION_RESULTS></VALIDATION_RESULTS>";

    private ValidationResults() {
    }

    public static synchronized void addValidationResult(String result, String scenario, long elapsedMs) {
        if (!result.equals("PASS") && !result.equals("FAIL") && !result.equals("TIMEOUT")) {
            throw new IllegalArgumentException("Unsupported validation result: " + result);
        }
        if (scenario == null || scenario.isBlank()) {
            throw new IllegalArgumentException("A scenario name is required.");
        }

        try {
            Files.createDirectories(REPORT_PATH.getParent());
            String xml = Files.exists(REPORT_PATH) ? Files.readString(REPORT_PATH) : EMPTY_REPORT;
            String entry = "  <VALIDATION>\n"
                    + "    <RESUTL>" + escapeXml(result) + "</RESUTL>\n"
                    + "    <SCENARIO>" + escapeXml(scenario) + "</SCENARIO>\n"
                    + "    <TIMELAPS>" + escapeXml(elapsedMs + " ms") + "</TIMELAPS>\n"
                    + "  </VALIDATION>\n";
            xml = xml.replace("</VALIDATION_RESULTS>", entry + "</VALIDATION_RESULTS>");
            Files.writeString(REPORT_PATH, xml, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to write validation results.", exception);
        }
    }

    public static void resetValidationResults() {
        try {
            Files.createDirectories(REPORT_PATH.getParent());
            Files.writeString(REPORT_PATH, EMPTY_REPORT, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to reset validation results.", exception);
        }
    }

    private static String escapeXml(String value) {
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&apos;");
    }
}