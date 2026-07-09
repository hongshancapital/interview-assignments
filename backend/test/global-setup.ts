import db from '../src/db/db';

const database = 'test_demo_database';

// Create the database
async function createTestDatabase() {
  try {
    await db.raw(`DROP DATABASE IF EXISTS ${database}`);
    await db.raw(`CREATE DATABASE ${database}`);
    await db.raw(`use ${database};`);
    console.log('create-database-done');
  } catch (error: any) {
    throw new Error(error);
  }
  // finally {
  //   await db.destroy();
  // }
}

// Seed the database with schema and data
async function seedTestDatabase() {
  try {
    await db.migrate.latest();
    await db.seed.run();
  } catch (error: any) {
    throw new Error(error);
  } // finally {
  //   await db.destroy();
  // }
}

module.exports = async () => {
  try {
    await createTestDatabase();
    await seedTestDatabase();
    console.log('Test database created successfully');
  } catch (error: any) {
    process.exit(1);
  }
};
