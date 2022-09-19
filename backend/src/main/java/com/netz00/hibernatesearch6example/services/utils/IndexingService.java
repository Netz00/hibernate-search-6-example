package com.netz00.hibernatesearch6example.services.utils;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * @author Božo Durdov
 * Date: 29-Jun-2022
 * Service defining Mass Indexer
 */
@Service
public class IndexingService {


    private final EntityManager em;

    public IndexingService(EntityManager em) {
        this.em = em;
    }

    /**
     * Method for reindexing all entities with MassIndexer
     */

    @Transactional
    public void massIndexer() {
        SearchSession searchSession = Search.session(em);
        try {
            searchSession.massIndexer()
                    .startAndWait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
