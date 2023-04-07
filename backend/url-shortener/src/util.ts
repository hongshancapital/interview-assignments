import { nanoid } from 'nanoid';
import { SHORT_CODE_MAX_LENGTH } from './shortUrl';

/**
 * 获取一个符合规范且与传入的短码不同的短码
 * @param shortCode 短域名唯一编码
 * @returns 不同于传入参数的另一个短码
 */
function getDiffShortCode(shortCode: string): string {
    const diffShortCode = nanoid(SHORT_CODE_MAX_LENGTH);
    if (diffShortCode != shortCode) {
        return diffShortCode;
    }
    return getDiffShortCode(shortCode);
}

export { getDiffShortCode };
