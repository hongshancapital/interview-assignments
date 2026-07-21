//
//  PageLoaderManager.swift
//  AuditionDemo
//
//  Created by dian bao on 2022/7/28.
//

import SwiftUI

//MARK: -分页加载管理器
class PageLoaderManager : ObservableObject{
    //MARK: - 实现单例
    static let sharedInstance = PageLoaderManager()
    
    //MARK: - 初始化方法
    private init(){
        //根据手机竖屏情况下的屏幕高度粗略估算分页加载一次加载的条目总数
        PageCount = Int((UIScreen.main.bounds.size.height/CGFloat(100.0)))+1
    }
    
    //MARK: - 属性
    var results:[Item] = []         //需要加载的所有条目
    @Published
    var LoadingBegin = false        //是否开始第一次加载分页数据
    @Published
    var LoadingFinish = false       //是否所有数据已经加载完毕
    @Published
    var PageEndIndex = 0            //已经加载的条目哨兵尾数
    var PageCount = 10              //分页加载一次加载条目数量，可以根据屏幕高度粗略估算
    
    //MARK: - 方法
    //加载分页数据
    func LoadPageData()
    {
        //如果已经全部加载完毕直接返回
        if( LoadingFinish == true )
        {
            return
        }
        let PageStartIndex = PageEndIndex
        DispatchQueue.main.async{
            if( PageStartIndex + self.PageCount > self.results.count )
            {
                self.LoadingFinish = true
                self.PageEndIndex = self.results.count
            }
            else
            {
                self.PageEndIndex = PageStartIndex + self.PageCount
            }
            self.LoadingBegin = true
        }
    }
}
