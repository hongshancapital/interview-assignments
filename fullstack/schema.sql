CREATE TABLE shortlink.short_origin_table (
	shortlinkId varchar(8) NOT NULL,
	originalLink TEXT NOT NULL,
	createTime TIMESTAMP NOT NULL
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;
