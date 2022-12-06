import express from 'express';
import e, { Request, Response, NextFunction } from "express";
import {db} from '../db';
const siphash24 = require('siphash24')
import getIPAdress from '../util/ip'
import { Client } from 'redis-om'
import { REDIS_OPTION } from '../environment/secrets';

export const getDomain = async (req: Request, res: Response, next: NextFunction): Promise<void> => {
    res.render("domain", {title: "域名service"});
};

export const postCreateTinyDomain = async (req: Request, res: Response, next: NextFunction): Promise<void> => {
        let hash = siphash24(Buffer.from(req.body.longurl), Buffer.from('012345678012345678'));
        let key = String.fromCharCode.apply(null, hash)
        const client = new Client()
        await client.open(REDIS_OPTION)
        const aString = await client.execute(['auth','root'])
        let domain=await client.get(key);
        if(aString==='OK'&& domain===null) {
           await client.set(key, req.body.longurl)
        } 
        res.render("domain", {
            title: "域名service",
            shortresult: `http://${getIPAdress()}/${String.fromCharCode.apply(null, hash)}`
        });
        await client.close();
};

export const postCreateHugeDomain = async (req: Request, res: Response, next: NextFunction): Promise<void> => {
        const str=req.body.shorturl;
        if(str) {
            const key = str.substring(str.lastIndexOf('/') + 1)
            const client = new Client()
            await client.open(REDIS_OPTION)

            const aString = await client.execute(['auth','root'])
            let domain=await client.get(key);
            
            if(aString==='OK'&& domain) {
                res.render("domain", {
                    title: "域名service",
                    longresult: domain
                });
            } else {
                res.render("domain", {
                    title: "域名service",
                    longresult: "not found"
                });
            }
        }
};