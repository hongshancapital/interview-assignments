import { Provide, Inject } from "@midwayjs/decorator";
import { InjectEntityModel } from "@midwayjs/orm";
import { ShortLinkUrl } from "../../entity/tb_shortlink_url";
import { Repository } from "typeorm";
import { isIncluded } from "../../../utils/helper";

@Provide()
export class ShortLinkUrlService {
  @InjectEntityModel(ShortLinkUrl)
  urlModel: Repository<ShortLinkUrl>;

  async save(options) {
    let result = await this.urlModel.findOne({
      where: {
        url: options.url,
      },
    });

    if (result) {
      return result;
    }

    result = await this.urlModel.save(options);
    return result;
  }

  async query(options) {
    if (!isIncluded(Object.keys(options), ["url", "shortUrl"])) {
      return null;
    }

    return await this.urlModel.find({
      where: options,
    });
  }

  async update(params) {}

  async remove(params) {}
}
