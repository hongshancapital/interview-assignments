import fs from "fs";

const files = fs.readdirSync(__dirname).filter((file: string) => file.indexOf("index") < 0);

const controllers = {} as any;

for (const file of files) {
    if (file.toLowerCase().endsWith("js")) {
        const controller = require(`./${file}`);
        controllers[`${file.replace(/\.js/, "")}`] = controller;
    }
}

export default controllers;
