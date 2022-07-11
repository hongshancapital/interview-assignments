//
//  ViewModel.swift
//  DemoApp
//
//  Created by Gao on 2022/7/10.
//

import Foundation
import Combine
import SwiftUI

class ViewModel: ObservableObject {
    @Published var data: [AppModel] = []
    @Published var favoriteArr: [Bool] = []
    @Published var loadingState: LoadingState = .Loading
    @Published var errorMessage: String?
    let pageSize: Int = 20
    var loadMoreSubject = CurrentValueSubject<Void, Error>(())
    var refreshSubject = PassthroughSubject<Void, Never>()
    var favoriteSubject = PassthroughSubject<(Int, Bool), Never>()
    var cancellable = Set<AnyCancellable>()
    var dataService = ApiService()
    
    init(){
        addLoadAppsSubscription()
        addRefreshSubscription()
    }
    
    func isLoadMore(app: AppModel){
        if let last = data.last {
            if app.id == last.id{
                loadMoreSubject.send()
            }
        }
    }
    
   func addLoadAppsSubscription() {
       self.dataService.getListData()
           .flatMap{$0.publisher}
           .collect(pageSize)
           .zip(self.loadMoreSubject)
           .delay(for: .seconds(1), scheduler: DispatchQueue.global())
           .receive(on: RunLoop.main)
           .handleEvents (
            receiveOutput: { [weak self] _ in
               self?.loadingState = .LoadMore
           })
           .sink { [weak self] completoin in
               switch completoin {
               case .finished:
                   self?.loadingState = .NoMoreData
                   break;
               case .failure(let error):
                   print("error：\(error.localizedDescription)")
                   break;
               }
           } receiveValue: { [weak self] value in
               if value.0.count > 0 {
                   self?.data.append(contentsOf: value.0)
               }
           }
           .store(in: &cancellable)
   }
    
    func addRefreshSubscription(){
        refreshSubject.debounce(for: .seconds(1), scheduler: RunLoop.main)
            .sink{ [weak self] _ in
                self?.errorMessage = nil
                self?.loadingState = .Loading
                self?.data.removeAll()
                self?.addLoadAppsSubscription()
            }
            .store(in: &cancellable)
    }
}
