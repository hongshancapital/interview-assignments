
export default class ShortUrlRepo {
  //用map模拟数据库
  private static fakeDb = new Map<string, string>();

  // 根据短链接key查找长链接
  public static findByKey(key: string): string | undefined {
    return this.fakeDb.get(key);
  }
  //存储长链接和对应的短链接key
  public static Add(key: string, url: string) {
      this.fakeDb.set(key, url);
  }
}
