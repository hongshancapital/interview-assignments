import { ApiProperty } from "@nestjs/swagger";
import { IsNotEmpty } from "class-validator";
import { FindOperator } from "typeorm";

export enum UserRole {
  administrator = 'administrator',
  shopowner = 'shopowner',
  person = 'person',

  vip = 'vip',
  user = 'user',

  manager = 'manager',
  director = 'director',
  finance = 'finance',
  hr = 'hr',
}

export enum IMethod {
  GET = 'GET',
  POST = 'POST',
  PUT = 'PUT',
  PATCH = 'PATCH',
  DELETE = 'DELETE',
  '(GET|POST|PUT|DELETE|PATCH)' = '(GET|POST|PUT|DELETE|PATCH)',
  '(POST|PUT|PATCH)' = '(POST|PUT|PATCH)',
}

export class IFindOneOptions<Entity = any> {
  /**
   * Specifies what columns should be retrieved.
   */
  @ApiProperty({
    type: [String],
    required: false
  })
  select?: any[];
  /**
   * Simple condition that should be applied to match entities.
   */
  @ApiProperty({
    type: JSON,
    required: false
  })
  // @IsJSON()
  where?: { [key: string]: number | string | FindOperator<string | number> };
  /**
   * Indicates what relations of entity should be loaded (simplified left join form).
   */
  @ApiProperty({
    type: [String],
    required: false
  })
  relations?: string[];
  /**
   * Specifies what relations should be loaded.
   */
  @ApiProperty({
    type: Object,
    required: false
  })
  join?: any;
  /**
   * Order, in which entities should be ordered.
   */
  @ApiProperty({
    type: JSON,
    required: false
  })
  order?: {
    [key: string]: 'ASC' | 'DESC' | 1 | -1;
  };
  /**
   * Enables or disables query result caching.
   */
  cache?: boolean | number | {
    id: any;
    milliseconds: number;
  };
  /**
   * If sets to true then loads all relation ids of the entity and maps them into relation values (not relation objects).
   * If array of strings is given then loads only relation ids of the given properties.
   */
  loadRelationIds?: boolean | {
    relations?: string[];
    disableMixedMap?: boolean;
  };
  /**
   * Indicates if eager relations should be loaded or not.
   * By default they are loaded when find methods are used.
   */
  loadEagerRelations?: boolean;
}

export class IFindManyOptions<Entity = any> extends IFindOneOptions<Entity> {
  /**
   * Offset (paginated) where from entities should be taken.
   */
  @ApiProperty({
    default: 0,
    required: false
  })
  skip?: number;
  /**
   * Limit (paginated) - max number of entities should be taken.
   */
  @ApiProperty({
    default: 10,
    required: false
  })
  take?: number;
}

export class ICasbin {
  @ApiProperty({
    enum: UserRole,
    default: 'user',
    required: true
  })
  @IsNotEmpty()
  role: UserRole;

  @ApiProperty({
    default: '/api/*',
    required: true
  })
  name: string;

  @ApiProperty({
    enum: IMethod,
    default: 'GET',
    required: true
  })
  method: IMethod
}

export class ICasbinRole {
  @ApiProperty({
    enum: UserRole,
    default: 'user',
    required: true
  })
  role: UserRole;
}