import { Message, MessageBox, Loading, Notification } from 'element-ui';
import { Toast, Dialog } from 'vant';

import { ElNotificationComponent, ElNotificationOptions } from 'element-ui/types/notification';
import { ElLoadingComponent, LoadingServiceOptions } from 'element-ui/types/loading';
import { ElMessageBoxOptions, MessageBoxData } from 'element-ui/types/message-box';

import { VanToast } from 'vant/types/toast';

import 'element-ui/lib/theme-chalk/message.css';
import 'element-ui/lib/theme-chalk/message-box.css';
import 'element-ui/lib/theme-chalk/loading.css';
import 'element-ui/lib/theme-chalk/notification.css';

import 'vant/lib/toast/style';
import 'vant/lib/dialog/style';

import i18n from '../lang';
import store from '../store';

interface CloseNotificationFn {
    (): void;
}

class UITool {
    private allNoticing: Set<CloseNotificationFn>;

    constructor() {
        this.allNoticing = new Set();
    }

    private t(message: string): string {
        return i18n.t(message).toString();
    }

    private get isMobile(): boolean {
        return store.state.screenType === 'phone' || store.state.screenType === 'ipad';
    }

    public success(message: string): void {
        if (this.isMobile) {
            Toast({
                type: 'success',
                message: this.t(message),
                position: 'top'
            });
        } else {
            Message({
                showClose: true,
                message: this.t(message),
                type: 'success'
            });
        }
    }

    public error(message: string): void {
        if (this.isMobile) {
            Toast({
                type: 'fail',
                message: this.t(message),
                position: 'top'
            });
        } else {
            Message({
                showClose: true,
                message: this.t(message),
                type: 'error'
            });
        }
    }
    public warn(message: string): void {
        if (this.isMobile) {
            Toast({
                message: this.t(message),
                position: 'top'
            });
        } else {
            Message({
                showClose: true,
                message: this.t(message),
                type: 'warning'
            });
        }
    }

    public async alert(message: string, title?: string, option?: ElMessageBoxOptions): Promise<true> {
        if (this.isMobile) {
            return await Dialog.alert({
                message: this.t(message),
                ...title ? { title: this.t(title) } : {}
            }).then(() => true);
        }
        return await MessageBox.alert(this.t(message), this.t(title || ''), {
            ...option,
            confirmButtonText: this.t(option?.confirmButtonText || 'yes'),
            type: option?.type || 'info',
            showClose: false,
            showCancelButton: false,
            closeOnClickModal: false,
            closeOnPressEscape: false,
            showInput: false
        }).then(() => true);
    }

    public async confirm(message: string, title?: string, option?: ElMessageBoxOptions): Promise<boolean> {
        if (this.isMobile) {
            return await Dialog.confirm({
                message: this.t(message),
                ...title ? { title: this.t(title) } : {}
            }).then(() => true).catch(() => false);
        }
        return await MessageBox.alert(this.t(message), this.t(title || ''), {
            ...option,
            confirmButtonText: this.t(option?.confirmButtonText || 'yes'),
            cancelButtonText: this.t(option?.cancelButtonText || 'cancel'),
            type: option?.type || 'info',
            showClose: false,
            showCancelButton: true,
            closeOnClickModal: false,
            closeOnPressEscape: false,
            showInput: false
        }).then(() => true).catch(() => false);
    }

    public async prompt(message: string, title: string, option?: ElMessageBoxOptions): Promise<string | false> {
        return await MessageBox.alert(this.t(message), this.t(title), {
            ...option,
            confirmButtonText: this.t(option?.confirmButtonText || 'yes'),
            cancelButtonText: this.t(option?.cancelButtonText || 'cancel'),
            type: 'info',
            showClose: false,
            showCancelButton: true,
            closeOnClickModal: false,
            closeOnPressEscape: false,
            showInput: true,
            inputErrorMessage: this.t(option?.inputErrorMessage || 'it_is_illegal')
        }).then((data: MessageBoxData) => {
            if (data === 'cancel' || data === 'close' || data === 'confirm') {
                return false;
            } else {
                return data.value;
            }
        }).catch(() => false);
    }

    public loading(option?: LoadingServiceOptions, text?: string): ElLoadingComponent | VanToast {
        if (this.isMobile) {
            return Toast.loading({
                message: this.t(text || ''),
                forbidClick: true,
                duration: 0
            });
        }
        return Loading.service({
            ...option,
            text: this.t(text || '')
        });
    }

    public noticing(title: string, message: string, option?: ElNotificationOptions): ElNotificationComponent {
        const noticing = Notification({
            ...option,
            title: this.t(title),
            message: this.t(message)
        });

        this.allNoticing.add(noticing.close);
        return noticing;
    }

    public noticed(title: string, message: string, option?: ElNotificationOptions): ElNotificationComponent {
        const noticed = Notification({
            ...option,
            title: this.t(title),
            message: this.t(message)
        });

        this.allNoticing.add(noticed.close);
        return noticed;
    }

    public closeAllNotice(): void {
        this.allNoticing.forEach(a => a());
    }
}

export default new UITool();
