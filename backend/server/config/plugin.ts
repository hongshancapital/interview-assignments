import { EggPlugin } from 'egg';

const plugin: EggPlugin = {
    sequelize: {
        enable: true,
        package: 'egg-sequelize',
    },
    routerPlus: {
        enable: true,
        package: 'egg-router-plus',
    },
    joi: {
        enable: true,
        package: 'egg-joi',
    },
    static: true,
    ejs: {
        enable: true,
        package: 'egg-view-ejs',
    },
};

export default plugin;
