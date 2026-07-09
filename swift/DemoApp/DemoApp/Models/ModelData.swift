//
//  ModelData.swift
//  DemoApp
//
//  Created by 王俊 on 2022/8/30.
//

import Foundation
import Combine

final class ModelData: ObservableObject {
    @Published var appInfos : [AppInfo] = []
    @Published var allDataIsLoaded : Bool = false
    @Published var favoriteAppIds : Set<Int> = []   // 收藏的app的ID单独放这里，以免下拉刷新时覆盖掉数据
    @Published var error : Error?
    
    init() {
        reloadData()
    }
    
    func reloadData() {
        self.error = nil
        self.allDataIsLoaded = false
        guard let url = URL(string: "https://itunes.apple.com/search?entity=software&limit=25&term=chat") else {return}
        URLSession.shared.dataTask(with: url) { data, response, error in
            if let error = error {
                DispatchQueue.main.async {
                    self.error = error
                }
            } else if let data = data {
                do {
                    let decoder = JSONDecoder()
                    let responseInfo : ResponseInfo = try decoder.decode(ResponseInfo.self, from: data)
                    DispatchQueue.main.async {
                        self.appInfos = responseInfo.results
                    }
                } catch {
                    DispatchQueue.main.async {
                        self.error = error
                    }
                }
            }
        }.resume()
    }
    
    func loadMoreDataIfNeeded() {
        if allDataIsLoaded {
            return
        }
        guard let url = URL(string: "https://itunes.apple.com/search?entity=software&limit=50&term=chat") else {return}
        URLSession.shared.dataTask(with: url) { data, response, error in
            if let error = error {
                DispatchQueue.main.async {
                    self.error = error
                }
            } else if let data = data {
                do {
                    let decoder = JSONDecoder()
                    let responseInfo : ResponseInfo = try decoder.decode(ResponseInfo.self, from: data)
                    DispatchQueue.main.async {
                        self.appInfos = responseInfo.results
                        self.allDataIsLoaded = true
                    }
                } catch {
                    DispatchQueue.main.async {
                        self.error = error
                    }
                }
            }
        }.resume()
    }
}
