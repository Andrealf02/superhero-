package infraestructure;

import application.exception.SuperHeroNotFoundException;
import domain.SuperHero;
import domain.SuperHeroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SuperHeroControllerTest {

    @Mock
    private SuperHeroService superHeroService;

    @InjectMocks
    private SuperHeroController superHeroController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getSuperHeroById() {
        Long heroId = 1L;
        SuperHero superHero = new SuperHero(heroId, "Superman");

        when(superHeroService.getSuperHeroById(heroId)).thenReturn(superHero);

        ResponseEntity<SuperHero> result = superHeroController.getSuperHeroById(heroId);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getSuperHeroByIdNotFound() {
        Long heroId = 1L;

        when(superHeroService.getSuperHeroById(heroId)).thenReturn(null);

        ResponseEntity<SuperHero> result = superHeroController.getSuperHeroById(heroId);

        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getSuperHeroesByName() {
        String heroName = "Super";
        List<SuperHero> superHeroes = new ArrayList<>();
        superHeroes.add(new SuperHero(1L, "Superman"));
        superHeroes.add(new SuperHero(2L, "Supergirl"));

        when(superHeroService.getSuperHeroesByName(heroName)).thenReturn(superHeroes);

        ResponseEntity<List<SuperHero>> result = superHeroController.getSuperHeroesByName(heroName);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void createSuperHero() {
        Long heroId = 1L;
        String heroName = "Superman";
        SuperHero superHero = new SuperHero(heroId, heroName);

        when(superHeroService.createSuperHero(heroId, heroName)).thenReturn(superHero);

        ResponseEntity<SuperHero> result = superHeroController.createSuperHero(superHero);

        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    void updateSuperHero() {
        Long heroId = 1L;
        String updatedName = "UpdatedSuperman";
        SuperHero existingHero = new SuperHero(heroId, "Superman");

        when(superHeroService.updateSuperHero(heroId, updatedName)).thenReturn(existingHero);

        ResponseEntity<SuperHero> result = superHeroController.updateSuperHero(heroId, updatedName);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void updateSuperHeroNotFound() {
        Long heroId = 1L;
        String updatedName = "UpdatedSuperman";

        when(superHeroService.updateSuperHero(heroId, updatedName)).thenThrow(new SuperHeroNotFoundException("Superhero not found with id: " + heroId));

        ResponseEntity<SuperHero> result = superHeroController.updateSuperHero(heroId, String.valueOf(new SuperHero()));

        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void deleteSuperHero() {
        Long heroId = 1L;

        ResponseEntity<Void> result = superHeroController.deleteSuperHero(heroId);

        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}
