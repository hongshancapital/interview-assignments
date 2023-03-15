import murmurhash from 'murmurhash';
import base62 from 'base62';

// 生成短链
export function spawnShortKey (id: number) {
    const value = murmurhash.v3(id.toString());
    return base62.encode(value);
}
