package rs.raf.demo.repositories.user;

import rs.raf.demo.entities.User;

import java.util.List;

public interface UserRepository {
    public User findUser(String email);
    public List<User> allUser();
    public User addUser(User user);
    public Integer userActivity(String email);
    public User updateUser(User user, String email);

}
