module.exports = (sequelize, Sequelize) => {
  const LinkModel = sequelize.define('link', {
    origin: {
      type: Sequelize.STRING
    },
    short: {
      type: Sequelize.STRING,
      unique: true
    },
  });

  return LinkModel;
};
