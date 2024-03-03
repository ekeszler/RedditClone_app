package RedditClone.DTOs;

public class CommentRequestDTO {

    private Long postId;
    private Long userId;
    private String text;

    public CommentRequestDTO() {
    }

    public CommentRequestDTO(Long postId, Long userId, String text) {
        this.postId = postId;
        this.userId = userId;
        this.text = text;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
