package kr.ac.kaist.se.tardis.users.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import kr.ac.kaist.se.tardis.users.impl.UserImpl;

public interface UserRepository extends JpaRepository<UserImpl, String> {
	List<UserImpl> findByUsername(String username);
}
