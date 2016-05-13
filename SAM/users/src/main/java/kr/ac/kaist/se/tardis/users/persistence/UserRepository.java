package kr.ac.kaist.se.tardis.users.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.kaist.se.tardis.users.User;

public interface UserRepository extends JpaRepository<User, String> {

}
