CREATE DATABASE IF NOT EXISTS `file-center` DEFAULT CHARACTER SET = utf8mb4;
Use `file-center`;


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info` (
  `id` varchar(32) NOT NULL COMMENT '文件md5',
  `name` varchar(128) NOT NULL COMMENT '文件名',
  `is_img` tinyint(1) NOT NULL COMMENT '是否是图片',
  `content_type` varchar(128) NOT NULL COMMENT '类型',
  `size` int(11) NOT NULL COMMENT '文件大小',
  `path` varchar(255) DEFAULT NULL COMMENT '物理路径',
  `url` varchar(1024) NOT NULL COMMENT '链接',
  `source` varchar(32) NOT NULL COMMENT '文件资源',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



-- ----------------------------
-- Table structure for file_info_extend
-- ----------------------------
DROP TABLE IF EXISTS `file_info_extend`;
CREATE TABLE `file_info_extend` (
  `id` varchar(32) NOT NULL COMMENT '文件md5',
  `guid` varchar(32) NOT NULL COMMENT '文件分片ID',
  `name` varchar(128) NOT NULL COMMENT '扩展文件名',
  `size` int(11) NOT NULL COMMENT '文件大小',
  `path` varchar(255) DEFAULT NULL COMMENT '物理路径',
  `url` varchar(1024) NOT NULL COMMENT '链接',
  `source` varchar(32) NOT NULL COMMENT '文件资源',
  `file_id` varchar(32) DEFAULT NULL COMMENT '文件ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件扩展表';
