//
//  SearchItemView.swift
//  SwiftUI_SearchExample
//
//  Created by Hongwei Chen on 2021/10/21.
//

import SwiftUI

/// SearchList的Cell
struct SearchItemView: View {
    
    /// Item数据
    var item: SearchItemModel
    
    /// Render
    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: 10) {
                Text(item.name).font(.body)
                Text(item.stock.value).font(.callout).foregroundColor(Color.secondary)
            }
            Spacer()
            Text(item.priceText)
                .font(.caption)
                .foregroundColor(item.stock == .instock ? .blue : .gray)
                .padding(.horizontal, 5)
                .padding(.vertical, 2)
                .background(
                    /// 圆角背景
                    Capsule(style: .continuous)
                        .foregroundColor(item.stock == .instock ? .blue.opacity(0.1) : .gray.opacity((0.1)))
                )
        }
    }
    
}
