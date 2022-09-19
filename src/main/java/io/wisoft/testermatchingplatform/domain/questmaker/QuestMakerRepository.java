package io.wisoft.testermatchingplatform.domain.questmaker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuestMakerRepository extends JpaRepository<QuestMaker,Long> {


    @Override
    Optional<QuestMaker> findById(Long id);

    Optional<QuestMaker> findByEmail(String email);

    @Query("update QuestMaker q set q.refreshToken = ?2 where q.id = ?1")
    @Modifying
    void setRefreshToken(Long id, String token);

    @Query("select q.refreshToken from QuestMaker q where q.id = ?1")
    String getRefreshToken(Long id);
}
