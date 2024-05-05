package com.inventivit.dreamapp.repository;

import com.inventivit.dreamapp.entity.Case;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseRepository extends JpaRepository<Case, Long> {
	Case findByTitle(String title);
}
