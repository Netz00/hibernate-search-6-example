package com.netz00.hibernatesearch6example.services;

import com.netz00.hibernatesearch6example.services.utils.IndexingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledJob {
    @Autowired
    IndexingService indexingService;

    @Scheduled(cron = "${cron-expression.mass-indexer}")
    public void massIndexing() {
        indexingService.massIndexer();
    }
}
