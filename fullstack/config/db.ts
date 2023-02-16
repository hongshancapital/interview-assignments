import mysql from 'mysql';

export default mysql.createConnection({
	host:"127.0.0.1",
	user:"yong",
    password:"scdt!23",
	port:3306,
	database:"scdt"
});

