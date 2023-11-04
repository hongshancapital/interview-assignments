//
//  DataManager.swift
//  demo
//
//  Created by 朱伟 on 2022/2/11.
//

import Foundation
import Combine

class DataManager : ObservableObject {
    @Published var list = [ShowItem](){
        didSet {
            subscribeToChanges()
        }
    }
    
    private var anyCancellable: AnyCancellable?

    init(){
        subscribeToChanges()
    }
    
    func subscribeToChanges() {
        anyCancellable = list
             .publisher
             .flatMap { todo in todo.objectWillChange }
             .sink { [weak self] in
                 self?.objectWillChange.send()
             }
     }

}

class ShowItem: ObservableObject,Identifiable {
    var id: UUID = UUID()
    var image : String
    var title: String
    var des : String
    @Published var like: Bool
    
    init(image: String, title : String, des: String){
        self.image = image
        self.title = title
        self.des = des
        self.like = false
    }
}

struct DataModel : Codable {
    var resultCount:Int
    var results: [ItemModel]
}

struct ItemModel : Codable {
    var trackCensoredName: String
    var artworkUrl60: String
    var description: String
}
