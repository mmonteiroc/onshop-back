package xyz.mmonteiroc.eshop.manager;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.mmonteiroc.eshop.entity.User;
import xyz.mmonteiroc.eshop.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Code created by: mmonteiroc
 * Email: miguelmonteiroclaveri@gmail.com
 * Github: https://github.com/mmonteiroc
 * LinkedIn: https://www.linkedin.com/in/mmonteiroc/?locale=en_US
 * Date of creation: 20/08/2020
 * Package: xyz.mmonteiroc.eshop.manager
 * Project: eshop
 */
@Service
public class UserManager {

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id) {
        User user = this.userRepository.findByIdUser(id);
        return user;
    }

    public User findByEmail(String email) {
        User user = this.userRepository.findByEmail(email);
        return user;
    }

    public Set<User> findAll() {
        List<User> users = this.userRepository.findAll();
        return new HashSet<>(users);
    }

    public void create(User... users) {
        /*
         * since the user is going to be created as new,
         * we cypher the passwd before inserting it
         * */
        for (User user : users) {
            user.setPasswd(this.cypherPassword(user.getPasswd()));
        }

        Iterable<User> iterable = Arrays.asList(users);
        this.userRepository.saveAll(iterable);
    }

    /**
     * @param cypherPasswd If its true, we gonna cypher the passwd before we
     *                     update the user. Thats correctly used when the password was changed
     * @param users        Array of users to update;
     */
    public void update(boolean cypherPasswd, User... users) {
        /*
         * We cypher the passwd in case was changed the passwd
         * */
        if (cypherPasswd) {
            for (User user : users) {
                user.setPasswd(this.cypherPassword(user.getPasswd()));
            }
        }

        Iterable<User> iterable = Arrays.asList(users);
        this.userRepository.saveAll(iterable);
    }

    public void delete(User... users) {
        Iterable<User> iterable = Arrays.asList(users);
        this.userRepository.deleteAll(iterable);
    }


    private String cypherPassword(String passwordWithoutCypher) {
        return BCrypt.withDefaults().hashToString(12, passwordWithoutCypher.toCharArray());
    }

    public boolean validatePassword(User fromLoginNotCypher, User fromDatabaseToValidate) {
        BCrypt.Result result = BCrypt.verifyer().verify(fromLoginNotCypher.getPasswd().toCharArray(), fromDatabaseToValidate.getPasswd());

        return result.verified;
    }
}
