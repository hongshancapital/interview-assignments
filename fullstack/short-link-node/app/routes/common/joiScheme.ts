import Joi from "joi"

const addLinkJoiScheme = {
  oriUrl: Joi.string().required().error(new Error("oriUrl没有经过验证")),
  appid: Joi.string().required().error(new Error("appid没有经过验证"))
}

const updateLinkJoiScheme = {
  oriUrl: Joi.string().required().error(new Error("oriUrl没有经过验证")),
  appid: Joi.string().required().error(new Error("appid没有经过验证")),
  slink: Joi.string().required().error(new Error("slink没有经过验证"))
}

const getLinkJoiScheme = {
  appid: Joi.string().required().error(new Error("appid没有经过验证")),
  slink: Joi.string().required().error(new Error("slink没有经过验证"))
}

export { addLinkJoiScheme, updateLinkJoiScheme, getLinkJoiScheme }
