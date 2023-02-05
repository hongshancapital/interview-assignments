// tslint:disable
import Sequelize from '@types/sequelize'


// table: applicationProcess
export interface urlAttribute {
    id?:number;
    shortId?:string;
    url?:string;
    ip?:string;
    ua?:string
    createdAt?:Date;
    updatedAt?:Date;
    deletedAt?:Date;
  }
  export interface urlInstance extends Sequelize.Instance<urlAttribute>, urlAttribute { }
  export type urlProcessModel = Sequelize.Model<urlInstance, urlAttribute>

  declare module 'egg' {
    interface Context {
      model: Sequelize.Sequelize & {
        Url: urlProcessModel,
      }
    }
    interface Application {
      model: Sequelize.Sequelize & {
        Url: urlProcessModel,
      }
    }
  }