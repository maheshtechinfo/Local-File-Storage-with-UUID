package com.orchasp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orchasp.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

}
