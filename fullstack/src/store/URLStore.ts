import { PrismaClient } from "@prisma/client";

class URLStore {
  private surlCache: Map<string, string>;
  private prisma: PrismaClient;

  constructor() {
    this.surlCache = new Map();
    this.prisma = new PrismaClient();

    this.loadAllPairs();
    /*
      .then(async () => {
        await this.prisma.$disconnect();
      })
      .catch(async (e) => {
        console.error(e);
        await this.prisma.$disconnect();
        process.exit(1);
      });*/
  }

  async loadAllPairs() {
    const urlpairs = await this.prisma.urlTable.findMany({
      select: {
        shortUrl: true,
        longUrl: true,
      },
    });
    for (const urlpair of urlpairs) {
      this.surlCache.set(urlpair.shortUrl, urlpair.longUrl);
    }
    //console.log("surlCache = ", this.surlCache);
  }

  async addUrlPairs(shortUrl: string, longUrl: string) {
    this.surlCache.set(shortUrl, longUrl);
    console.log("2surlCache = ", this.surlCache);

    try{
      const result = await this.prisma.urlTable.create({
        data: {
          shortUrl,
          longUrl,
        },
      });
    }catch(e){
      console.log(e + "#shortUrl:"+ shortUrl);
    }
    
    //console.log(" new urlpair = ", result);
  }

  checkExist(shortUrl: string): boolean {
    console.log("checkExist surlCache = ", this.surlCache);
    return this.surlCache.has(shortUrl) ;
  }

  getLongURL(shortUrl: string): string | undefined {
    return this.surlCache.get(shortUrl);
  }
}

export { URLStore };

