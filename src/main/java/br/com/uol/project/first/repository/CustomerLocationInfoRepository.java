package br.com.uol.project.first.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.uol.project.first.entity.CustomerLocationInfo;

@Repository
public interface CustomerLocationInfoRepository extends JpaRepository<CustomerLocationInfo, Long> {

}
