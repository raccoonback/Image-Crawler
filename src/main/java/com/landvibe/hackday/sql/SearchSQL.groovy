package com.landvibe.hackday.sql

class SearchSQL {
    public static final String SELECT_SEARCH = '''
        SELECT id, word, is_crawled, created_at, file_name 
        FROM SEARCH 
        WHERE is_crawled IS FALSE;
    '''

    public static final String UPDATE_SEARCH = '''
        UPDATE SEARCH 
        SET word = :word, is_crawled = :crawled, created_at = :createdAt, file_name = :fileName 
        WHERE id = :id
    '''
}
