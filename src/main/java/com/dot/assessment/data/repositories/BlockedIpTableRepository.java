package com.dot.assessment.data.repositories;

import com.dot.assessment.data.entities.BlockedIpTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedIpTableRepository extends JpaRepository<BlockedIpTable, String> {
}
