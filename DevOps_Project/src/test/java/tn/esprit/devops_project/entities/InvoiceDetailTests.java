package tn.esprit.devops_project.entities;
import org.junit.jupiter.api.Test;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.InvoiceDetail;
import tn.esprit.devops_project.entities.Product;

import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
public class InvoiceDetailTests {

    @Test
    public void testAttributesAndRelationships() {
        // Create an InvoiceDetail
        InvoiceDetail invoiceDetail = new InvoiceDetail();
        invoiceDetail.setIdInvoiceDetail(1L);
        invoiceDetail.setQuantity(5);
        invoiceDetail.setPrice(50.0f);

        // Test attributes
        assertEquals(1L, invoiceDetail.getIdInvoiceDetail());
        assertEquals(5, invoiceDetail.getQuantity());
        assertEquals(50.0f, invoiceDetail.getPrice());

        // Test relationships
        Product product = new Product();
        invoiceDetail.setProduct(product);
        assertEquals(product, invoiceDetail.getProduct());

        Invoice invoice = new Invoice();
        invoiceDetail.setInvoice(invoice);
        assertEquals(invoice, invoiceDetail.getInvoice());

        // Additional tests to check relationships and attributes
    }
}
