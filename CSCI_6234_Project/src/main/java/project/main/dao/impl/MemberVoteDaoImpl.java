package project.main.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.main.dao.MemberVoteDao;
import project.main.model.MemberVote;
import project.main.model.VotesCount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("memberVoteDao")
public class MemberVoteDaoImpl implements MemberVoteDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    static class MemberVoteMapper implements RowMapper<MemberVote> {
        @Override
        public MemberVote mapRow(ResultSet rs, int rowNum) throws SQLException {
            MemberVote memberVote = new MemberVote(rs.getInt("voting_event_id"), rs.getInt("voter_id"),
                    rs.getInt("movie_id"), rs.getInt("for_or_against"));
            return memberVote;
        }
    }

    @Override
    public boolean voteExist(int votingEventId, int voterId, int movieId) {
        String sql = "select * from member_vote where voting_event_id = ? and voter_id = ? and movie_id = ?";
        return jdbcTemplate.query(sql, new MemberVoteMapper(), votingEventId, voterId, movieId).size() > 0;
    }

    @Override
    public int updateVote(int votingEventId, int voterId, int movieId, int forOrAgainst) {
        String sql = "update member_vote set for_or_against = ? where voting_event_id = ? and voter_id = ? and movie_id = ?";
        return jdbcTemplate.update(sql, forOrAgainst, votingEventId, voterId, movieId);
    }

    @Override
    public int createVote(int votingEventId, int voterId, int movieId, int forOrAgainst) {
        String sql = "insert into member_vote (voting_event_id, voter_id, movie_id, for_or_against) values (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, votingEventId, voterId, movieId, forOrAgainst);
    }

    static class VotesCountMapper implements RowMapper<VotesCount> {
        @Override
        public VotesCount mapRow(ResultSet rs, int rowNum) throws SQLException {
            VotesCount votesCount = new VotesCount(rs.getInt("movie_id"), rs.getInt("vote_score"));
            return votesCount;
        }
    }

    @Override
    public List<VotesCount> getVotes(int votingEventId) {
        String sql = "select movie_id, sum(for_or_against) as vote_score from member_vote where voting_event_id = ? group by movie_id";
        return jdbcTemplate.query(sql, new VotesCountMapper(), votingEventId);
    }
}
