//
//  LHViewModel.swift
//  LHAppListDemo
//
//  Created by lzh on 2022/3/26.
//

import Foundation
import Combine

enum LHViewModelLoadDataState {
    case normal
    case loading
    case failed
    case noData
}

class LHViewModel : ObservableObject {
    
    ///列表数据list
    @Published var models : [LHAppInfo] = [LHAppInfo]()
    
    ///数据头底部加载状态
    @Published var loadState : LHViewModelLoadDataState = .normal
    
    ///mock的数据list
    var mockDatas : [LHAppInfo] = [LHAppInfo]()
    
    ///当前分页拉取数据页标
    var curOffset : Int = 0
    
    let dataPerPage = 15
    
    ///点赞
    func like(app:LHAppInfo) {
        if let index = models.firstIndex(where: { $0.id == app.id }) {
            models[index].isLike = true
        }
    }
    
    ///底部刷新
    func loadMoreAppInfo(){
        loadState = .loading
        Task.detached(priority:.high) { [weak self] in
            guard let self = `self` else {
                return
            }
            let result = await request(LHAppAPI.searchApp(entity: "software", limit: self.dataPerPage, term: "chat", offset: self.curOffset), modelType: LHAppInfoRes.self)
            if let resp = result.resp {
                DispatchQueue.main.async {
                    if self.curOffset > 45 {
                        self.loadState = .noData
                        return
                    }
                    let fliterRes = resp.results.filter { info in
                        return self.models.firstIndex(where:{ $0.id == info.id}) == nil
                    }
                    self.curOffset += fliterRes.count
                    self.loadState = .normal
                    self.models.append(contentsOf: fliterRes)
                }
            } else if let err = result.err {
                
                DispatchQueue.main.async {
                    self.loadState = .failed
                }
                print("异步请求获取失败__\(err)")
            }
        }
    }
    
    ///头部刷新
    func loadRecentAppInfo() {
        loadState = .loading
        self.curOffset = 0
        Task.detached(priority: .high) { [weak self] in
            guard let self = `self` else {
                return
            }
            let result = await request(LHAppAPI.searchApp(entity: "software", limit: self.dataPerPage, term: "chat", offset: self.curOffset), modelType: LHAppInfoRes.self)
            if let resp = result.resp {
                DispatchQueue.main.async {
                    self.curOffset += resp.resultCount
                    self.loadState = .normal
                    var newRes = [LHAppInfo]()
                    for var info in resp.results {
                        if let index = self.models.firstIndex(where: {$0.id == info.id}) {
                            let existInfo = self.models[index]
                            info.updateWith(info: existInfo)
                        }
                        newRes.append(info)
                    }
                    self.models = newRes
                }
            } else if let err = result.err {
                print("异步请求获取失败__\(err)")
            }
        }
    }
}
