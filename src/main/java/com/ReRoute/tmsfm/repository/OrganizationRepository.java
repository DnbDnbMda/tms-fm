package com.ReRoute.tmsfm.repository;

import com.ReRoute.tmsfm.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

//@Repository
public interface OrganizationRepository extends JpaRepository<Organization, UUID> {


}
