const { DataTypes, Sequelize } = require('sequelize');
module.exports = {
    id: {
        type: DataTypes.UUID,
        primaryKey: true,
        defaultValue: Sequelize.UUIDV4
    },
    longName: {
        type: DataTypes.STRING,
        allowNull: false,
        field: 'long_name'
    },
    shortName: {
        type: DataTypes.STRING,
        allowNull: false,
        field: 'short_name'
    }
}
export { };