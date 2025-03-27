package br.com.itau.ensurancequote.domains.models;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Product {

    private final String id;
    private final String name;
    private final LocalDateTime createdAt;
    private final boolean active;
    private final List<String> offers;

    public Product(
            String id,
            String name,
            LocalDateTime createdAt,
            boolean active,
            List<String> offers
    ) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.active = active;
        this.offers = (offers == null)
                ? Collections.emptyList()
                : List.copyOf(offers);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isActive() {
        return active;
    }

    public List<String> getOffers() {
        return offers;
    }

    public static class Builder {
        private String id;
        private String name;
        private LocalDateTime createdAt;
        private boolean active;
        private List<String> offers;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        public Builder offers(List<String> offers) {
            this.offers = offers;
            return this;
        }

        public Product build() {
            return new Product(id, name, createdAt, active, offers);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", active=" + active +
                ", offers=" + offers +
                '}';
    }
}
