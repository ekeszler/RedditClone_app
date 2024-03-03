package RedditClone.Controllers;

import RedditClone.DTOs.SubredditRequestDTO;
import RedditClone.DTOs.VoteRequestDTO;
import RedditClone.DTOs.VoteResponseDTO;
import RedditClone.Model.Subreddit;
import RedditClone.Model.Vote;
import RedditClone.Services.SubredditService;
import RedditClone.Services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vote")
public class VoteController {

    VoteService voteService;
    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }





    @PostMapping
    public ResponseEntity<VoteResponseDTO> addVote(@RequestBody VoteRequestDTO voteRequestDTO) {
        VoteResponseDTO vote = voteService.addVote(voteRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(vote);
    }
}
