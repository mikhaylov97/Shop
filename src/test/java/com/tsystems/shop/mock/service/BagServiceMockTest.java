package com.tsystems.shop.mock.service;

import com.tsystems.shop.dao.impl.ProductDaoImpl;
import com.tsystems.shop.model.Attribute;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.Size;
import com.tsystems.shop.model.dto.BagProductDto;
import com.tsystems.shop.service.impl.BagServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class BagServiceMockTest {

    private Product product = new Product();
    private Attribute attribute = new Attribute();
    private Size size1 = new Size();
    private Set<Size> sizes = new HashSet<>();
    private BagProductDto product1 = new BagProductDto();
    private BagProductDto product2 = new BagProductDto();
    private BagProductDto product3 = new BagProductDto();

    @Mock
    ProductDaoImpl productDao;

    @InjectMocks
    BagServiceImpl bagService;

    @Before
    public void init() {
        size1.setId(1L);
        size1.setAvailableNumber("2");
        size1.setSize("40");
        sizes.add(size1);

        attribute.setId(1L);
        attribute.setSizes(sizes);
        attribute.setDescription("Some description");

        product.setId(1L);
        product.setName("Tee");
        product.setImage("/image/1");
        product.setPrice("199");
        product.setActive(true);
        product.setAttributes(attribute);
        product.setCategory(null);

        product1.setId(1L);
        product1.setAmount(2);
        product1.setSizeId(1L);
        product1.setTotalPrice(192);


        product2.setId(2L);
        product2.setAmount(2);
        product2.setSizeId(1L);
        product2.setTotalPrice(200);

        product3.setId(3L);
        product3.setAmount(2);
        product3.setSizeId(1L);
        product3.setTotalPrice(12);
    }

    @Test
    public void calculateTotalPriceMockTest1() {
        //do
        String result = bagService.calculateTotalPrice(new ArrayList<>(Arrays.asList(product1, product2, product3)));
        //check
        Assert.assertNotNull(result);
        Assert.assertEquals("404", result);
    }

    @Test
    public void calculateTotalPriceMockTest2() {
        //do
        String result = bagService.calculateTotalPrice(new ArrayList<>(Collections.singletonList(product1)));
        //check
        Assert.assertNotNull(result);
        Assert.assertEquals("192", result);
    }

    @Test
    public void calculateTotalPriceMockTest3() {
        //do
        String result = bagService.calculateTotalPrice(null);
        //check
        Assert.assertNotNull(result);
        Assert.assertEquals("0", result);
    }

    @Test
    public void calculateTotalPriceMockTest4() {
        //do
        String result = bagService.calculateTotalPrice(new ArrayList<>());
        //check
        Assert.assertNotNull(result);
        Assert.assertEquals("0", result);
    }

    @Test
    public void calculateTotalPriceMockTest5() {
        //do
        String result = bagService.calculateTotalPrice(new ArrayList<>(Arrays.asList(product2, product3)));
        //check
        Assert.assertNotNull(result);
        Assert.assertEquals("212", result);
    }

    @Test
    public void checkIfProductAlreadyExistMockTest1() {
        //do
        boolean result = bagService.checkIfProductAlreadyExist(new ArrayList<>(), 1);
        //check
        Assert.assertFalse(result);
    }

    @Test(expected = NullPointerException.class)
    public void checkIfProductAlreadyExistMockTest2() {
        //do
        boolean result = bagService.checkIfProductAlreadyExist(null, 1);
    }

    @Test
    public void checkIfProductAlreadyExistMockTest3() {
        //do
        boolean result = bagService.checkIfProductAlreadyExist(new ArrayList<>(Arrays.asList(product1, product2, product3)), 2);
        //check
        Assert.assertTrue(result);
    }

    @Test
    public void checkIfProductAlreadyExistMockTest4() {
        //do
        boolean result = bagService.checkIfProductAlreadyExist(new ArrayList<>(Arrays.asList(product1, product2, product3)), 4);
        //check
        Assert.assertFalse(result);
    }

    @Test
    public void createBagProductFromProductMockTest1() {
        //prepare
        Mockito.when(productDao.findSizeById(1L)).thenReturn(size1);
        //do
        BagProductDto result = bagService.createBagProductFromProduct(product, 1L);
        //check
        Mockito.verify(productDao).findSizeById(1L);
        Assert.assertNotNull(result);
        Assert.assertEquals(product.getId(), result.getId());
        Assert.assertEquals(product.getName(), result.getName());
        Assert.assertEquals(size1.getSize(), result.getSize());
        Assert.assertEquals(size1.getId(), result.getSizeId());
    }

    @Test(expected = NullPointerException.class)
    public void createBagProductFromProductMockTest2() {
        //do
        bagService.createBagProductFromProduct(null, 1);
    }

    @Test
    public void convertBagProductDtoToProductMockTest1() {
        //prepare
        Mockito.when(productDao.findProductById(1L)).thenReturn(product);
        //do
        Product result = bagService.convertBagProductDtoToProduct(product1);
        //verify
        Mockito.verify(productDao).findProductById(1L);
        Assert.assertNotNull(result);
        Assert.assertEquals(product.getId(), result.getId());
        Assert.assertEquals(product.getName(), result.getName());

    }

    @Test
    public void convertBagProductDtoToProductMockTest2() {
        //do
        Product result = bagService.convertBagProductDtoToProduct(null);
        //check
        Mockito.verifyZeroInteractions(productDao);
        Assert.assertNull(result);
    }

    @Test(expected = NullPointerException.class)
    public void deleteFromBagMockTest1() {
        //do
        bagService.deleteFromBag(1, 1, null);
    }

    @Test
    public void deleteFromBagMockTest2() {
        //prepare
        List<BagProductDto> bag = new ArrayList<>(Arrays.asList(product1, product2, product3));
        int bagSizeBefore = bag.size();
        int sizeAmountBefore = Integer.parseInt(size1.getAvailableNumber());
        Mockito.when(productDao.findProductById(product.getId())).thenReturn(product);
        //do
        bagService.deleteFromBag(product.getId(), size1.getId(), bag);
        int bagSizeAfter = bag.size();
        int sizeAmountAfter = Integer.parseInt(size1.getAvailableNumber());
        //check
        Mockito.verify(productDao).findProductById(product.getId());
        Mockito.verify(productDao).saveProduct(product);
        Assert.assertTrue(bagSizeBefore == bagSizeAfter + 1);
        Assert.assertTrue(sizeAmountAfter == sizeAmountBefore + product1.getAmount());
    }

    @Test
    public void deleteFromBagMockTest3() {
        //prepare
        List<BagProductDto> bag = new ArrayList<>(Arrays.asList(product1, product2, product3));
        int bagSizeBefore = bag.size();
        int sizeAmountBefore = Integer.parseInt(size1.getAvailableNumber());
        Mockito.when(productDao.findProductById(product.getId())).thenReturn(product);
        //do
        bagService.deleteFromBag(10L, size1.getId(), bag);
        int bagSizeAfter = bag.size();
        int sizeAmountAfter = Integer.parseInt(size1.getAvailableNumber());
        //check
        Mockito.verifyZeroInteractions(productDao);
        Assert.assertTrue(bagSizeBefore == bagSizeAfter);
        Assert.assertTrue(sizeAmountAfter == sizeAmountBefore);
    }
}
