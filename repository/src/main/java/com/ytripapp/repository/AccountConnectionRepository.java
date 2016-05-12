package com.ytripapp.repository;

import com.ytripapp.domain.AccountConnection;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountConnectionRepository extends CrudRepository<AccountConnection, Long> {

    Optional<AccountConnection> findByConnectionId(String connectionId);

}
