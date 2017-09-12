package com.tsystems.shop.mock.service;

import com.tsystems.shop.dao.impl.CategoryDaoImpl;
import com.tsystems.shop.dao.impl.ProductDaoImpl;
import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.dto.CategoryDto;
import com.tsystems.shop.service.impl.CategoryServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceMockTest {

    private Category category = new Category();
    private List<Category> categories = new ArrayList<>();
    private List<Product> products = new ArrayList<>();

    @Mock
    CategoryDaoImpl categoryDao;

    @Mock
    ProductDaoImpl productDao;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Before
    public void init() {
        category.setActive(true);
        category.setId(0);
        category.setParent(null);
        category.setName("Name");
        category.setHierarchyNumber("1");

        Category category1 = new Category();
        category1.setActive(true);
        category1.setId(1);
        category1.setParent(null);
        category1.setName("Category1");
        category1.setHierarchyNumber("2");

        Category category2 = new Category();
        category2.setActive(false);
        category2.setId(2);
        category2.setParent(null);
        category2.setName("Category2");
        category2.setHierarchyNumber("2");

        Category category3 = new Category();
        category3.setActive(true);
        category3.setId(3);
        category3.setParent(null);
        category3.setName("Category3");
        category3.setHierarchyNumber("2");

        categories.addAll(Arrays.asList(category1, category2, category3));
    }

    @Test
    public void saveNewCategoryMockTest1() {
        //prepare
        Mockito.when(categoryDao.saveCategory(category)).thenReturn(category);
        //do
        categoryService.saveNewCategory(category);
        //check
        Mockito.verify(categoryDao).saveCategory(category);
    }

    @Test
    public void saveNewCategoryMockTest2() {
        //do
        Category result = categoryService.saveNewCategory(category);
        //check
        Assert.assertNull(result);
    }

    @Test
    public void findCategoriesMockTest1() {
        //prepare
        Mockito.when(categoryDao.findCategories()).thenReturn(new ArrayList<>());
        //do
        categoryService.findCategories(true);
        //check
        Mockito.verify(categoryDao).findCategories();
    }

    @Test
    public void findCategoriesMockTest2() {
        //prepare
        Mockito.when(categoryDao.findCategories()).thenReturn(new ArrayList<>());
        //
        categoryService.findCategories(false);
        //check
        Mockito.verify(categoryDao).findCategories();
    }

    @Test
    public void findCategoriesMockTest3() {
        //prepare
        Mockito.when(categoryDao.findCategories()).thenReturn(categories);
        //do
        List<Category> result = categoryService.findCategories(true);
        //check
        Mockito.verify(categoryDao).findCategories();
        Assert.assertTrue(result.size() == 3);
    }

    @Test
    public void findCategoriesMockTest4() {
        //prepare
        Mockito.when(categoryDao.findCategories()).thenReturn(categories);
        //do
        List<Category> result = categoryService.findCategories(false);
        //check
        Mockito.verify(categoryDao).findCategories();
        Assert.assertTrue(result.size() == 2);
    }

    @Test
    public void findCategoryByIdMockTest1() {
        //prepare
        Mockito.when(categoryDao.findCategoryById(String.valueOf(categories.get(0).getId()))).thenReturn(categories.get(0));
        //do
        categoryService.findCategoryById(String.valueOf(categories.get(0).getId()),true);
        //check
        Mockito.verify(categoryDao).findCategoryById(String.valueOf(categories.get(0).getId()));
    }

    @Test
    public void findCategoryByIdMockTest2() {
        //prepare
        Mockito.when(categoryDao.findCategoryById(String.valueOf(categories.get(0).getId()))).thenReturn(categories.get(0));
        //do
        categoryService.findCategoryById(String.valueOf(categories.get(0).getId()),false);
        //check
        Mockito.verify(categoryDao).findCategoryById(String.valueOf(categories.get(0).getId()));
    }

    @Test
    public void findCategoryByIdMockTest3() {
        //prepare
        Mockito.when(categoryDao.findCategoryById(String.valueOf(categories.get(0).getId()))).thenReturn(categories.get(0));
        //do
        Category result = categoryService.findCategoryById(String.valueOf(categories.get(0).getId()),true);
        //check
        Mockito.verify(categoryDao).findCategoryById(String.valueOf(categories.get(0).getId()));
        Assert.assertNotNull(result);
        Assert.assertEquals(categories.get(0).getId(), result.getId());
    }

    @Test
    public void findCategoryByIdMockTest4() {
        //prepare
        Mockito.when(categoryDao.findCategoryById(String.valueOf(categories.get(0).getId()))).thenReturn(categories.get(0));
        //do
        Category result = categoryService.findCategoryById(String.valueOf(categories.get(0).getId()),false);
        //check
        Mockito.verify(categoryDao).findCategoryById(String.valueOf(categories.get(0).getId()));
        Assert.assertNotNull(result);
        Assert.assertEquals(categories.get(0).getId(), result.getId());
    }

    @Test
    public void findCategoryByIdMockTest5() {
        //prepare
        Mockito.when(categoryDao.findCategoryById(String.valueOf(categories.get(1).getId()))).thenReturn(categories.get(1));
        //do
        Category result = categoryService.findCategoryById(String.valueOf(categories.get(1).getId()),false);
        //check
        Mockito.verify(categoryDao).findCategoryById(String.valueOf(categories.get(1).getId()));
        Assert.assertNull(result);
    }

    @Test
    public void findRootCategoriesMockTest1() {
        //prepare
        Mockito.when(categoryDao.findRootCategories()).thenReturn(categories);
        //do
        categoryService.findRootCategories();
        //check
        Mockito.verify(categoryDao).findRootCategories();
    }

    @Test
    public void findChildsMockTest1() {
        //prepare
        Mockito.when(categoryDao.findChilds(category)).thenReturn(categories);
        //do
        categoryService.findChilds(category, true);
        //check
        Mockito.verify(categoryDao).findChilds(category);
    }

    @Test
    public void findChildsMockTest2() {
        //prepare
        Mockito.when(categoryDao.findChilds(category)).thenReturn(categories);
        //do
        categoryService.findChilds(category, false);
        //check
        Mockito.verify(categoryDao).findChilds(category);
    }

    @Test
    public void findChildsMockTest3() {
        //prepare
        Mockito.when(categoryDao.findChilds(category)).thenReturn(categories);
        //do
        List<Category> result = categoryService.findChilds(category, true);
        //check
        Mockito.verify(categoryDao).findChilds(category);
        Assert.assertTrue(result.size() == 3);
    }

    @Test
    public void findChildsMockTest4() {
        //prepare
        Mockito.when(categoryDao.findChilds(category)).thenReturn(categories);
        //do
        List<Category> result = categoryService.findChilds(category, false);
        //check
        Mockito.verify(categoryDao).findChilds(category);
        Assert.assertTrue(result.size() == 2);
    }

    @Test
    public void findCategoriesByHierarchyNumberMockTest1() {
        //prepare
        Mockito.when(categoryDao.findCategoriesByHierarchyNumber("1")).thenReturn(categories);
        //do
        List<Category> result = categoryService.findCategoriesByHierarchyNumber("1", false);
        //check
        Mockito.verify(categoryDao).findCategoriesByHierarchyNumber("1");
    }

    @Test
    public void findCategoriesByHierarchyNumberMockTest2() {
        //prepare
        Mockito.when(categoryDao.findCategoriesByHierarchyNumber("1")).thenReturn(categories);
        //do
        List<Category> result = categoryService.findCategoriesByHierarchyNumber("1", true);
        //check
        Mockito.verify(categoryDao).findCategoriesByHierarchyNumber("1");
    }

    @Test
    public void findCategoriesByHierarchyNumberMockTest3() {
        //prepare
        Mockito.when(categoryDao.findCategoriesByHierarchyNumber("1")).thenReturn(categories);
        //do
        List<Category> result = categoryService.findCategoriesByHierarchyNumber("1", true);
        //check
        Mockito.verify(categoryDao).findCategoriesByHierarchyNumber("1");
        Assert.assertTrue(result.size() == categories.size());
    }

    @Test
    public void findCategoriesByHierarchyNumberMockTest4() {
        //prepare
        Mockito.when(categoryDao.findCategoriesByHierarchyNumber("1")).thenReturn(categories);
        //do
        List<Category> result = categoryService.findCategoriesByHierarchyNumber("1", false);
        //check
        Mockito.verify(categoryDao).findCategoriesByHierarchyNumber("1");
        Assert.assertTrue(result.size() == categories.stream().filter(Category::getActive).count());
    }

    @Test
    public void convertCategoryToCategoryDtoMockTest1() {
        //prepare
        Mockito.when(productDao.findProductsByCategory(category)).thenReturn(products);
        //do
        categoryService.convertCategoryToCategoryDto(category);
        //check
        Mockito.verify(productDao).findProductsByCategory(category);
    }

    @Test(expected = NullPointerException.class)
    public void convertCategoryToCategoryDtoMockTest2() {
        //do
        categoryService.convertCategoryToCategoryDto(null);
    }

    @Test
    public void convertCategoryToCategoryDtoMockTest3() {
        //prepare
        Mockito.when(productDao.findProductsByCategory(category)).thenReturn(products);
        Mockito.when(categoryDao.findCategoryById("0")).thenReturn(category);
        //do
        CategoryDto result = categoryService.convertCategoryToCategoryDto(category);
        //check
        Mockito.verify(categoryDao).findCategoryById("0");
        Assert.assertNotNull(result);
        Assert.assertEquals(category.getId(), result.getId());
        Assert.assertEquals(category.getName(), result.getName());
        Assert.assertEquals(category.getHierarchyNumber(), String.valueOf(result.getHierarchyNumber()));
        Assert.assertEquals(0, result.getNumberOfSales());
        Assert.assertEquals(0, result.getNumberOfProducts());
    }

    @Test(expected = NullPointerException.class)
    public void convertCategoriesToCategoriesDtoMockTest1() {
        //do
        List<CategoryDto> result = categoryService.convertCategoriesToCategoriesDto(null);
    }

    @Test
    public void convertCategoriesToCategoriesDtoMockTest2() {
        //do
        List<CategoryDto> result = categoryService.convertCategoriesToCategoriesDto(categories);
        //check
        Assert.assertNotNull(result);
        Assert.assertTrue(result.get(0).getId() == categories.get(0).getId());
        Assert.assertTrue(result.get(2).getName().equals(categories.get(2).getName()));
        Assert.assertTrue(result.get(1).getHierarchyNumber() == Integer.parseInt(categories.get(1).getHierarchyNumber()));
    }
}
