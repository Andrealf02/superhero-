package infraestructure;

import domain.SuperHero;
import domain.SuperHeroService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;  // Importa el tipo correcto
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/superheroes")
public class SuperHeroController {

    private final SuperHeroService superHeroService;

    public SuperHeroController(SuperHeroService superHeroService) {
        this.superHeroService = superHeroService;
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<SuperHero>> getAllSuperHeroesPaged(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        Page<SuperHero> superHeroes = superHeroService.getAllSuperHeroes(PageRequest.of(page, size));
        return new ResponseEntity<>(superHeroes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuperHero> getSuperHeroById(@PathVariable Long id) {
        SuperHero superHero = superHeroService.getSuperHeroById(id);
        return superHero != null ?
                new ResponseEntity<>(superHero, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SuperHero>> getSuperHeroesByName(@RequestParam String name) {
        List<SuperHero> superHeroes = superHeroService.getSuperHeroesByName(name);
        return new ResponseEntity<>(superHeroes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SuperHero> createSuperHero(@RequestBody SuperHero superHero) {
        SuperHero createdSuperHero = superHeroService.createSuperHero(superHero.getId(), superHero.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSuperHero);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuperHero> updateSuperHero(@PathVariable Long id, @RequestBody String updatedName) {
        SuperHero updatedSuperHero = superHeroService.updateSuperHero(id, updatedName);
        return updatedSuperHero != null ?
                new ResponseEntity<>(updatedSuperHero, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSuperHero(@PathVariable Long id) {
        superHeroService.deleteSuperHero(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
