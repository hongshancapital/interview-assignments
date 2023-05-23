//
//  AppDataManager.swift
//  SwiftDemo
//
//  Created by liuyang on 2023/3/5.
//

import Foundation

let AppCountOfOnePage = 10

class AppDataManager: ObservableObject {
    @Published var appDatas : [AppItemModel] = []
    
    //解析出来的所有model
    private var totalAppDatas: [AppItemModel] = []
    
    //下拉请求数据
    func requestAppData(complete:@escaping () -> Void) {
        DispatchQueue.global().async {
            //子线程读取本地json
            let totalAppDatas = self.loadLocalJson()
            if totalAppDatas.count == 0 {
                return
            }
            
            DispatchQueue.main.async {
                // 主线程更新数据
                var appDatas: [AppItemModel]
                if totalAppDatas.count <= AppCountOfOnePage {
                    appDatas = totalAppDatas
                }
                else {
                    //取前10个,剩下的用于上滑加载
                    appDatas = Array(totalAppDatas[0..<AppCountOfOnePage])
                }
                
                self.appDatas = appDatas
                self.totalAppDatas = totalAppDatas
                
                complete()
            }
        }
    }
    
    //上滑加载更多数据
    func requestMoreAppData(complete:@escaping (_ canLoadMore: Bool) -> Void) {
        var canLoadMore = false
        if self.totalAppDatas.count == 0 {
            complete(canLoadMore)
            return
        }
        
        //延迟1s模拟请求
        DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 1.0, execute: {
            if self.totalAppDatas.count > self.appDatas.count {
                let start = self.appDatas.count
                let end = self.totalAppDatas.count - self.appDatas.count > AppCountOfOnePage ? self.appDatas.count + AppCountOfOnePage : self.totalAppDatas.count
                
                self.appDatas.append(contentsOf: Array(self.totalAppDatas[start..<end]))
                
                canLoadMore = true
            }
            
            complete(canLoadMore)
        })
    }
    
    //加载本地json
    func loadLocalJson() -> Array<AppItemModel> {
        var jsonDic: NSDictionary = NSDictionary()
        guard let filePath = Bundle.main.path(forResource: "AppData", ofType: "json") else { return [] }
        
        do {
            let data = try Data(contentsOf: URL(fileURLWithPath: filePath))
            jsonDic = try JSONSerialization.jsonObject(with: data, options: JSONSerialization.ReadingOptions.mutableContainers) as! NSDictionary
        } catch {
            print("Data decode error")
        }
        
        let results = jsonDic["results"] as! NSArray
        let AppItemModels: [AppItemModel] = ([AppItemModel].deserialize(from: results) as? [AppItemModel])!
        
        return AppItemModels
    }
}
