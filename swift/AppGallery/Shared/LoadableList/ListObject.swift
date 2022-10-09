//
//  ListObject.swift
//  AppGallery
//
//  Created by X Tommy on 2022/8/11.
//

import Foundation

protocol ListObject: ObservableObject {
    
    associatedtype Output: Identifiable, Equatable
    
    var loadState: LoadableState<[Output]> { get }
    
    var isFetching: Bool { get }
    
    func load() async
    
    func refresh() async
    
    func loadMore() async
    
}
