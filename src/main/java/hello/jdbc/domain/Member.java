package hello.jdbc.domain;

import lombok.Data;

@Data
public class Member {

    private String memebrId;
    private int money;

    public Member() {

    }

    public Member(String memebrId, int money) {
        this.memebrId = memebrId;
        this.money = money;
    }
}
