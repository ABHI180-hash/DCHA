package com.dcha.repository;

import com.dcha.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    List<Language> findByStateId(Long stateId);
    List<Language> findByNameContainingIgnoreCase(String name);
}
