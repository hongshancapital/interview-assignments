import {
  FindOneOptions,
  BaseEntity,
  DeleteResult,
  Repository,
  DeepPartial,
  FindManyOptions,
  SaveOptions,
  FindConditions,
} from 'typeorm';
import { QueryDeepPartialEntity } from 'typeorm/query-builder/QueryPartialEntity';


export class CrudService<T extends BaseEntity> {
  protected repository: Repository<T>;

  public async find(conditions?: FindManyOptions<T>): Promise<T[]> {
    return await this.repository.find(conditions);
  }

  public async count(options?: FindOneOptions<T>): Promise<number> {
    return await this.repository.count(options);
  }

  public async countBy(options?: FindOneOptions<T>): Promise<number> {
    return await this.repository.count(options);
  }

  public createQueryBuilder(type: string) {
    return this.repository.createQueryBuilder(type);
  }

  public async findAndCount(
    options?: FindManyOptions<T>,
  ): Promise<[T[], number]> {
    return await this.repository.findAndCount(options);
  }

  public async findAll(options?: FindManyOptions<T>): Promise<T[]> {
    return await this.repository.find(options);
  }

  public async findOneByWhere(options?: FindOneOptions<T>): Promise<T> {
    try {
      return await this.repository.findOneOrFail(options);
    } catch (err) {
      return null;
    }
  }

  public async findOneOrFail(
    conditions?: FindConditions<T>,
    options?: FindOneOptions<T>,
  ): Promise<T> {
    try {
      return await this.repository.findOneOrFail({
        where: conditions,
        ...options,
      });
    } catch (err) {
      return null;
    }
  }
  
  async findOneById(id: string | number): Promise<T> {
    return await this.repository.findOne(id);
  }

  public async findOne(conditions?: FindConditions<T>): Promise<T> {
    try {
      return await this.repository.findOne({
        where: conditions,
      });
    } catch (err) {
      return null;
    }
  }

  public create<E extends DeepPartial<T>>(entity: E) {
    return this.repository.create(entity);
  }

  public async save(data: any, options?: SaveOptions): Promise<T> {
    return this.repository.save(data, options);
  }

  public async update(
    options: FindConditions<T>,
    data: QueryDeepPartialEntity<T>,
  ): Promise<T>  {
    await this.repository.update(options, data);
    return await this.findOne(options)
  }
  
  public async saveAll(data: DeepPartial<T[]>): Promise<T[]> {
    return this.repository.save(data);
  }

  public async patch(id: string, data: any): Promise<T> {
    const entity = await this.findOneById(id);
    Object.assign(entity, data);
    return this.repository.save(entity as DeepPartial<T>);
  }

  public async put(data: DeepPartial<T>): Promise<T> {
    const entity = this.repository.create(data);
    return this.repository.save(entity as DeepPartial<T>);
  }

  public async delete(id: string): Promise<DeleteResult> {
    return this.repository.delete(id);
  }

  public async query(sql: string): Promise<any> {
    return this.repository.query(sql);
  }
}
