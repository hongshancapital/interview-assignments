//
//  ContentViewModel.swift
//  SwiftUIDemo
//
//  Created by didi_qihang on 2022/3/13.
//

import Foundation

class ContentViewModel: ObservableObject {
    @Published var items: [Item] = []
    
    func getItemList() {
        self.items = ItemListApiService.shared.getItemList()?.items ?? []
    }
}
