package com.ytripapp.domain;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "group_name")
public abstract class Account implements Serializable {

    private static final long serialVersionUID = 9093121684989379693L;

    public enum Group {
        Guest,
        Host,
        Admin,
        Editor
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Transient
    private Group group;

    private boolean enabled;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLastSignin;

    private String password;

    private String stipeCustId;

    private String apnsDeviceToken;

    @Embedded
    private UserProfile profile;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<AccountConnection> connections = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    @CollectionTable(
        name = "account_authorities",
        joinColumns = @JoinColumn(name = "account_id")
    )
    private Set<Authority> authorities = new HashSet<>();
}
