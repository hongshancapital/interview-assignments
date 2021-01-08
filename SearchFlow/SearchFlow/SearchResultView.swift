//
//  SearchResultView.swift
//  SearchFlow
//
//  Created by evan on 2021/1/6.
//

import SwiftUI

struct SearchResultView : View {
    @EnvironmentObject var store: SearchStore
    private var series: [Brands.Series] { return store.state.searchResult }
    private var isEmpty: Bool { return store.state.isEmpty }
    
    var body: some View {
        ScrollView {
            if isEmpty {
                Text("No result")
                    .frame(height: 230)
                    .foregroundColor(Color.darkGray)
                    .font(.system(size: 17))
            } else {
                ForEach(series) { series in
                    VStack(spacing: 0) {
                        HStack {
                            Text(series.name)
                                .font(.system(size: 13, weight: .medium))
                                .foregroundColor(.darkGray)
                            Spacer()
                        }
                        .padding(EdgeInsets(top: 8, leading: 16, bottom: 8, trailing: 16))
                        ForEach(series.items) { item in
                            SearchItemRow(item: item)
                            Spacer()
                                .frame(height: 0.5)
                                .background(Color.darkestGray)
                        }
                    }
                }
            }
        }
    }
}
