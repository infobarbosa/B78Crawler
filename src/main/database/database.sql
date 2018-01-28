CREATE KEYSPACE crawler
    WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}
    AND durable_writes = true;

CREATE TABLE crawler.pages (
    parent_page text,
    child_page text,
    PRIMARY KEY ((parent_page), child_page)
);

CREATE TABLE crawler.page_detail (
    page text,
    page_detail text,
    PRIMARY KEY ((page))
);
