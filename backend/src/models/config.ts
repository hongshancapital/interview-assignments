import { Sequelize } from 'sequelize';
import configs from '../configs';

// Option 3: Passing parameters separately (other dialects)
const sequelize = new Sequelize(configs.sequelize);

export default sequelize;