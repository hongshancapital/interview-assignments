//
//  ViewModel.swift
//  DemoApp
//
//  Created by Gao on 2022/7/10.
//

import Foundation


class ViewModel: ObservableObject {
    var hasNoMoreData: Bool = false
    var isLoading: Bool = false
    var isRefreshing: Bool = false
    var appList: [AppModel] = []
    var pageIndex: Int = 1;
    var pageSize: Int = 20;
    
    func loadData(){
        
    }
    
    func hasMore(){
        
    }
    
    
    
}
