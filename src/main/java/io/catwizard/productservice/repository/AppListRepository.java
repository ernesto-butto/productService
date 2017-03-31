package io.catwizard.productservice.repository;

import io.catwizard.productservice.domain.AppList;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AppList entity.
 */
@SuppressWarnings("unused")
public interface AppListRepository extends JpaRepository<AppList,Long> {

    @Query("select appList from AppList appList where appList.user.login = ?#{principal.username}")
    List<AppList> findByUserIsCurrentUser();

}
