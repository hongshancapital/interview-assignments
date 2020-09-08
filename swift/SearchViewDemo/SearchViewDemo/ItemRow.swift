//
//  ItemRow.swift
//  SearchDemo
//
//  Created by Mason Sun on 2020/9/8.
//

import SwiftUI

struct ItemRow: View {
    var item: Item

    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text(item.name)
                    .font(.system(size: 17, weight: .bold))
                Text(item.status.displayText)
                    .font(.system(size: 14, weight: .regular))
                    .foregroundColor(.gray)
            }
            Spacer()
            Text(item.price.formattedAmount)
                .foregroundColor(priceColor)
                .padding(.horizontal, 16)
                .padding(.vertical, 4)
                .background(priceColor.opacity(0.1))
                .cornerRadius(12)
        }
        .padding(.vertical, 8)
        .padding(.horizontal, 12)
    }

    var priceColor: Color {
        switch item.status {
        case .inStock:
            return .blue
        case .outStock:
            return .gray
        }
    }
}

struct ItemRow_Previews: PreviewProvider {
    static var previews: some View {
        ItemRow(item: .defaultValue)
    }
}
