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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

                       for (String token : getResumeTokens(removeStopWords(content, language.getLanguage()))) {
                            token_content += token + " ";
                        }

                        resumes.add(new Resume(String.valueOf(count.getAndIncrement()), e.getFileName().toString(),
                                e.toAbsolutePath().toString(), token_content, getLanguageName(language.getLanguage()) , pagesNumber,  getmetadata(file)));

                    } catch (IOException ioException) {
                        ioException.printStackTrace();

                    } catch (TikaException tikaException) {
                        tikaException.printStackTrace();
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    } catch (SAXException saxException) {
                        saxException.printStackTrace();
                    }


                }
        );
        return resumes;
    }

    public void createDirectory(String dirName) throws IOException {
        Path path = Paths.get(currentWorkingDir.normalize().toString() + "/resumes/" + dirName);
        Files.createDirectories(path);

    }

    public String getLanguageName(String languageCode) {
        Map<String, String> language_identifier = new HashMap<>();
        language_identifier.put("pt", "Português");
        language_identifier.put("en", "Inglês");
        language_identifier.put("fr", "Francês");
        language_identifier.put("gen", "Alemão");

        return language_identifier.get(languageCode);
    }

    public Date getmetadata(File file) throws IOException, ParseException, TikaException, SAXException {

        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(file);
        ParseContext context = new ParseContext();
        parser.parse(inputstream, handler, metadata, context);
        String input =metadata.get("created");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern ( "EEE MMM d HH:mm:ss zzz yyyy" , Locale.ENGLISH );
        ZonedDateTime zdt = formatter.parse ( input , ZonedDateTime:: from );
        java.util.Date date = java.util.Date.from( zdt.toInstant() );
        DateFormat df3 = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = df3.format(date);
        return date;

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

