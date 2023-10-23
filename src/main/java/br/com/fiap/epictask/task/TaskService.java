package br.com.fiap.epictask.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import br.com.fiap.epictask.user.User;

@Service
public class TaskService {

    @Autowired
    TaskRepository repository;

    public List<Task> findAll(){
        return repository.findAll();
    }

    public boolean delete(Long id) {
        var task = repository.findById(id);
        if(task.isEmpty()) return false;

        repository.deleteById(id);
        return true;
    }

    public void save(Task task) {
        repository.save(task);
    }

    public boolean increment(Long id) {
        var optionalTask = repository.findById(id);

        if (optionalTask.isEmpty()) return false;

        var task = optionalTask.get();

        if (task.getStatus() == null) task.setStatus(0);
        if (task.getStatus() == 100) return false;

        task.setStatus(task.getStatus() + 10);
        repository.save(task);
        return true;

    }

    public boolean decrement(Long id) {
        var optionalTask = repository.findById(id);

        if (optionalTask.isEmpty()) return false;

        var task = optionalTask.get();

        if (task.getStatus() == null || task.getStatus() == 0) return false;

        task.setStatus(task.getStatus() - 10);
        repository.save(task);
        return true;
    }

    public boolean catchTask(Long id, User user) {
        var optionalTask = repository.findById(id);

        if (optionalTask.isEmpty()) return false;

        var task = optionalTask.get();

        if (task.getUser() != null) return false;

        task.setUser(user);

        repository.save(task);
        return true;
        
    }
    
}
