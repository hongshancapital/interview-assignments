/*
  mvc架构，加载module
*/

const router = module.exports = require('express').Router()
const modelConfig = require('../model-config.json')

const readModels = () => {
  const models = []
  for (const key in modelConfig) {
    models.push(modelConfig[key].name)
  }
  return models
}

const models = readModels()

for (const it of models) {
  router.use(`/${it}`, require(`../models/${it}.js`))
}
