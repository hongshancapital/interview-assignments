//
//  SearchResultView.swift
//  HRAssinment
//
//  Created by Henry Zhang on 2021/10/21.
//

import SwiftUI

struct SearchResultView : View {
    let model:CommodityItemModel
    
    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: 0) {
                Text(model.title)
                    .font(.body)
                
                Text(model.stockStatus.desc)
                    .font(.caption2)
                    .foregroundColor(Color.secondary)
            }
            
            Spacer()
            
            Text(model.priceString)
                .font(.caption)
                .foregroundColor(model.stockStatus == .instock ? .blue : .gray)
                .padding(.vertical,6)
                .padding(.horizontal)
                .background(
                    Capsule(style: .continuous)
                        .foregroundColor(model.stockStatus == .instock ? .blue.opacity(0.1) : .gray.opacity(0.1))
                )
        }
    }
}

struct SearchResultView_Previews: PreviewProvider {
    static var previews: some View {
        let model = CommodityMock.mockPageOne()[0]
        
        Group {
            ForEach(model.items) { item in
                SearchResultView(model: item)
            }
        }
        .previewLayout(.sizeThatFits)
        .frame(width: 375)
    }
}
