export class Url {
  /**
   * primary key
   */
  id: number;

  /**
   * index, unique
   */
  code: string;

  /**
   * unique
   */
  originalUrl: string;

  /**
   * index
   */
  originalUrlHash?: string;
}
