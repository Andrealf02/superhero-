package domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuperHeroRepository extends JpaRepository<SuperHero, Long> {
    List<SuperHero> findByNameContaining(String name);
}
