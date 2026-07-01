import * as express from "express";
import pgPromise from "pg-promise";
import shortUniqueId from "short-unique-id";
import validUrl from "valid-url";

export const register = ( app: express.Application ) => {
    // console.log(process.env);
    const port = parseInt( process.env.PGPORT || "5432", 10 );
    const config = {
        database: process.env.PGDATABASE || "postgres",
        host: process.env.PGHOST || "localhost",
        port,
        user: process.env.PGUSER || "postgres",
        password: process.env.PGPASSWORD || ""
    };

    const pgp = pgPromise();
    const db = pgp( config );
    // console.log(db);

    app.post( `/api/domain/shorten`, async ( req: any, res ) => {
        // console.log(req);
        const originUrl = req.body.url;
        console.log(originUrl);
        if (!validUrl.isUri(originUrl)) {
            return res.status(401).json('Invalid base URL')
        }
        const links = await db.any( `
            SELECT  short_url
            FROM    links
            WHERE   origin_url = $[originUrl]`, { originUrl } );
        // console.log(links);
        if (links.length > 0) {
            return res.json({
                short_url: links[0].short_url
            })
        }
        const uid = new shortUniqueId({length: 8});
        const code = uid();
        console.log(code);
        console.log(Date.now());
        await db.none('INSERT INTO links(origin_url, short_url, created_at) VALUES(${originUrl}, $<code>, $/createdAt/)', {
            originUrl,
            code,
            createdAt: Date.now()/1000
        });

        return res.json({
            short_url: code
        })
    });



    app.post( `/api/domain/reccovery`, async ( req: any, res ) => {
        const code = req.body.code;
        const links = await db.any( `
            SELECT  origin_url
            FROM    links
            WHERE   short_url = $[code]`, { code } );
        // console.log(links);
        if (links.length > 0) {
            return res.json({
                origin_url: links[0].origin_url
            })
        }
    } );


};