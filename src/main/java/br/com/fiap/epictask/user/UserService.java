package br.com.fiap.epictask.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public boolean addScore(int score, Long userId){
        var optional = repository.findById(userId);

        if (optional.isEmpty()) return false;

        var user = optional.get();

        user.setScore(user.getScore() + score);
        repository.save(user);
        return true;

    }
    
}
