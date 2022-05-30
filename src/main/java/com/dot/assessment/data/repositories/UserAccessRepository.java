package com.dot.assessment.data.repositories;

import com.dot.assessment.data.entities.UserAccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccessRepository extends JpaRepository<UserAccessLog, String> {
    @Query(value = "select * from user_access_log where ip in ( select ip from user_access_log where date between TO_TIMESTAMP (?1, 'yyyy-MM-dd HH24:MI:SS') and TO_TIMESTAMP(?2, 'yyyy-MM-dd HH24:MI:SS') group by ip having count(ip) > ?3 )", nativeQuery = true)
    List<UserAccessLog> findAllThatHavePassedLimit(String start, String end,int limit);
}
