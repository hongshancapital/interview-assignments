
export default class ShortUrlRepo {
  private static fakeDb = new Map<string, string>();
  // contains critical information of the user
  public static findByKey(key: string): string | undefined {
    return this.fakeDb.get(key);
  }
  public static Add(key: string, url: string) {
      this.fakeDb.set(key, url);
  }
}
