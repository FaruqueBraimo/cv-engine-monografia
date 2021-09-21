package Engine.Application.Form.cv.engine.resume;

import Engine.Application.Form.cv.engine.model.Resume;
import Engine.Application.Form.cv.engine.service.ElasticResumeServiceImpl;
import org.apache.tika.metadata.Metadata;
import com.itextpdf.text.pdf.PdfReader;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.joda.time.LocalDate;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@NoArgsConstructor
public class ResumeDatabase {


    File file;
    static Path currentWorkingDir = Paths.get("").toAbsolutePath();
    static Path path;


    public List<Resume> loadResume(String dirName) throws IOException {
        path = Paths.get(currentWorkingDir.normalize().toString() + "/resumes/" + dirName + "/");
        Stream<Path> paths = Files.walk(path);
        List<Resume> resumes = new ArrayList<Resume>();
        List<Path> result;
        AtomicInteger count = new AtomicInteger(1);
        result = paths.filter(Files::isRegularFile)
                .collect(Collectors.toList());
        result.forEach(e -> {

                    try {

                        File file = new File(e.toAbsolutePath().toString());

                        //Count Number pages
                        PdfReader document = new PdfReader(new FileInputStream(file));
                        int pagesNumber = document.getNumberOfPages();

                        // Detect Language

                        String content = new Tika().parseToString(file);
                        LanguageIdentifier language = new LanguageIdentifier(content);
                        String token_content = "";

                        // MetaData of Documents
                        Path filePath = Paths.get(e.toAbsolutePath().toString());

                        BasicFileAttributes attr =
                                Files.readAttributes(filePath, BasicFileAttributes.class);

                        TimeZone utc = TimeZone.getTimeZone("UTC");
                        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        SimpleDateFormat destFormat = new SimpleDateFormat("yyyy/MM/dd");
                        System.out.println(attr.creationTime());
                        sourceFormat.setTimeZone(utc);
                        Date convertedDate = sourceFormat.parse(attr.creationTime().toString());



                        for (String token : getResumeTokens(removeStopWords(content, language.getLanguage()))) {
                            token_content += token + " ";
                        }
                        resumes.add(new Resume(String.valueOf(count.getAndIncrement()), e.getFileName().toString(),
                                e.toAbsolutePath().toString(), token_content, language.getLanguage(), pagesNumber,destFormat.format(convertedDate)));

                    } catch (IOException ioException) {
                        ioException.printStackTrace();

                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    } catch (TikaException tikaException) {
                        tikaException.printStackTrace();
                    }


                }
        );
        return resumes;
    }

    public void createDirectory(String dirName) throws IOException {
        Path path = Paths.get(currentWorkingDir.normalize().toString() + "/resumes/" + dirName);
        Files.createDirectories(path);

    }

    public String getmetadata(String fileName) throws IOException, ParseException {

        Path file = Paths.get(fileName);
        BasicFileAttributes attr =
                Files.readAttributes(file, BasicFileAttributes.class);

        TimeZone utc = TimeZone.getTimeZone("UTC");
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat destFormat = new SimpleDateFormat("yyyy/MM/dd");
        sourceFormat.setTimeZone(utc);
        Date convertedDate = sourceFormat.parse(attr.creationTime().toString());
        return destFormat.format(convertedDate);

    }


    public String[] getResumeTokens(String resumeWithoutStopWords) {
        WhitespaceTokenizer tokenizer = WhitespaceTokenizer.INSTANCE;
        String tokens[] = tokenizer.tokenize(resumeWithoutStopWords);
        return tokens;
    }

    public String removeStopWords(String resume, String language) {
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




}

