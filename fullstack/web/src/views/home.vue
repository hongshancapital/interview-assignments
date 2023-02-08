<template>
    <el-main class="demo_style">
        <!-- {{ $t('SUCCESS') }}
        {{ username }}
        <p class="demo_style" @click="testApi">原data数据：{{ test }}</p>
        language {{ language }} -->
        首页
        <div id="videoElement" class="video_test"></div>
    </el-main>
</template>

<script lang="ts">
import { Vue, Component, Watch } from 'vue-property-decorator';
import { State, Action } from 'vuex-class';
import { RootState, UserState, SetLanguageAction } from '../store/stateModel';
import API from '../api';
import Tips from '../ui-frame/ui-tips';
@Component
export default class Hoom extends Vue {
    /**
     * 相当于原vuex里面的数据
     */
    @State(state => state)
    private allStore!: RootState;

    @State(state => state.user)
    private user!: UserState;

    @State('language')
    private language!: string;

    @Action('setLanguage')
    private setLanguage!: SetLanguageAction;

    private get username(): string {
        return this.user.username;
    }

    private test = 'string-data';
    // private video: DPlayer;

    @Watch('username', { immediate: true, deep: true })
    private onChangeUsername(/*newVal: string, oldVal: string*/) {
        console.log(this.username); /* eslint-disable-line no-console */
    }

    created(): void {
        // setInterval(() => {
        //     const newName = new Date().getTime();
        //     this.setUserName(newName); // 相当于store.dispatch('setUserName', newName);
        // }, 1000);
    }

    mounted(): void {
        // this.video = new DPlayer({
        //     container: document.getElementById('videoElement'),
        //     live: false,
        //     autoplay: false,
        //     theme: 'red',
        //     loop: false, //视频循环播放
        //     screenshot: false, //是否开启截屏，如果开启，视频和视频封面需要允许跨域
        //     preload: 'auto', //视频预加载，可选值: 'none', 'metadata', 'auto'
        //     volume: 0.7, //默认音量，请注意播放器会记忆用户设置，用户手动设置音量后默认音量即失效
        //     playbackSpeed: [0.5, 0.75, 1, 1.25, 1.5, 2], //可选的播放速率，可以设置成自定义的数组
        //     logo: '', //在左上角展示一个 logo，你可以通过 CSS 调整它的大小和位置
        //     video: {
        //         url: '', //'http://127.0.0.1:3000/tests/test/api',
        //         //都有哪些清晰度
        //         quality: [
        //             {
        //                 name: 'HD',
        //                 url: 'demo.m3u8',
        //                 type: 'hls'
        //             },
        //             {
        //                 name: 'SD',
        //                 url: 'http://192.168.1.158:4016/api/surpassadm/recordmgr/1.0/play',
        //                 // url: 'http://127.0.0.1:3000/tests/test/api',
        //                 type: 'normal'
        //             }
        //         ],
        //         type: 'auto', //可选值: 'auto', 'hls', 'flv', 'dash', 'webtorrent', 'normal' 或其他自定义类型
        //         defaultQuality: 1,
        //         pic: 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1948309409,2412109308&fm=26&gp=0.jpg', //封面
        //         thumbnails: 'http://192.168.1.158:4016/api/surpassadm/recordmgr/1.0/picture' //缩略图
        //     },
        //     //自定义右键菜单
        //     contextmenu: [
        //         {
        //             text: 'custom1',
        //             link: 'https://github.com/DIYgod/DPlayer'
        //         },
        //         {
        //             text: 'custom2',
        //             click: () => {
        //                 Tips.success('菜单2');
        //             }
        //         }
        //     ],
        //     highlight: [
        //         {
        //             time: 20,
        //             text: '这是第 20 秒'
        //         },
        //         {
        //             time: 120,
        //             text: '这是 2 分钟'
        //         }
        //     ], //自定义进度条提示点
        //     mutex: true //	互斥，阻止多个播放器同时播放，当前播放器播放时暂停其他播放器
        //     // 字幕
        //     // subtitle: {
        //     //     url: '', //字幕链接
        //     //     type: 'webvtt', //字幕类型，可选值: 'webvtt', 'ass'，目前只支持 webvtt
        //     //     fontSize: '20px', //字幕字号
        //     //     bottom: '40px', //字幕距离播放器底部的距离，取值形如: '10px' '10%'
        //     //     color: '#fff' //字幕颜色
        //     // },
        //     // 弹幕
        //     // danmaku: {
        //     //     id: '213123', //弹幕池 id，必须唯一
        //     //     api: '',
        //     //     token: '', //弹幕后端验证 token
        //     //     maximum: '10', //弹幕最大数量
        //     //     addition: undefined, //额外外挂弹幕
        //     //     user: 'DIYgod', //弹幕用户名
        //     //     bottom: '', //弹幕距离播放器底部的距离，防止遮挡字幕，取值形如: '10px' '10%'
        //     //     unlimited: false //海量弹幕模式，即使重叠也展示全部弹幕，请注意播放器会记忆用户设置，用户手动设置后即失效
        //     // }
        // });
    }

    private async testApi(): Promise<void | boolean> {
        // console.log(this.$t('FAILED'));
        console.log(123); /* eslint-disable-line no-console */
        const { error, data } = await API.Account.test({ 'test-body': '中文测试' });

        if (error) {
            // throw error.type || 'USER_SAVE_FAILED';
            return Tips.alert('test message', 'test');
            // return Tips.error(error?.type || 'USER_SAVE_FAILED');
        }
        Tips.success('SUCCESS');
        console.log(456); /* eslint-disable-line no-console */
        console.log(data); /* eslint-disable-line no-console */
    }
}
</script>

<style lang="less" scoped>
.demo_style {
    color: @theme_color;
}
.video_test {
    width: 1000px;
}
</style>
