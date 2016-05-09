package com.ytripapp.repository;

import com.ytripapp.domain.AccountConnection;
import com.ytripapp.domain.AccountConnection.ConnectionType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountConnectionRepository extends CrudRepository<AccountConnection, Long> {

    Optional<AccountConnection> findByConnectionIdAndConnectionType(String connectionId, ConnectionType connectionType);

}
