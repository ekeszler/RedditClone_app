package RedditClone.Controllers;

import RedditClone.DTOs.CommentRequestDTO;
import RedditClone.Model.Comment;
import RedditClone.Model.Post;
import RedditClone.Services.CommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping
    public ResponseEntity<Object> createComment(@RequestBody CommentRequestDTO commentRequestDTO)  {
        try {
            Comment comment = commentService.addComment(commentRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(comment);
        } catch(JsonProcessingException | MessagingException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}
