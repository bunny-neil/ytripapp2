package com.ytripapp.domain;

import com.ytripapp.domain.ConnectionProvider.ProviderName;
import lombok.Data;

import java.io.Serializable;

@Data
public class AccountConnection implements Serializable {

    private static final long serialVersionUID = -5089680400853558017L;

    public enum ConnectionType {
        Email,
        External
    }

    private Long id;
    private ProviderName providerName;
    private ConnectionType connectionType;
    private String connectionId;
    private String externalLink;
    private Account account;

}
