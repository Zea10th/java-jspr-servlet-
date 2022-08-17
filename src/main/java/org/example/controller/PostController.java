package org.example.controller;

import org.example.model.Post;
import org.example.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<Post> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public Post getById(@PathVariable long id) {
        return service.getById(id);
    }

    @PostMapping
    public Post save(@RequestBody Post post) {
        return service.save(post);
    }

    @DeleteMapping("/{id}")
    public void removeById(long id) {
        service.removeById(id);
    }

//    @GetMapping
//    public void all(HttpServletResponse response) throws IOException {
//        response.setContentType(APPLICATION_JSON);
//        final var data = service.all();
//        final var gson = new Gson();
//        response.getWriter().print(gson.toJson(data));
//    }
//
//    @GetMapping("/{id}")
//    public void getById(long id, HttpServletResponse response) throws IOException {
//        response.setContentType(APPLICATION_JSON);
//        final var gson = new Gson();
//        final var data = service.getById(id);
//        response.getWriter().print(gson.toJson(data));
//    }
//
//    @PostMapping
//    public void save(Reader body, HttpServletResponse response) throws IOException {
//        response.setContentType(APPLICATION_JSON);
//        final var gson = new Gson();
//        final var posts = gson.fromJson(body, Post.class);
//        final var data = service.save(posts);
//        response.getWriter().print(gson.toJson(data));
//    }
//
//    @DeleteMapping("/{id}")
//    public void removeById(long id, HttpServletResponse response) throws IOException {
//        response.setContentType(APPLICATION_JSON);
//        final var gson = new Gson();
//        final var data = service.getById(id);
//        service.removeById(id);
//        response.getWriter().print(gson.toJson(data));
//    }
}
