Select * from Book
Select * from error_entity

Delete from error_entity

Delete from Book

commit

rollback


ALTER TABLE distributors ADD COLUMN address varchar(30);

CREATE TABLE ErrorResponse (
    status  varchar ,
    statusMessage varchar,
    created_at timestamp DEFAULT current_timestamp
)


