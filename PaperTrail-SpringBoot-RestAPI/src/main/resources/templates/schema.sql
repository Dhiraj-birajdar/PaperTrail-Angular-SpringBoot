#
#     create table book (
#         id integer generated by default as identity,
#         created_at timestamp(6) not null,
#         created_by varchar(255) not null,
#         last_modified timestamp(6),
#         last_modified_by varchar(255),
#         archived boolean not null,
#         author varchar(255),
#         book_cover varchar(255),
#         isbn varchar(255),
#         shareable boolean not null,
#         synopsis varchar(255),
#         title varchar(255),
#         primary key (id)
#     );
#
#     create table book_transaction_history (
#         id integer generated by default as identity,
#         created_at timestamp(6) not null,
#         created_by varchar(255) not null,
#         last_modified timestamp(6),
#         last_modified_by varchar(255),
#         return_approved boolean not null,
#         returned boolean not null,
#         user_id varchar(255),
#         book_id integer,
#         primary key (id)
#     );
#
#     create table feedback (
#         id integer generated by default as identity,
#         created_at timestamp(6) not null,
#         created_by varchar(255) not null,
#         last_modified timestamp(6),
#         last_modified_by varchar(255),
#         comment varchar(255),
#         note float(53) not null,
#         book_id integer,
#         primary key (id)
#     );
#
