package hello.jdbc.domain.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach() {
        //기본 DriverManager - 항상 새로운 커넥션 획득
//        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        //커넥션 풀링
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        repository = new MemberRepositoryV1(dataSource);
    }

    @Test
    void crud() throws SQLException, IllegalStateException {
        //save
        Member memeber = new Member("memberV11", 10000);
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

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}