package Engine.Application.Form.cv.engine;

import Engine.Application.Form.cv.engine.model.Candidate;
import Engine.Application.Form.cv.engine.reporitory.CandidateRepository;
import Engine.Application.Form.cv.engine.resource.CandidateResouce;
import Engine.Application.Form.cv.engine.service.CandidateService;
import Engine.Application.Form.cv.engine.service.ElasticResumeService;
import Engine.Application.Form.cv.engine.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CandidateResouce.class)
@ActiveProfiles(value = "dev")
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CandidateService service;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CandidateRepository candidateRepository;

    @MockBean
    private ElasticResumeService elasticResumeService;

    @MockBean
    private UserService userService;


    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();
    }


    @Test
    public void test() throws Exception {

        Candidate candidate = Candidate.builder().
                candidateId(UUID.randomUUID().toString())
                .nome("Faruque")
                .resumeId(UUID.randomUUID().toString())
                .build();

        Iterable<Candidate> candidateIterable = Collections.singleton(candidate);

        when(service.getJobs()).thenReturn(candidateIterable);

        this.mockMvc.perform(get("/api/candidate")
                )
                .andExpect(status().isOk()
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1));
    }

    @Test
    public void createOrEditOrDeleteTaskShouldExecuteOperation() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        Candidate candidate = Candidate.builder().
                candidateId(UUID.randomUUID().toString())
                .nome("Faruque")
                .resumeId(UUID.randomUUID().toString())
                .build();

        when(service.saveJob(any())).thenReturn(candidate);


        this.mockMvc.perform(post("/api/candidate/create", candidate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("candidate1", "{\"candidateId\": \"4532756279624064\"}")
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(candidate)))
                .andDo(print())
                .andExpect(status().is(200));


    }
}




