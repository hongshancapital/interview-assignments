/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.up = function(knex) {
  return knex.schema.hasTable('url_map').then(function(exists) {
    if (!exists) {
      return knex.schema.createTable('url_map', function(table) {
        table.comment('链接表')
        table.increments('id').primary()
        table.string('shortUrl').comment('短链接')
        table.text('longUrl').comment('长链接')
        table.timestamp('created_at', { precision: 6 })
      })
    }
  })
}

/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.down = function(knex) {
  return knex.schema.dropTable('url_map')
}
