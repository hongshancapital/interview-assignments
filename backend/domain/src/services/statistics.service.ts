import { container, singleton } from 'tsyringe';
import { Statistics } from "../interfaces/statistics.interface";
import { StatisticsModel } from "../models/statistics.model";
import { Snowflake } from '../utils/snowflake';


@singleton()
export class StatisticsService {

  private snowflake: Snowflake; 

  constructor() {
    this.snowflake = new Snowflake( 2n, 6n);
  }

  public async createByOption(option: Statistics): Promise<Statistics> {
    if(!option.uid) {
      option.uid = `${ this.snowflake.nextId()}`;
    }
    return await StatisticsModel.create({ ...option });
  }

}
const statisticsService = container.resolve(StatisticsService);
export default statisticsService;