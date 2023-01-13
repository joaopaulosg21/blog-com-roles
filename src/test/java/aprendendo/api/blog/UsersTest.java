package aprendendo.api.blog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit4.SpringRunner;

import aprendendo.api.blog.entities.Role;
import aprendendo.api.blog.entities.User;
import aprendendo.api.blog.entities.DTO.UserDTO;
import aprendendo.api.blog.repository.RoleRepository;
import aprendendo.api.blog.repository.UserRepository;
import aprendendo.api.blog.service.UserService;

@RunWith(SpringRunner.class)
@Import(UserServiceConfiguration.class)
public class UsersTest {


	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private RoleRepository roleRepository;
	
	@Before
	public void setup() {
		User user = new User(1l,"teste","teste@email.com","123",null);
		Role role = new Role(1l,"user");
		List<User> users = new ArrayList<User>();
		users.add(user);

		Mockito.when(userRepository.findByEmail(user.getEmail()))
		.thenReturn(Optional.empty());

		Mockito.when(roleRepository.findByName(role.getName()))
		.thenReturn(Optional.of(role));

		Mockito.when(userRepository.save(Mockito.any(User.class)))
		.thenReturn(user);

		Mockito.when(userRepository.findAll())
		.thenReturn(users);
	}
    
    @Test
	public void createUserTest() {
		User user = new User(1l,"teste","teste@email.com","123",null);
		UserDTO expect = userService.createUser(user);
		
		assertEquals(user.getEmail(),expect.getEmail());
	}
	
	@Test
	public void findAllUsersTest() {
		User user = new User(1l,"teste","teste@email.com","123",null);

		List<UserDTO> users = userService.findAll();
		
		assertEquals(1,users.size());
		assertEquals(user.getEmail(),users.get(0).getEmail());
	}

}
