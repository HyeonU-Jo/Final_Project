package com.work.finalproject.entity;


import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private long tokenid;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(targetEntity = member_tbl.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "num")
    private member_tbl member_tbl;

    public ConfirmationToken() {
    }

    public ConfirmationToken(member_tbl member_tbl) {
        this.member_tbl = member_tbl;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public member_tbl getMember() {
        return member_tbl;
    }

}
