import { BaseContextClass, Context } from 'egg';
import { ModelStatic, WhereOptions, Op } from 'sequelize';
import { UrlMapAttr, UrlMapCreationAttr, UrlMapModel } from '../model/url-map';
import { UrlMapStateEnum } from '../utils/enum';

export default class UrlMapDao extends BaseContextClass {
  model: ModelStatic<UrlMapModel>;

  constructor(ctx: Context) {
    super(ctx);
    this.model = ctx.model.UrlMap;
  }

  async getMCount(): Promise<number> {
    return await this.model.count();
  }

  async getMMaxId(): Promise<number> {
    return await this.model.max('id');
  }

  async getMDataById(id: number): Promise<UrlMapModel | null> {
    return await this.model.findByPk(id);
  }

  async getOneMData(option: WhereOptions<UrlMapAttr>): Promise<UrlMapModel | null> {
    return await this.model.findOne({
      where: option,
    });
  }

  async getMDatas(whereOption: WhereOptions<UrlMapAttr>): Promise<UrlMapModel[]> {
    return await this.model.findAll({
      where: whereOption,
      order: [['id', 'DESC']],
    });
  }

  async getExpiredMDatas(): Promise<UrlMapModel[]> {
    return await this.getMDatas({
      state: UrlMapStateEnum.Normal,
      expireDate: {
        [Op.lt]: new Date(),
      },
    });
  }

  async create(option: UrlMapCreationAttr): Promise<UrlMapModel> {
    return await this.model.create(option);
  }

  async updateDataById(id: number | number[], option: Partial<UrlMapAttr>) {
    return await this.model.update(option, {
      where: { id },
    });
  }

  async destroy(option: WhereOptions<UrlMapAttr>): Promise<number> {
    return await this.model.destroy({
      where: option,
    });
  }
}
