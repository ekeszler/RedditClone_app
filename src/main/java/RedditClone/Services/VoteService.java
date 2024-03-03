package RedditClone.Services;

import RedditClone.DTOs.VoteRequestDTO;
import RedditClone.DTOs.VoteResponseDTO;
import RedditClone.Model.Post;
import RedditClone.Model.User;
import RedditClone.Model.Vote;
import RedditClone.Repositories.PostRepository;
import RedditClone.Repositories.UserRepository;
import RedditClone.Repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class VoteService {

    private PostRepository postRepository;
    private UserRepository userRepository;

    private VoteRepository voteRepository;


    @Autowired
    public VoteService(PostRepository postRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    @Transactional
    public VoteResponseDTO addVote(VoteRequestDTO voteRequestDTO){
        User user = userRepository.findById(voteRequestDTO.getUserId()).orElseThrow(() -> new RuntimeException("user not found"));
        Post post = postRepository.findById(voteRequestDTO.getPostId()).orElseThrow(() -> new RuntimeException("post not found"));
        //daca userul are un vot la post deja
            //sterg votul
            //scad din vote count valoarea tipului votului
        //altfel
            //fac un vote nou
            //il leg de user si post
            //ii setez count-ul in functie de tip
            //ii setez tipul
        VoteResponseDTO voteResponseDTO = new VoteResponseDTO();
        Optional<Vote> voteOptional = voteRepository.findByPost_IdAndUser_Id(voteRequestDTO.getPostId(), voteRequestDTO.getUserId());
        if (voteOptional.isPresent()){
            post.setVoteCount(post.getVoteCount()-voteOptional.get().getVoteType().getValue());
            postRepository.save(post);
            voteRepository.delete(voteOptional.get());
            voteResponseDTO.setDeleteMessage( "already a vote "+ voteOptional.get()+" so the vote was dele");
            //throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, "already a vote "+ voteOptional.get()+" so the vote was deleted");
        }else {
            Vote vote = new Vote();
            vote.setPost(post);
            vote.setUser(user);
            post.setVoteCount(post.getVoteCount()+voteRequestDTO.getVoteType().getValue());
            vote.setVoteType(voteRequestDTO.getVoteType());
            postRepository.save(post);
            voteRepository.save(vote);
            voteResponseDTO.setVoteType(vote.getVoteType());
        }
        return voteResponseDTO;

    }
}
