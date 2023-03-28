-- CreateTable
CREATE TABLE "ShortUrl" (
    "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "urlCode" TEXT NOT NULL,
    "longUrl" TEXT NOT NULL
);

-- CreateIndex
CREATE UNIQUE INDEX "ShortUrl_urlCode_key" ON "ShortUrl"("urlCode");
