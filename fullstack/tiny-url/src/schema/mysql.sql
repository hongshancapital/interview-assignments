
CREATE TABLE `segments` (
  `app_tag` varchar(32) NOT NULL,
  `max_id` bigint NOT NULL,
  `step` bigint NOT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY(`app_tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='segmentid业务ID池';

--
-- 转存表中的数据 `segments`
--

INSERT INTO `segments` (`app_tag`, `max_id`, `step`, `update_time`) VALUES
('tiny_id', 0, 10000, '2022-04-27 15:06:00');
