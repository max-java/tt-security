create table if not exists article
(
    article_id bigint not null unique,
    title varchar(64),
    text clob
);

insert into article
values (1, 'Chek-in Check-out', 'Everybody should be checked in when they come <br> everybody should checkout before leave'),
       (2, 'Force remote check-in', 'If system glitched, administrator can change person status remotely by performing <br><tt>curl -X PATCH "http://localhost:8082/api/card/5555" -H "accept: */*" -H "Content-Type: text/plain" -d "true"</tt>');