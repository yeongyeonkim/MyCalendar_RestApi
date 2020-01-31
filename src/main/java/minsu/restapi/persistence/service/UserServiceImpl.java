package minsu.restapi.persistence.service;

import minsu.restapi.persistence.dao.UserRepository;
import minsu.restapi.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public int save(User user) { //가입이 잘 되었으면 i = 1;
        String email = user.getEmail();
        if (email == "" || userRepository.existsByEmail(email)) {
            return 0;
        }
        else {
            userRepository.save(user);
            return 1;
        }
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteImg(String email){
        User user = userRepository.findByEmail(email);
        user.setImg("default.png");
    }
//    public void save(User user){
//        userRepository.save(user);
//    }

    public User signin(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user != null) {
            return user;
        } else {
            throw new RuntimeException("없는 유저");
        }

    }


    @Override
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }


    public boolean checkEmail(String email) {
        boolean result = userRepository.existsByEmail(email);
        return result;
    }

    public boolean checkName(String name) {
        boolean result = userRepository.existsByName(name);
        return result;

    }

    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public User findById(Long id) {
        User user = userRepository.findById(id).get();
        return user;
    }

}
