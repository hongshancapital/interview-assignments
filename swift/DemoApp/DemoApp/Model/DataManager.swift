//
//  DataManager.swift
//  DemoApp
//
//  Created by liang on 2022/5/19.
//

import Foundation
import UIKit

class DataManager: ObservableObject {
    @Published var appList: [AppModel] = []
    var dataProvider: DataProvider
    @Published var hasMore: Bool = true
    let fetchCountOnce = 20;
    
    init() {
//        self.dataProvider = FileDataProvider()
        self.dataProvider = NetworkDataProvider()
    }
    
    func fetchMore(_ completed: @escaping ()-> Void) {
        if hasMore {
            dataProvider.fetchAppModel(from: appList.last, count: fetchCountOnce) {[self] (modelList, err) in
                DispatchQueue.main.async {
                    if let modelList = modelList {
                        appList.append(contentsOf: modelList)
                        if modelList.count < fetchCountOnce {
                            hasMore = false
                        }
                        completed()
                    } else {
                        hasMore = false
                        completed()
                    }
                }
            }
        }
    }
    
    func refresh(_ completed: @escaping ()-> Void) {
        dataProvider.fetchAppModel(from: nil, count: fetchCountOnce) {[self] (modelList, err) in
            DispatchQueue.main.async {
                if let modelList = modelList {
                    appList = modelList
                    if modelList.count < fetchCountOnce {
                        hasMore = false
                    } else {
                        hasMore = true
                    }
                    completed()
                } else {
                    // TODO: 处理异常
                }
            }
        }
    }
}
