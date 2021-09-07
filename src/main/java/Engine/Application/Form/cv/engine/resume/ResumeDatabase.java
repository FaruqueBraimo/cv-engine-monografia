package Engine.Application.Form.cv.engine.resume;

import Engine.Application.Form.cv.engine.model.Resume;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.LanguageIdentifier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@NoArgsConstructor
public class ResumeDatabase {
    File file;
    static Path currentWorkingDir = Paths.get("").toAbsolutePath();
    static Path path;

    public  List<Resume> loadResume(String dirName) throws IOException {
        path = Paths.get(currentWorkingDir.normalize().toString() + "/resumes/" + dirName + "/");
        Stream<Path> paths = Files.walk(path);
        List<Resume> resumes = new ArrayList<Resume>();
        List<Path> result;

        result = paths.filter(Files::isRegularFile)
                .collect(Collectors.toList());

        result.forEach(e -> {
                    try {
                        File file = new File(e.toAbsolutePath().toString());
                        String content = new Tika().parseToString(file);
                        LanguageIdentifier language = new LanguageIdentifier(content);
                        String token_content = "";

                        for (String token : getResumeTokens(removeStopWords(content, language.getLanguage()))) {
                            token_content += token + " ";
                        }
                        resumes.add(new Resume(e.getFileName().toString(), e.toAbsolutePath().toString(), token_content, language.getLanguage()));

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (TikaException tikaException) {
                        tikaException.printStackTrace();
                    }


                }
        );
        return resumes;
    }

    public  void createDirectory(String dirName) throws IOException {
        Path path = Paths.get(currentWorkingDir.normalize().toString() + "/resumes/" + dirName);
        Files.createDirectories(path);

    }

    public  String[] getResumeTokens(String resumeWithoutStopWords) {
        WhitespaceTokenizer tokenizer = WhitespaceTokenizer.INSTANCE;
        String tokens[] = tokenizer.tokenize(resumeWithoutStopWords);
        return tokens;
    }

    public  String removeStopWords(String resume, String language) {
        String result = "";
        List<String> stopWordsList = new ArrayList<>();
        try {
            if (language.equalsIgnoreCase("pt")) {
                stopWordsList = Files.readAllLines(Paths.get("src/main/java/Engine/Application/Form/cv/engine/resume/StopWords-pt.txt"));
            } else {
                stopWordsList = Files.readAllLines(Paths.get("src/main/java/Engine/Application/Form/cv/engine/resume/StopWords-En.txt"));
            }

            String stopwordsRegex = stopWordsList.stream()
                    .collect(Collectors.joining("|", "\\b(", ")\\b\\s?"));
            result = resume.toLowerCase().replaceAll(stopwordsRegex, "");


        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    public static void main(String[] args) throws IOException {
        //createDirectory("desenvolvedor-java-junior");
       // System.out.println(loadResume("desenvolvedor-java-junior"));


    }


}

