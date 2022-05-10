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
    var curPage : Int = 0
    
    init() {
        initMockData()
    }
    
    ///初始化mock数据
    private func initMockData() {
        guard let filePath = Bundle.main.path(forResource: "data", ofType: "txt") else {
            loadState = .failed
            return
        }
        do {
            let contents = try String(contentsOfFile: filePath)
            if let data = contents.data(using: String.Encoding.utf8) {
                let decoder = JSONDecoder()
                let dataDict = try decoder.decode(LHAppInfoRes.self, from: data)
                mockDatas = dataDict.results
            }
        } catch let error{
            print("error is \(error)")
            loadState = .failed
        }
        
    }
    
    ///点赞
    func like(app:LHAppInfo) {
        if let index = models.firstIndex(where: { $0.id == app.id }) {
            models[index].isLike = true
        }
    }
    
    ///底部刷新
    func loadMoreAppInfo() {
        loadState = .loading
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) { [weak self] in
            guard let self = `self` else {
                return
            }
            if self.mockDatas.count == 0 {
                self.loadState = .failed
                return
            }
            var dataPerPage = 15
            var curIndex = self.curPage * dataPerPage
            let oldModelsCount = self.models.count
            while dataPerPage > 0 && curIndex < self.mockDatas.count {
                let info = self.mockDatas[curIndex]
                curIndex = curIndex + 1
                dataPerPage = dataPerPage - 1
                self.models.append(info)
            }
            if oldModelsCount == self.models.count {
                self.loadState = .noData
            }else{
                self.curPage = self.curPage + 1
                self.loadState = .normal
            }
        }
    }
    
    ///头部刷新
    func loadRecentAppInfo() {
        loadState = .loading
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) { [weak self] in
            guard let self = `self` else {
                return
            }
            if self.mockDatas.count == 0 {
                self.loadState = .failed
                return
            }
            var dataPerPage = 15
            var curIndex = 0
            var newModels = [LHAppInfo]()
            while dataPerPage > 0 && curIndex < self.mockDatas.count {
                let info = self.mockDatas[curIndex]
                curIndex = curIndex + 1
                dataPerPage = dataPerPage - 1
                newModels.append(info)
            }
            self.curPage = 1
            self.loadState = .normal
            self.models = newModels
            
        }
    }
}
