import { Sequelize, STRING } from 'sequelize';

export const linkModel = (sequelize: Sequelize) => {
  const LinkModel = sequelize.define('link', {
    origin: {
      type: STRING
    },
    short: {
      type: STRING,
      unique: true
    },
  });

  return LinkModel;
};
