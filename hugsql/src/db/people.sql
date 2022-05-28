-- :name create-people-table
-- :command :execute
-- :result :raw
create table people (
  id         SERIAL, 
  name       varchar(40),
  pet        varchar(40),
  created_at timestamp not null default current_timestamp
)

-- :name drop-people-table-if-exists :!
drop table if exists people

-- :name insert-person :! :n
insert into people (name, pet)
values (:name, :pet)

-- :name list-people :? :*
select * from people
