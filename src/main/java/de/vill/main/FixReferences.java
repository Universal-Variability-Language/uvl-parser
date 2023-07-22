package de.vill.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import de.vill.exception.FaultyReference;
import de.vill.exception.ParseError;
import de.vill.model.FeatureModel;

public class FixReferences {

    public static void main(String[] args) throws IOException {
        FixReferences fixer = new FixReferences();
        List<File> files = new ArrayList<>();
        listf(args[0], files);

        for (File file : files) {
            System.out.println("Fixing " + file.getName());
            fixer.fixUVLFile(file.getAbsolutePath(), true, true);
        }

    }

    private void fixUVLFile(String path, boolean onlyFeatureNameDots, boolean replaceRequiresExcludes) throws IOException {
        UVLModelFactory factory = new UVLModelFactory();
        FeatureModel model;
        String content = Files.readString(Paths.get(path));
        content = content.replaceAll("(?m)^namespace.*", "");
        if (onlyFeatureNameDots) {
            content = content.replace(".", ",");
        }
        if (replaceRequiresExcludes) {
            content = content.replace(" requires ", " => ");
            content = content.replace(" REQUIRES ", " => ");
            content = content.replace(" excludes ", " => !");
            content = content.replace(" EXCLUDES ", " => !");

        }
        try {
            model = factory.parse(content);
            if (!model.faultyReferences.isEmpty()) {
                String fixedUvl = fixContent(content, model);
                Files.writeString(Paths.get(path), fixedUvl);
            }
        } catch (FaultyReference e) {
            String fixedUvl = fixContent(content, e.getFeatureModel());
            Files.writeString(Paths.get(path), fixedUvl);
        } catch (ParseError e) {
            e.printStackTrace();
        }

    }

    private String fixContent(String content, FeatureModel featureModel) {
        String newContent = content;
        for (String faultyReference : featureModel.faultyReferences) {
            String cleanName = cleanName(faultyReference);
            // if (cleanName.contains("+")) {
            // System.out.println("");
            // }
            // Debug
            newContent = newContent.replaceAll("(^|\\s|\\!|\\()(" + Pattern.quote(cleanName) + ")(\\)|$|\\s)",
                    "$1\"$2\"$3");

        }
        return newContent;
    }

    private String cleanName(String oldName) {
        String cleanName = oldName;
        cleanName = cleanName.trim();
        return cleanName;
    }


    public static void listf(String directoryName, List<File> files) {
        File directory = new File(directoryName);

        // Get all files from a directory.
        File[] fList = directory.listFiles();
        if (fList != null)
            for (File file : fList) {
                if (file.isFile()) {
                    if (file.getName().endsWith(".uvl")) {
                        files.add(file);
                    }
                } else if (file.isDirectory()) {
                    listf(file.getAbsolutePath(), files);
                }
            }
    }
}
