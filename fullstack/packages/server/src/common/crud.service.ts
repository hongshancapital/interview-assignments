import { UnprocessableEntityException } from '@nestjs/common';
import {
  FindOneOptions,
  BaseEntity,
  DeleteResult,
  Repository,
  DeepPartial,
  FindManyOptions,
  SaveOptions,
  UpdateResult,
  In,
  FindConditions,
} from 'typeorm';
import { validate } from 'class-validator';


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

  public async findOneById(
    id: string,
    options?: FindOneOptions<T>,
  ): Promise<T> {
    return await this.repository.findOneOrFail({
      where: {
        id,
      },
      ...options,
    });
  }

  public async findByIds(
    ids: string[],
    options?: FindOneOptions<T>,
  ): Promise<T[]> {
    return await this.repository.find({
      where: {
        id: In(ids),
      },
      ...options,
    });
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

  public async findOne(conditions?: FindConditions<T>): Promise<T> {
    try {
      return await this.repository.findOne({
        where: conditions,
      });
    } catch (err) {
      return null;
    }
  }

  public async create(data: any, options?: SaveOptions): Promise<T> {
    const entity: any = this.repository.create(data);
    return this.repository.save(entity, options);
  }

  public async save(data: any, options?: SaveOptions): Promise<T> {
    return this.create(data, options);
  }

  public async update(
    options: FindConditions<T>,
    data: DeepPartial<T>,
  ): Promise<UpdateResult> {
    const entity: any = this.repository.create(data);
    return this.repository.update(options, entity);
  }
  
  public async saveAll(data: any): Promise<T[]> {
    const entity: any = this.repository.create(data);
    return this.repository.save(entity);
  }

  public async patch(id: string, data: any): Promise<T> {
    const entity: any = await this.findOneById(id);
    Object.assign(entity, data);
    return this.repository.save(entity);
  }

  public async put(data: any): Promise<T> {
    const entity: any = this.repository.create(data);
    return this.repository.save(entity);
  }

  public async delete(id: string): Promise<DeleteResult> {
    return this.repository.delete(id);
  }

  public async query(sql: string): Promise<any> {
    return this.repository.query(sql);
  }
}
