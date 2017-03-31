package io.catwizard.productservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AppList.
 */
@Entity
@Table(name = "app_list")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "registration")
    private LocalDate registration;

    @Column(name = "jsonconfig")
    private String jsonconfig;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @OneToMany(mappedBy = "appList")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "appList")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AppList name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getRegistration() {
        return registration;
    }

    public AppList registration(LocalDate registration) {
        this.registration = registration;
        return this;
    }

    public void setRegistration(LocalDate registration) {
        this.registration = registration;
    }

    public String getJsonconfig() {
        return jsonconfig;
    }

    public AppList jsonconfig(String jsonconfig) {
        this.jsonconfig = jsonconfig;
        return this;
    }

    public void setJsonconfig(String jsonconfig) {
        this.jsonconfig = jsonconfig;
    }

    public Boolean isIsEnabled() {
        return isEnabled;
    }

    public AppList isEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public AppList products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public AppList addProduct(Product product) {
        this.products.add(product);
        product.setAppList(this);
        return this;
    }

    public AppList removeProduct(Product product) {
        this.products.remove(product);
        product.setAppList(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public AppList tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public AppList addTag(Tag tag) {
        this.tags.add(tag);
        tag.setAppList(this);
        return this;
    }

    public AppList removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.setAppList(null);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public AppList user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AppList appList = (AppList) o;
        if (appList.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, appList.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AppList{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", registration='" + registration + "'" +
            ", jsonconfig='" + jsonconfig + "'" +
            ", isEnabled='" + isEnabled + "'" +
            '}';
    }
}
