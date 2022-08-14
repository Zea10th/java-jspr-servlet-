package org.example.service;

import org.example.exception.NotFoundException;
import org.example.model.Post;
import org.example.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
        repository.save(new Post(0,"EmptyPost"));
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    public Post save(Post post) {
        var topic = new Post();
        if (post.getId() == 0) {
            topic.setId(repository.getFreeId());
            topic.setContent(post.getContent());
            return repository.save(topic);
        }
        return repository.save(post);
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}

