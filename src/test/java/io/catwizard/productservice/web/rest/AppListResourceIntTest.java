package io.catwizard.productservice.web.rest;

import io.catwizard.productservice.ProductServiceApp;

import io.catwizard.productservice.domain.AppList;
import io.catwizard.productservice.repository.AppListRepository;
import io.catwizard.productservice.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AppListResource REST controller.
 *
 * @see AppListResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductServiceApp.class)
public class AppListResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REGISTRATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REGISTRATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_JSONCONFIG = "AAAAAAAAAA";
    private static final String UPDATED_JSONCONFIG = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ENABLED = false;
    private static final Boolean UPDATED_IS_ENABLED = true;

    @Autowired
    private AppListRepository appListRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAppListMockMvc;

    private AppList appList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AppListResource appListResource = new AppListResource(appListRepository);
        this.restAppListMockMvc = MockMvcBuilders.standaloneSetup(appListResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppList createEntity(EntityManager em) {
        AppList appList = new AppList()
            .name(DEFAULT_NAME)
            .registration(DEFAULT_REGISTRATION)
            .jsonconfig(DEFAULT_JSONCONFIG)
            .isEnabled(DEFAULT_IS_ENABLED);
        return appList;
    }

    @Before
    public void initTest() {
        appList = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppList() throws Exception {
        int databaseSizeBeforeCreate = appListRepository.findAll().size();

        // Create the AppList
        restAppListMockMvc.perform(post("/api/app-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appList)))
            .andExpect(status().isCreated());

        // Validate the AppList in the database
        List<AppList> appListList = appListRepository.findAll();
        assertThat(appListList).hasSize(databaseSizeBeforeCreate + 1);
        AppList testAppList = appListList.get(appListList.size() - 1);
        assertThat(testAppList.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAppList.getRegistration()).isEqualTo(DEFAULT_REGISTRATION);
        assertThat(testAppList.getJsonconfig()).isEqualTo(DEFAULT_JSONCONFIG);
        assertThat(testAppList.isIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
    }

    @Test
    @Transactional
    public void createAppListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appListRepository.findAll().size();

        // Create the AppList with an existing ID
        appList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppListMockMvc.perform(post("/api/app-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appList)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AppList> appListList = appListRepository.findAll();
        assertThat(appListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = appListRepository.findAll().size();
        // set the field null
        appList.setName(null);

        // Create the AppList, which fails.

        restAppListMockMvc.perform(post("/api/app-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appList)))
            .andExpect(status().isBadRequest());

        List<AppList> appListList = appListRepository.findAll();
        assertThat(appListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAppLists() throws Exception {
        // Initialize the database
        appListRepository.saveAndFlush(appList);

        // Get all the appListList
        restAppListMockMvc.perform(get("/api/app-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].registration").value(hasItem(DEFAULT_REGISTRATION.toString())))
            .andExpect(jsonPath("$.[*].jsonconfig").value(hasItem(DEFAULT_JSONCONFIG.toString())))
            .andExpect(jsonPath("$.[*].isEnabled").value(hasItem(DEFAULT_IS_ENABLED.booleanValue())));
    }

    @Test
    @Transactional
    public void getAppList() throws Exception {
        // Initialize the database
        appListRepository.saveAndFlush(appList);

        // Get the appList
        restAppListMockMvc.perform(get("/api/app-lists/{id}", appList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appList.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.registration").value(DEFAULT_REGISTRATION.toString()))
            .andExpect(jsonPath("$.jsonconfig").value(DEFAULT_JSONCONFIG.toString()))
            .andExpect(jsonPath("$.isEnabled").value(DEFAULT_IS_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAppList() throws Exception {
        // Get the appList
        restAppListMockMvc.perform(get("/api/app-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppList() throws Exception {
        // Initialize the database
        appListRepository.saveAndFlush(appList);
        int databaseSizeBeforeUpdate = appListRepository.findAll().size();

        // Update the appList
        AppList updatedAppList = appListRepository.findOne(appList.getId());
        updatedAppList
            .name(UPDATED_NAME)
            .registration(UPDATED_REGISTRATION)
            .jsonconfig(UPDATED_JSONCONFIG)
            .isEnabled(UPDATED_IS_ENABLED);

        restAppListMockMvc.perform(put("/api/app-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAppList)))
            .andExpect(status().isOk());

        // Validate the AppList in the database
        List<AppList> appListList = appListRepository.findAll();
        assertThat(appListList).hasSize(databaseSizeBeforeUpdate);
        AppList testAppList = appListList.get(appListList.size() - 1);
        assertThat(testAppList.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppList.getRegistration()).isEqualTo(UPDATED_REGISTRATION);
        assertThat(testAppList.getJsonconfig()).isEqualTo(UPDATED_JSONCONFIG);
        assertThat(testAppList.isIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
    }

    @Test
    @Transactional
    public void updateNonExistingAppList() throws Exception {
        int databaseSizeBeforeUpdate = appListRepository.findAll().size();

        // Create the AppList

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAppListMockMvc.perform(put("/api/app-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appList)))
            .andExpect(status().isCreated());

        // Validate the AppList in the database
        List<AppList> appListList = appListRepository.findAll();
        assertThat(appListList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAppList() throws Exception {
        // Initialize the database
        appListRepository.saveAndFlush(appList);
        int databaseSizeBeforeDelete = appListRepository.findAll().size();

        // Get the appList
        restAppListMockMvc.perform(delete("/api/app-lists/{id}", appList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AppList> appListList = appListRepository.findAll();
        assertThat(appListList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppList.class);
    }
}
