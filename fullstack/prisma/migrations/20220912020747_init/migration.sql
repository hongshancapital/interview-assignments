-- CreateTable
CREATE TABLE "UrlTable" (
    "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "shortUrl" TEXT NOT NULL,
    "longUrl" TEXT NOT NULL,
    "createdAt" DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" DATETIME NOT NULL
);

-- CreateIndex
CREATE UNIQUE INDEX "UrlTable_shortUrl_key" ON "UrlTable"("shortUrl");
