//
//  BusinessProvider.swift
//  AuditionDemo
//
//  Created by dian bao on 2022/7/28.
//

import SwiftUI
import Combine

//MARK: -单条业务数据
struct Item{                        //每一条目的数据，每一条服务器返回有很多属性，以下仅保存我们的Demo需要的几个和展示相关的属性
    var trackId:Int64 = 0           //每一条目的唯一标识Id
    var trackName:String = ""       //每一条目的标题
    var description:String = ""     //每一条目的描述
    
    var artworkUrl60:String = ""    //60X60图标，优先在高于或等于320x480且低于640x960分辨率设备展示
    var artworkUrl100:String = ""   //100X100图标，优先在高于或等于640x960且低于1242x2208分辨率设备展示
    var artworkUrl512:String = ""   //512X512图标，优先在高于或等于1242x2208及以上分辨率设备展示
}

//MARK: -业务数据汇总
struct Result{                      //业务数据
    var resultCount:Int = 0         //展示条数数量
    var results:[Item] = []         //展示条目集合
}

//MARK: -业务数据提供者
class BusinessProvider: ObservableObject {
    //MARK: - 属性
    @Published
    var state:LoadContentState = LoadContentState.LOADING               //加载状态
    @Published
    var needWarn:Bool = false                                           //是否需要警告
    var plManager:PageLoaderManager = PageLoaderManager.sharedInstance  //分页加载器
    var result:Result? = nil                                            //业务数据
    var description:String = ""                                         //错误描述
    var LoadErrorCode:Int = 0                                           //加载错误码
    private var cancellable: AnyCancellable?                            //发布者
    private let nrLoader = NetResourceLoader.sharedInstance             //网络资源加载器
    
    //MARK: - 方法
    func HandleLoadingFail(_ errorcode:Int,_ description:String)        //处理加载失败错误
    {
        self.LoadErrorCode = errorcode
        self.state = .FAIL
        self.description = description
        
        let dict = UserDefaults.standard.object(forKey: "cachedDict") as? NSDictionary      //如果有缓存数据本次先用缓存数据
        if( dict != nil )
        {
            self.handleDict(dict!)
        }
    }
    
    func loadData(sUrl:String) {                                        //加载业务数据
        let url = URL.init(string: sUrl)
        if( url == nil )                                                //如果网络URL解析不对则直接报错
        {
            DispatchQueue.main.async {
                self.state = LoadContentState.FAIL
                self.needWarn = true
                self.description = "URL解析错误！"
            }
            return
        }

        self.cancellable = nrLoader.dictPublisher(for: url!)
            .sink(receiveCompletion: { completion in
                DispatchQueue.main.async {
                    switch completion {
                    case .finished:
                        self.state = LoadContentState.SUCCESS
                    case .failure(let error):
                        
                        let UrlError = error as? URLError
                        let r = UrlError?.code
                        if( r != nil )
                        {
                            self.HandleLoadingFail(r!.rawValue,error.localizedDescription)
                        }
                        self.needWarn = true
                        print("错误：\(error.localizedDescription)")
                    }
                }
            }, receiveValue: { dict in
                
                self.handleDict(dict)
                UserDefaults.standard.set(dict, forKey: "cachedDict")
                
            })
    }
    
    func handleDict(_ dict:NSDictionary)                                 //将较为原始的业务数据转换为更核心的业务数据，并做一定的数据有效性核查工作（业务数据已经封装成字典形式，根据网络加载或者本地缓存的更原始数据解析得来）
    {
        var _result = Result()
        //尝试读取条目总数
        let resultCount = dict.object(forKey: "resultCount") as? Int
        //尝试读取所有条目数据
        let results = dict.object(forKey: "results") as? [NSDictionary]
        
        if( resultCount != nil )
        {
            _result.resultCount = resultCount!
        }
        
        if( results != nil )
        {
            for _item in results!{
                var item = Item()
                item.trackId = (_item.object(forKey: "trackId") as? Int64) != nil ? (_item.object(forKey: "trackId") as! Int64) : 0
                item.trackName = (_item.object(forKey: "trackName") as? String) != nil ? (_item.object(forKey: "trackName") as! String) : ""
                item.description = (_item.object(forKey: "description") as? String) != nil ? (_item.object(forKey: "description") as! String) : ""
                item.artworkUrl60 = (_item.object(forKey: "artworkUrl60") as? String) != nil ? (_item.object(forKey: "artworkUrl60") as! String) : ""
                item.artworkUrl100 = (_item.object(forKey: "artworkUrl100") as? String) != nil ? (_item.object(forKey: "artworkUrl100") as! String) : ""
                item.artworkUrl512 = (_item.object(forKey: "artworkUrl512") as? String) != nil ? (_item.object(forKey: "artworkUrl512") as! String) : ""
                
                _result.results.append(item)
            }
        }
        
        resetPageLoader(_result.results)
        self.result = _result
        
        if( _result.resultCount != _result.results.count )
        {
            self.needWarn = true
            self.description = "获取的总数量和实际数据总数量不符！"
        }
    }
    
    func resetPageLoader(_ results:[Item])                  //重置分页加载器数据
    {
        self.plManager.PageEndIndex = 0
        self.plManager.LoadingFinish = false
        self.plManager.LoadingBegin = false
        self.plManager.results = results
        self.plManager.LoadPageData()
    }
    
}
