import { check } from "express-validator"

export const URLCheck = check('url').isURL().isLength({ min: 10, max: 1000 }).withMessage('must be a url');
export const TOKENCHECK = check('url').isAlphanumeric().isLength({ min: 1, max: 8 }).withMessage('must be a alpha number');