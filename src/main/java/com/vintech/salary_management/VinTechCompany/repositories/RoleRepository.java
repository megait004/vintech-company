package com.vintech.salary_management.VinTechCompany.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vintech.salary_management.VinTechCompany.models.RoleModel;

import jakarta.transaction.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {
    RoleModel findByRole(String role);

    RoleModel findByroleSlug(String roleSlug);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM role WHERE id = :id", nativeQuery = true)
    void deleteRoleByIdForce(@Param("id") Long id);

    @Query("SELECT a.username FROM AccountModel a WHERE a.role.id = :roleId")
    List<String> findAccountsByRoleId(@Param("roleId") Long roleId);
}
