import ShortLinkModel from "./model"


class ShortLinkController {
    static readonly SelectCharacters = "ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678";
    static readonly KeyLength = 8

    private readonly key: string;
    private readonly originLink: string;

    constructor(key?: string, originLink?: string) {
        this.key = key || ""
        this.originLink = originLink || ""
    }

    static randomKey(): string {
        let key: string = "";
        for (let i = 0; i < ShortLinkController.KeyLength; i++) {
            key += ShortLinkController.SelectCharacters.charAt(Math.floor(Math.random() * ShortLinkController.SelectCharacters.length));
        } 
        return key;
    }

    async add() {
        if (this.key !== "") {
            const shortLinkModel = new ShortLinkModel(this.key, this.originLink);
            const isExist: Boolean = await shortLinkModel.add()
            if (isExist) {
                return {
                    error: 'key has existed!',
                    key: this.key
                }
            }
            return {
                key: this.key,
                error: null,
            }
        }

        while(true) {
            const key = ShortLinkController.randomKey()
            const shortLinkModel = new ShortLinkModel(key, this.originLink);
            if (true === await shortLinkModel.add()) {
                 continue
            }
            return {
                key,
                error: null,
            }
        }
    }

    async get() {
        const shortLinkModel = new ShortLinkModel(this.key);
        return await shortLinkModel.get();
    }
}

export default ShortLinkController;