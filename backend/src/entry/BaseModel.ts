import { Model } from 'objection';

export default class BaseModel extends Model {
  public readonly id!: number;
  public createdAt?: string;

  public $beforeInsert(): void {
    this.createdAt = this.datetimeString();
  }

  // mysql format 0000-00-00 00:00:00
  private datetimeString(): string {
    return new Date()
      .toISOString()
      .slice(0, 19)
      .replace('T', ' ');
  }
}
