package org.example.repository;

import org.example.model.Post;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PostRepository {
    private Map<Long, Post> repository;

    public PostRepository() {
        this.repository = new ConcurrentHashMap<>();
    }

    public List<Post> all() {
        return new ArrayList<>(repository.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.of(new Post(id, getContent(id)));
    }

    public Post save(Post post) {
        final var id = post.getId();
        repository.put(id, post);
        return new Post(id, getContent(post.getId()));
    }

    public void removeById(long id) {
        repository.remove(id);
    }

    public long getFreeId() {
        long freeId = 0;
        for (long i = 0; i < Collections.max(repository.keySet()); i++) {
            if (!repository.containsKey(i)) {
                freeId = i;
                break;
            }
        }
        return freeId;
    }

    private String getContent(long id) {
        return repository.get(id).getContent();
    }
}
