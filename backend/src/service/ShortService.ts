import ShortModel from "../model/ShortModel";

class ShortService {
  public static findByShortKey(short_url: string) {
    return ShortModel.findOne({ where: { short_url }, attributes: ['short_url', 'original_url'] });
  }

  public static findOrCreate(short_url: string, original_url: string) {

    return ShortModel.findOrCreate({ where: { original_url }, defaults: { short_url, original_url }, attributes: ['short_url', 'original_url'] });
  }

  public static destroy() {
    return ShortModel.destroy({where: {}, truncate: true});
  }
}

export default ShortService;