
export class PublicRes {
    public success: boolean;
    public errorMsg: string;
    constructor(success: boolean, errorMsg: string) {
        this.success = success;
        this.errorMsg = errorMsg;
    }

    public buildJson(): any {
        return {"success":this.success, "errorMsg": this.errorMsg};
    }
}