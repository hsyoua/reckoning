/**
  create table usr_info
  by:hsy  2021:07:21
 */
drop table if exists usr_info;
CREATE TABLE `usr_info` (
                            `user_id` int(9) NOT NULL COMMENT '用户id',
                            `password` varchar(18) NOT NULL COMMENT '用户密码',
                            `user_name` varchar(300) NOT NULL COMMENT '用户名称',
                            `mobile_no` varchar(11) NOT NULL COMMENT '用户手机',
                            `user_role` varchar(2) NOT  NULL COMMENT '用户角色：01-管理员、02-普通用户',
                            `user_remarks` varchar(300) DEFAULT NULL COMMENT '用户备注',
                            `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
                            `create_time` datetime NOT NULL COMMENT '创建时间',
                            `modify_time` datetime NOT NULL COMMENT '修改时间',
                            PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户信息表' ;



/**
  create table billing_info
  by:hsy  2021:07:27
 */
drop table if exists billing_info;
CREATE TABLE `billing_info` (
                                `billing_id` bigint(12) NOT NULL COMMENT '账单id',
                                `amount` decimal(20,2) NOT NULL COMMENT '金额',
                                `people_num` INTEGER(3) NOT NULL COMMENT '人数',
                                `billing_status` varchar(2) NOT NULL COMMENT '账单状态：01-待结算、02-结算中、03-结算完成（账单关闭）',
                                `allocation_method` varchar(2) DEFAULT '01' COMMENT '分摊方式：01-AA',
                                `consumption_notes` varchar(300) DEFAULT NULL COMMENT '消费备注',
                                `consumer_address` varchar(300) DEFAULT NULL COMMENT '消费地址',
                                `create_user_id` integer(9) NOT NULL COMMENT '创建用户ID',
                                `dissipate` datetime NOT NULL COMMENT '消费时间',
                                `create_time` datetime NOT NULL COMMENT '创建时间',
                                `modify_time` datetime NOT NULL COMMENT '修改时间',
                                PRIMARY KEY (`billing_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账单信息表';




/**
  create table usr_bill_association
  by:hsy  2021:07:21
 */
drop table if exists usr_bill_association;
CREATE TABLE `usr_bill_association` (
                                        `association_id` bigint(12) NOT NULL COMMENT '关联id',
                                        `user_id` INTEGER(9) NOT NULL COMMENT '用户id',
                                        `billing_id` bigint(12) NOT NULL COMMENT '账单id',
                                        `user_participation_type` VARCHAR(2) NOT NULL COMMENT '用户参与类型：01-支出者、02-分摊者',
                                        `apportioned_amount` decimal(20,2) NOT NULL COMMENT '分摊金额',
                                        `payment_status` VARCHAR(2) not null COMMENT '支付状态：00-无需支付、01-待支付、02-已支付',
                                        `create_time` datetime NOT NULL COMMENT '创建时间',
                                        `modify_time` datetime NOT NULL COMMENT '修改时间',
                                        PRIMARY KEY (`association_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账单关联表';


/**
  create table review_info
  by:hsy  2021:08:03
 */
drop table if exists review_info;
CREATE TABLE `review_info` (
                               `review_id` bigint(12) NOT NULL COMMENT '审核id',
                               `reviewer_id` INTEGER(9) NOT NULL COMMENT '审核人id',
                               `billing_id` bigint(9) NOT NULL COMMENT '账单id',
                               `review_time` datetime default NULL COMMENT '审核时间',
                               `review_status` VARCHAR(2) not null COMMENT '审核状态：00-待审核、01-审核通过、02-审核拒绝',
                               `rejection_reason` VARCHAR(300) DEFAULT NULL COMMENT '拒绝原因：当审核拒绝时，拒绝原因不能为空',
                               `create_time` datetime NOT NULL COMMENT '创建时间',
                               `modify_time` datetime NOT NULL COMMENT '修改时间',
                               PRIMARY KEY (`review_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账单审核表';
