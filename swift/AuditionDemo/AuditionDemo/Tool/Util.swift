//
//  Util.swift
//  AuditionDemo
//
//  Created by dian bao on 2022/7/28.
//

import UIKit

//MARK: -小工具
class Util{
    //MARK: - 实现单例
    static let sharedInstance = Util()
    //MARK: - 初始化
    private init(){
        //根据不同手机屏幕分辨率，调整加载图片的优先级顺序
        let scale = UIScreen.main.scale
        if( scale >= 3 )
        {
            names = ["big","medium","small"]
        }
        else if( scale == 2 )
        {
            names = ["medium","big","small"]
        }
        else
        {
            names = ["small","medium","big"]
        }
    }
    //MARK: - 属性
    //存储各个尺寸的图标URL数组
    var names:[String] = []
    
    //MARK: - 方法
    //获取某条目的需要加载的图标的URL数组
    func getImageUrls(_ dict:Item)->[URL]
    {
        var urls:[URL] = []
        for name in names
        {
            var _URL:URL?
            if( name == "small" )
            {
                _URL = URL.init(string: dict.artworkUrl60)
            }
            else if( name == "medium" )
            {
                _URL = URL.init(string: dict.artworkUrl100)
            }
            else if( name == "big" )
            {
                _URL = URL.init(string: dict.artworkUrl512)
            }
            
            if( _URL != nil )
            {
                urls.append(_URL!)
            }
        }
        return urls
    }
    
    //点击收藏按钮的震动反馈
    func feedbackGenerator() {
        let gen = UIImpactFeedbackGenerator.init(style: .light);//light震动效果的强弱
        gen.prepare();//反馈延迟最小化
        gen.impactOccurred()//触发效果
    }
    
    //顶部状态栏高度（包括安全区）
    func statusBarHeight() -> CGFloat {
        var statusBarHeight: CGFloat = 0
        if #available(iOS 13.0, *) {
            let scene = UIApplication.shared.connectedScenes.first
            guard let windowScene = scene as? UIWindowScene else { return 0 }
            guard let statusBarManager = windowScene.statusBarManager else { return 0 }
            statusBarHeight = statusBarManager.statusBarFrame.height
        } else {
            statusBarHeight = UIApplication.shared.statusBarFrame.height
        }
        return statusBarHeight
    }
    
    //为了更好的演示Demo效果，可在info.plist文件中进行配置(PS：在手机性能和网络较好的情况下比较难出现动画加载效果，故程序通过设置延时操作来比较容易展示，实际生产中肯定是不需要如此影响体验多余操作)
    func forSimulation()->Bool
    {
        let forSimulation = Bundle.main.infoDictionary?["FOR_SIMULATION"] as? String
        return forSimulation == "YES" ? true : false
    }
    
    //配置的MOCK API测试URL
    func demoUrl()->String
    {
        let TEST_URL = Bundle.main.infoDictionary?["TEST_URL"] as? String
        return TEST_URL != nil ? TEST_URL! : ""
    }
}
