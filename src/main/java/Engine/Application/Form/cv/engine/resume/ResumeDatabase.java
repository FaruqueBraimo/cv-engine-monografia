package Engine.Application.Form.cv.engine.resume;

import Engine.Application.Form.cv.engine.model.Resume;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.metadata.Metadata;
import com.itextpdf.text.pdf.PdfReader;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@NoArgsConstructor
public class ResumeDatabase {


    File file;
    static Path currentWorkingDir = Paths.get("").toAbsolutePath();
    static Path path;




    public List<Resume> loadResume(String job) throws IOException {
        path = Paths.get(currentWorkingDir.normalize().toString() + "/resumes/" + job + "/");
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


                        String target = Arrays.asList(content.split(" "))
                                .stream()
                                .filter(word -> !word.contains("http"))
                                .filter(word -> !word.contains("www"))
                                .map(word -> word + " ")
                                .collect(Collectors.joining());


                        LanguageIdentifier language = new LanguageIdentifier(content);
                        String candidatePhone = getNumber(content);
                        String candidateEmail = getEmail(content);
                        String token_content = "";


                        for (String token : getResumeTokens(removeStopWords(target, language.getLanguage()))) {

                            String pattern = "[● \uF0B7 ^ _  * \uF03E]*";
                            token_content += token.replaceAll("g[0-9].*?\\b", "").replaceAll(pattern, "") + " ";
                        }

                        resumes.add(new Resume(String.valueOf(count.getAndIncrement()), FilenameUtils.removeExtension(e.getFileName().toString()),
                                e.toAbsolutePath().toString(), token_content, getLanguageName(language.getLanguage()), pagesNumber, getmetadata(file), job, candidatePhone, candidateEmail));

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
        String language = "Desconhecido";

        Map<String, String> language_identifier = new HashMap<>();
        language_identifier.put("pt", "Português");
        language_identifier.put("en", "Inglês");
        language_identifier.put("fr", "Francês");
        language_identifier.put("gen", "Alemão");
        language_identifier.put("es", "Espanhol");
        language_identifier.put("it", "Italiano");

        if (language_identifier.get(languageCode) != null) {
            language = language_identifier.get(languageCode);
        }

        return language;

    }

    public Date getmetadata(File file) throws IOException, ParseException, TikaException, SAXException {

        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(file);
        ParseContext context = new ParseContext();
        parser.parse(inputstream, handler, metadata, context);
        String input = metadata.get("created");
        Date date = new Date();
        if (input != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz yyyy", Locale.ENGLISH);
            ZonedDateTime zdt = formatter.parse(input, ZonedDateTime::from);
            date = Date.from(zdt.toInstant());
        }

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
    public  String getEmail(String content) {
        String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Zaz0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern p = Pattern.compile(content);
        Matcher matcher = p.matcher(pattern);

        if(matcher.matches()) {
            return  content;
        }
        return "Nao encontrado";

    }

    public  String getNumber(String phoneNumber) {
        String internationalPattern = "^\\+(?:[0-9]\\s*?){6,14}[0-9]$";
        String localPattern = "^\\(?([0-9]{3})\\)?[-.\\s*]?([0-9]{3})[-.\\s*]?([0-9]{3,5})$";

        Pattern pInternational = Pattern.compile(internationalPattern);
        Pattern pLocal = Pattern.compile(localPattern);

        Matcher mInternational = pInternational.matcher(phoneNumber);
        Matcher mLocal = pLocal.matcher(phoneNumber);

        if (mInternational.matches()) {
            return phoneNumber;
        } else if (mLocal.matches()) {
            return phoneNumber;
        }
        return "Nao encontrado";
    }

    public static void main(String[] args) {
        String eduation = "Licenciatura no Institute Superior";
       // String patternToMatch = "(Institute|Instituto)\\s " ;
       String patternToMatch = "/([A-Z][^\\s,.]+[.]?\\s[(]?)*(College|University|Institute|Law School|School of|Academy|Instituto|Universidade|Escola)[^,\\d]*(?=,|\\d)/";

        Pattern pattern = Pattern.compile(patternToMatch);

        Matcher matcher = pattern.matcher(eduation);
        while(matcher.find()) {
            System.out.println("found: " + matcher.group());
        }

    }


}

