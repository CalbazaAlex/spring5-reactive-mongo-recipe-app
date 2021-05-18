package guru.springframework.repositories;

import guru.springframework.domain.Category;
import guru.springframework.repositories.reactive.CategoryReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Before
    public void setUp() throws Exception {
        this.categoryReactiveRepository.deleteAll().block();
    }

    @Test
    public void testSave() {
        Category category = new Category();
        category.setDescription("foo");
        category.setId("1234");

        this.categoryReactiveRepository.save(category).block();

        assertEquals(this.categoryReactiveRepository.count().block(), Long.valueOf(1L));
    }

    @Test
    public void testFindByDescription() {
        Category category = new Category();
        category.setDescription("foo");
//        category.setId("1234");

        this.categoryReactiveRepository.save(category).block();

        Category fetchedCategory = categoryReactiveRepository.findByDescription("foo").block();

        assertNotNull(fetchedCategory.getId());
    }
}
