package ru.clevertec.ecl.knyazev.data.domain.pagination;

import java.util.Collection;

/**
 * Represents contract for creating pagination output
 * from object's collection
 */
public interface Pager {

    /**
     *
     * Split inObjCollection that satisfying paging data
     *
     * @param inObjCollection object's collection for modifying according
     *                        to given paging data
     * @param paging          data object
     * @return collection of T objects that modified according
     * to given paging data. Represents split inObjCollection that
     * satisfying paging data
     */
    <T> Collection<T> getPaginationResult(Collection<T> inObjCollection, Paging paging);

}
