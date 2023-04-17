//
//  AppListViewModel.swift
//  hongshandemo
//
//  Created by 林纪涛 on 2023/4/12.
//

import Foundation



// MVVM模式的ViewModel,负责数据管理
class AppListViewModel: ObservableObject{
    // 用@Published来声明状态属性,当状态属性改变时,SwiftUI会自动刷新UI
    @Published var dataSource: [AppModel] = []
    @Published var errorState: EmptyView.ErrorState = .none // 没有错误
    let totalPage = 3 // 列表数据为分页数据,模拟最大页数,实际中应该由接口数据下发最大页数
    var page = 0 // 列表接口分页索引,从0开始
    
    
    init() {
        // 初始化时请求首页数据
        self.refreshData()
    }
    // 计算是否已请求所有数据
    func hasMoreData() -> Bool{
        return self.page < self.totalPage
    }
    
    func processModelToList(_ responseModel: ResponseModel) -> Void {
        // 将新数据添加到数据源
        guard let newModels = responseModel.results else{
            return
        }
        self.dataSource.append(contentsOf: newModels)
    }
}

extension AppListViewModel{// 网络请求
    // 下拉刷新数据
    func refreshData() -> Void {
        self.requestListData(page:0) { data in
            guard let responseModel = data else{
                return
            }
            self.page = 0
            self.dataSource.removeAll()// 数据请求成功后,删除就数据
            self.processModelToList(responseModel)
        }
    }
    // 加载更多数据
    func loadMoreData() -> Void {
        if(self.page < self.totalPage){
            
            self.requestListData(page:self.page+1) { data in
                guard let responseModel = data else{
                    return
                }
                self.page += 1
                self.processModelToList(responseModel)
            }
        }
    }
    /*
     请求数据
        page: 分页接口,页码索引
        completeHandle: 请求结束的回调函数
     */
    
    func requestListData(page pageIndex: Int, _ completeHandle:@escaping(_ data: ResponseModel?) -> Void) -> Void{
        // 构建URL
        let baseUrl = "https://itunes.apple.com/search?entity=software&limit=50&term=chat"
        let urlStr = baseUrl + "&page=\(pageIndex)"
        let url:URL = URL(string: urlStr)!
        // 发送HTTP请求的的session对象
        let session = URLSession.shared
        // 构建请求request
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        // 发一个get请求
        let task = session.dataTask(with: request as URLRequest) {(
            data, response, error) in
            DispatchQueue.main.async {
                guard let _data = data, let _:URLResponse = response, error == nil else {
                    self.errorState = .netWorkError
                    completeHandle(nil)
                    return
                }
                let info = try? JSONDecoder().decode(ResponseModel.self, from: _data)
                if(info?.results?.count ?? 0 <= 0){
                    self.errorState = .noData
                }else{
                    self.errorState = .none
                }
                completeHandle(info)
            }
        }
        task.resume()
    }

}
//数据模型,通过json解析获得
class ResponseModel: Decodable {
    var resultCount: Int?
    var results: [AppModel]?
    enum CodingKeys: CodingKey {
        case resultCount
        case results
    }
    
    required init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        self.resultCount = try container.decodeIfPresent(Int.self, forKey: .resultCount)
        self.results = try container.decodeIfPresent([AppModel].self, forKey: .results)
    }
}
// cell单元格数据模型
class AppModel:  Decodable,ObservableObject,Identifiable{
    var artworkUrl60: String?// 图标
    var artworkUrl100: String?// 图标
    
    var description: String?// 详情
    var trackName: String?// 标题
    @Published var masked:   Bool = false // 模型中存在json不存在的字段,将导致解析失败.
    enum CodingKeys: CodingKey {
        case artworkUrl100
        case artworkUrl60
        case description
        case trackName
    }
    
    required init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        self.artworkUrl60 = try container.decode(String.self, forKey: .artworkUrl60)
        self.artworkUrl100 = try container.decode(String.self, forKey: .artworkUrl100)
        self.description = try container.decode(String.self, forKey: .description)
        self.trackName = try container.decode(String.self, forKey: .trackName)
    }
}
