import murmurhash from 'murmurhash';

import { num10To62 } from '@/utils';

export default new class {
    constructor() {
        //
    }

    public generateShortDomain(url: string, prefix?: string): string {
        let pre = '';

        if (prefix) {
            pre = new URL(prefix).origin;
        } else {
            pre = new URL(url).origin;
        }

        return `${pre}/${num10To62(murmurhash.v3(url))}`;
    }
};
