import defaultConfig from './config.default';

export type { ConfigScheme } from './config.default';

const config: typeof defaultConfig = { ...defaultConfig };

const appEnv = process.env.APP_ENV;

if (appEnv) {
    console.log(`Load ${appEnv} configs...`, 'APP');

    try {
        const envConfigs = require(`./config.${appEnv}.ts`).default;

        Object.assign(config, envConfigs);
    } catch (e) {
        console.error(`Load ${appEnv} configs failed!`, e, 'APP');
    }
} else {
    console.error(`APP_ENV is required!`, '', 'APP');
}

export default config;
