import express from "express";
import shortDomain from "../controllers/shortDomain";

const routers = express.Router();

routers.route('/getDomain/:shortDomain').get(shortDomain.getDomain);
routers.route('/createShortDomain').post(shortDomain.createShortDomain);

export default routers