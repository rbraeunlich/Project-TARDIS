package kr.ac.kaist.se.tardis.users.copy;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWithoutPasswordRepository extends JpaRepository<UserWithoutPassword, String> {

}
