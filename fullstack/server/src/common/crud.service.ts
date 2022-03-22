import { UnprocessableEntityException } from '@nestjs/common';
import { BaseEntity, DeleteResult, Repository, DeepPartial, FindManyOptions, SaveOptions, UpdateResult } from 'typeorm';
import { validate } from 'class-validator';
import { FindOneOptions, FindOptionsWhere } from 'typeorm';

export class CrudService<T extends BaseEntity> {
  protected repository: Repository<T>;

  public async find(
    options?: FindManyOptions<T>
  ): Promise<T[]> {
    return await this.repository.find(options);
  }

  public async count(
    options?: FindOneOptions<T>
  ): Promise<number> {
    return await this.repository.count(options);
  }

  public createQueryBuilder(
    type: string
  ) {
    return this.repository.createQueryBuilder(type)
  }

  public async findAndCount(
    options?: FindManyOptions<T>
  ): Promise<[T[], number]> {
    return await this.repository.findAndCount(options);
  }

  public async findAll(
    options?: FindManyOptions<T>
  ): Promise<T[]> {
    return await this.repository.find(options);
  }

  public async findOneBy(where: FindOptionsWhere<T> | FindOptionsWhere<T>[]): Promise<T> {
    return await this.repository.findOneBy(where);
  }

  public async findOneByWhere(
    options?: FindOneOptions<T>,
  ): Promise<T> {
    try {
      return await this.repository.findOneOrFail(options);
    } catch(err) {
      return null
    }
  }

  public async findOne(
    options?: FindOneOptions<T>,
  ): Promise<T> {
    try {
      return await this.repository.findOneOrFail(options);
    } catch(err) {
      return null
    }
  }

  public async create(data: any, options?: SaveOptions): Promise<T> {
    const entity: any = this.repository.create(data);
    return this.repository.save(entity, options)
  }

  public async save(data: any, options?: SaveOptions): Promise<T> {
    return this.create(data, options);
  }

  public async update(options: FindOptionsWhere<T>, data: DeepPartial<T>): Promise<UpdateResult> {
    const entity: any = this.repository.create(data);
    return this.repository.update(options, entity)
  }
  
  public async saveAll(data: any): Promise<T[]> {
    const entity: any = this.repository.create(data);
    return this.repository.save(entity)
  }

  public async put(data: any): Promise<T> {
    const entity: any = this.repository.create(data);
    return this.repository.save(entity)
  }

  public async delete(id: string): Promise<DeleteResult> {
    return this.repository.delete(id);
  }

  public async query(sql: string): Promise<any> {
    return this.repository.query(sql)
  }

  private async validate(entity: T) {
    const errors = await validate(entity, {
      validationError: {
        target: false,
        value: false,
      },
    });
    if (errors.length) {
      throw new UnprocessableEntityException(errors);
    }
  }
}