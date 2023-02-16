//
//  SCDTAppListViewModel.swift
//  SCDTAppListViewModel
//
//  Created by freeblow on 2023/2/15.
//

import SwiftUI
import UIKit


@MainActor final class SCDTAppListViewModel : ObservableObject{

    @Published private(set) var loadStatus: PageLoadingStatus = .first
    @Published private(set) var datas: [AppProductModel] = []
    @Published private(set) var error: DataError = .none
    
    @Published private(set) var isHaveMore: Bool = true
    
    private (set) var page: Int = 1
    private (set) var pageSize: Int = 20
    
    private (set) var isForcePageSize = true
    
    //MARK: Init Method
    
    init(){
        initPageSize()
    }
    
    init(pageSize: Int, isForce: Bool = true){
        self.pageSize = pageSize
        self.isForcePageSize = isForce
        initPageSize()
    }
    
    
    //MARK:  Public Method
    func favoriteStatusSync(item: AppProductModel) async {
        guard let index = self.datas.firstIndex(of: item) else {
            fatalError("Can't find the tapped product , plese continue `toggle favorite button` action..")
        }
        self.datas[index].isFavorite.toggle()
    }
    
    
    //MARK:  Private Method
    private func initPageSize(){
        // page size could not be 0
        
        if isForcePageSize && pageSize <= 0 {
            pageSize = 20
        }
        
        if !isForcePageSize && pageSize <= 0{
            loadStatus = .empty
        }
    }
    
    private func loadData(isRefresh: Bool) async -> Void{
        guard loadStatus != .loading else { return }
        
        ///clean error status
        self.error = .none
        
        if isRefresh {
            page = 1
            self.datas.removeAll()
            if loadStatus != .first{
                loadStatus = .refresh
            }
        }else{
            if(!isHaveMore){ return }
            loadStatus = .loading
        }
        
        do{
            let mPage = isRefresh ? page : (page + 1)
            let tItems = try await DataProvider.shared.items(mPage, pageSize)
            
            if tItems.1 != nil{
                error = tItems.1 ?? .none
                
                if page != 1{
                    loadStatus = .stop
                }else{
                    loadStatus = .error
                }
                
            }else{
                if (loadStatus ==  .first || loadStatus == .refresh) && tItems.0.count == 0{
                    loadStatus = .empty
                    return
                }
                
                self.datas.append(contentsOf: tItems.0)
                
                //add page and change load status
                if !isRefresh {
                    page += 1
                }
                
                if tItems.0.count < pageSize{
                    loadStatus = .finish
                    self.isHaveMore = false
                }else{
                    loadStatus = .stop
                }
            }
            
        }catch{
            ///catch exception , then return
            self.error =  .loadData(msg: error.localizedDescription)
            if page != 1{
                loadStatus = .stop
            }else{
                loadStatus = .error
            }
        }
    }
    
}

//MARK: PageListProtocolViewModel
extension SCDTAppListViewModel: PageListProtocolViewModel{
   
    typealias T = AppProductModel
    
    func loadMore() async {
        await loadData(isRefresh: false)
    }
    
    func refresh() async  {
        await loadData(isRefresh: true)
    }
    
    /// update page load status
    func updateLoadStatus(status: PageLoadingStatus){
        self.loadStatus = status
    }
    
}
