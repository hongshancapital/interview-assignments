//
//  HomeViewModel.swift
//  InterviewDemo
//
//  Created by 陈凝 on 2022/8/1.
//

import Foundation

class HomeViewModel:ObservableObject{
    
    @Published var homeDataList:[Result]?
    
    @Published var homeDataListFetchCount:Int = 10
    
    // MARK: Main Fetch Data Func
    @MainActor
    func fetchHomeDataList(timeOut:TimeInterval = 8,isRefresh:Bool=false) async {
        let sessionConfig = URLSessionConfiguration.default
        sessionConfig.timeoutIntervalForRequest = timeOut
        
        if isRefresh{
            self.homeDataListFetchCount = 10
        }
        
        let jsonData = Task { () -> DataModel in
            let url = URL(string: "https://itunes.apple.com/search?entity=software&limit=\(homeDataListFetchCount)&term=chat")!
            let (data, _) = try await URLSession(configuration: sessionConfig).data(from: url)
                return try JSONDecoder().decode(DataModel.self, from: data)
            }
        do {
            self.homeDataList = try await jsonData.value.results
            self.homeDataListFetchCount+=10
        }
        catch{
            print("load data fail")
        }
    }
    
    // MARK: is now can load more data form server
    func canLoadMore() -> Bool{
        return !(self.homeDataListFetchCount >= 50)
    }
    
    // MARK: load more func
    func loadMore() async {
        if self.canLoadMore(){
            await self.fetchHomeDataList()
        }
    }
    
    // MARK: refresh all listData func ,reset the homeDataListFetchCount also,to load data form begining
    func refresh() async {
        await self.fetchHomeDataList(isRefresh: true)
    }
}

