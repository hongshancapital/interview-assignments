#!/usr/bin/env node
'use strict';

const models = require('../models');

models.sequelize.sync().done(() => {
   console.log('sync db done and waiting for 1 minitue to exit,Or CTRL+C to exit.');
},(err) => {
   console.log(err);
});
