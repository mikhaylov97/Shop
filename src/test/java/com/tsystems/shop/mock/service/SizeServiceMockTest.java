package com.tsystems.shop.mock.service;

import com.tsystems.shop.dao.impl.ProductDaoImpl;
import com.tsystems.shop.model.Attribute;
import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.Size;
import com.tsystems.shop.service.impl.SizeServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SizeServiceMockTest {

    private Product product;
    private Attribute attribute;
    private Category category;
    private Set<Size> sizes = new HashSet<>();

    @Mock
    ProductDaoImpl productDao;

    @InjectMocks
    SizeServiceImpl sizeService;

    @Before
    public void init() {
        Size size1 = new Size();
        size1.setId(1L);
        size1.setSize("38");
        size1.setAvailableNumber("2");
        Size size2 = new Size();
        size2.setId(2L);
        size2.setSize("39");
        size2.setAvailableNumber("3");

        sizes.add(size1);
        sizes.add(size2);

        category = new Category();
        category.setActive(true);
        category.setId(1L);
        category.setHierarchyNumber("1");
        category.setName("MEN'S");
        category.setParent(null);

        attribute = new Attribute();
        attribute.setId(1L);
        attribute.setDescription("Some description");
        attribute.setSizes(sizes);

        product = new Product();
        product.setName("Tee");
        product.setActive(true);
        product.setImage("/image/1");
        product.setId(1L);
        product.setPrice("199");
        product.setCategory(category);
        product.setAttributes(attribute);
    }

    @Test
    public void getAvailableAmountOfSizeMockTest1() {
        //prepare
        when(productDao.findAvailableAmountOfSize(1)).thenReturn(123);
        //do
        int result = sizeService.getAvailableAmountOfSize(1);
        //check
        verify(productDao).findAvailableAmountOfSize(1);
        Assert.assertEquals(123, result);
    }

    @Test
    public void findSizesFromProductsMockTest1() {
        //do
        Set<String> sizeSet = sizeService.findSizesFromProducts(new ArrayList<>(Collections.singletonList(product)));
        //check
        Assert.assertNotNull(sizeSet);
        Assert.assertTrue(sizeSet.size() != 0);
        Assert.assertTrue(sizeSet.size() == 2);
        Assert.assertTrue(sizeSet.contains("38"));
        Assert.assertTrue(sizeSet.contains("39"));
    }

    @Test
    public void findSizesFromProductsMockTest2() {
        //do
        Set<String> sizeSet = sizeService.findSizesFromProducts(new ArrayList<>());
        //check
        Assert.assertNotNull(sizeSet);
        Assert.assertTrue(sizeSet.size() == 0);
    }

    @Test
    public void findSizesFromProductsMockTest3() {
        //do
        Set<String> sizeSet = sizeService.findSizesFromProducts(null);
        //check
        Assert.assertNotNull(sizeSet);
        Assert.assertTrue(sizeSet.size() == 0);
    }
}
