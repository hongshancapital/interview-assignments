import { EggAppConfig, EggAppInfo ,PowerPartial } from 'egg';

export default (appInfo: EggAppInfo) => {
    const config = {} as PowerPartial<EggAppConfig>;
    config.keys = appInfo.name +  '_6787555797';

    config.security = {
        csrf: {
            enable: false,
        },
    };

    //joi
    config.joi = {
        options: {
            //过滤不存在 schema 中的字段
            stripUnknown: true,
        },
        locale: {
            'zh-cn': {}
        },
        throw: true, // throw immediately when capture exception
        throwHandle: (error) => {
            return error;
        }, // error message format when throw is true
        errorHandle: (error) => {
            return error;
        }, // error message format when throw is false
        resultHandle: (result) => {
            return result;
        } // fromat result
    };
    // the return config will combines to EggAppConfig
    return {
        ...config,
    };
}
