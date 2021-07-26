/**
  create table usr_info
  by:hsy  2021:07:21
 */
drop table if exists usr_info;
CREATE TABLE `usr_info` (
                            `user_id` int(9) NOT NULL COMMENT '用户id',
                            `password` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
                            `user_name` varchar(50) NOT NULL COMMENT '用户名称',
                            `mobile_no` varchar(11) NOT NULL COMMENT '用户手机',
                            `user_role` varchar(2) NOT NULL COMMENT '用户角色：01-管理员、02-普通用户',
                            `user_remarks` varchar(300) DEFAULT NULL COMMENT '用户备注',
                            `last_login_time` datetime DEFAULT NULL COMMONT '最后登录时间',
                            `create_time` datetime NOT NULL COMMENT '创建时间',
                            `modify_time` datetime NOT NULL COMMENT '修改时间',
                            PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户信息表' ;
