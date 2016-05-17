package kr.ac.kaist.se.tardis.users.api;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.kaist.se.tardis.users.UserWithoutPassword;

public interface UserWithoutPasswordRepository extends JpaRepository<UserWithoutPassword, String> {

}
