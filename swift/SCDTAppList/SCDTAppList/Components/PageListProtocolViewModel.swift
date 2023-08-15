//
//  PageListViewModelProtocol.swift
//  SCDTAppList
//
//  Created by freeblow on 2023/2/15.
//

import SwiftUI
import Foundation

protocol PageListProtocolViewModel: ObservableObject{
    associatedtype T : Identifiable,Equatable
    
    var loadStatus: PageLoadingStatus { get }
    
    var isHaveMore: Bool { get }
    
    var datas: [T] { get }
    
    var error: DataError {get}
    
    var page: Int {get}
    
    var pageSize: Int {get}
    
    func updateLoadStatus(status: PageLoadingStatus)
    
    func refresh() async -> Void
    
    func loadMore() async -> Void
}
