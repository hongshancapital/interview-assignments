/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.up = async function (knex) {
  await knex.schema.createTable('short_url', function (table) {
    table.increments('id')
      .primary()
      .unique();
    table.string('short_code', 8)
      .notNullable()
      .unique()
      .comment('短码');
    table.string('origin_url', 200)
      .notNullable()
      .comment('对应的原始的长链接');
    table.string('app_id', 20)
      .notNullable()
      .comment('所属应用id');
    table.datetime('created_at')
      .default(knex.fn.now())
      .notNullable()
      .comment('创建时间');
  });
};

/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.down = async function (knex) {
  await knex.schema.dropTableIfExists('short_url');
};
