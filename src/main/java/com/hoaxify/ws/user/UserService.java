package com.hoaxify.ws.user;

import com.hoaxify.ws.user.vm.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    UserRepository userRepository;     // buraya @Autowired yazarakta injection yapılabilir.
    PasswordEncoder passwordEncoder;

    @Autowired
    //2 türlü injection var. constructor injection yapmak 2. si
    //bir class ta sadece br tane constructor varsa ek olarak @Autowired yazılmasına gerek yok. zaten ilk olarak const. çalışır.
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user) {
        //user objesini kullanarak gelen password ü encrypt ederiz.
        String encryptedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }

    public Page<User> getUsers(Pageable page) {
        return userRepository.findAll(page);         //user ları pageable yapıyoruz
    }
}
