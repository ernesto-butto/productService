package io.catwizard.productservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.catwizard.productservice.domain.AppList;

import io.catwizard.productservice.repository.AppListRepository;
import io.catwizard.productservice.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AppList.
 */
@RestController
@RequestMapping("/api")
public class AppListResource {

    private final Logger log = LoggerFactory.getLogger(AppListResource.class);

    private static final String ENTITY_NAME = "appList";
        
    private final AppListRepository appListRepository;

    public AppListResource(AppListRepository appListRepository) {
        this.appListRepository = appListRepository;
    }

    /**
     * POST  /app-lists : Create a new appList.
     *
     * @param appList the appList to create
     * @return the ResponseEntity with status 201 (Created) and with body the new appList, or with status 400 (Bad Request) if the appList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/app-lists")
    @Timed
    public ResponseEntity<AppList> createAppList(@Valid @RequestBody AppList appList) throws URISyntaxException {
        log.debug("REST request to save AppList : {}", appList);
        if (appList.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new appList cannot already have an ID")).body(null);
        }
        AppList result = appListRepository.save(appList);
        return ResponseEntity.created(new URI("/api/app-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /app-lists : Updates an existing appList.
     *
     * @param appList the appList to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated appList,
     * or with status 400 (Bad Request) if the appList is not valid,
     * or with status 500 (Internal Server Error) if the appList couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/app-lists")
    @Timed
    public ResponseEntity<AppList> updateAppList(@Valid @RequestBody AppList appList) throws URISyntaxException {
        log.debug("REST request to update AppList : {}", appList);
        if (appList.getId() == null) {
            return createAppList(appList);
        }
        AppList result = appListRepository.save(appList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, appList.getId().toString()))
            .body(result);
    }

    /**
     * GET  /app-lists : get all the appLists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of appLists in body
     */
    @GetMapping("/app-lists")
    @Timed
    public List<AppList> getAllAppLists() {
        log.debug("REST request to get all AppLists");
        List<AppList> appLists = appListRepository.findAll();
        return appLists;
    }

    /**
     * GET  /app-lists/:id : get the "id" appList.
     *
     * @param id the id of the appList to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the appList, or with status 404 (Not Found)
     */
    @GetMapping("/app-lists/{id}")
    @Timed
    public ResponseEntity<AppList> getAppList(@PathVariable Long id) {
        log.debug("REST request to get AppList : {}", id);
        AppList appList = appListRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(appList));
    }

    /**
     * DELETE  /app-lists/:id : delete the "id" appList.
     *
     * @param id the id of the appList to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/app-lists/{id}")
    @Timed
    public ResponseEntity<Void> deleteAppList(@PathVariable Long id) {
        log.debug("REST request to delete AppList : {}", id);
        appListRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
