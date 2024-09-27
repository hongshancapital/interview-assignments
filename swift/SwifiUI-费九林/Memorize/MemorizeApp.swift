//
//  MemorizeApp.swift
//  Memorize
//
//  Created by 费九林 on 2022/9/26.
//

import SwiftUI

@main
struct MemorizeApp: App {
    var body: some Scene {
        WindowGroup {
            ItemListView(viewModel: ItemMemoryGame())
        }
    }
}
