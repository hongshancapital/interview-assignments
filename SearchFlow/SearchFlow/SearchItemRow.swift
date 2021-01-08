//
//  SearchItemRow.swift
//  SearchFlow
//
//  Created by evan on 2021/1/6.
//

import SwiftUI

struct SearchItemRow : View {
    let item: Brands.Item
    
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text(item.name)
                    .font(.system(size: 15, weight: .semibold))
                    .foregroundColor(.darkestGray)
                Text(item.status.text)
                    .font(.system(size: 13))
                    .foregroundColor(.darkerGray)
            }
            Spacer()
            Text(item.price.currencyFormatted(showDecimals: true))
                .font(.system(size: 13))
                .foregroundColor(item.status.color)
                .padding(
                    EdgeInsets(top: 4, leading: 8, bottom: 4, trailing: 8)
                )
                .background(item.status.color.opacity(0.3))
                .cornerRadius(8)
        }
        .padding(
            EdgeInsets(top: 12, leading: 16, bottom: 12, trailing: 16)
        )
        .background(Color.white)
    }
}
