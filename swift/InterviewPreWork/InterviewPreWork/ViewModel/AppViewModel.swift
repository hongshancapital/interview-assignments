//
//  AppViewModel.swift
//
//
//  Created by jeffy on 2022/5/2.
//

import SwiftUI

/// 用于请求失败时 .failure 参数需要一个遵守 Error 协议对象
struct LoadingError: Error {}


/// 返回数据
struct Response: Codable {
    var resultCount: Int
    var results: [AppItem]
}


/// 请求数据的类
final class AppViewModel: ObservableObject {
    
    /// @Published 在result 的值变化时通知接收者更新
    @Published var result:Result<Response, Error>? = nil
    var noMore:Bool = true
    
    /// 重写 get 方法, 只有在 result==.success 的时候返回值
    var source: Response? {
        try? result?.get()
    }
    
    /// 请求地址
    let url: URL = URL(string: "https://itunes.apple.com/search?entity=software&limit=50&term=chat")!
    
    /// 请求数据的方法
    func reload() -> Void {
        URLSession.shared.dataTask(with: url) { data, _, _ in
            // SwiftUI 中修改值应放在主线程
            DispatchQueue.main.async {
                if let data = data {
                    if let response = try? JSONDecoder().decode(Response.self, from: data) {
                        self.result = .success(response)
                        self.noMore = response.results.count >= response.resultCount
                        return
                    }
                }
                self.result = .failure(LoadingError())
                self.noMore = true
            }
        }.resume()
    }
    
    func loadMore() -> Void {
        
        //  MARK: 在 url 中添加相应参数才能实现翻页加载
        if self.noMore { return }
//        URLSession.shared.dataTask(with: url) { data, _, _ in
//            // SwiftUI 中修改值应放在主线程
//            DispatchQueue.main.async {
//                if let data = data {
//                    if let response = try? JSONDecoder().decode(Response.self, from: data) {
//                        self.result = .success(response)
//                        self.noMore = response.results.count >= response.resultCount
//                        return
//                    }
//                }
//                self.result = .failure(LoadingError())
//                self.noMore = true
//            }
//        }.resume()
    }
}

// MARK: - Fake

extension AppViewModel {
    
    /// fakeReload, the first 10 items
    func fakeReload() -> Void {
        DispatchQueue.main.asyncAfter(deadline: .now() + 2, execute: {
            if let path = Bundle.main.path(forResource: "response", ofType: "json") {
                let fileURL = URL(fileURLWithPath: path)
                if let data = try? Data(contentsOf: fileURL) {
                    if let response = try? JSONDecoder().decode(Response.self, from: data) {
                        let count = min(10, response.results.count)
                        let fakeResponse = Response(resultCount: response.resultCount, results: Array(response.results[0..<count]))
                        self.result = .success(fakeResponse)
                        self.noMore = count >= response.results.count
                        return
                    }
                }
                self.result = .failure(LoadingError())
                self.noMore = true
            }
        })
    }
    
    /// fakeLoadMore, 10 items more
    func fakeLoadMore() -> Void {
        
        if self.noMore { return }
        let fakeCount = self.source?.results.count ?? 0
        DispatchQueue.main.asyncAfter(deadline: .now() + 2, execute: {
            if let path = Bundle.main.path(forResource: "response", ofType: "json") {
                let fileURL = URL(fileURLWithPath: path)
                if let data = try? Data(contentsOf: fileURL) {
                    if let response = try? JSONDecoder().decode(Response.self, from: data) {
                        let count = min(10+fakeCount, response.results.count)
                        let fakeResponse = Response(resultCount: response.resultCount, results: Array(response.results[0..<count]))
                        self.result = .success(fakeResponse)
                        self.noMore = count >= response.results.count
                        return
                    }
                }
                self.result = .failure(LoadingError())
                self.noMore = true
            }
        })
    }
}
