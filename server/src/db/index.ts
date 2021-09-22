import sqlite3 from 'sqlite3';

const DBSOURCE = "db.sqlite"

let db = new sqlite3.Database(DBSOURCE, (err) => {
    if (err) {
      // Cannot open database
      console.error(err.message)
      throw err
    }else{
        console.log('Connected to the SQLite database.')
        db.run(`CREATE TABLE toolbox_prefs (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name text, 
            tools text,
            CONSTRAINT name_unique UNIQUE (name)
            )`,
        (err) => {
            console.log(err);
        });  
    }
});


export default db;
