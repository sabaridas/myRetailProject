package com.target.myRetail.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.target.myRetail.errors.MyRetailException;
import com.target.myRetail.model.CurrentPrice;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDaoTest {

    @Mock
    private MongoCollection mockCollection;
    @Mock
    private MongoDatabase mockDB;
    @InjectMocks
    private ProductDaoImpl productDao;

    @Before
    public void initMocks() throws MyRetailException {
        when(mockDB.getCollection(anyString())).thenReturn(mockCollection);
    }

    @Test
    public void getProductPrice_validProdId_success() throws MyRetailException {
        final double DELTA = 1e-15;
        FindIterable iterable = mock(FindIterable.class);
        Document prodPriceDoc = new Document("_id", "13860428").append("price", 13.49d).append("currency","USD");
        when(mockCollection.find(eq("_id", "13860428"))).thenReturn(iterable);
        when(iterable.first()).thenReturn(prodPriceDoc);

        CurrentPrice currentPrice = productDao.getProductPrice("13860428");

        assertEquals(currentPrice.getValue(),13.49d,DELTA);
    }

    @Test(expected = MyRetailException.class)
    public void getProductPrice_productNotFound_failure() throws MyRetailException {
        final double DELTA = 1e-15;
        FindIterable iterable = mock(FindIterable.class);
        MongoCursor cursor = mock(MongoCursor.class);
        Document prodPriceDoc = new Document("_id", "13860428").append("price", 13.49d).append("currency","USD");
        when(mockCollection.find(eq("_id", "13860428"))).thenReturn(iterable);
        when(iterable.first()).thenReturn(null);

        CurrentPrice currentPrice = productDao.getProductPrice("13860428");
    }
}
