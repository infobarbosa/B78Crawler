CREATE KEYSPACE crawler
    WITH replication = {'class': 'NetworkTopologyStrategy', 'MACBOOKPRO': 3, 'UBUNTU': 3}
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
