/*
  Warnings:

  - A unique constraint covering the columns `[longUrl]` on the table `ShortUrl` will be added. If there are existing duplicate values, this will fail.

*/
-- CreateIndex
CREATE UNIQUE INDEX "ShortUrl_longUrl_key" ON "ShortUrl"("longUrl");
