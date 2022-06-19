package Engine.Application.Form.cv.engine;

import Engine.Application.Form.cv.engine.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CvEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(CvEngineApplication.class, args);
	}




	@Bean
	CommandLineRunner runner(UserService userService) {
		return args -> {
//			userService.saveRole(new Role(null, "ROLE_USER"));
//			userService.saveRole(new Role(null, "ROLE_MANAGER"));
//			userService.saveRole(new Role(null, "ROLE_ADMIN"));
//			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
//

			//userService.saveUser(new UserEntity(null, "Faruque", "123",new ArrayList<>()));
//			userService.saveUser(new UserEntity(null, "Abu", "123",new ArrayList<>()));
//			userService.saveUser(new UserEntity(null, "Jonh", "123",new ArrayList<>()));
//			userService.saveUser(new UserEntity(null, "Aida", "123",new ArrayList<>()));

		//	userService.addRoleToUser("Faruque", "ROLE_SUPER_ADMIN");
//			userService.addRoleToUser("Abu", "ROLE_MANAGER");
//			userService.addRoleToUser("Jonh", "ROLE_ADMIN");
//			userService.addRoleToUser("Aida", "ROLE_SUPER_ADMIN");
//			Candidate candidate = new Candidate();
//
//			List<Skills> skills = new ArrayList<>();
//			skills.add(new Skills(UUID.randomUUID(), "Olacle", "3 Years", candidate));
//			skills.add(new Skills(UUID.randomUUID(), "Sql", "2 Years", candidate));
//			skills.add(new Skills(UUID.randomUUID(), "PHP", "1 Year", candidate));
//
//			List<Experience> experienceList = new ArrayList<>();
//			experienceList.add(new Experience(UUID.randomUUID(), "OPen SL", LocalDate.now(), LocalDate.now(), "Jobava muito aqui", false, candidate));
//			experienceList.add(new Experience(UUID.randomUUID(), "Tara Travel", LocalDate.now(), LocalDate.now(), "Fui escravo", false, candidate));
//			experienceList.add(new Experience(UUID.randomUUID(), "BCI", LocalDate.now(), LocalDate.now(), "Job actual", true, candidate));
//
//			candidate.setObjectID(UUID.randomUUID());
//			candidate.setName("Faruque");
//			candidate.setBirth_date(LocalDate.now());
//			candidate.setNacionality("Mozambique");
//			candidate.setProvince("Maputo");
//			candidate.setSkills(skills);
//			candidate.setExperience(experienceList);
//
//
//
//			ArgoliaSerach argoliaSerach = new ArgoliaSerach();
//			argoliaSerach.saveCandidate(candidate);


		};
	}
}
