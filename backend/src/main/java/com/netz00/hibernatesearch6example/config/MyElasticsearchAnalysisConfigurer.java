package com.netz00.hibernatesearch6example.config;

import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurationContext;
import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurer;

public class MyElasticsearchAnalysisConfigurer implements ElasticsearchAnalysisConfigurer {

    @Override
    public void configure(ElasticsearchAnalysisConfigurationContext context) {

        context.analyzer("edgeNgram").custom()
                .tokenizer("whitespace")
                .tokenFilters("lowercase", "edge_ngram_def");

        context.tokenFilter("edge_ngram_def")
                .type("edge_ngram")
                .param("min_gram", 1)
                .param("max_gram", 4)
                .param("preserve_original", true);
    }
}
