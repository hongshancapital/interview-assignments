//
//  SearchItemView.swift
//  LocalServerApp
//
//  Created by 梁泽 on 2021/9/30.
//

import SwiftUI

struct SearchItemView: View {
    let model: CommodityItemModel
    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: 8) {
                Text(model.title)
                    .font(.body)
                
                Text(model.stockStatus.desc)
                    .font(.caption2)
                    .foregroundColor(Color.secondary)
            }
            
            Spacer()
            
            Text(model.priceText)
                .font(.caption)
                .foregroundColor(model.stockStatus == .instock ? .blue : .gray)
                .padding(.vertical, 6)
                .padding(.horizontal)
                .background(
                    Capsule(style: .continuous)
                        .foregroundColor(model.stockStatus == .instock ? .blue.opacity(0.05) : .gray.opacity(0.05))
                )
            
        }
    }
}

struct SearchItemView_Previews: PreviewProvider {
    static var previews: some View {
        let model = CommodityMock.mockPageOne()[0]
        
        Group {
            ForEach(model.items) { item in
                SearchItemView(model: item)
            }
        }
        .previewLayout(.sizeThatFits)
        .frame(width: 375)
        
    }
}
