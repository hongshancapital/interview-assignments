import passport from "passport"
import {Strategy as LocalStrategy } from 'passport-local'
import { Request, Response, NextFunction } from "express";
import {db} from '../db'

const db1=db;
passport.serializeUser<any, any>((req, user:any, done) => {
    done(undefined, user.email);
});

passport.deserializeUser((email, done) => {
    let user=db1.users.find(()=>(item:any)=>{item.email===email});
    done(undefined,user);
});

passport.use(new LocalStrategy({ usernameField: "email" }, (email, password, done) => {
    db.users.push({email:email,password:password});
    return done(undefined, {email:email,password:password});
}));

export const isAuthenticated = (req: Request, res: Response, next: NextFunction) => {
    if (req.isAuthenticated()) {
        return next();
    }
    res.redirect("/login");
};