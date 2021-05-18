package guru.springframework.repositories;

import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by jt on 6/17/17.
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryIT {

    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @Before
    public void setUp() throws Exception {
        this.unitOfMeasureReactiveRepository.deleteAll().block();
    }

    @Test
    public void testUnitOfMeasureSave() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription("new unit");
        unitOfMeasure.setId("1234");

        Mono<UnitOfMeasure> savedUnitOfMeasureMono = this.unitOfMeasureReactiveRepository.save(unitOfMeasure);

        assertEquals(Objects.requireNonNull(savedUnitOfMeasureMono.block()).getId(), unitOfMeasure.getId());
        assertEquals(this.unitOfMeasureReactiveRepository.count().block(), Long.valueOf(1L));
    }

    @Test
    public void testFindByDescription() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription("foo");

        this.unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

        UnitOfMeasure fetchedUom = unitOfMeasureReactiveRepository.findByDescription("foo").block();

        assertNotNull(fetchedUom.getId());
    }

}