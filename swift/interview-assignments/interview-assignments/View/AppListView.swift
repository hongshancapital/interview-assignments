//
//  AppListView.swift
//  interview-assignments
//
//  Created by Pedro Pei on 2022/5/23.
//

import SwiftUI

struct AppListView: View {
    
    let items: [ApplyListModel.Results]
    
    var body: some View {
        VStack {
            ForEach(items, id: \.self) { item in
                AppItemView(results: item)
            }
        }
    }
}
