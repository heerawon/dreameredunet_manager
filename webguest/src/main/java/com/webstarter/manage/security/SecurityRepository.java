package com.webstarter.manage.security;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecurityRepository extends JpaRepository<SecurityMemberEntiry, Long> {

    Optional<SecurityMemberEntiry> findByEmail(String username);
    Optional<SecurityMemberEntiry> findByUserId(String username);
}
