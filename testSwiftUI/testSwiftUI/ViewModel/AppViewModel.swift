//
//  AppViewModel.swift
//  testSwiftUI
//
//  Created by pchen on 2023/4/5.
//

import Foundation
import Combine

enum ListPageState {
    case isInitState
    case contentState
    case ErrorState
    case emptyState
}

@MainActor
public class AppViewModel: ObservableObject {
    
    private let urlString = "https://itunes.apple.com/search?entity=software&limit=50&term=chat"
    
    @Published var appModels: [AppModel] = []
    @Published  var listPullState: ListPullStateEnum = .none
    @Published  var pageState: ListPageState = .isInitState
    
    private var nextIndex = 0
    private let pageNumber = 20
    
    func refreshApp() async {
        
        do {
            try await self.refreshAppModels()
            self.listPullState = .none
            self.pageState = .contentState
        }catch {
            print(#function, error)
            self.listPullState = .none
            self.pageState = .ErrorState
        }
    }
    
    func refreshAppModels() async throws{
        
        do {
            let resonseMode = try await NetworkService<AppResonseModel>.fetchAppData(urlString)
            self.nextIndex = 1;
            self.appModels = self.getPageIndexArray(pageIndex: 0, array: resonseMode.results) as! [AppModel]
        }catch {
            throw error
        }
    }
    
    func loadMore() {
        
        self.listPullState = .isLoadMoreing
        self.nextPageAppModels { Loadcount, error in
            if (error == nil && Loadcount == 0) {
                self.listPullState = .noMoreState
            }else {
                self.listPullState = .none
            }
        }
    }

    func nextPageAppModels() async throws -> Int {
        
        do {
            let resonseMode = try await NetworkService<AppResonseModel>.fetchAppData(urlString)
            
            let models = self.getPageIndexArray(pageIndex: 0, array: resonseMode.results) as! [AppModel]
            self.appModels.append(contentsOf: models[0..<models.count])
            self.nextIndex += 1
            return models.count
        }catch {
            throw error
        }
    }
    
    func nextPageAppModels(complete: @escaping (Int, Error?) -> Void) {
    
        NetworkService<AppResonseModel>.fetchAppData(urlString) { [weak self] model,error  in
            guard let self = self, let model = model else {
                DispatchQueue.main.async {complete(0, error)}
                return
            }
            
            let models = self.getPageIndexArray(pageIndex: self.nextIndex, array: model.results) as! [AppModel]
            
            DispatchQueue.main.async {
                
                self.appModels.append(contentsOf: models[0..<models.count])
                complete(models.count, error)
                self.nextIndex += 1
            }
        }
    }
    
    internal func getPageIndexArray(pageIndex: Int, array:[Any]) -> [Any] {
        
        let startIndex = pageIndex * self.pageNumber
        var endIndex = startIndex + self.pageNumber
        
        guard startIndex >= 0, startIndex < array.count else {
            return []
        }
        
        endIndex = array.count > endIndex ? endIndex : array.count
        let pageArray = array[startIndex..<endIndex]
        
        return Array(pageArray)
    }
}
