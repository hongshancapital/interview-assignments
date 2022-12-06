import express from 'express';
import bodyParser from "body-parser";
import passport from "passport";
import path from 'path'
import * as domainServe from './controller/domain'
import * as userServe from './controller/user'
import * as homeServe from './controller/home'
import * as passportConfig from "./config/passport";
import session from "express-session";
import flash from "express-flash";

console.log(process.env.NDOE_DEV);
const app = express();
app.use(
    express.static(path.join(__dirname, "public"), { maxAge: 31557600000 })
);

app.use(session({ secret: 'sessionsecret',     resave: true,
saveUninitialized: true,cookie: { maxAge: 60000 }}))
app.use(passport.initialize());
app.use(passport.session());
app.use(flash());


app.set("views", path.join(__dirname, "./views"));
app.set("view engine", "pug");
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.get("/", homeServe.index);
app.get("/login",userServe.getLogin)
app.post("/login",userServe.postLogin)
app.get("/domain",passportConfig.isAuthenticated,domainServe.getDomain)
app.post("/domain/tiny", passportConfig.isAuthenticated, domainServe.postCreateTinyDomain);
app.post("/domain/huge", passportConfig.isAuthenticated, domainServe.postCreateHugeDomain);

app.set("port", process.env.PORT || 3000);

export default app;