export default interface IUrlRepository {
  createShortUrl(originalUrl: string): Promise<string>;
  findByShortPath(shortPath: string): Promise<string | undefined>;
}
