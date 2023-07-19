package hello.jdbc.domain.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        //save
        Member memeber = new Member("memberV21", 10000);
        repository.save(memeber);

        //findById
        Member findMember = repository.findById(memeber.getMemebrId());
        log.info("findMember={}", findMember);
        log.info("member == findMember {}", memeber == findMember);           // false
        log.info("member equals findMember {}", memeber.equals(findMember));  // true
        assertThat(findMember).isEqualTo(memeber);

        //update: money:  10000 ->  20000
        repository.update(memeber.getMemebrId(), 20000);
        Member updateMember = repository.findById(memeber.getMemebrId());
        assertThat(updateMember.getMoney()).isEqualTo(20000);

        //delete
        repository.delete(memeber.getMemebrId());
        assertThatThrownBy(() -> repository.findById(memeber.getMemebrId()))
                .isInstanceOf(NoSuchElementException.class);
    }


}