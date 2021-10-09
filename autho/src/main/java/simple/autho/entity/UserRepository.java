package simple.autho.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer>{
    @Query("SELECT u FROM User u WHERE u.userName = ?1")
    public User findUserByName(String username);
}
