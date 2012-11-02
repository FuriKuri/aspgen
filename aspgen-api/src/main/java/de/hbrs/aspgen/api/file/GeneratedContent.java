package de.hbrs.aspgen.api.file;

public class GeneratedContent {
    private final String filename;
    private final String content;
    private final String generatorname;

    public GeneratedContent(final String filename, final String content, final String generatorname) {
        super();
        this.filename = filename;
        this.content = content;
        this.generatorname = generatorname;
    };

    public String getFilename() {
        return filename;
    }

    public String getClassName() {
        return filename.split("_")[0];
    }

    public String getSimpleGeneratorName() {
        return filename.split("_")[1].replace(".aj", "");
    }

    public String getContent() {
        return content;
    }

    public String getGeneratorName() {
        return generatorname;
    }
}
