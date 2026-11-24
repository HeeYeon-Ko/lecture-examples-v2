package com.growmighty.lectures.firstday.tangledmonolith.user.infrastructure;

import com.growmighty.lectures.firstday.tangledmonolith.user.domain.User;
import com.growmighty.lectures.firstday.tangledmonolith.user.domain.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId);
    }
}
