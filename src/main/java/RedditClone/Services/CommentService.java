package RedditClone.Services;

import RedditClone.DTOs.CommentRequestDTO;
import RedditClone.DTOs.Mappers.CommentMapper;
import RedditClone.Model.Comment;
import RedditClone.Model.Post;
import RedditClone.Model.User;
import RedditClone.Repositories.CommentRepository;
import RedditClone.Repositories.PostRepository;
import RedditClone.Repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;



@Service
public class CommentService {

    private CommentRepository commentRepository;

    private static final String BASE_URL = "https://api.api-ninjas.com/v1/profanityfilter";

    private UserRepository userRepository;
    private PostRepository postRepository;

    private CommentMapper commentMapper;

    private RestTemplate restTemplate;

    private ObjectMapper objectMapper;
    private EmailService emailService;



    @Autowired
    public CommentService(EmailService emailService, ObjectMapper objectMapper, CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository, CommentMapper commentMapper, RestTemplate restTemplate) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentMapper = commentMapper;
        this.restTemplate = restTemplate;
        this.objectMapper=objectMapper;
        this.emailService=emailService;
    }

    @Transactional
    public Comment addComment (CommentRequestDTO commentRequestDTO) throws JsonProcessingException, MessagingException {
        if (hasProfanity(commentRequestDTO.getText())){
            throw new RuntimeException("watch out your words");
        }
        Post post = postRepository.findById(commentRequestDTO.getPostId()).orElseThrow(() ->new RuntimeException("post not found"));
        User user = userRepository.findById(commentRequestDTO.getUserId()).orElseThrow(() -> new RuntimeException("user not found"));

        Comment comment = commentMapper.mapCommentRequestDTOtoComment(commentRequestDTO);
        comment.setPost(post);
        comment.setUser(user);

        //trimitem mail
        emailService.sendMessage(post.getUser().getEmail(),"new comment at your post", comment.getText() +" BY "+user.getUsername());
        return commentRepository.save(comment);
    }

    public boolean hasProfanity(String text) throws JsonProcessingException {
        String url = UriComponentsBuilder
                .fromUriString(BASE_URL)
                .queryParam("text", text)
                .toUriString();
        //nxilksEe1FIjNYSTWp0VtA==E6gUdGdXKxb4rTDt

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", "nxilksEe1FIjNYSTWp0VtA==E6gUdGdXKxb4rTDt");

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        // Access the response body
        String response = responseEntity.getBody();
        JsonNode root = objectMapper.readTree(response);
        return root.path("has_profanity").asBoolean();

    }


}
