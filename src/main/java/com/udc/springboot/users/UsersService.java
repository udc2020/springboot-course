package com.udc.springboot.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    // GET:  /api/users
    public List<Users> getUsers() {
        return usersRepository.findAll();
    }

    // POST: /api/users
    public void addUser(Users user) {
        Optional<Users> usersByName = usersRepository.findUsersByName(user.getName());
        if (usersByName.isPresent()) throw new IllegalStateException("invalid name");

        usersRepository.save(user);
    }

    //DELETE: /api/users/id
    public void removeUser(Long userId) {

        boolean exists = usersRepository.existsById(userId);

        if (!exists) throw new IllegalStateException("Not Found");

        usersRepository.deleteById(userId);
    }

    // PUT: /api/users
    @Transactional
    public void updateUser(Long userId, String name, String phone) {
        Users user = usersRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("user id :" + userId + "dont exists")
        );

        if (name != null
                && name.length() > 0 &&
                !Objects.equals(user.getName(),name)

        ) {
            user.setName(name);
        }

        if (phone != null
                && phone.length() > 0 &&
                !Objects.equals(user.getPhone(),phone)

        ) {
            user.setPhone(phone);
        }

    }
}



