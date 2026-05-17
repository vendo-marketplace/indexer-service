package com.vendo.indexer_service.adapter.product.out;

import com.vendo.indexer_service.adapter.product.out.elasticsearch.index.ProductReindexAdapter;
import com.vendo.indexer_service.domain.product.Product;
import com.vendo.indexer_service.test_utils.builder.ProductDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductReindexAdapterTest {

    @Captor
    ArgumentCaptor<List<IndexQuery>> captor;
    @Mock
    private ElasticsearchOperations operations;
    @InjectMocks
    private ProductReindexAdapter productReindexAdapter;

    @Test
    void reindex_shouldSuccessfullyReindex() {
        List<Product> products = List.of(ProductDataBuilder.withAllFields().build());

        when(operations.bulkIndex(anyList(), any(IndexCoordinates.class)))
                .thenReturn(List.of());

        productReindexAdapter.reindex(products);

        verify(operations).bulkIndex(captor.capture(), any(IndexCoordinates.class));

        assertThat(productReindexAdapter.isProcessing()).isFalse();
        assertThat(captor.getValue().size()).isEqualTo(1);
    }

    @Test
    void reindex_shouldLockMethod_whenAlreadyInProgress() throws InterruptedException {
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch holdLatch = new CountDownLatch(1);
        List<Product> products = List.of(ProductDataBuilder.withAllFields().build());

        when(operations.bulkIndex(anyList(), any(IndexCoordinates.class))).thenAnswer(invocation -> {
            startLatch.countDown();
            holdLatch.await();
            return List.of();
        });

        AtomicBoolean inProgress = new AtomicBoolean(false);

        Thread firstCall = new Thread(() -> productReindexAdapter.reindex(products));
        Thread secondCall = new Thread(() -> {
            productReindexAdapter.reindex(products);
            inProgress.set(productReindexAdapter.isProcessing());
        });

        firstCall.start();
        startLatch.await();

        secondCall.start();
        Thread.sleep(200);

        holdLatch.countDown();

        firstCall.join();
        secondCall.join();

        assertThat(inProgress.get()).isTrue();
        assertThat(productReindexAdapter.isProcessing()).isFalse();
    }
}
