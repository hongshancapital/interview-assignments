//
//  DataStorage.swift
//  SwiftUIDemo
//
//  Created by chenghao on 2022/5/11.
//

import Foundation

class DataStorage: ObservableObject {

    @Published var links: [DataModel] = []
    var dataProvider: DataProvider
    var url = Bundle.main.url(forResource: "data", withExtension: ".json")
    var page = 1
    var pageSize = 20
    var hasMore = true
    
    
    init(dataProvider: DataProvider) {
        self.dataProvider = dataProvider
    }
    
    func loadMore(isRefersh:Bool = false, completed:(()->(Void))? = nil) {
        if let url = url {
            dataProvider.request(url: url) { [self] (results:[DataModel]) in
                var begin = (page - 1) * pageSize
                begin = begin < 0 ? 0 : begin
                var last = page * pageSize
                last = last > results.count ? results.count : last
                
                if begin > results.count - 1 {
                    hasMore = false
                    return
                }
                
                DispatchQueue.main.async { [self] in
                    if isRefersh {
                        links = Array(results[begin..<last])
                    } else {
                        links.append(contentsOf: results[begin..<last])
                    }
                    completed?()
                }
                if last >= results.count {
                    hasMore = false
                }
                page += 1
            }
        } else {
            debugPrint("资源文件不存在")
        }
    }
    
    func refersh(completed:@escaping ()->(Void)) {
        page = 1
        hasMore = true
        loadMore(isRefersh: true) {
            completed()
        }
    }
}
