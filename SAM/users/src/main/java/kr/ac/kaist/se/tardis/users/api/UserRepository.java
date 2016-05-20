package kr.ac.kaist.se.tardis.users.api;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.kaist.se.tardis.persistence.NotPrimaryDataSource;
import kr.ac.kaist.se.tardis.users.impl.UserImpl;

@NotPrimaryDataSource
public interface UserRepository extends JpaRepository<UserImpl, String> {
}
