/**
 * 用类名区分工具库, 便于未来拆分
 */
export class ResUtil {
  static showError({ code = 10000, msg = "failed", data = {} }) {
    return ResUtil.showResult({
      code,
      msg,
      data,
    });
  }
  static showResult({ code = 0, msg = "success", data = {} }) {
    return {
      code,
      msg,
      data,
    };
  }
}

export class ShortUrlUtil {
  static isLegal(url: string) {
    return true;
  }
}
