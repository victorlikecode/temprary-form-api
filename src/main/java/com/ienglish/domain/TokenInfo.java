package com.ienglish.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Form 表單 token 產生的 id
 */
@Entity
@Data
@ToString(exclude = {"historySet"})
@Table(name = "token_info")
@NamedEntityGraph(name = "TokenWithHistory",
        attributeNodes = @NamedAttributeNode(value = "historySet",subgraph = "history-subgraph"),
        subgraphs = {
        @NamedSubgraph(name = "history-subgraph", attributeNodes = {
                @NamedAttributeNode(value="token"),
                @NamedAttributeNode(value="token_type"),
                @NamedAttributeNode(value="status")
        })
})
public class TokenInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "rsv_id", nullable = false)
    private Long rsv_id;

    @Column(length = 20, nullable = false)
    private String first_name;

    @Column(length = 20, nullable = false)
    private String last_name;

    @Column(length = 80, nullable = false)
    private String email;

    // token 字串
    @Column(unique = true)
    private String token;
    // service type
    @Column(length = 10, nullable = false)
    private String token_type;

//    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tokenInfo")
    private Set<TokenHistory> historySet;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "token" )
    private FormContent form;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date exiaryDate;
}
