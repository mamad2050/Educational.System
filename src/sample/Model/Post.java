package sample.Model;

import java.util.ArrayList;

public class Post {

    String subject;
    String area;
    public static ArrayList<Post> posts = new ArrayList<>();

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


    public Post(String subject, String area) {
        this.subject = subject;
        this.area = area;
    }

    public static ArrayList<Post> getPosts() {
        return posts;
    }

    public static void setPosts(ArrayList<Post> posts) {
        Post.posts = posts;
    }
}
