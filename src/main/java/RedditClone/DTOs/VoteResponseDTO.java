package RedditClone.DTOs;

import RedditClone.Model.VoteType;

public class VoteResponseDTO {

    private VoteType voteType;
    private String deleteMessage;

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public String getDeleteMessage() {
        return deleteMessage;
    }

    public void setDeleteMessage(String deleteMessage) {
        this.deleteMessage = deleteMessage;
    }
}
