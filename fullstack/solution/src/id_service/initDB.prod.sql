use short_url;

drop table if exists ranged_sequence;
create table ranged_sequence (range_id smallint unsigned unique not null, sequence integer unsigned not null);

delimiter ;;
drop procedure if exists init;
create procedure init()
begin
    set @r = 0;
    while @r < 0xffff do
        insert into ranged_sequence values
        (@r,      0), (@r+0x01, 0), (@r+0x02, 0), (@r+0x03, 0),
        (@r+0x04, 0), (@r+0x05, 0), (@r+0x06, 0), (@r+0x07, 0),
        (@r+0x08, 0), (@r+0x09, 0), (@r+0x0a, 0), (@r+0x0b, 0),
        (@r+0x0c, 0), (@r+0x0d, 0), (@r+0x0e, 0), (@r+0x0f, 0)
        ;
        set @r=@r+0x10;
    end while;
end
;;

delimiter ;

call init();

alter table ranged_sequence add primary key (range_id);
alter table ranged_sequence add index (sequence);

