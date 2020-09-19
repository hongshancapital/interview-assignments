//
//  ElementView.swift
//  SearchFlow
//
//  Created by evan on 2020/9/19.
//  Copyright © 2020 evan. All rights reserved.
//

import SwiftUI

struct ElementView: View {
    private let titleFont = Font.system(size: 16, weight: .semibold)
    private let subtitleFont = Font.system(size: 13)
    private let titleColor = Color.black
    private let subtitleColor = Color.gray
    
    var element: Element
    
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text(element.name).font(titleFont)
                Text(element.status.title).font(subtitleFont).foregroundColor(subtitleColor)
            }
            Spacer()
            Text(element.price.currencyFormatted())
                .foregroundColor(priceColor)
                .padding(.horizontal, Spacing.base)
                .padding(.vertical, Spacing.micro)
                .background(priceColor.opacity(0.2))
                .cornerRadius(Spacing.tiny)
        }
        .padding(.vertical, Spacing.micro)
        .padding(.horizontal, Spacing.threeQuarter)
    }
    
    var priceColor: Color {
        switch element.status {
        case .inStock: return .blue
        case .outStock: return .gray
        }
    }
}
