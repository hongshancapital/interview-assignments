//
//  ApplyListViewModel.swift
//  interview-assignments
//
//  Created by Pedro Pei on 2022/5/23.
//

import Foundation

class ApplyListViewModel: ObservableObject{
    
    let service = FileService()
    @Published var appListData: [ApplyListModel.Results] = []
    
    @Published var dataSource: [ApplyListModel.Results] = []
    
    func fetchData() {
        if let data = service.fetchJsonFile(){
            appListData = data.results
        }
    }
}

extension Array{
    
    subscript (safe index: Index) -> Iterator.Element? {
        return indices.contains(index) ? self[index] : nil
    }
}
