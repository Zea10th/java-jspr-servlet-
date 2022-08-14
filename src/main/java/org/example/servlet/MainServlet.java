package org.example.servlet;

import org.example.config.MainServletConfig;
import org.example.controller.PostController;
import org.example.repository.PostRepository;
import org.example.service.PostService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String API_POSTS = "/api/posts";
    private static final String API_POSTS_D = API_POSTS.concat("\\d+");
    private static final String APP_CONTEXT = "/";

    private PostController controller;
    private PostRepository repository;
    private PostService service;

    @Override
    public void init() {
        final var context = new AnnotationConfigApplicationContext(MainServletConfig.class);
        repository = context.getBean(PostRepository.class);
        service = context.getBean(PostService.class);
        controller = context.getBean(PostController.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            // primitive routing
            if (method.equals(METHOD_GET) && path.equals(API_POSTS)) {
                controller.all(resp);
                return;
            }
            if (method.equals(METHOD_GET) && path.matches(API_POSTS_D)) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf(APP_CONTEXT)));
                controller.getById(id, resp);
                return;
            }
            if (method.equals(METHOD_POST) && path.equals(API_POSTS)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(METHOD_DELETE) && path.matches(API_POSTS_D)) {
                final var id = Long.parseLong(path.substring(path.lastIndexOf(APP_CONTEXT)));
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

