import * as sequelize from 'sequelize';

// 新接口
declare namespace superSequelize {

  interface GetOneOrInitializeOptions<T> extends sequelize.FindOrInitializeOptions<T> {
    where?: sequelize.WhereOptions<T>;
  }

  interface GetOneOptions<T> extends sequelize.FindOptions<T> {
    where?: sequelize.WhereOptions<T>;
  }

  interface GetListOptions<T> extends GetOneOptions<T> {
    where?: sequelize.WhereOptions<T>;
    page?: number;
    pageSize?: number;
  }

  interface UpdateOptions<T> extends sequelize.UpdateOptions {
    where: {
      [P in keyof T]?: string | number | boolean | WhereLogic | WhereOptions<T[P]> | col | and | or | WhereGeometryOptions | WhereNested | Array<string | number> | null;
    };
  }

  interface DefineAttributeColumnOptions extends sequelize.DefineAttributeColumnOptions {
    allowUpdate?: boolean;
  }

  interface DefineAttributes {
    [name: string]: DefineAttributeColumnOptions;
  }

  // 业务属性
  type Attributes<T> = T & {
    id?: number;
    updateTime?: number; // DATETIME我们一般传入 +new Date()时间戳
    createTime?: number;
  }

  // 业务属性返回值
  type ResponseAttributes<T> = T & {
    id: number;
    updateTime: string; // DATETIME返回值是string
    createTime: string;
  }

  type Instance<T> = sequelize.Instance<ResponseAttributes<T>> & ResponseAttributes<T>;
}

export default superSequelize