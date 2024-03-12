package domain;

import application.SuperHeroServiceImpl;
import application.exception.SuperHeroNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SuperHeroServiceTest {

    @Mock
    private SuperHeroRepository superHeroRepository;

    @InjectMocks
    private SuperHeroServiceImpl superHeroService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllSuperHeroes() {
        List<SuperHero> superHeroes = new ArrayList<>();
        superHeroes.add(new SuperHero(1L, "SuperWoman"));
        superHeroes.add(new SuperHero(2L, "Spiderwoman"));
        Page<SuperHero> superHeroPage = new PageImpl<>(superHeroes);

        when(superHeroRepository.findAll(any(Pageable.class))).thenReturn(superHeroPage);

        Page<SuperHero> result = superHeroService.getAllSuperHeroes(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void getSuperHeroById() {
        Long heroId = 1L;
        SuperHero superHero = new SuperHero(heroId, "SuperWoman");

        when(superHeroRepository.findById(heroId)).thenReturn(Optional.of(superHero));

        SuperHero result = superHeroService.getSuperHeroById(heroId);

        assertNotNull(result);
        assertEquals("Superman", result.getName());
    }

    @Test
    void getSuperHeroesByName() {
        String heroName = "Super";
        List<SuperHero> superHeroes = new ArrayList<>();
        superHeroes.add(new SuperHero(1L, "SuperWoman"));
        superHeroes.add(new SuperHero(2L, "Supergirl"));

        when(superHeroRepository.findByNameContaining(heroName)).thenReturn(superHeroes);

        List<SuperHero> result = superHeroService.getSuperHeroesByName(heroName);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void createSuperHero() {
        Long heroId = 1L;
        String heroName = "Superman";
        SuperHero superHero = new SuperHero(heroId, heroName);

        when(superHeroRepository.save(superHero)).thenReturn(superHero);

        SuperHero result = superHeroService.createSuperHero(heroId, heroName);

        assertNotNull(result);
        assertEquals(heroName, result.getName());
    }

    @Test
    void updateSuperHero() {
        Long heroId = 1L;
        String updatedName = "UpdatedSuperman";
        SuperHero existingHero = new SuperHero(heroId, "SuperWoman");

        when(superHeroRepository.findById(heroId)).thenReturn(Optional.of(existingHero));
        when(superHeroRepository.save(existingHero)).thenReturn(existingHero);

        SuperHero result = superHeroService.updateSuperHero(heroId, updatedName);

        assertNotNull(result);
        assertEquals(updatedName, result.getName());
    }

    @Test
    void updateSuperHeroNotFound() {
        Long heroId = 1L;
        String updatedName = "UpdatedSuperman";

        when(superHeroRepository.findById(heroId)).thenReturn(Optional.empty());

        assertThrows(SuperHeroNotFoundException.class, () -> {
            superHeroService.updateSuperHero(heroId, updatedName);
        });
    }

    @Test
    void deleteSuperHero() {
        Long heroId = 1L;

        assertDoesNotThrow(() -> superHeroService.deleteSuperHero(heroId));
        verify(superHeroRepository, times(1)).deleteById(heroId);
    }
}
