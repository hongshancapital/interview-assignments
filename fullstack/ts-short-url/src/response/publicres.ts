
export class PublicRes {
    public success: boolean;
    public errorMsg: string;
    constructor(success: boolean, errorMsg: string) {
        this.success = success;
        this.errorMsg = errorMsg;
    }
}