//
//  ViewModel.swift
//  Demo
//
//  Created by lajsf on 2022/10/27.
//

import Foundation

class DataModel: Identifiable {
    var id = UUID()
    var icon: String
    var title: String
    var detail: String
    var like: Bool
    
    init(icon: String, title: String, detail: String, like: Bool) {
        self.icon = icon
        self.title = title
        self.detail = detail
        self.like = like
    }
}

class ViewModel: ObservableObject {
    
    @Published var data: [DataModel] = source1
    @Published var noMore  = false
    @Published var isLoading = false
    private var size = 10
    
    func refreshFetch() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            self.data = source1
            self.noMore = false
        }
    }
    
    func loadMore() {
        self.isLoading = true
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            self.isLoading = false
            let count = source2.count
            if count > 0{
                self.data.append(contentsOf: source2)
            }
            if (count < self.size){
                self.noMore = true
            }
        }
    }
    
    func updateItem(_ item: DataModel){
        var temp:[DataModel] = []
        for model in data {
            if (model.id == item.id){
                model.like.toggle()
                temp.append(model)
            }else{
                temp.append(model)
            }
        }
        data = temp
    }
}
