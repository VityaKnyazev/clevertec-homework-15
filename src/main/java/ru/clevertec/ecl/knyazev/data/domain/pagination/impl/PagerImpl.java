package ru.clevertec.ecl.knyazev.data.domain.pagination.impl;

import org.springframework.stereotype.Component;
import ru.clevertec.ecl.knyazev.data.domain.pagination.Paging;
import ru.clevertec.ecl.knyazev.data.domain.pagination.Pager;

import java.util.Collection;

/**
 * Represents contract realization of Pager
 * for pagination organization from input object's collection
 */
@Component
public class PagerImpl implements Pager {

    /**
     * Get paginated object's collection
     * from input object's collection
     *
     * @param inObjCollection object's collection for modifying according
     *                        to given paging data
     * @param paging          paging data object
     * @param <T>             object;s collection type
     * @return paginated object's collection from input object's collection
     * that modified according to given paging data.
     * Represents split inObjCollection that satisfying paging data.
     *
     * If pagination not required - returns inObjCollection
     */
    @Override
    public <T> Collection<T> getPaginationResult(Collection<T> inObjCollection, Paging paging) {

        if (paging.usePaging()) {

            return inObjCollection.stream()
                    .skip(paging.getOffset())
                    .limit(paging.getLimit())
                    .toList();
        }

        return inObjCollection;
    }
}
