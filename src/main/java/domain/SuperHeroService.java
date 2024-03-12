package domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.util.List;

public interface SuperHeroService {

    Page<SuperHero> getAllSuperHeroes(org.springframework.data.domain.Pageable pageable);

    SuperHero getSuperHeroById(Long id);
    List<SuperHero> getSuperHeroesByName(String name);

    SuperHero createSuperHero(Long id, String name);

    SuperHero updateSuperHero(Long id, String name);
    void deleteSuperHero(Long id);
}
