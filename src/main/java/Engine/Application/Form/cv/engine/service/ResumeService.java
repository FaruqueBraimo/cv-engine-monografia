package Engine.Application.Form.cv.engine.service;

import Engine.Application.Form.cv.engine.model.Resume;
import Engine.Application.Form.cv.engine.resume.ResumeDatabase;
import com.itextpdf.text.pdf.PdfReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import opennlp.tools.tokenize.WhitespaceTokenizer;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResumeService {

    private ResumeDatabase resumeDatabase = new ResumeDatabase();
    static Path currentWorkingDir = Paths.get("").toAbsolutePath();
    static Path path;
    private final ElasticResumeService elasticResumeService;


    public void saveResumes(MultipartFile file, String job_folder) {
        String uploadedFolder = System.getProperty("user.dir");

        if (uploadedFolder != null && !uploadedFolder.isEmpty()) {
            uploadedFolder += "/resumes/" + job_folder + "/";
        } else
            throw new RuntimeException("Ocorreu um erro ao carregar os ficheiros, por favor reporte este para suporte.");

        byte[] bytes = null;
        try {
            bytes = file.getBytes();
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
        Path path = null;
        try {
            path = Paths.get(uploadedFolder + file.getOriginalFilename());
            if (!Files.exists(path.getParent()))
                Files.createDirectories(path.getParent());
                Files.write(path, bytes);
                IndexFiles(file,job_folder );

        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());

        }

    }

    public void IndexFiles(MultipartFile multipartFile , String job) throws IOException {
        path = Paths.get(currentWorkingDir.normalize().toString() + "/resumes/" + job + "/" + multipartFile.getOriginalFilename());
        List<Resume> resumes = new ArrayList<Resume>();

        try {
            File file = new File(path.toAbsolutePath().toString());

            PdfReader document = new PdfReader(new FileInputStream(file));
            int pagesNumber = document.getNumberOfPages();

            String content = new Tika().parseToString(file);

            // clean the doc
            String target = Arrays.asList(content.split(" "))
                    .stream()
                    .filter(word -> !word.contains("http"))
                    .filter(word ->   !word.contains("www"))
                    .map(word -> word + " ")
                    .collect(Collectors.joining());

            LanguageIdentifier language = new LanguageIdentifier(content);
            String candidatePhone = getNumber(content);
            String candidateEmail = getEmail(content);

            String token_content = "";


            for (String token : getResumeTokens(removeStopWords(target, language.getLanguage()))) {
                // clean the doc again
                String pattern = "[??? \uF0B7 ^ _  * \uF03E]*";
                token_content += token.replaceAll("g[0-9].*?\\b", "").replaceAll(pattern, "") + " ";
            }

            elasticResumeService.saveResume(new Resume(UUID.randomUUID().toString(), FilenameUtils.removeExtension(file.getName().toString()),
                    file.getAbsolutePath(), token_content, getLanguageName(language.getLanguage()), pagesNumber, getmetadata(file), job, candidatePhone, candidateEmail));

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
    public String getLanguageName(String languageCode) {
        String language = "Desconhecido";

        Map<String, String> language_identifier = new HashMap<>();
        language_identifier.put("pt", "Portugu??s");
        language_identifier.put("en", "Ingl??s");
        language_identifier.put("fr", "Franc??s");
        language_identifier.put("gen", "Alem??o");
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


    public List<Resume> getResumeByJob(String job) throws IOException {
        return resumeDatabase.loadResume(job);
    }




    public void deleteResume(Resume resume) throws IOException {
        Path root = Paths.get(resume.getPath());
        Files.delete(root);
    }

    public  String getNumber(String text) {
        String internationalPattern = "(?:[0-9]\\s*?){6,14}[0-9]";
        String localPattern = "([0-9]{3})\\)?[-.\\s*]?([0-9]{3})[-.\\s*]?([0-9]{3,5})$";

        Pattern p
                = Pattern.compile("(?:[0-9]\\s*?)[0-9]+(?:[0-9]\\s*?){6,14}[0-9]");

        Pattern pattern = Pattern.compile(internationalPattern);

        Matcher m = p.matcher(text);

        while (m.find()) {
            return m.group();
        }
        return "Nao encontrado";
    }

    public  String getEmail(String text) {
        Pattern p
                = Pattern.compile(
                "[a-zA-Z0-9]"
                        + "[a-zA-Z0-9_.]"
                        + "*@[a-zA-Z0-9]"
                        + "+([.][a-zA-Z]+)+");

        Matcher m = p.matcher(text);

        while (m.find()) {
            return m.group();
        }
        return "Nao encontrado";

    }



    public void deleteAllResume(String job_folder) {
        Path root = Paths.get(currentWorkingDir.normalize().toString() + "/resumes/" + job_folder + "/");
        FileSystemUtils.deleteRecursively(root.toFile());

    }

    public static void main(String[] args) {
        path = Paths.get(currentWorkingDir.normalize().toString() + "/resumes/" + "job" + "/" + "name");
        System.out.println(path);

    }

    public Long countResumeByJob(String job) {
     return   elasticResumeService.countResumeByJob(job);
    }

    public Iterable<Resume> getResume() {
        return  elasticResumeService.findAll();
    }
}
