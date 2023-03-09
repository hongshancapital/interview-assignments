import defaultConfig from './config.default';

export type { ConfigScheme } from './config.default';

const config: typeof defaultConfig = { ...defaultConfig };

const appEnv = process.env.APP_ENV;

if (appEnv) {
    console.log(`Load ${appEnv} configs...`, 'APP');

    try {
        // eslint-disable-next-line @typescript-eslint/no-var-requires
        const envConfigs = require(__dirname + `./config.${appEnv}.ts`).default;

        Object.assign(config, envConfigs);
    } catch (e) {
        console.error(`Load ${appEnv} configs failed!`, e, 'APP');
    }
} else {
    console.error('APP_ENV is required!', '', 'APP');
}

try {
    // eslint-disable-next-line @typescript-eslint/no-var-requires
    const envConfigs = require('./config.local').default;

    Object.assign(config, envConfigs);
} catch {
    /* empty */
}

export default config;
