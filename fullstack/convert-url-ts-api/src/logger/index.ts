import { configure,getLogger } from "log4js";
configure({
    appenders: { cheese: { type: "file", filename: "cheese.log" } },
    categories: { default: { appenders: ["cheese"], level: "debug" } },
  })
export default getLogger()