alter table billing_info add   bill_theme  varchar(300)  not null  comment "账单主题";
/**
    Modify user table
    by:hsy  2021-7-28
 */
alter table usr_info add column user_status varchar(2) not null default'00' comment '用户状态：01-正常、02-冻结';
alter table usr_info add column password_error_num int(1) not null default 0 comment '密码错误次数：当错误次数达五次时，用户状态改为冻结';
