import { Application } from 'egg';

// const urlPattern = new RegExp('(http(|s)://){0,1}([\w-]+\.)+[\w-]+(/)?')

module.exports = (app: Application) => {
    const Joi = app.Joi;
    return {
        encodeUrl: Joi.object().keys({
            url: Joi.string(), // Joi.string().regex(urlPattern),
        })
    };
}