package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.Mission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MissionRepository {

    private final EntityManager em;

    public UUID save(Mission test) {
        em.persist(test);
        return test.getId();
    }

    public Mission findById(UUID id) {
        return em.find(Mission.class, id);
    }

    public List<Mission> findAll() {
        return em.createQuery("select m from Mission m", Mission.class)
                .getResultList();
    }

    public List<Mission> findApplyMissions() {
        LocalDate currentDate = LocalDate.now();
        String jpql = "select m from Mission m where m.applyInformationList >= :currentDate";
        return em.createQuery(jpql, Mission.class)
                .setParameter("currentDate", currentDate)
                .getResultList();
    }

    public List<Mission> findAppliedMissionsByTesterId(UUID testerId) {
        LocalDate currentDate = LocalDate.now();
        String jpql = "select distinct m " +
                "from Mission m " +
                "join m.applyInformationList a join a.tester te " +
                "where m.applyInformationList >= :currentDate and te.id = :testerId";
        return em.createQuery(
                        jpql,
                        Mission.class)
                .setParameter("currentDate", currentDate)
                .setParameter("testerId", testerId)
                .getResultList();
    }

    public List<Mission> findApplyMissionsExceptTesterId(UUID testerId) {
        LocalDate currentDate = LocalDate.now();
        String jpql = "select distinct m from Mission m join m.applyInformationList a join a.tester te where m.applyInformationList >= :currentDate and te.id <> :testerId";
        return em.createQuery(jpql, Mission.class)
                .setParameter("testerId", testerId)
                .setParameter("currentDate", currentDate)
                .getResultList();
    }

    public List<Mission> findApplyMissionsExceptTesterIdByCreated(UUID testerId) {
        LocalDate currentDate = LocalDate.now();
        String jpql = "select distinct m from Mission m join m.applyInformationList a join a.tester te where m.applyInformationList >= :currentDate and te.id <> :testerId order by m.createDateTime";
        return em.createQuery(jpql, Mission.class)
                .setParameter("testerId", testerId)
                .setParameter("currentDate", currentDate)
                .getResultList();
    }

    public List<Mission> findApplyMissionsExceptTesterIdByPopular(UUID testerId) {
        LocalDate currentDate = LocalDate.now();
        // 가능한지 Test 필요
        String jpql = "select distinct m from Mission m join m.applyInformationList a join a.tester te where m.applyInformationList >= :currentDate and te.id <> :testerId order by count(m)";
        return em.createQuery(jpql, Mission.class)
                .setParameter("testerId", testerId)
                .setParameter("currentDate", currentDate)
                .getResultList();

    }

    public List<Mission> findApplyMissionsExceptTesterIdByDeadLine(UUID testerId) {
        LocalDate currentDate = LocalDate.now();
        // 가능한지 Test 필요
        String jpql = "select distinct m from Mission m join m.applyInformationList a join a.tester te where m.applyInformationList >= :currentDate and te.id <> :testerId";
        List<Mission> resultList = em.createQuery(jpql, Mission.class)
                .setParameter("testerId", testerId)
                .setParameter("currentDate", currentDate)
                .getResultList();

        resultList.sort(Comparator.comparingInt(o -> o.getApplyInformationList().size()));
        return resultList;
    }

    public List<Mission> findAppliedMissionsByMakerId(UUID makerId) {
        String jpql = "select m from Mission m join m.maker ma where m.id = :makerId";
        List<Mission> resultList = em.createQuery(jpql, Mission.class)
                .setParameter("makerId", makerId)
                .getResultList();
        return resultList;
    }


//    public List<Tests> findPopularTop4() {
//        String jpql = "select t from Tests t join ApplyInformation a where t.testDate.recruitmentTimeEndgroup by t.id order by count(a)";
//        return em.createQuery(jpql, Tests.class)
//                .setFirstResult(0)
//                .setMaxResults(4)
//                .getResultList();
//
//    }

}