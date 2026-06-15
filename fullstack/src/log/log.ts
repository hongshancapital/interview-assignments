import config from 'config';
import log4js from 'log4js';

log4js.configure({
    appenders: {
        file: {
            type: 'file',
            filename: 'logs/main.log',
            maxLogSize: '2M',
            layout: {
                type: 'basic'
            }
        },
        out: {
            type: 'stdout',
            layout: {
                type: 'coloured'
            }
        }
    },
    categories: {
        default: {
            appenders: ['file', 'out'],
            level: config.get<string>('log.logLevel')
        }
    },
});

const logger = log4js.getLogger();
logger.info('Logger inited.');

export default logger;