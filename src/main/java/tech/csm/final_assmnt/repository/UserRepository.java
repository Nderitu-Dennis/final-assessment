package tech.csm.final_assmnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.csm.final_assmnt.model.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
//    fetching user frm DB
    Optional<User> findByUsername(String username);
}

