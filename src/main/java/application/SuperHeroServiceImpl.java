package application;

import application.exception.SuperHeroNotFoundException;
import domain.SuperHero;
import domain.SuperHeroRepository;
import domain.SuperHeroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;  // Importa el tipo correcto
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperHeroServiceImpl implements SuperHeroService {

    private static final Logger logger = LoggerFactory.getLogger(SuperHeroServiceImpl.class);

    private final SuperHeroRepository superHeroRepository;

    @Autowired
    public SuperHeroServiceImpl(SuperHeroRepository superHeroRepository) {
        this.superHeroRepository = superHeroRepository;
    }

    @Override
    public Page<SuperHero> getAllSuperHeroes(Pageable pageable) {
        return superHeroRepository.findAll(pageable);
    }

    @Override
    public SuperHero getSuperHeroById(Long id) {
        return superHeroRepository.findById(id).orElse(null);
    }

    @Override
    public List<SuperHero> getSuperHeroesByName(String name) {
        return superHeroRepository.findByNameContaining(name);
    }

    @Override
    public SuperHero createSuperHero(Long id, String name) {
        SuperHero superHero = new SuperHero(id, name);
        return superHeroRepository.save(superHero);
    }

    @Override
    public SuperHero updateSuperHero(Long id, String name) {
        logger.info("Updating Superhero with id: {}", id);

        SuperHero superHero = superHeroRepository.findById(id)
                .orElseThrow(() -> new SuperHeroNotFoundException("Superhero not found with id: " + id));

        superHero.setName(name);
        SuperHero updatedSuperHero = superHeroRepository.save(superHero);

        logger.info("Superhero updated successfully: {}", updatedSuperHero);
        return updatedSuperHero;
    }

    @Override
    public void deleteSuperHero(Long id) {
        superHeroRepository.deleteById(id);
    }
}
