package de.vill.config;

public class Configuration {
    private static String newlineSymbol = "\n";
    private static String tabulatorSymbol = "        ";

    /**
     * The newline symbol used for printing.
     *
     * @return the new line symbol
     */
    public static String getNewlineSymbol() {
        return newlineSymbol;
    }

    /**
     * Set the newline symbol that should be used when printing the feature model (default is \n). This should not be set
     * to something other then the common newline symbols.
     *
     * @param newlineSymbol The newline symbol used for printing.
     */
    public static void setNewlineSymbol(String newlineSymbol) {
        Configuration.newlineSymbol = newlineSymbol;
    }

    /**
     * The tabulator symbol used for printing.
     *
     * @return tabulator symbol
     */
    public static String getTabulatorSymbol() {
        return tabulatorSymbol;
    }

    /**
     * Set the tabulator symbol that should be used when printing the feature model (default ist 8 spaces). This
     * should not be set to something else than spaces or tabs!
     *
     * @param tabulatorSymbol The tabulator symbol used for printing.
     */
    public static void setTabulatorSymbol(String tabulatorSymbol) {
        Configuration.tabulatorSymbol = tabulatorSymbol;
    }
}
