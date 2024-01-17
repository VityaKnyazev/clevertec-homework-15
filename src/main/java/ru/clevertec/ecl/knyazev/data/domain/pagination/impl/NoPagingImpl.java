package ru.clevertec.ecl.knyazev.data.domain.pagination.impl;

import ru.clevertec.ecl.knyazev.data.domain.pagination.Paging;
import ru.clevertec.ecl.knyazev.data.domain.pagination.exception.PagingException;

/**
 * Represents realization of Paging contract
 * when paging not using
 */
public class NoPagingImpl implements Paging {
    @Override
    public Integer getPage() throws PagingException {
        throw new PagingException();
    }

    @Override
    public Integer getLimit() throws PagingException {
        throw new PagingException();
    }

    @Override
    public Integer getOffset() throws PagingException {
        throw new PagingException();
    }

    @Override
    public Boolean usePaging() throws PagingException {
        return false;
    }
}
