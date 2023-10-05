import db from '../src/db/db';

const database = 'test_demo_database';

module.exports = async () => {
  try {
    await db.raw(`DROP DATABASE IF EXISTS ${database}`);
  } catch (e) {
    console.log(e);
  } finally {
    db.destroy();
    process.exit(1);
  }
};
