import app from './app';
import baseConfig from './config/base.config';
import Database from './db/db';

Database.getInstance();

try {
    app.listen(baseConfig.PORT, () => {
        console.log(`URL Shortening Service running at PORT ${baseConfig.PORT}`);
    });
} catch (error) {
    if (error instanceof Error) {
        console.log(`Error occured: (${error.message})`)
    }
}