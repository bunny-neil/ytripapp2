package com.ytripapp.domain;

import com.ytripapp.domain.ConnectionProvider.ProviderName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "account_connections")
public class AccountConnection implements Serializable {

    private static final long serialVersionUID = -5089680400853558017L;

    public enum ConnectionType {
        Email,
        External
    }

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProviderName providerName;

    @Enumerated(EnumType.STRING)
    private ConnectionType connectionType;
    private String connectionId;
    private String externalLink;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
