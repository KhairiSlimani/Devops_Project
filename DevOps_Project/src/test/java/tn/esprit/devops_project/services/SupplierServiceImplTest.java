package tn.esprit.devops_project.services;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.SupplierServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@ActiveProfiles("test")

public class SupplierServiceImplTest {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierServiceImpl supplierService;


    @Test
    @DatabaseSetup("/data-set/supplier-data.xml")
    public void testRetrieveAllSuppliers() {
        List<Supplier> suppliers = supplierService.retrieveAllSuppliers();
        assertEquals(suppliers.size(),1 );
    }

    @Test
    @DatabaseSetup("/data-set/supplier-data.xml")
    public void testAddSupplier() {
        Supplier supplier = new Supplier();
        supplier.setCode("S3");
        supplier.setLabel("Supplier 3");
        Supplier addedSupplier = supplierService.addSupplier(supplier);
        assertNotNull(addedSupplier.getIdSupplier());
    }

    @Test
    @DatabaseSetup("/data-set/supplier-data.xml")
    public void testUpdateSupplier() {
        Long supplierId = 1L; // Adjust to a valid supplier ID in your dataset
        Supplier supplier = supplierRepository.getOne(supplierId);
        supplier.setLabel("Updated Supplier 1");
        Supplier updatedSupplier = supplierService.updateSupplier(supplier);
        assertEquals("Updated Supplier 1", updatedSupplier.getLabel());
    }

    @Test
    @DatabaseSetup("/data-set/supplier-data.xml")
    public void testDeleteSupplier() {
        Long supplierId = 1L; // Adjust to a valid supplier ID in your dataset
        supplierService.deleteSupplier(supplierId);
        Supplier deletedSupplier = supplierRepository.findById(supplierId).orElse(null);
        assertNull(deletedSupplier);
    }

    @Test
    @DatabaseSetup("/data-set/supplier-data.xml") // Load test data if needed
    void retrieveSupplier_ExistingId() {
        Long existingId = 1L;

        Supplier supplier = supplierService.retrieveSupplier(existingId);

        // You can add more assertions here to check the retrieved supplier object.
        assertEquals(existingId, supplier.getIdSupplier());
        // Add more assertions as needed.
    }

    @Test
    void retrieveSupplier_NonExistingId() {
        Long nonExistingId = 100L;

        // Mock the repository to return an empty optional when findById is called with the non-existing ID.
        SupplierRepository supplierRepository = mock(SupplierRepository.class);
        when(supplierRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Create a new SupplierServiceImpl instance with the mocked repository.
        SupplierServiceImpl supplierService = new SupplierServiceImpl(supplierRepository);

        // Perform the test and check if it throws an exception.
        assertThrows(IllegalArgumentException.class, () -> supplierService.retrieveSupplier(nonExistingId));
    }
}